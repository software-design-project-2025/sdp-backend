// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.model.Session;
// import com.ctrlaltdelinquents.backend.repo.SessionRepo;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PathVariable;
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