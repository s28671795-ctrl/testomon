/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;

public class AnalyticsStateEventsPublisher
implements AnalyticsStateListener {
    private final MessageQueueConnector messageQueueConnector;
    private final VideoFileProgressTracker progressTracker;
    private final StreamManagerImpl streamManager;
    private final UsersManagerImpl usersManager;

    @Inject
    public void init(AnalyticsManager analyticsManager) {
        analyticsManager.addAnalyticsStateListener(this);
    }

    @Override
    public void onAnalyticsPending(Analytics analytics) {
        this.messageQueueConnector.publish(AnalyticsStateEventsPublisher.getSubject("pending", analytics), new byte[0]);
    }

    @Override
    public void onAnalyticsStarted(Analytics analytics) {
        this.messageQueueConnector.publish(AnalyticsStateEventsPublisher.getSubject("started", analytics), new byte[0]);
    }

    @Override
    public void onAnalyticsStopped(Analytics analytics) {
        this.messageQueueConnector.publish(AnalyticsStateEventsPublisher.getSubject("stopped", analytics), new byte[0]);
    }

    @Override
    public void onAnalyticsPaused(Analytics analytics) {
        this.messageQueueConnector.publish(AnalyticsStateEventsPublisher.getSubject("paused", analytics), new byte[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onAnalyticsDeleted(Analytics analytics) {
        byte[] body2 = new byte[]{};
        try {
            AnalyticsDTO dto = new AnalyticsDTO(this.usersManager.get(1), analytics, this.streamManager.get(analytics.getStreamUUID()), this.progressTracker.getProgress(analytics.getId()));
            body2 = Serialization.writeValueAsString(dto).getBytes();
        }
        finally {
            this.messageQueueConnector.publish(AnalyticsStateEventsPublisher.getSubject("deleted", analytics), body2);
        }
    }

    private static String getSubject(String state, Analytics analytics) {
        return String.format("vezha.analytics.%s.state.%s", analytics.getId(), state);
    }

    @Inject
    public AnalyticsStateEventsPublisher(MessageQueueConnector messageQueueConnector, VideoFileProgressTracker progressTracker, StreamManagerImpl streamManager, UsersManagerImpl usersManager) {
        this.messageQueueConnector = messageQueueConnector;
        this.progressTracker = progressTracker;
        this.streamManager = streamManager;
        this.usersManager = usersManager;
    }
}

