package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.model.SessionMembers;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;
import com.ctrlaltdelinquents.backend.service.SessionMembersService;
import com.ctrlaltdelinquents.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions") // Using your teammate's base path
@CrossOrigin(origins = {"http://localhost:4200", "https://witty-coast-007353203.1.azurestaticapps.net"})
public class SessionController {

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionMembersService sessionMembersService;

    
    // GET all upcoming sessions for a specific user
    @GetMapping("/upcoming")
    public List<Session> getUpcomingSessions(@RequestParam String userId) {
        return sessionRepo.findUpcomingSessions(userId);
    }

    // GET total study hours for a user in the past 7 days
    @GetMapping("/study-hours")
    public StudyHoursResponse getStudyHoursLast7Days(@RequestParam String userId) {
        Double totalHours = sessionRepo.getActualStudyHoursLast7Days(userId);

        // Round to nearest whole number for simplicity
        int roundedHours = totalHours != null ? (int) Math.round(totalHours) : 0;
        double exactHours = totalHours != null ? totalHours : 0.0;

        return new StudyHoursResponse(userId, roundedHours, exactHours);
    }

    // GET number of sessions attended by user in past 7 days
    @GetMapping("/num-sessions")
    public SessionCountResponse getNumberOfSessionsAttended(@RequestParam String userId) {
        Integer sessionCount = sessionRepo.countSessionsAttendedLast7Days(userId);

        // Handle null case and return 0 if no sessions found
        int count = sessionCount != null ? sessionCount : 0;

        return new SessionCountResponse(userId, count);
    }


    /**
     * Creates a new session and automatically adds the creator as a member.
     */
    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        try {
            Session createdSession = sessionService.createSession(session);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Deletes a session. Only the creator can do this.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer id, @RequestParam String userId) {
        try {
            sessionService.deleteSession(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            if (e.getMessage().contains("Only the session creator")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    // --- JOIN / LEAVE ---

    /**
     * Joins the specified user to a session.
     * Performs an overlap check.
     */
    @PostMapping("/{id}/join")
    public ResponseEntity<SessionMembers> joinSession(@PathVariable Integer id, @RequestParam String userId) {
        try {
            SessionMembers membership = sessionMembersService.joinSession(id, userId);
            return ResponseEntity.ok(membership);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict for overlap
        }
    }

    /**
     * Removes the specified user from a session.
     * Creator cannot leave.
     */
    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Void> leaveSession(@PathVariable Integer id, @RequestParam String userId) {
        try {
            sessionMembersService.leaveSession(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            if (e.getMessage().contains("Creator cannot leave")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    // --- CREATOR ACTIONS ---

    /**
     * Extends the end time of a session. Creator only.
     */
    @PatchMapping("/{id}/extend")
    public ResponseEntity<Session> extendSession(@PathVariable Integer id,
                                                 @RequestBody Map<String, String> payload) {
        try {
            String userId = payload.get("userId");
            LocalDateTime newEndTime = LocalDateTime.parse(payload.get("newEndTime"));
            Session session = sessionService.extendSession(id, userId, newEndTime);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Ends a session immediately, setting status to 'completed'. Creator only.
     */
    @PatchMapping("/{id}/end")
    public ResponseEntity<Session> endSession(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        try {
            String userId = payload.get("userId");
            Session session = sessionService.endSession(id, userId);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // --- DATA FOR "INTENSE PAGE" ---

    /**
     * GET all filterable sessions (for the main discovery view).
     */
    @GetMapping
    public ResponseEntity<List<Session>> getDiscoverSessions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Session> sessions = sessionService.findSessions(search, startDate, endDate);
        return ResponseEntity.ok(sessions);
    }

    /**
     * GET sessions for the "Past Sessions" tab.
     */
    @GetMapping("/past")
    public ResponseEntity<List<Session>> getPastSessions(@RequestParam String userId) {
        return ResponseEntity.ok(sessionService.getPastSessions(userId));
    }

    /**
     * GET sessions for the "My Sessions" (created) tab.
     */
    @GetMapping("/my-created")
    public ResponseEntity<List<Session>> getMyCreatedSessions(@RequestParam String userId) {
        return ResponseEntity.ok(sessionService.getSessionsByCreator(userId));
    }

    /**
     * GET a single session by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Integer id) {
        return sessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT: Updates a session. Creator only, and only if "scheduled".
     */
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Integer id,
                                                 @RequestParam String userId,
                                                 @RequestBody Session sessionDetails) {
        try {
            Session updatedSession = sessionService.updateScheduledSession(id, userId, sessionDetails);
            return ResponseEntity.ok(updatedSession);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Only the creator") || e.getMessage().contains("Only 'scheduled'")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if (e.getMessage().contains("Session not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== YOUR ORIGINAL RESPONSE DTOs =====
    
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