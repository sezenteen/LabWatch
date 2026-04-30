package com.example.labwatch.service.implement;

import com.example.labwatch.model.Activity;
import com.example.labwatch.repository.ActivityRepository;
import com.example.labwatch.service.AlertService;
import com.example.labwatch.service.ActivityService;
import com.example.labwatch.service.BlockedAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final BlockedAppService blockedAppService;
    private final AlertService alertService;
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ямар нэгэн үйлдэл илэрсэнгүй."));
    }

    @Override
    public Activity createActivity(Activity activity) {
        Activity savedActivity = activityRepository.save(activity);
        triggerAlertIfBlocked(savedActivity);
        return savedActivity;
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        Activity existingActivity = getActivityById(id);

        existingActivity.setComputerID(activity.getComputerID());
        existingActivity.setUsername(activity.getUsername());
        existingActivity.setAppName(activity.getAppName());
        existingActivity.setWindowTitle(activity.getWindowTitle());
        existingActivity.setStartedAt(activity.getStartedAt());
        existingActivity.setEndedAt(activity.getEndedAt());
        existingActivity.setDurationSeconds(activity.getDurationSeconds());

        Activity savedActivity = activityRepository.save(existingActivity);
        triggerAlertIfBlocked(savedActivity);
        return savedActivity;
    }

    @Override
    public void deleteActivity(Long id) {
        Activity existingActivity = getActivityById(id);
        activityRepository.delete(existingActivity);
    }

    @Override
    public List<Activity> getActivitiesByComputer(Long computerId) {
        return activityRepository.findByComputerID_Id(computerId);
    }

    @Override
    public List<Activity> getTodayActivities() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        return activityRepository.findByStartedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Activity> getActivitiesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return activityRepository.findByStartedAtBetween(start, end);
    }

    @Override
    public List<Object[]> getDailyAppUsage() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);

        return activityRepository.getAppUsageBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Activity> getActiveActivities() {
        return activityRepository.findByEndedAtIsNull();
    }

    @Override
    public List<Activity> getActivitiesByAppName(String appName) {
        return activityRepository.findByAppName(appName);
    }

    private void triggerAlertIfBlocked(Activity activity) {
        if (activity.getComputerID() == null || activity.getComputerID().getId() == null) {
            return;
        }

        String appName = activity.getAppName();
        if (appName != null && blockedAppService.isAppBlocked(appName)) {
            alertService.triggerAlert(activity.getComputerID().getId(), appName);
        }
    }
}
