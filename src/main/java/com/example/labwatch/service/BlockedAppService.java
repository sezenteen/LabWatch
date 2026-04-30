package com.example.labwatch.service;

import com.example.labwatch.model.BlockedApp;

import java.util.List;

public interface BlockedAppService {
//    crud
    List<BlockedApp> getAllBlockedApps();
    BlockedApp getBlockedAppById(Long id);
    BlockedApp createBlockedApp(BlockedApp blockedApp);
    BlockedApp updateBlockedApp(Long id, BlockedApp blockedApp);
    void deleteBlockedApp(Long id);

//    monitoring
    boolean isAppBlocked(String appName);

}
