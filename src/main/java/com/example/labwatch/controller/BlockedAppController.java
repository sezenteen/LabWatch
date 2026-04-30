package com.example.labwatch.controller;

import com.example.labwatch.model.BlockedApp;
import com.example.labwatch.service.BlockedAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blockedapps")
@RequiredArgsConstructor
public class BlockedAppController {

    private final BlockedAppService blockedAppService;

    @GetMapping
    public List<BlockedApp> getAllBlockedApps() {
        return blockedAppService.getAllBlockedApps();
    }

    @GetMapping("/{id}")
    public BlockedApp getBlockedAppById(@PathVariable Long id) {
        return blockedAppService.getBlockedAppById(id);
    }

    @PostMapping
    public BlockedApp createBlockedApp(@RequestBody BlockedApp blockedApp) {
        return blockedAppService.createBlockedApp(blockedApp);
    }

    @PutMapping("/{id}")
    public BlockedApp updateBlockedApp(@PathVariable Long id, @RequestBody BlockedApp blockedApp) {
        return blockedAppService.updateBlockedApp(id, blockedApp);
    }

    @DeleteMapping("/{id}")
    public void deleteBlockedApp(@PathVariable Long id) {
        blockedAppService.deleteBlockedApp(id);
    }

    @GetMapping("/check")
    public boolean isAppBlocked(@RequestParam String appName) {
        return blockedAppService.isAppBlocked(appName);
    }
}
