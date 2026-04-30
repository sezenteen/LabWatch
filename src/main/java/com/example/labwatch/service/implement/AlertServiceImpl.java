package com.example.labwatch.service.implement;

import com.example.labwatch.model.Alert;
import com.example.labwatch.repository.AlertRepository;
import com.example.labwatch.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public Alert getAlertById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Мэдэгдэл байхгүй!"));
    }

    @Override
    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    @Override
    public Alert updateAlert(Long id, Alert alert) {
        Alert existingAlert = getAlertById(id);

        existingAlert.setComputerID(alert.getComputerID().getId());
        existingAlert.setAppName(alert.getAppName());
        existingAlert.setMessage(alert.getMessage());

        return alertRepository.save(existingAlert);
    }


    @Override
    public void deleteAlert(Long id) {
        Alert existingAlert = getAlertById(id);
        alertRepository.delete(existingAlert);
    }

    @Override
    public List<Alert> getAlertsByComputer(Long computerId) {
        return alertRepository.findByComputerID(computerId);
    }

    @Override
    public List<Alert> getRecentAlerts() {
        return alertRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public Alert triggerAlert(Long computerId, String appName) {

        Alert alert = new Alert();
        alert.setComputerID(computerId);
        alert.setAppName(appName);
        alert.setMessage("Хориглосон программ илэрлээ: " + appName);
        return alertRepository.save(alert);
    }
}
