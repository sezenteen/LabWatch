package com.example.labwatch.repository;

import com.example.labwatch.model.BlockedApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedAppsRepository extends JpaRepository<BlockedApp, Long> {
    boolean existsByAppNameIgnoreCase(String appName);
}
