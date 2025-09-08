package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepo extends JpaRepository<Module, String> {

}
