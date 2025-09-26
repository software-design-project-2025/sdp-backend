package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT c FROM User c WHERE c.userid = :userid")
    List<User> findByUserId(@Param("userid") String userid);
}