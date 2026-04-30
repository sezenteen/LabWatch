package com.example.labwatch.service;

import com.example.labwatch.model.Alert;

import java.util.List;

public interface AlertService {
//    crud
    List<Alert> getAllAlerts();
    Alert getAlertById(Long id);
    Alert createAlert(Alert alert);
    Alert updateAlert(Long id, Alert alert);
    void deleteAlert(Long id);

//    alerts
    List<Alert> getAlertsByComputer(Long computerId);

//    recent - admin can see
    List<Alert> getRecentAlerts();

//    blocked app detect
    Alert triggerAlert(Long computerId, String appName);
}
