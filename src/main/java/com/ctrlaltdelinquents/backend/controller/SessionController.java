package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.repo.SessionRepo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class SessionController {

    private final SessionRepo sessionRepo;

    public SessionController(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    // GET all upcoming sessions for a specific user
    @GetMapping("/sessions/upcoming")
    // @RequestBody
    public List<Session> getUpcomingSessions(@RequestParam String userId) {
        return sessionRepo.findUpcomingSessions(userId);
    }

    // GET total study hours for a user in the past 7 days
    @GetMapping("/sessions/study-hours")
    public StudyHoursResponse getStudyHoursLast7Days(@RequestParam String userId) {
        Double totalHours = sessionRepo.getActualStudyHoursLast7Days(userId);

        // Round to nearest whole number for simplicity
        int roundedHours = totalHours != null ? (int) Math.round(totalHours) : 0;
        double exactHours = totalHours != null ? totalHours : 0.0;

        return new StudyHoursResponse(userId, roundedHours, exactHours);
    }

    // GET number of sessions attended by user in past 7 days
    @GetMapping("/sessions/num-sessions")
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