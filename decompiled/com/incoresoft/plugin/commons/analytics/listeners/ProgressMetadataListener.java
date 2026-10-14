/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.listeners;

import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.dto.progress.VideoFileProgressDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.analytics.fileprocessing.ws.VideoFileProgressWSAnswer;
import com.incoresoft.plugin.commons.http.websocket.NotificationsWS;
import com.incoresoft.plugin.commons.http.websocket.WSBroadcaster;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ProgressMetadataListener
extends ServiceMetadataListener<VideoFileProgressDTO> {
    @Deprecated(since="25.1.22")
    private final NotificationsWS notificationsWS;
    private final WSBroadcaster wsBroadcaster;
    private final VideoFileProgressTracker progressTracker;
    private final AnalyticsManager analyticsManager;
    private final StreamsManager streamsManager;
    private final String pluginName;
    private final String topic;

    public ProgressMetadataListener(@NotNull NotificationsWS notificationsWS, @NotNull VideoFileProgressTracker progressTracker, @NotNull AnalyticsManager analyticsManager, @NotNull StreamsManager streamsManager, @NotNull String pluginName, @NotNull String topic) {
        this.notificationsWS = Objects.requireNonNull(notificationsWS);
        this.wsBroadcaster = null;
        this.progressTracker = Objects.requireNonNull(progressTracker);
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.pluginName = Objects.requireNonNull(pluginName);
        this.topic = Objects.requireNonNull(topic);
    }

    public ProgressMetadataListener(@NotNull WSBroadcaster notificationsWS, @NotNull VideoFileProgressTracker progressTracker, @NotNull AnalyticsManager analyticsManager, @NotNull StreamsManager streamsManager, @NotNull String pluginName, @NotNull String topic) {
        this.notificationsWS = null;
        this.wsBroadcaster = Objects.requireNonNull(notificationsWS);
        this.progressTracker = Objects.requireNonNull(progressTracker);
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.pluginName = Objects.requireNonNull(pluginName);
        this.topic = Objects.requireNonNull(topic);
    }

    @Override
    public String pluginName() {
        return this.pluginName;
    }

    @Override
    public Class<VideoFileProgressDTO> metadataClass() {
        return VideoFileProgressDTO.class;
    }

    @Override
    public String analyticsTopic() {
        return this.topic;
    }

    @Override
    public void onMetadata(VideoFileProgressDTO message) {
        VideoFileProgress progress = new VideoFileProgress(message);
        Stream stream = (Stream)this.streamsManager.get(message.getStreamId());
        this.progressTracker.setProgress(progress);
        if (this.notificationsWS != null) {
            this.notificationsWS.broadcast(new VideoFileProgressWSAnswer(progress, stream));
        }
        if (this.wsBroadcaster != null) {
            this.wsBroadcaster.broadcast(new VideoFileProgressWSAnswer(progress, stream));
        }
        if (progress.getProgressPercent() == -1.0) {
            this.progressTracker.resetProgress(message.getVaId());
            this.analyticsManager.stop(message.getVaId());
            if (this.notificationsWS != null) {
                this.notificationsWS.broadcast(new VideoFileProgressWSAnswer(this.progressTracker.getProgress(progress.getVaId()), stream));
            }
            if (this.wsBroadcaster != null) {
                this.wsBroadcaster.broadcast(new VideoFileProgressWSAnswer(this.progressTracker.getProgress(progress.getVaId()), stream));
            }
        }
    }
}

