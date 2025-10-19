package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.dto.GroupJoinRequestWithDetails;
import com.ctrlaltdelinquents.backend.model.GroupJoinRequest;
import com.ctrlaltdelinquents.backend.model.GroupMembers;
import com.ctrlaltdelinquents.backend.repo.GroupJoinRequestRepo;
import com.ctrlaltdelinquents.backend.repo.GroupMembersRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/join-requests")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupJoinRequestController {

    private final GroupJoinRequestRepo groupJoinRequestRepo;
    private final GroupMembersRepo groupMembersRepo;

    public GroupJoinRequestController(GroupJoinRequestRepo groupJoinRequestRepo, GroupMembersRepo groupMembersRepo) {
        this.groupJoinRequestRepo = groupJoinRequestRepo;
        this.groupMembersRepo = groupMembersRepo;
    }

    // Endpoint for a user to get the status of THEIR requests with details.
    @GetMapping("/my-status/details")
    public List<GroupJoinRequestWithDetails> getMyRequestsWithDetails(@RequestParam String userId) {
        return groupJoinRequestRepo.findMyRequestsWithDetails(userId);
    }

    // Endpoint for a group creator to see who is requesting to join their groups.
    @GetMapping("/for-creator")
    public List<GroupJoinRequestWithDetails> getPendingRequestsForCreator(@RequestParam String creatorId) {
        return groupJoinRequestRepo.findPendingRequestsForCreator(creatorId);
    }

    // Endpoint for a user to CREATE a request to join a group
    @PostMapping
    public GroupJoinRequest createJoinRequest(@RequestBody JoinRequestDTO request) {
        GroupJoinRequest newRequest = new GroupJoinRequest();
        newRequest.setGroupId(request.getGroupId());
        newRequest.setUserId(request.getUserId());
        return groupJoinRequestRepo.save(newRequest);
    }

    // Endpoint for a user to get the status of THEIR requests
    @GetMapping("/my-status")
    public List<GroupJoinRequest> getMyRequests(@RequestParam String userId) {
        return groupJoinRequestRepo.findByUserId(userId);
    }

    // Endpoint for a group creator to APPROVE a request
    @PostMapping("/{requestId}/approve")
    @Transactional
    public ResponseEntity<?> approveRequest(@PathVariable Integer requestId) {
        return groupJoinRequestRepo.findById(requestId).map(request -> {
            // 1. Update the request status to 'approved'
            request.setStatus("approved");
            groupJoinRequestRepo.save(request);

            // 2. Add the user to the group_members table
            GroupMembers newMember = new GroupMembers();
            newMember.setGroupid(request.getGroupId());
            newMember.setUserid(request.getUserId());
            groupMembersRepo.save(newMember);

            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint for a group creator to REJECT a request
    @PostMapping("/{requestId}/reject")
    @Transactional
    public ResponseEntity<?> rejectRequest(@PathVariable Integer requestId) {
        return groupJoinRequestRepo.findById(requestId).map(request -> {
            request.setStatus("rejected");
            groupJoinRequestRepo.save(request);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

// DTO for creating a join request
class JoinRequestDTO {
    private int groupId;
    private String userId;

    // Getters and Setters
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
