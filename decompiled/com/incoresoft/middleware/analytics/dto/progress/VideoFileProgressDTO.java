/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.progress;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;

@AnalyticsToken(value="progress")
public final class VideoFileProgressDTO
implements ServiceMetadata {
    @JSONField(name="stream_id")
    private final int streamId;
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="progress_percent")
    private final double progressPercent;
    @JSONField(name="progress_duration")
    private final long fileDuration;
    @JSONField(name="progress_timestamp")
    private final long currentProgress;
    @JSONField(name="timestamp")
    private final long timestamp;

    public VideoFileProgressDTO(VideoFileProgress progress) {
        this.streamId = progress.getStreamId();
        this.vaId = progress.getVaId();
        this.progressPercent = progress.getProgressPercent();
        this.fileDuration = progress.getFileDuration().toMillis();
        this.currentProgress = progress.getCurrentProgress().toMillis();
        this.timestamp = progress.getTimestamp().toEpochMilli();
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

    public long getFileDuration() {
        return this.fileDuration;
    }

    public long getCurrentProgress() {
        return this.currentProgress;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VideoFileProgressDTO)) {
            return false;
        }
        VideoFileProgressDTO other = (VideoFileProgressDTO)o;
        if (this.getStreamId() != other.getStreamId()) {
            return false;
        }
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (Double.compare(this.getProgressPercent(), other.getProgressPercent()) != 0) {
            return false;
        }
        if (this.getFileDuration() != other.getFileDuration()) {
            return false;
        }
        if (this.getCurrentProgress() != other.getCurrentProgress()) {
            return false;
        }
        return this.getTimestamp() == other.getTimestamp();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStreamId();
        result = result * 59 + this.getVaId();
        long $progressPercent = Double.doubleToLongBits(this.getProgressPercent());
        result = result * 59 + (int)($progressPercent >>> 32 ^ $progressPercent);
        long $fileDuration = this.getFileDuration();
        result = result * 59 + (int)($fileDuration >>> 32 ^ $fileDuration);
        long $currentProgress = this.getCurrentProgress();
        result = result * 59 + (int)($currentProgress >>> 32 ^ $currentProgress);
        long $timestamp = this.getTimestamp();
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        return result;
    }

    public String toString() {
        return "VideoFileProgressDTO(streamId=" + this.getStreamId() + ", vaId=" + this.getVaId() + ", progressPercent=" + this.getProgressPercent() + ", fileDuration=" + this.getFileDuration() + ", currentProgress=" + this.getCurrentProgress() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public VideoFileProgressDTO(int streamId, int vaId, double progressPercent, long fileDuration, long currentProgress, long timestamp) {
        this.streamId = streamId;
        this.vaId = vaId;
        this.progressPercent = progressPercent;
        this.fileDuration = fileDuration;
        this.currentProgress = currentProgress;
        this.timestamp = timestamp;
    }
}

