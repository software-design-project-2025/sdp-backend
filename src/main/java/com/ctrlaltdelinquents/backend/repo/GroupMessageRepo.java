package com.ctrlaltdelinquents.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctrlaltdelinquents.backend.model.GroupMessage;

import java.util.List;

public interface GroupMessageRepo extends JpaRepository<GroupMessage, Integer>{

    @Query("SELECT g FROM GroupMessage g WHERE g.groupid = :groupid")
    List<GroupMessage> findByGroup(@Param("groupid") int groupid);
}
