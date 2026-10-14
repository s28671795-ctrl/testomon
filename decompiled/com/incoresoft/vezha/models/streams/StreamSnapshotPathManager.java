/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.vezha.config.VezhaConfigManager;
import java.io.File;
import java.time.ZonedDateTime;

public class StreamSnapshotPathManager {
    private static final String SNAPSHOTS_DIRECTORY = "snapshots";
    private final VezhaConfigManager configManager;

    public String getSnapshotStoragePath(Stream stream, File originalImageFile) {
        String prefix = this.getPrefix();
        int streamId = stream.getId();
        String fileName = originalImageFile.getName();
        ZonedDateTime now = ZonedDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        return prefix + String.format("%s/%d/%d/%d/%d/%d/%s", SNAPSHOTS_DIRECTORY, streamId, year, month, day, hour, fileName);
    }

    private String getPrefix() {
        if (this.configManager.getStorage().localDirectory().isPresent()) {
            return "";
        }
        return "analytics/";
    }

    @Inject
    public StreamSnapshotPathManager(VezhaConfigManager configManager) {
        this.configManager = configManager;
    }
}

