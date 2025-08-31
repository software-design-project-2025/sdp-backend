package com.ctrlaltdelinquents.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ctrlaltdelinquents.backend.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
}
