package com.example.labwatch.service;

import com.example.labwatch.model.Activity;

import java.util.List;

public interface ActivityService {

    // CRUD
    List<Activity> getAllActivities();
    Activity getActivityById(Long id);
    Activity createActivity(Activity activity);
    Activity updateActivity(Long id, Activity activity);
    void deleteActivity(Long id);

    // IMPORTANT
    List<Activity> getActivitiesByComputer(Long computerId);
    List<Activity> getTodayActivities();
    List<Activity> getActivitiesByAppName(String appName);
}