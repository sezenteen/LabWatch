package com.example.labwatch.repository;

import com.example.labwatch.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByComputerId(Long computerId);

    List<Activity> findByAppName(String appName);

    List<Activity> findByStartedAtBetween(LocalDateTime start, LocalDateTime end);
}
