package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.GroupMembers;
import com.ctrlaltdelinquents.backend.model.GroupMemberId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepo extends JpaRepository<GroupMembers, GroupMemberId> {

    @Query("SELECT g FROM GroupMembers g WHERE g.groupid = :groupid")
    List<GroupMembers> findByGroup(@Param("groupid") String groupid);
}