package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Integer> {
}
