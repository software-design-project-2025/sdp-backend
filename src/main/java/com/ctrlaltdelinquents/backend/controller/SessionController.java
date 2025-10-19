package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "https://witty-coast-007353203.1.azurestaticapps.net"})

public class SessionController {

    private final SessionRepo sessionRepo;

    public SessionController(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Integer id) {
        return sessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sessions/creator/{creatorId}")
    public ResponseEntity<List<Session>> getSessionsByCreator(@PathVariable String creatorId) {
        List<Session> sessions = sessionService.getSessionsByCreator(creatorId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/sessions/group/{groupId}")
    public ResponseEntity<List<Session>> getSessionsByGroup(@PathVariable int groupId) {
        List<Session> sessions = sessionService.getSessionsByGroup(groupId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        try {
            Session createdSession = sessionService.createSession(session);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/sessions/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Integer id, @RequestBody Session session) {
        try {
            Session updatedSession = sessionService.updateSession(id, session);
            return ResponseEntity.ok(updatedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET all upcoming sessions for a specific user
    @GetMapping("/auth/sessions/upcoming")
    // @RequestBody
    public List<Session> getUpcomingSessions(@RequestParam String userId) {
        return sessionRepo.findUpcomingSessions(userId);
    }

    // GET total study hours for a user in the past 7 days
    @GetMapping("/auth/sessions/study-hours")
    public StudyHoursResponse getStudyHoursLast7Days(@RequestParam String userId) {
        Double totalHours = sessionRepo.getActualStudyHoursLast7Days(userId);

        // Round to nearest whole number for simplicity
        int roundedHours = totalHours != null ? (int) Math.round(totalHours) : 0;
        double exactHours = totalHours != null ? totalHours : 0.0;

        return new StudyHoursResponse(userId, roundedHours, exactHours);
    }

    // GET number of sessions attended by user in past 7 days
    @GetMapping("/auth/sessions/num-sessions")
    public SessionCountResponse getNumberOfSessionsAttended(@RequestParam String userId) {
        Integer sessionCount = sessionRepo.countSessionsAttendedLast7Days(userId);

        // Handle null case and return 0 if no sessions found
        int count = sessionCount != null ? sessionCount : 0;

        return new SessionCountResponse(userId, count);
    }

    // Add this Response DTO inside your SessionController class
    public static class SessionCountResponse {
        private String userId;
        private int numSessions;

        public SessionCountResponse() {
        }

        public SessionCountResponse(String userId, int numSessions) {
            this.userId = userId;
            this.numSessions = numSessions;
        }

        // Getters and setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getNumSessions() {
            return numSessions;
        }

        public void setNumSessions(int numSessions) {
            this.numSessions = numSessions;
        }
    }

    // Response DTO for study hours
    public static class StudyHoursResponse {
        private String userId;
        private int totalHours;
        private double exactHours;

        public StudyHoursResponse() {
        }

        public StudyHoursResponse(String userId, int totalHours, double exactHours) {
            this.userId = userId;
            this.totalHours = totalHours;
            this.exactHours = exactHours;
        }

        // Getters and setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(int totalHours) {
            this.totalHours = totalHours;
        }

        public double getExactHours() {
            return exactHours;
        }

        public void setExactHours(double exactHours) {
            this.exactHours = exactHours;
        }
    }
}
//     // GET all upcoming sessions for a specific user
//     @GetMapping("/sessions/upcoming")
//     // @RequestBody
//     public List<Session> getUpcomingSessions(@RequestParam String userId) {
//         return sessionRepo.findUpcomingSessions(userId);
//     }
// }
