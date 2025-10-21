package com.ctrlaltdelinquents.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.ctrlaltdelinquents.backend.model.GroupMessage;
import com.ctrlaltdelinquents.backend.repo.GroupMessageRepo;

@RestController
@RequestMapping("/api/groupMessage")
public class GroupMessageController {

    @Autowired
    private final GroupMessageRepo groupMessageRepo;
    
    public GroupMessageController(GroupMessageRepo groupMessageRepo) {
        this.groupMessageRepo = groupMessageRepo;
    }

    @GetMapping("/getGroupMessage")
    @ResponseBody
    public List<GroupMessage> getGroupMessages(@RequestParam int groupid){
        return groupMessageRepo.findByGroup(groupid);
    }

    @PostMapping("/sendGroupMessage")
    public GroupMessage createMessage(@RequestBody GroupMessage groupMessage) {
        return groupMessageRepo.save(groupMessage);
    }
    
}
