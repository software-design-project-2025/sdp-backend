package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>{
    List<User> findByUserid(String userid);
}