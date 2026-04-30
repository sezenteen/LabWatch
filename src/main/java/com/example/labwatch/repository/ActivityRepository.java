package com.example.labwatch.repository;

import com.example.labwatch.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByComputerID_Id(Long computerId);

    List<Activity> findByStartedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Activity> findByAppName(String appName);

    List<Activity> findByEndedAtIsNull();

    @Query("""
        SELECT a.appName, SUM(a.durationSeconds)
        FROM Activity a
        WHERE a.startedAt BETWEEN :start AND :end
        GROUP BY a.appName
        ORDER BY SUM(a.durationSeconds) DESC
    """)
    List<Object[]> getAppUsageBetween(LocalDateTime start, LocalDateTime end);
}
