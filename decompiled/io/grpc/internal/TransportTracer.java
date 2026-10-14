/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.InternalChannelz;
import io.grpc.internal.LongCounter;
import io.grpc.internal.LongCounterFactory;
import io.grpc.internal.TimeProvider;

public final class TransportTracer {
    private static final Factory DEFAULT_FACTORY = new Factory(TimeProvider.SYSTEM_TIME_PROVIDER);
    private final TimeProvider timeProvider;
    private long streamsStarted;
    private long lastLocalStreamCreatedTimeNanos;
    private long lastRemoteStreamCreatedTimeNanos;
    private long streamsSucceeded;
    private long streamsFailed;
    private long keepAlivesSent;
    private FlowControlReader flowControlWindowReader;
    private long messagesSent;
    private long lastMessageSentTimeNanos;
    private final LongCounter messagesReceived = LongCounterFactory.create();
    private volatile long lastMessageReceivedTimeNanos;

    public TransportTracer() {
        this.timeProvider = TimeProvider.SYSTEM_TIME_PROVIDER;
    }

    private TransportTracer(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public InternalChannelz.TransportStats getStats() {
        long localFlowControlWindow = this.flowControlWindowReader == null ? -1L : this.flowControlWindowReader.read().localBytes;
        long remoteFlowControlWindow = this.flowControlWindowReader == null ? -1L : this.flowControlWindowReader.read().remoteBytes;
        return new InternalChannelz.TransportStats(this.streamsStarted, this.lastLocalStreamCreatedTimeNanos, this.lastRemoteStreamCreatedTimeNanos, this.streamsSucceeded, this.streamsFailed, this.messagesSent, this.messagesReceived.value(), this.keepAlivesSent, this.lastMessageSentTimeNanos, this.lastMessageReceivedTimeNanos, localFlowControlWindow, remoteFlowControlWindow);
    }

    public void reportLocalStreamStarted() {
        ++this.streamsStarted;
        this.lastLocalStreamCreatedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportRemoteStreamStarted() {
        ++this.streamsStarted;
        this.lastRemoteStreamCreatedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportStreamClosed(boolean success) {
        if (success) {
            ++this.streamsSucceeded;
        } else {
            ++this.streamsFailed;
        }
    }

    public void reportMessageSent(int numMessages) {
        if (numMessages == 0) {
            return;
        }
        this.messagesSent += (long)numMessages;
        this.lastMessageSentTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportMessageReceived() {
        this.messagesReceived.add(1L);
        this.lastMessageReceivedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportKeepAliveSent() {
        ++this.keepAlivesSent;
    }

    public void setFlowControlWindowReader(FlowControlReader flowControlWindowReader) {
        this.flowControlWindowReader = Preconditions.checkNotNull(flowControlWindowReader);
    }

    public static Factory getDefaultFactory() {
        return DEFAULT_FACTORY;
    }

    public static final class Factory {
        private final TimeProvider timeProvider;

        @VisibleForTesting
        public Factory(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        public TransportTracer create() {
            return new TransportTracer(this.timeProvider);
        }
    }

    public static interface FlowControlReader {
        public FlowControlWindows read();
    }

    public static final class FlowControlWindows {
        public final long remoteBytes;
        public final long localBytes;

        public FlowControlWindows(long localBytes, long remoteBytes) {
            this.localBytes = localBytes;
            this.remoteBytes = remoteBytes;
        }
    }
}

