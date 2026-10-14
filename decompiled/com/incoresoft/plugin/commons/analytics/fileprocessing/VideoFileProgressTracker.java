/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.fileprocessing;

import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public class VideoFileProgressTracker {
    private final Map<Integer, VideoFileProgress> cache = new ConcurrentHashMap<Integer, VideoFileProgress>();

    public void setProgress(@NotNull VideoFileProgress videoFileProgress) {
        this.cache.put(videoFileProgress.getVaId(), Objects.requireNonNull(videoFileProgress));
    }

    public void resetProgress(int vaId) {
        this.cache.remove(vaId);
    }

    public VideoFileProgress getProgress(int vaId) {
        return this.cache.getOrDefault(vaId, new VideoFileProgress(0, vaId, 0.0, Duration.ZERO, Duration.ZERO, Instant.now()));
    }
}

