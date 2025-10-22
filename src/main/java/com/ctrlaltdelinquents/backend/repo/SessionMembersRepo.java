package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.SessionMembers;
import com.ctrlaltdelinquents.backend.model.SessionMembersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionMembersRepo extends JpaRepository<SessionMembers, SessionMembersId> {
    @Modifying
    @Query("DELETE FROM SessionMembers sm WHERE sm.sessionid = :sessionId")
    void deleteBySessionid(@Param("sessionId") Integer sessionId);

    List<SessionMembers> findBySessionid(Integer sessionId);
}