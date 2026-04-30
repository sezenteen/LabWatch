package com.example.labwatch.service.implement;

import com.example.labwatch.model.BlockedApp;
import com.example.labwatch.repository.BlockedAppsRepository;
import com.example.labwatch.service.BlockedAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockedAppServiceImpl implements BlockedAppService {

    private final BlockedAppsRepository blockedAppsRepository;

    @Override
    public List<BlockedApp> getAllBlockedApps() {
        return blockedAppsRepository.findAll();
    }

    @Override
    public BlockedApp getBlockedAppById(Long id) {
        return blockedAppsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Хориглосон программ олдсонгүй!"));
    }

    @Override
    public BlockedApp createBlockedApp(BlockedApp blockedApp) {
        return blockedAppsRepository.save(blockedApp);
    }

    @Override
    public BlockedApp updateBlockedApp(Long id, BlockedApp blockedApp) {
        BlockedApp existingBlockedApp = getBlockedAppById(id);

        existingBlockedApp.setAppName(blockedApp.getAppName());
        existingBlockedApp.setReason(blockedApp.getReason());

        return blockedAppsRepository.save(existingBlockedApp);
    }

    @Override
    public void deleteBlockedApp(Long id) {
        blockedAppsRepository.deleteById(id);
    }

    @Override
    public boolean isAppBlocked(String appName) {
        return blockedAppsRepository.existsByAppNameIgnoreCase(appName);
    }
}
