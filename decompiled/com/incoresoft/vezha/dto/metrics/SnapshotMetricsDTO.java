/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.dto.system.DiskSpaceInfoDTO;
import com.incoresoft.vezha.dto.metrics.MetricsInstalledPluginDTO;
import com.incoresoft.vezha.dto.metrics.MetricsLicenseDTO;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetrics;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public record SnapshotMetricsDTO(@JSONField(name="hardware_id") UUID hardwareId, @JSONField(name="application_disk_space") DiskSpaceInfoDTO applicationDiskSpace, @JSONField(name="storage_disk_space") DiskSpaceInfoDTO storageDiskSpace, @JSONField(name="started_analytics_number") int startedAnalyticsNumber, @JSONField(name="processing_analytics_number") int processingAnalyticsNumber, @JSONField(name="paused_analytics_number") int pausedAnalyticsNumber, @JSONField(name="licenses") Collection<MetricsLicenseDTO> licenses, @JSONField(name="core_version") Version coreVersion, @JSONField(name="installed_plugins") Collection<MetricsInstalledPluginDTO> installedPlugins, @JSONField(name="time") Instant time) {
    public SnapshotMetricsDTO(SnapshotMetrics metrics) {
        this(metrics.hardwareId(), new DiskSpaceInfoDTO(metrics.applicationDiskSpace()), new DiskSpaceInfoDTO(metrics.storageDiskSpace()), metrics.startedAnalyticsNumber(), metrics.processingAnalyticsNumber(), metrics.pausedAnalyticsNumber(), metrics.licenses().stream().map(MetricsLicenseDTO::new).toList(), metrics.coreVersion(), metrics.installedPlugins().stream().map(MetricsInstalledPluginDTO::new).toList(), metrics.time());
    }
}

