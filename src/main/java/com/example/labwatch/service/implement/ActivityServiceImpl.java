package com.example.labwatch.service.implement;

import com.example.labwatch.model.Activity;
import com.example.labwatch.repository.ActivityRepository;
import com.example.labwatch.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        Activity existingActivity = getActivityById(id);

        existingActivity.setComputerID(activity.getComputerID());
        existingActivity.setUsername(activity.getUsername());
        existingActivity.setAppName(activity.getAppName());
        existingActivity.setDurationSeconds(activity.getDurationSeconds());

        return activityRepository.save(existingActivity);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity existingActivity = getActivityById(id);
        activityRepository.delete(existingActivity);
    }

    @Override
    public List<Activity> getActivitiesByComputer(Long computerId) {
        return activityRepository.findByComputerId(computerId);
    }

    @Override
    public List<Activity> getTodayActivities() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        return activityRepository.findByStartedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Activity> getActivitiesByAppName(String appName) {
        return activityRepository.findByAppName(appName);
    }
}
