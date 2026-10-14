/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.cameraanalytics;

import com.google.inject.Inject;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequestListener;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequestReceiver;
import com.incoresoft.vezha.dto.servers.cameraanalytics.InitializationDTO;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializationRequester {
    private static final Logger log = LoggerFactory.getLogger(InitializationRequester.class);
    private final MessageQueueConnector messageQueueConnector;
    private final InitializationRequestReceiver initializationRequestReceiver;

    @Inject
    public InitializationRequester(@NotNull MessageQueueConnector messageQueueConnector, @NotNull InitializationRequestReceiver initializationRequestReceiver) {
        this.messageQueueConnector = Objects.requireNonNull(messageQueueConnector);
        this.initializationRequestReceiver = Objects.requireNonNull(initializationRequestReceiver);
    }

    public void init() {
        this.requestInitialization();
    }

    public void requestInitialization() {
        this.messageQueueConnector.publish("request-camera-analytics-initialization", new byte[0]);
    }

    public void requestInitialization(String serverId) {
        this.messageQueueConnector.publish("request-camera-analytics-initialization-%s".formatted(serverId), new byte[0]);
    }

    public void requestInitializationAsync(String serverId) {
        InitializationRequestListenerImpl listener = new InitializationRequestListenerImpl(this.initializationRequestReceiver);
        this.initializationRequestReceiver.addListener(listener);
        this.requestInitialization(serverId);
        listener.await(Duration.ofSeconds(10L));
    }

    static class InitializationRequestListenerImpl
    implements InitializationRequestListener {
        private final InitializationRequestReceiver initializationRequestReceiver;
        private final CountDownLatch latch;

        public InitializationRequestListenerImpl(@NotNull InitializationRequestReceiver initializationRequestReceiver) {
            this.initializationRequestReceiver = Objects.requireNonNull(initializationRequestReceiver);
            this.latch = new CountDownLatch(1);
        }

        public void await(Duration timeout2) {
            try {
                boolean timeoutReached;
                boolean bl = timeoutReached = !this.latch.await(timeout2.toMillis(), TimeUnit.MILLISECONDS);
                if (timeoutReached) {
                    throw new RuntimeException("Initialization request timeout");
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onInitializationRequest(InitializationDTO initializationDTO) {
            this.latch.countDown();
            this.initializationRequestReceiver.removeListener(this);
        }
    }
}

