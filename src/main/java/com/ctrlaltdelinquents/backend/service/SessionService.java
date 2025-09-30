package com.ctrlaltdelinquents.backend.service;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepo sessionRepository;

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

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session updateSession(Integer id, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));

        session.setTitle(sessionDetails.getTitle());
        session.setStart_time(sessionDetails.getStart_time());
        session.setEnd_time(sessionDetails.getEnd_time());
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
}