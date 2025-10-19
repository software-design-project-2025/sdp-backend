package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {

    // Finds 5 random, active groups that a user is NOT a member of and does NOT
    // have a pending/approved request for.
    @Query(value = "SELECT * FROM \"group\" g " +
            "WHERE g.active = TRUE " +
            "AND g.groupid NOT IN (SELECT gm.groupid FROM group_members gm WHERE gm.userid = :userId) " +
            "AND g.groupid NOT IN (SELECT gjr.groupid FROM group_join_request gjr WHERE gjr.userid = :userId AND gjr.status IN ('pending', 'approved')) "
            +
            "ORDER BY RANDOM() " +
            "LIMIT 5", nativeQuery = true)
    List<Group> findRandomDiscoverableGroups(@Param("userId") String userId);
}
