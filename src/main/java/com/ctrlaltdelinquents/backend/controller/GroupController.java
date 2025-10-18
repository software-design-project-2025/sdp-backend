package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Group;
import com.ctrlaltdelinquents.backend.model.GroupMembers;
import com.ctrlaltdelinquents.backend.repo.GroupRepo;
import com.ctrlaltdelinquents.backend.repo.GroupMembersRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/groups")
public class GroupController {

    private final GroupRepo groupRepo;
    private final GroupMembersRepo groupMembersRepo;

    public GroupController(GroupRepo groupRepo, GroupMembersRepo groupMembersRepo) {
        this.groupRepo = groupRepo;
        this.groupMembersRepo = groupMembersRepo;
    }

    @PostMapping
    @Transactional
    public Group createGroup(@RequestBody Group newGroup) {
        // 1. Save the new group to get its generated ID
        Group savedGroup = groupRepo.save(newGroup);

        // 2. Automatically add the creator as a member of their new group
        GroupMembers creatorAsMember = new GroupMembers();
        creatorAsMember.setGroupid(savedGroup.getGroupid());
        creatorAsMember.setUserid(savedGroup.getCreatorid());
        groupMembersRepo.save(creatorAsMember);

        return savedGroup;
    }

    @GetMapping("/discover")
    public List<Group> discoverGroups(@RequestParam String userId) {
        return groupRepo.findRandomDiscoverableGroups(userId);
    }
}
