/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.grpc.CallCredentials;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qdrant.client.ApiKeyCredentials;
import io.qdrant.client.grpc.CollectionsGrpc;
import io.qdrant.client.grpc.PointsGrpc;
import io.qdrant.client.grpc.QdrantGrpc;
import io.qdrant.client.grpc.SnapshotsGrpc;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QdrantGrpcClient
implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(QdrantGrpcClient.class);
    @Nullable
    private final CallCredentials callCredentials;
    private final ManagedChannel channel;
    private final boolean shutdownChannelOnClose;
    @Nullable
    private final Duration timeout;

    QdrantGrpcClient(ManagedChannel channel, boolean shutdownChannelOnClose, @Nullable CallCredentials callCredentials, @Nullable Duration timeout2) {
        this.callCredentials = callCredentials;
        this.channel = channel;
        this.shutdownChannelOnClose = shutdownChannelOnClose;
        this.timeout = timeout2;
    }

    public static Builder newBuilder(ManagedChannel channel) {
        return new Builder(channel, false);
    }

    public static Builder newBuilder(ManagedChannel channel, boolean shutdownChannelOnClose) {
        return new Builder(channel, shutdownChannelOnClose);
    }

    public static Builder newBuilder(String host2) {
        return new Builder(host2, 6334, true);
    }

    public static Builder newBuilder(String host2, int port) {
        return new Builder(host2, port, true);
    }

    public static Builder newBuilder(String host2, int port, boolean useTransportLayerSecurity) {
        return new Builder(host2, port, useTransportLayerSecurity);
    }

    public ManagedChannel channel() {
        return this.channel;
    }

    public QdrantGrpc.QdrantFutureStub qdrant() {
        return (QdrantGrpc.QdrantFutureStub)((QdrantGrpc.QdrantFutureStub)QdrantGrpc.newFutureStub(this.channel).withCallCredentials(this.callCredentials)).withDeadline(this.timeout != null ? Deadline.after(this.timeout.toMillis(), TimeUnit.MILLISECONDS) : null);
    }

    public PointsGrpc.PointsFutureStub points() {
        return (PointsGrpc.PointsFutureStub)((PointsGrpc.PointsFutureStub)PointsGrpc.newFutureStub(this.channel).withCallCredentials(this.callCredentials)).withDeadline(this.timeout != null ? Deadline.after(this.timeout.toMillis(), TimeUnit.MILLISECONDS) : null);
    }

    public CollectionsGrpc.CollectionsFutureStub collections() {
        return (CollectionsGrpc.CollectionsFutureStub)((CollectionsGrpc.CollectionsFutureStub)CollectionsGrpc.newFutureStub(this.channel).withCallCredentials(this.callCredentials)).withDeadline(this.timeout != null ? Deadline.after(this.timeout.toMillis(), TimeUnit.MILLISECONDS) : null);
    }

    public SnapshotsGrpc.SnapshotsFutureStub snapshots() {
        return (SnapshotsGrpc.SnapshotsFutureStub)((SnapshotsGrpc.SnapshotsFutureStub)SnapshotsGrpc.newFutureStub(this.channel).withCallCredentials(this.callCredentials)).withDeadline(this.timeout != null ? Deadline.after(this.timeout.toMillis(), TimeUnit.MILLISECONDS) : null);
    }

    @Override
    public void close() {
        if (this.shutdownChannelOnClose && !this.channel.isShutdown() && !this.channel.isTerminated()) {
            try {
                this.channel.shutdown().awaitTermination(5L, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                logger.warn("exception thrown when shutting down channel", e);
            }
        }
    }

    public static class Builder {
        private final ManagedChannel channel;
        private final boolean shutdownChannelOnClose;
        @Nullable
        private CallCredentials callCredentials;
        @Nullable
        private Duration timeout;

        Builder(ManagedChannel channel, boolean shutdownChannelOnClose) {
            this.channel = channel;
            this.shutdownChannelOnClose = shutdownChannelOnClose;
        }

        Builder(String host2, int port, boolean useTransportLayerSecurity) {
            this.channel = Builder.createChannel(host2, port, useTransportLayerSecurity);
            this.shutdownChannelOnClose = true;
        }

        public Builder withApiKey(String apiKey) {
            this.callCredentials = new ApiKeyCredentials(apiKey);
            return this;
        }

        public Builder withTimeout(@Nullable Duration timeout2) {
            this.timeout = timeout2;
            return this;
        }

        public Builder withCallCredentials(@Nullable CallCredentials callCredentials) {
            this.callCredentials = callCredentials;
            return this;
        }

        public QdrantGrpcClient build() {
            return new QdrantGrpcClient(this.channel, this.shutdownChannelOnClose, this.callCredentials, this.timeout);
        }

        private static ManagedChannel createChannel(String host2, int port, boolean useTransportLayerSecurity) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host2, port);
            if (useTransportLayerSecurity) {
                channelBuilder.useTransportSecurity();
            } else {
                channelBuilder.usePlaintext();
            }
            return channelBuilder.build();
        }
    }
}

