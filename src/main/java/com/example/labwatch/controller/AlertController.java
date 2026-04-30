package com.example.labwatch.controller;

import com.example.labwatch.model.Alert;
import com.example.labwatch.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable Long id) {
        return alertService.getAlertById(id);
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    @PutMapping("/{id}")
    public Alert updateAlert(@PathVariable Long id, @RequestBody Alert alert) {
        return alertService.updateAlert(id, alert);
    }

    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
    }

    @GetMapping("/computer/{computerId}")
    public List<Alert> getAlertsByComputer(@PathVariable Long computerId) {
        return alertService.getAlertsByComputer(computerId);
    }

    @GetMapping("/recent")
    public List<Alert> getRecentAlerts() {
        return alertService.getRecentAlerts();
    }

    @PostMapping("/trigger")
    public Alert triggerAlert(@RequestParam Long computerId, @RequestParam String appName) {
        return alertService.triggerAlert(computerId, appName);
    }
}
