package com.ctrlaltdelinquents.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctrlaltdelinquents.backend.model.GroupMembers;
import com.ctrlaltdelinquents.backend.repo.GroupMembersRepo;

@RestController
@RequestMapping("/api/groupmembers")
public class GroupMembersController {

    @Autowired
    private final GroupMembersRepo groupMembersRepo;

    public GroupMembersController(GroupMembersRepo groupMembersRepo){
        this.groupMembersRepo = groupMembersRepo;
    }

    @GetMapping("/findmembers")
    public List<GroupMembers> findMembersByGroupId(@RequestParam String groupid){
        return groupMembersRepo.findByGroup(groupid);
    }
}
