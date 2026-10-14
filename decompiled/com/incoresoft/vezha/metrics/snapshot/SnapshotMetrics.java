/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.snapshot;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.license.License;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.system.disk.DiskSpaceInfo;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public record SnapshotMetrics(UUID hardwareId, DiskSpaceInfo applicationDiskSpace, DiskSpaceInfo storageDiskSpace, int startedAnalyticsNumber, int processingAnalyticsNumber, int pausedAnalyticsNumber, Collection<License> licenses, Version coreVersion, Collection<InstalledPluginInfo> installedPlugins, Instant time) {
}

