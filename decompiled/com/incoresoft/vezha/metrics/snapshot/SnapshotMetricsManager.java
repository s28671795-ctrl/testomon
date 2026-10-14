/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.snapshot;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.license.LicenseManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.system.disk.DiskSpaceManager;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetrics;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import com.license4j.hardware.HardwareID;
import java.time.Instant;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;

public class SnapshotMetricsManager {
    private final DiskSpaceManager diskSpaceManager;
    private final AnalyticsManagerImpl analyticsManager;
    private final LicenseManager licenseManager;
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;

    public SnapshotMetrics getMetrics() {
        return new SnapshotMetrics(UUID.nameUUIDFromBytes(HardwareID.generateHardwareResult().getBytes()), this.diskSpaceManager.getDiskSpace(System.getProperty("user.dir")), this.diskSpaceManager.getStorageDiskSpace(), this.analyticsManager.getAll(analytics -> analytics.getStatus() == AnalyticsStatus.started).size(), this.analyticsManager.getAll(analytics -> analytics.getStatus() == AnalyticsStatus.processing).size(), this.analyticsManager.getAll(analytics -> analytics.getStatus() == AnalyticsStatus.paused).size(), this.licenseManager.getLicenses(), this.coreServiceVersionSupplier.getCoreVersion(), CollectionUtils.union(this.analyticsPluginsManagerFacade.getInstalledPluginsInfos(), this.eventManagerPluginsManagerFacade.getInstalledPluginsInfos()), Instant.now());
    }

    @Inject
    public SnapshotMetricsManager(DiskSpaceManager diskSpaceManager, AnalyticsManagerImpl analyticsManager, LicenseManager licenseManager, CoreServiceVersionSupplier coreServiceVersionSupplier, AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade, EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade) {
        this.diskSpaceManager = diskSpaceManager;
        this.analyticsManager = analyticsManager;
        this.licenseManager = licenseManager;
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.analyticsPluginsManagerFacade = analyticsPluginsManagerFacade;
        this.eventManagerPluginsManagerFacade = eventManagerPluginsManagerFacade;
    }
}

