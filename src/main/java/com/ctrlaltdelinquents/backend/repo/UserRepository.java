package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{
}