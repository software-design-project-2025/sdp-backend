package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<UserModel, Integer>{
    
}
