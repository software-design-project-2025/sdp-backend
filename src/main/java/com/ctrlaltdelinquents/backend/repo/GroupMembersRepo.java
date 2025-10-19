package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.GroupMembers;
import com.ctrlaltdelinquents.backend.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepo extends JpaRepository<GroupMembers, GroupMemberId> {

}