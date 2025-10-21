package com.ctrlaltdelinquents.backend.service;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.model.SessionMembers;
import com.ctrlaltdelinquents.backend.model.SessionMembersId;
import com.ctrlaltdelinquents.backend.repo.SessionMembersRepo;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionMembersService {

    @Autowired
    private SessionMembersRepo sessionMembersRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Transactional
    public SessionMembers joinSession(Integer sessionId, String userId) {
        // 1. Find the session user wants to join
        Session sessionToJoin = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        // 2. Check for overlapping sessions
        List<Session> overlaps = sessionRepo.findOverlappingSessions(
                userId, sessionToJoin.getStartTime(), sessionToJoin.getEndTime());

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Cannot join session. It overlaps with another session: " + overlaps.get(0).getTitle());
        }

        // 3. Check if user is already a member
        SessionMembersId id = new SessionMembersId(sessionId, userId);
        if (sessionMembersRepo.existsById(id)) {
            // User is already in the session, just return the existing record
            return sessionMembersRepo.findById(id).get();
        }

        // 4. Create and save the new membership
        SessionMembers newMember = new SessionMembers(sessionId, userId);
        return sessionMembersRepo.save(newMember);
    }

    @Transactional
    public void leaveSession(Integer sessionId, String userId) {
        // 1. Find the session
        Session session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        // 2. Check if user is the creator
        if (session.getCreatorid().equals(userId)) {
            throw new RuntimeException("Creator cannot leave their own session. You must delete it instead.");
        }

        // 3. Find and delete the membership record
        SessionMembersId id = new SessionMembersId(sessionId, userId);
        if (!sessionMembersRepo.existsById(id)) {
            throw new RuntimeException("User is not a member of this session.");
        }

        sessionMembersRepo.deleteById(id);
    }
}