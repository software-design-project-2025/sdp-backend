package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findAllByUserid(String userid);
    List<Topic> findAllByUseridAndStatus(String userid, String status);
}
