/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.InlineMe;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerBuilder;
import io.grpc.Internal;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.ServerStreamTracer;
import io.grpc.internal.FixedObjectPool;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InternalServer;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerImplBuilder;
import io.grpc.internal.SharedResourcePool;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServer;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.ReflectiveChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;

@CheckReturnValue
@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1784")
public final class NettyServerBuilder
extends ForwardingServerBuilder<NettyServerBuilder> {
    public static final int DEFAULT_FLOW_CONTROL_WINDOW = 0x100000;
    static final long MAX_CONNECTION_IDLE_NANOS_DISABLED = Long.MAX_VALUE;
    static final long MAX_CONNECTION_AGE_NANOS_DISABLED = Long.MAX_VALUE;
    static final long MAX_CONNECTION_AGE_GRACE_NANOS_INFINITE = Long.MAX_VALUE;
    static final int MAX_RST_COUNT_DISABLED = 0;
    private static final long MIN_MAX_CONNECTION_IDLE_NANO = TimeUnit.SECONDS.toNanos(1L);
    private static final long MIN_MAX_CONNECTION_AGE_NANO = TimeUnit.SECONDS.toNanos(1L);
    private static final long AS_LARGE_AS_INFINITE = TimeUnit.DAYS.toNanos(1000L);
    private static final ObjectPool<? extends EventLoopGroup> DEFAULT_BOSS_EVENT_LOOP_GROUP_POOL = SharedResourcePool.forResource(Utils.DEFAULT_BOSS_EVENT_LOOP_GROUP);
    private static final ObjectPool<? extends EventLoopGroup> DEFAULT_WORKER_EVENT_LOOP_GROUP_POOL = SharedResourcePool.forResource(Utils.DEFAULT_WORKER_EVENT_LOOP_GROUP);
    private final ServerImplBuilder serverImplBuilder;
    private final List<SocketAddress> listenAddresses = new ArrayList<SocketAddress>();
    private TransportTracer.Factory transportTracerFactory = TransportTracer.getDefaultFactory();
    private ChannelFactory<? extends ServerChannel> channelFactory = Utils.DEFAULT_SERVER_CHANNEL_FACTORY;
    private final Map<ChannelOption<?>, Object> channelOptions = new HashMap();
    private final Map<ChannelOption<?>, Object> childChannelOptions = new HashMap();
    private ObjectPool<? extends EventLoopGroup> bossEventLoopGroupPool = DEFAULT_BOSS_EVENT_LOOP_GROUP_POOL;
    private ObjectPool<? extends EventLoopGroup> workerEventLoopGroupPool = DEFAULT_WORKER_EVENT_LOOP_GROUP_POOL;
    private boolean forceHeapBuffer;
    private ProtocolNegotiator.ServerFactory protocolNegotiatorFactory;
    private final boolean freezeProtocolNegotiatorFactory;
    private int maxConcurrentCallsPerConnection = Integer.MAX_VALUE;
    private boolean autoFlowControl = true;
    private int flowControlWindow = 0x100000;
    private int maxMessageSize = 0x400000;
    private int maxHeaderListSize = 8192;
    private long keepAliveTimeInNanos = GrpcUtil.DEFAULT_SERVER_KEEPALIVE_TIME_NANOS;
    private long keepAliveTimeoutInNanos = GrpcUtil.DEFAULT_SERVER_KEEPALIVE_TIMEOUT_NANOS;
    private long maxConnectionIdleInNanos = Long.MAX_VALUE;
    private long maxConnectionAgeInNanos = Long.MAX_VALUE;
    private long maxConnectionAgeGraceInNanos = Long.MAX_VALUE;
    private boolean permitKeepAliveWithoutCalls;
    private long permitKeepAliveTimeInNanos = TimeUnit.MINUTES.toNanos(5L);
    private int maxRstCount;
    private long maxRstPeriodNanos;
    private Attributes eagAttributes = Attributes.EMPTY;

    public static NettyServerBuilder forPort(int port) {
        return NettyServerBuilder.forAddress(new InetSocketAddress(port));
    }

    public static NettyServerBuilder forPort(int port, ServerCredentials creds) {
        return NettyServerBuilder.forAddress(new InetSocketAddress(port), creds);
    }

    public static NettyServerBuilder forAddress(SocketAddress address) {
        return new NettyServerBuilder(address);
    }

    public static NettyServerBuilder forAddress(SocketAddress address, ServerCredentials creds) {
        ProtocolNegotiators.FromServerCredentialsResult result = ProtocolNegotiators.from(creds);
        if (result.error != null) {
            throw new IllegalArgumentException(result.error);
        }
        return new NettyServerBuilder(address, result.negotiator);
    }

    private NettyServerBuilder(SocketAddress address) {
        this.serverImplBuilder = new ServerImplBuilder(new NettyClientTransportServersBuilder());
        this.listenAddresses.add(address);
        this.protocolNegotiatorFactory = ProtocolNegotiators.serverPlaintextFactory();
        this.freezeProtocolNegotiatorFactory = false;
    }

    NettyServerBuilder(SocketAddress address, ProtocolNegotiator.ServerFactory negotiatorFactory) {
        this.serverImplBuilder = new ServerImplBuilder(new NettyClientTransportServersBuilder());
        this.listenAddresses.add(address);
        this.protocolNegotiatorFactory = Preconditions.checkNotNull(negotiatorFactory, "negotiatorFactory");
        this.freezeProtocolNegotiatorFactory = true;
    }

    @Override
    @Internal
    protected ServerBuilder<?> delegate() {
        return this.serverImplBuilder;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder addListenAddress(SocketAddress listenAddress) {
        this.listenAddresses.add(Preconditions.checkNotNull(listenAddress, "listenAddress"));
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder channelType(Class<? extends ServerChannel> channelType) {
        Preconditions.checkNotNull(channelType, "channelType");
        return this.channelFactory(new ReflectiveChannelFactory<ServerChannel>(channelType));
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder channelFactory(ChannelFactory<? extends ServerChannel> channelFactory) {
        this.channelFactory = Preconditions.checkNotNull(channelFactory, "channelFactory");
        return this;
    }

    @CanIgnoreReturnValue
    public <T> NettyServerBuilder withOption(ChannelOption<T> option, T value) {
        this.channelOptions.put(option, value);
        return this;
    }

    @CanIgnoreReturnValue
    public <T> NettyServerBuilder withChildOption(ChannelOption<T> option, T value) {
        this.childChannelOptions.put(option, value);
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder bossEventLoopGroup(EventLoopGroup group) {
        if (group != null) {
            return this.bossEventLoopGroupPool(new FixedObjectPool<EventLoopGroup>(group));
        }
        return this.bossEventLoopGroupPool(DEFAULT_BOSS_EVENT_LOOP_GROUP_POOL);
    }

    @CanIgnoreReturnValue
    NettyServerBuilder bossEventLoopGroupPool(ObjectPool<? extends EventLoopGroup> bossEventLoopGroupPool) {
        this.bossEventLoopGroupPool = Preconditions.checkNotNull(bossEventLoopGroupPool, "bossEventLoopGroupPool");
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder workerEventLoopGroup(EventLoopGroup group) {
        if (group != null) {
            return this.workerEventLoopGroupPool(new FixedObjectPool<EventLoopGroup>(group));
        }
        return this.workerEventLoopGroupPool(DEFAULT_WORKER_EVENT_LOOP_GROUP_POOL);
    }

    @CanIgnoreReturnValue
    NettyServerBuilder workerEventLoopGroupPool(ObjectPool<? extends EventLoopGroup> workerEventLoopGroupPool) {
        this.workerEventLoopGroupPool = Preconditions.checkNotNull(workerEventLoopGroupPool, "workerEventLoopGroupPool");
        return this;
    }

    void setForceHeapBuffer(boolean value) {
        this.forceHeapBuffer = value;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder sslContext(SslContext sslContext) {
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ServerCredentials");
        if (sslContext != null) {
            Preconditions.checkArgument(sslContext.isServer(), "Client SSL context can not be used for server");
            GrpcSslContexts.ensureAlpnAndH2Enabled(sslContext.applicationProtocolNegotiator());
            this.protocolNegotiatorFactory = ProtocolNegotiators.serverTlsFactory(sslContext);
        } else {
            this.protocolNegotiatorFactory = ProtocolNegotiators.serverPlaintextFactory();
        }
        return this;
    }

    @CanIgnoreReturnValue
    @Internal
    public final NettyServerBuilder protocolNegotiator(ProtocolNegotiator protocolNegotiator) {
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ServerCredentials");
        this.protocolNegotiatorFactory = ProtocolNegotiators.fixedServerFactory(protocolNegotiator);
        return this;
    }

    void setTracingEnabled(boolean value) {
        this.serverImplBuilder.setTracingEnabled(value);
    }

    void setStatsEnabled(boolean value) {
        this.serverImplBuilder.setStatsEnabled(value);
    }

    void setStatsRecordStartedRpcs(boolean value) {
        this.serverImplBuilder.setStatsRecordStartedRpcs(value);
    }

    void setStatsRecordRealTimeMetrics(boolean value) {
        this.serverImplBuilder.setStatsRecordRealTimeMetrics(value);
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder maxConcurrentCallsPerConnection(int maxCalls) {
        Preconditions.checkArgument(maxCalls > 0, "max must be positive: %s", maxCalls);
        this.maxConcurrentCallsPerConnection = maxCalls;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder initialFlowControlWindow(int initialFlowControlWindow) {
        Preconditions.checkArgument(initialFlowControlWindow > 0, "initialFlowControlWindow must be positive");
        this.flowControlWindow = initialFlowControlWindow;
        this.autoFlowControl = true;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder flowControlWindow(int flowControlWindow) {
        Preconditions.checkArgument(flowControlWindow > 0, "flowControlWindow must be positive: %s", flowControlWindow);
        this.flowControlWindow = flowControlWindow;
        this.autoFlowControl = false;
        return this;
    }

    @Deprecated
    @CanIgnoreReturnValue
    @InlineMe(replacement="this.maxInboundMessageSize(maxMessageSize)")
    public NettyServerBuilder maxMessageSize(int maxMessageSize) {
        return this.maxInboundMessageSize(maxMessageSize);
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder maxInboundMessageSize(int bytes) {
        Preconditions.checkArgument(bytes >= 0, "bytes must be non-negative: %s", bytes);
        this.maxMessageSize = bytes;
        return this;
    }

    @Deprecated
    @CanIgnoreReturnValue
    @InlineMe(replacement="this.maxInboundMetadataSize(maxHeaderListSize)")
    public NettyServerBuilder maxHeaderListSize(int maxHeaderListSize) {
        return this.maxInboundMetadataSize(maxHeaderListSize);
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder maxInboundMetadataSize(int bytes) {
        Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be positive: %s", bytes);
        this.maxHeaderListSize = bytes;
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        Preconditions.checkArgument(keepAliveTime > 0L, "keepalive time must be positive\uff1a%s", keepAliveTime);
        this.keepAliveTimeInNanos = timeUnit.toNanos(keepAliveTime);
        this.keepAliveTimeInNanos = KeepAliveManager.clampKeepAliveTimeInNanos(this.keepAliveTimeInNanos);
        if (this.keepAliveTimeInNanos >= AS_LARGE_AS_INFINITE) {
            this.keepAliveTimeInNanos = Long.MAX_VALUE;
        }
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        Preconditions.checkArgument(keepAliveTimeout > 0L, "keepalive timeout must be positive: %s", keepAliveTimeout);
        this.keepAliveTimeoutInNanos = timeUnit.toNanos(keepAliveTimeout);
        this.keepAliveTimeoutInNanos = KeepAliveManager.clampKeepAliveTimeoutInNanos(this.keepAliveTimeoutInNanos);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder maxConnectionIdle(long maxConnectionIdle, TimeUnit timeUnit) {
        Preconditions.checkArgument(maxConnectionIdle > 0L, "max connection idle must be positive: %s", maxConnectionIdle);
        this.maxConnectionIdleInNanos = timeUnit.toNanos(maxConnectionIdle);
        if (this.maxConnectionIdleInNanos >= AS_LARGE_AS_INFINITE) {
            this.maxConnectionIdleInNanos = Long.MAX_VALUE;
        }
        if (this.maxConnectionIdleInNanos < MIN_MAX_CONNECTION_IDLE_NANO) {
            this.maxConnectionIdleInNanos = MIN_MAX_CONNECTION_IDLE_NANO;
        }
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder maxConnectionAge(long maxConnectionAge, TimeUnit timeUnit) {
        Preconditions.checkArgument(maxConnectionAge > 0L, "max connection age must be positive: %s", maxConnectionAge);
        this.maxConnectionAgeInNanos = timeUnit.toNanos(maxConnectionAge);
        if (this.maxConnectionAgeInNanos >= AS_LARGE_AS_INFINITE) {
            this.maxConnectionAgeInNanos = Long.MAX_VALUE;
        }
        if (this.maxConnectionAgeInNanos < MIN_MAX_CONNECTION_AGE_NANO) {
            this.maxConnectionAgeInNanos = MIN_MAX_CONNECTION_AGE_NANO;
        }
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder maxConnectionAgeGrace(long maxConnectionAgeGrace, TimeUnit timeUnit) {
        Preconditions.checkArgument(maxConnectionAgeGrace >= 0L, "max connection age grace must be non-negative: %s", maxConnectionAgeGrace);
        this.maxConnectionAgeGraceInNanos = timeUnit.toNanos(maxConnectionAgeGrace);
        if (this.maxConnectionAgeGraceInNanos >= AS_LARGE_AS_INFINITE) {
            this.maxConnectionAgeGraceInNanos = Long.MAX_VALUE;
        }
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder permitKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        Preconditions.checkArgument(keepAliveTime >= 0L, "permit keepalive time must be non-negative: %s", keepAliveTime);
        this.permitKeepAliveTimeInNanos = timeUnit.toNanos(keepAliveTime);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder permitKeepAliveWithoutCalls(boolean permit) {
        this.permitKeepAliveWithoutCalls = permit;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyServerBuilder maxRstFramesPerWindow(int maxRstStream, int secondsPerWindow) {
        Preconditions.checkArgument(maxRstStream > 0, "maxRstStream must be positive");
        Preconditions.checkArgument(secondsPerWindow > 0, "secondsPerWindow must be positive");
        if (maxRstStream == Integer.MAX_VALUE) {
            maxRstStream = 0;
        }
        this.maxRstCount = maxRstStream;
        this.maxRstPeriodNanos = TimeUnit.SECONDS.toNanos(secondsPerWindow);
        return this;
    }

    void eagAttributes(Attributes eagAttributes) {
        this.eagAttributes = Preconditions.checkNotNull(eagAttributes, "eagAttributes");
    }

    NettyServer buildTransportServers(List<? extends ServerStreamTracer.Factory> streamTracerFactories) {
        this.assertEventLoopsAndChannelType();
        ProtocolNegotiator negotiator = this.protocolNegotiatorFactory.newNegotiator(this.serverImplBuilder.getExecutorPool());
        return new NettyServer(this.listenAddresses, this.channelFactory, this.channelOptions, this.childChannelOptions, this.bossEventLoopGroupPool, this.workerEventLoopGroupPool, this.forceHeapBuffer, negotiator, streamTracerFactories, this.transportTracerFactory, this.maxConcurrentCallsPerConnection, this.autoFlowControl, this.flowControlWindow, this.maxMessageSize, this.maxHeaderListSize, this.keepAliveTimeInNanos, this.keepAliveTimeoutInNanos, this.maxConnectionIdleInNanos, this.maxConnectionAgeInNanos, this.maxConnectionAgeGraceInNanos, this.permitKeepAliveWithoutCalls, this.permitKeepAliveTimeInNanos, this.maxRstCount, this.maxRstPeriodNanos, this.eagAttributes, this.serverImplBuilder.getChannelz());
    }

    @VisibleForTesting
    void assertEventLoopsAndChannelType() {
        boolean allProvided = this.channelFactory != Utils.DEFAULT_SERVER_CHANNEL_FACTORY && this.bossEventLoopGroupPool != DEFAULT_BOSS_EVENT_LOOP_GROUP_POOL && this.workerEventLoopGroupPool != DEFAULT_WORKER_EVENT_LOOP_GROUP_POOL;
        boolean nonProvided = this.channelFactory == Utils.DEFAULT_SERVER_CHANNEL_FACTORY && this.bossEventLoopGroupPool == DEFAULT_BOSS_EVENT_LOOP_GROUP_POOL && this.workerEventLoopGroupPool == DEFAULT_WORKER_EVENT_LOOP_GROUP_POOL;
        Preconditions.checkState(allProvided || nonProvided, "All of BossEventLoopGroup, WorkerEventLoopGroup and ChannelType should be provided or neither should be");
    }

    @CanIgnoreReturnValue
    NettyServerBuilder setTransportTracerFactory(TransportTracer.Factory transportTracerFactory) {
        this.transportTracerFactory = transportTracerFactory;
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder useTransportSecurity(File certChain, File privateKey) {
        SslContext sslContext;
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ServerCredentials");
        try {
            sslContext = GrpcSslContexts.forServer(certChain, privateKey).build();
        }
        catch (SSLException e) {
            throw new RuntimeException(e);
        }
        this.protocolNegotiatorFactory = ProtocolNegotiators.serverTlsFactory(sslContext);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyServerBuilder useTransportSecurity(InputStream certChain, InputStream privateKey) {
        SslContext sslContext;
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ServerCredentials");
        try {
            sslContext = GrpcSslContexts.forServer(certChain, privateKey).build();
        }
        catch (SSLException e) {
            throw new RuntimeException(e);
        }
        this.protocolNegotiatorFactory = ProtocolNegotiators.serverTlsFactory(sslContext);
        return this;
    }

    private final class NettyClientTransportServersBuilder
    implements ServerImplBuilder.ClientTransportServersBuilder {
        private NettyClientTransportServersBuilder() {
        }

        @Override
        public InternalServer buildClientTransportServers(List<? extends ServerStreamTracer.Factory> streamTracerFactories) {
            return NettyServerBuilder.this.buildTransportServers(streamTracerFactories);
        }
    }
}

