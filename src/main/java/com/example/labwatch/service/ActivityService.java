package com.example.labwatch.service;

import com.example.labwatch.model.Activity;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityService {

    // CRUD
    List<Activity> getAllActivities();
    Activity getActivityById(Long id);
    Activity createActivity(Activity activity);
    Activity updateActivity(Long id, Activity activity);
    void deleteActivity(Long id);

    // Filtering
    List<Activity> getActivitiesByComputer(Long computerId);
    List<Activity> getActivitiesByAppName(String appName);

    // Time-based
    List<Activity> getTodayActivities();
    List<Activity> getActivitiesBetweenDates(LocalDateTime start, LocalDateTime end);

    // Reporting
    List<Object[]> getDailyAppUsage();

    // Real-time
    List<Activity> getActiveActivities();
}