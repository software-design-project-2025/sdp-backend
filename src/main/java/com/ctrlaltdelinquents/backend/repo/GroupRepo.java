package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {

}
