/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models;

import com.incoresoft.middleware.analytics.dto.progress.VideoFileProgressDTO;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class VideoFileProgress {
    private final int streamId;
    private final int vaId;
    private final double progressPercent;
    private final Duration fileDuration;
    private final Duration currentProgress;
    private final Instant timestamp;

    public VideoFileProgress(VideoFileProgressDTO dto) {
        this.streamId = dto.getStreamId();
        this.vaId = dto.getVaId();
        this.progressPercent = dto.getProgressPercent();
        this.fileDuration = Duration.of(dto.getFileDuration(), ChronoUnit.MILLIS);
        this.currentProgress = Duration.of(dto.getCurrentProgress(), ChronoUnit.MILLIS);
        this.timestamp = Instant.ofEpochMilli(dto.getTimestamp());
    }

    public int getStreamId() {
        return this.streamId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public double getProgressPercent() {
        return this.progressPercent;
    }

    public Duration getFileDuration() {
        return this.fileDuration;
    }

    public Duration getCurrentProgress() {
        return this.currentProgress;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VideoFileProgress)) {
            return false;
        }
        VideoFileProgress other = (VideoFileProgress)o;
        if (this.getStreamId() != other.getStreamId()) {
            return false;
        }
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (Double.compare(this.getProgressPercent(), other.getProgressPercent()) != 0) {
            return false;
        }
        Duration this$fileDuration = this.getFileDuration();
        Duration other$fileDuration = other.getFileDuration();
        if (this$fileDuration == null ? other$fileDuration != null : !((Object)this$fileDuration).equals(other$fileDuration)) {
            return false;
        }
        Duration this$currentProgress = this.getCurrentProgress();
        Duration other$currentProgress = other.getCurrentProgress();
        if (this$currentProgress == null ? other$currentProgress != null : !((Object)this$currentProgress).equals(other$currentProgress)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStreamId();
        result = result * 59 + this.getVaId();
        long $progressPercent = Double.doubleToLongBits(this.getProgressPercent());
        result = result * 59 + (int)($progressPercent >>> 32 ^ $progressPercent);
        Duration $fileDuration = this.getFileDuration();
        result = result * 59 + ($fileDuration == null ? 43 : ((Object)$fileDuration).hashCode());
        Duration $currentProgress = this.getCurrentProgress();
        result = result * 59 + ($currentProgress == null ? 43 : ((Object)$currentProgress).hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "VideoFileProgress(streamId=" + this.getStreamId() + ", vaId=" + this.getVaId() + ", progressPercent=" + this.getProgressPercent() + ", fileDuration=" + String.valueOf(this.getFileDuration()) + ", currentProgress=" + String.valueOf(this.getCurrentProgress()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public VideoFileProgress(int streamId, int vaId, double progressPercent, Duration fileDuration, Duration currentProgress, Instant timestamp) {
        this.streamId = streamId;
        this.vaId = vaId;
        this.progressPercent = progressPercent;
        this.fileDuration = fileDuration;
        this.currentProgress = currentProgress;
        this.timestamp = timestamp;
    }
}

