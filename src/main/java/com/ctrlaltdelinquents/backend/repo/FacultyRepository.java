package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacultyRepository  extends JpaRepository<Faculty, Integer> {
}
