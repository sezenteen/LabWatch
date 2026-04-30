package com.example.labwatch.controller;

import com.example.labwatch.model.Activity;
import com.example.labwatch.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

//    CRUD
    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Long id,
                                   @RequestBody Activity activity) {
        return activityService.updateActivity(id, activity);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
    }

//  Filtering
    @GetMapping("/computer/{computerId}")
    public List<Activity> getByComputer(@PathVariable Long computerId) {
        return activityService.getActivitiesByComputer(computerId);
    }

    @GetMapping("/app")
    public List<Activity> getByAppName(@RequestParam String appName) {
        return activityService.getActivitiesByAppName(appName);
    }

//  time range
    @GetMapping("/today")
    public List<Activity> getTodayActivities() {
        return activityService.getTodayActivities();
    }

    @GetMapping("/range")
    public List<Activity> getBetweenDates(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        return activityService.getActivitiesBetweenDates(start, end);
    }

// reporting
    @GetMapping("/report/daily")
    public List<Object[]> getDailyAppUsage() {
        return activityService.getDailyAppUsage();
    }

//    real-time
    @GetMapping("/active")
    public List<Activity> getActiveActivities() {
        return activityService.getActiveActivities();
    }
}