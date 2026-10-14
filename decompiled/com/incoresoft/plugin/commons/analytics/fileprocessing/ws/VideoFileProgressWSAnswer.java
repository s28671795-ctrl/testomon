/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.fileprocessing.ws;

import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.analytics.fileprocessing.ws.VideoFileWSAnswer;
import java.time.Duration;

public class VideoFileProgressWSAnswer
extends VideoFileWSAnswer {
    public VideoFileProgressWSAnswer(VideoFileProgress progress, Stream stream) {
        super("VIDEO_FILE_PROGRESS", progress);
        this.data.put("progress_percentage", (Object)progress.getProgressPercent());
        Duration fileDuration = stream.getDuration();
        this.data.put("file_duration", (Object)String.format("%02d:%02d:%02d", fileDuration.toHoursPart(), fileDuration.toMinutesPart(), fileDuration.toSecondsPart()));
        Duration currentProgress = progress.getCurrentProgress();
        this.data.put("progress_time", (Object)String.format("%02d:%02d:%02d", currentProgress.toHoursPart(), currentProgress.toMinutesPart(), currentProgress.toSecondsPart()));
    }
}

