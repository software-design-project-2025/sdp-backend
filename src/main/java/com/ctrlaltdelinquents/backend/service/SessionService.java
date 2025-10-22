package com.ctrlaltdelinquents.backend.service;

import com.ctrlaltdelinquents.backend.dto.SessionParticipantDTO;
import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.model.SessionMembers;
import com.ctrlaltdelinquents.backend.repo.SessionMembersRepo;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepo sessionRepository;

    @Autowired
    private SessionMembersRepo sessionMembersRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Integer id) {
        return sessionRepository.findById(id);
    }

    public List<Session> getSessionsByCreator(String creatorId) {
        return sessionRepository.findByCreatorid(creatorId);
    }

    public List<Session> getSessionsByGroup(int groupId) {
        return sessionRepository.findByGroupid(groupId);
    }

    @Transactional
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session updateSession(Integer id, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));

        session.setTitle(sessionDetails.getTitle());
        session.setStartTime(sessionDetails.getStartTime());
        session.setEndTime(sessionDetails.getEndTime());
        session.setStatus(sessionDetails.getStatus());
        session.setLocation(sessionDetails.getLocation());
        session.setDescription(sessionDetails.getDescription());
        session.setCreatorid(sessionDetails.getCreatorid());
        session.setGroupid(sessionDetails.getGroupid());

        return sessionRepository.save(session);
    }

    public void deleteSession(Integer id) {
        sessionRepository.deleteById(id);
    }

    // Service method to find sessions using filters
    public List<Session> findSessions(String search, LocalDateTime startDate, LocalDateTime endDate) {
        // Create a Specification for filtering
        Specification<Session> spec = (root, query, cb) -> cb.conjunction();

        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%")
                    )
            );
        }

        if (startDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("startTime"), startDate)
            );
        }

        if (endDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("endTime"), endDate)
            );
        }

        // Only show scheduled or in-progress sessions in the main discover list
        spec = spec.and((root, query, cb) ->
                root.get("status").in("scheduled", "in_progress")
        );

        // Sort by start time, soonest first
        return sessionRepository.findAll(spec, org.springframework.data.domain.Sort.by("startTime").ascending());
    }

    // --- CRUD OPERATIONS ---

    public void deleteSession(Integer id, String userId) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));

        // Security Check: Only creator can delete
        if (!session.getCreatorid().equals(userId)) {
            throw new RuntimeException("Only the session creator can delete this session.");
        }

        // Must delete members first due to foreign key constraints
        sessionMembersRepo.deleteBySessionid(id); // (Need to add this method to SessionMembersRepo)

        sessionRepository.deleteById(id);
    }

    // --- TAB-SPECIFIC GETTERS ---

    public List<Session> getUpcomingSessions(String userId) {
        return sessionRepository.findUpcomingSessions(userId);
    }

    public List<Session> getPastSessions(String userId) {
        return sessionRepository.findPastSessions(userId);
    }

    // --- CREATOR-ONLY ACTIONS ---

    @Transactional
    public Session extendSession(Integer sessionId, String userId, LocalDateTime newEndTime) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        // 1. Security Check: Is user the creator?
        if (!session.getCreatorid().equals(userId)) {
            throw new RuntimeException("Only the session creator can extend the time.");
        }

        // 2. Logic Check: Is new time after current end time?
        if (newEndTime.isBefore(session.getEndTime())) {
            throw new RuntimeException("New end time must be after the current end time.");
        }

        // 3. Logic Check: Is session active?
        if (!"in_progress".equals(session.getStatus()) && !"scheduled".equals(session.getStatus())) {
            throw new RuntimeException("Can only extend 'scheduled' or 'in_progress' sessions.");
        }

        session.setEndTime(newEndTime);
        return sessionRepository.save(session);
    }

    @Transactional
    public Session endSession(Integer sessionId, String userId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        // 1. Security Check: Is user the creator?
        if (!session.getCreatorid().equals(userId)) {
            throw new RuntimeException("Only the session creator can end the session.");
        }

        // 2. Logic Check: Don't end an already completed session
        if ("completed".equals(session.getStatus())) {
            throw new RuntimeException("Session is already completed.");
        }

        session.setStatus("completed");
        session.setEndTime(LocalDateTime.now()); // Set end time to now
        return sessionRepository.save(session);
    }

    @Transactional
    public Session updateScheduledSession(Integer id, String userId, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));

        // 1. Security Check: Is user the creator?
        if (!session.getCreatorid().equals(userId)) {
            throw new RuntimeException("Only the session creator can edit this session.");
        }

        // 2. Logic Check: Is session scheduled?
        if (!"scheduled".equals(session.getStatus())) {
            throw new RuntimeException("Only 'scheduled' sessions can be edited.");
        }

        // 3. Update only the allowed fields
        session.setTitle(sessionDetails.getTitle());
        session.setStartTime(sessionDetails.getStartTime());
        session.setEndTime(sessionDetails.getEndTime());
        session.setLocation(sessionDetails.getLocation());
        session.setDescription(sessionDetails.getDescription());
        // We explicitly DO NOT update creatorid, groupid, or status

        return sessionRepository.save(session);
    }

    /**
     * Retrieves a list of participants (ID and username) for a given session.
     * @param sessionId The ID of the session.
     * @return A list of SessionParticipantDTOs.
     * @throws RuntimeException if the session is not found.
     */
    @Transactional(readOnly = true) // Good practice for read operations
    public List<SessionParticipantDTO> getSessionMembers(Integer sessionId) {
        // Optional: Check if session exists first
        if (!sessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Session not found with id: " + sessionId);
        }

        // 1. Find all memberships for the session
        List<SessionMembers> memberships = sessionMembersRepo.findBySessionid(sessionId);

        // 2. Map memberships to DTOs, fetching username for each member
        return memberships.stream()
                .map(member -> {
                    String userId = member.getUserid();
                    // Fetch the User entity to get the username
                    // Handle cases where user might not be found (though unlikely with FK constraints)
                    String username = userRepo.findById(userId)
                            .map(User::getUserid) // Assuming User has getUsername()
                            .orElse("Unknown User");
                    return new SessionParticipantDTO(userId, username);
                    // If sending profile picture:
                    // String profilePic = userRepo.findById(userId).map(User::getProfilePicture).orElse(null);
                    // return new SessionParticipantDTO(userId, username, profilePic);
                })
                .collect(Collectors.toList());
    }
}