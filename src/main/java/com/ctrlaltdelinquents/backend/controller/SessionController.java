// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.model.Session;
// import com.ctrlaltdelinquents.backend.repo.SessionRepo;

// //import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// //import org.springframework.web.bind.annotation.RequestBody;
// //mport org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.List;

// @RestController
// @RequestMapping("/api/auth")
// public class SessionController {

//     private final SessionRepo sessionRepo;

//     public SessionController(SessionRepo sessionRepo) {
//         this.sessionRepo = sessionRepo;
//     }

//     // GET all upcoming sessions for a specific user
//     @GetMapping("/sessions/upcoming")
//     // @RequestBody
//     public List<Session> getUpcomingSessions(@RequestParam String userId) {
//         return sessionRepo.findUpcomingSessions(userId);
//     }
// }

package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Session;
import com.ctrlaltdelinquents.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Integer id) {
        return sessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<Session>> getSessionsByCreator(@PathVariable String creatorId) {
        List<Session> sessions = sessionService.getSessionsByCreator(creatorId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/group/{groupId}")
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

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Integer id, @RequestBody Session session) {
        try {
            Session updatedSession = sessionService.updateSession(id, session);
            return ResponseEntity.ok(updatedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}