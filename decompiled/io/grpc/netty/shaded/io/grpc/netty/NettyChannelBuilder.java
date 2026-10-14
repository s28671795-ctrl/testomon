/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.InlineMe;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingChannelBuilder2;
import io.grpc.HttpConnectProxiedSocketAddress;
import io.grpc.Internal;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.AtomicBackoff;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.FixedObjectPool;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.ManagedChannelImplBuilder;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.SharedResourcePool;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientTransport;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.ReflectiveChannelFactory;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLException;

@CheckReturnValue
@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1784")
public final class NettyChannelBuilder
extends ForwardingChannelBuilder2<NettyChannelBuilder> {
    public static final int DEFAULT_FLOW_CONTROL_WINDOW = 0x100000;
    private static final boolean DEFAULT_AUTO_FLOW_CONTROL;
    private static final long AS_LARGE_AS_INFINITE;
    private static final ChannelFactory<? extends Channel> DEFAULT_CHANNEL_FACTORY;
    private static final ObjectPool<? extends EventLoopGroup> DEFAULT_EVENT_LOOP_GROUP_POOL;
    private final ManagedChannelImplBuilder managedChannelImplBuilder;
    private TransportTracer.Factory transportTracerFactory = TransportTracer.getDefaultFactory();
    private final Map<ChannelOption<?>, Object> channelOptions = new HashMap();
    private ChannelFactory<? extends Channel> channelFactory = DEFAULT_CHANNEL_FACTORY;
    private ObjectPool<? extends EventLoopGroup> eventLoopGroupPool = DEFAULT_EVENT_LOOP_GROUP_POOL;
    private boolean autoFlowControl = DEFAULT_AUTO_FLOW_CONTROL;
    private int flowControlWindow = 0x100000;
    private int maxHeaderListSize = 8192;
    private int maxInboundMessageSize = 0x400000;
    private long keepAliveTimeNanos = Long.MAX_VALUE;
    private long keepAliveTimeoutNanos = GrpcUtil.DEFAULT_KEEPALIVE_TIMEOUT_NANOS;
    private boolean keepAliveWithoutCalls;
    private ProtocolNegotiator.ClientFactory protocolNegotiatorFactory = new DefaultProtocolNegotiator();
    private final boolean freezeProtocolNegotiatorFactory;
    private LocalSocketPicker localSocketPicker;
    private final boolean useGetForSafeMethods = false;
    private Class<? extends SocketAddress> transportSocketType = InetSocketAddress.class;

    public static NettyChannelBuilder forAddress(SocketAddress serverAddress) {
        return new NettyChannelBuilder(serverAddress);
    }

    public static NettyChannelBuilder forAddress(SocketAddress serverAddress, ChannelCredentials creds) {
        ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(creds);
        if (result.error != null) {
            throw new IllegalArgumentException(result.error);
        }
        return new NettyChannelBuilder(serverAddress, creds, result.callCredentials, result.negotiator);
    }

    public static NettyChannelBuilder forAddress(String host2, int port) {
        return NettyChannelBuilder.forTarget(GrpcUtil.authorityFromHostAndPort(host2, port));
    }

    public static NettyChannelBuilder forAddress(String host2, int port, ChannelCredentials creds) {
        return NettyChannelBuilder.forTarget(GrpcUtil.authorityFromHostAndPort(host2, port), creds);
    }

    public static NettyChannelBuilder forTarget(String target) {
        return new NettyChannelBuilder(target);
    }

    public static NettyChannelBuilder forTarget(String target, ChannelCredentials creds) {
        ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(creds);
        if (result.error != null) {
            throw new IllegalArgumentException(result.error);
        }
        return new NettyChannelBuilder(target, creds, result.callCredentials, result.negotiator);
    }

    NettyChannelBuilder(String target) {
        this.managedChannelImplBuilder = new ManagedChannelImplBuilder(target, new NettyChannelTransportFactoryBuilder(), new NettyChannelDefaultPortProvider());
        this.freezeProtocolNegotiatorFactory = false;
    }

    NettyChannelBuilder(String target, ChannelCredentials channelCreds, CallCredentials callCreds, ProtocolNegotiator.ClientFactory negotiator) {
        this.managedChannelImplBuilder = new ManagedChannelImplBuilder(target, channelCreds, callCreds, new NettyChannelTransportFactoryBuilder(), new NettyChannelDefaultPortProvider());
        this.protocolNegotiatorFactory = Preconditions.checkNotNull(negotiator, "negotiator");
        this.freezeProtocolNegotiatorFactory = true;
    }

    NettyChannelBuilder(SocketAddress address) {
        this.managedChannelImplBuilder = new ManagedChannelImplBuilder(address, NettyChannelBuilder.getAuthorityFromAddress(address), new NettyChannelTransportFactoryBuilder(), new NettyChannelDefaultPortProvider());
        this.freezeProtocolNegotiatorFactory = false;
    }

    NettyChannelBuilder(SocketAddress address, ChannelCredentials channelCreds, CallCredentials callCreds, ProtocolNegotiator.ClientFactory negotiator) {
        this.managedChannelImplBuilder = new ManagedChannelImplBuilder(address, NettyChannelBuilder.getAuthorityFromAddress(address), channelCreds, callCreds, new NettyChannelTransportFactoryBuilder(), new NettyChannelDefaultPortProvider());
        this.protocolNegotiatorFactory = Preconditions.checkNotNull(negotiator, "negotiator");
        this.freezeProtocolNegotiatorFactory = true;
    }

    @Override
    @Internal
    protected ManagedChannelBuilder<?> delegate() {
        return this.managedChannelImplBuilder;
    }

    private static String getAuthorityFromAddress(SocketAddress address) {
        if (address instanceof InetSocketAddress) {
            InetSocketAddress inetAddress = (InetSocketAddress)address;
            return GrpcUtil.authorityFromHostAndPort(inetAddress.getHostString(), inetAddress.getPort());
        }
        return address.toString();
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder channelType(Class<? extends Channel> channelType) {
        return this.channelType(channelType, null);
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder channelType(Class<? extends Channel> channelType, @Nullable Class<? extends SocketAddress> transportSocketType) {
        Preconditions.checkNotNull(channelType, "channelType");
        return this.channelFactory(new ReflectiveChannelFactory<Channel>(channelType), transportSocketType);
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder channelFactory(ChannelFactory<? extends Channel> channelFactory) {
        return this.channelFactory(channelFactory, null);
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder channelFactory(ChannelFactory<? extends Channel> channelFactory, @Nullable Class<? extends SocketAddress> transportSocketType) {
        this.channelFactory = Preconditions.checkNotNull(channelFactory, "channelFactory");
        this.transportSocketType = transportSocketType;
        return this;
    }

    @CanIgnoreReturnValue
    public <T> NettyChannelBuilder withOption(ChannelOption<T> option, T value) {
        this.channelOptions.put(option, value);
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder negotiationType(NegotiationType type) {
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ChannelCredentials");
        if (!(this.protocolNegotiatorFactory instanceof DefaultProtocolNegotiator)) {
            return this;
        }
        ((DefaultProtocolNegotiator)this.protocolNegotiatorFactory).negotiationType = type;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder eventLoopGroup(@Nullable EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup != null) {
            return this.eventLoopGroupPool(new FixedObjectPool<EventLoopGroup>(eventLoopGroup));
        }
        return this.eventLoopGroupPool(DEFAULT_EVENT_LOOP_GROUP_POOL);
    }

    @CanIgnoreReturnValue
    NettyChannelBuilder eventLoopGroupPool(ObjectPool<? extends EventLoopGroup> eventLoopGroupPool) {
        this.eventLoopGroupPool = Preconditions.checkNotNull(eventLoopGroupPool, "eventLoopGroupPool");
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder sslContext(SslContext sslContext) {
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ChannelCredentials");
        if (sslContext != null) {
            Preconditions.checkArgument(sslContext.isClient(), "Server SSL context can not be used for client channel");
            GrpcSslContexts.ensureAlpnAndH2Enabled(sslContext.applicationProtocolNegotiator());
        }
        if (!(this.protocolNegotiatorFactory instanceof DefaultProtocolNegotiator)) {
            return this;
        }
        ((DefaultProtocolNegotiator)this.protocolNegotiatorFactory).sslContext = sslContext;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder initialFlowControlWindow(int initialFlowControlWindow) {
        Preconditions.checkArgument(initialFlowControlWindow > 0, "initialFlowControlWindow must be positive");
        this.flowControlWindow = initialFlowControlWindow;
        this.autoFlowControl = true;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder flowControlWindow(int flowControlWindow) {
        Preconditions.checkArgument(flowControlWindow > 0, "flowControlWindow must be positive");
        this.flowControlWindow = flowControlWindow;
        this.autoFlowControl = false;
        return this;
    }

    @Deprecated
    @CanIgnoreReturnValue
    @InlineMe(replacement="this.maxInboundMetadataSize(maxHeaderListSize)")
    public NettyChannelBuilder maxHeaderListSize(int maxHeaderListSize) {
        return this.maxInboundMetadataSize(maxHeaderListSize);
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder maxInboundMetadataSize(int bytes) {
        Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be > 0");
        this.maxHeaderListSize = bytes;
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder usePlaintext() {
        this.negotiationType(NegotiationType.PLAINTEXT);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder useTransportSecurity() {
        this.negotiationType(NegotiationType.TLS);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        Preconditions.checkArgument(keepAliveTime > 0L, "keepalive time must be positive");
        this.keepAliveTimeNanos = timeUnit.toNanos(keepAliveTime);
        this.keepAliveTimeNanos = KeepAliveManager.clampKeepAliveTimeInNanos(this.keepAliveTimeNanos);
        if (this.keepAliveTimeNanos >= AS_LARGE_AS_INFINITE) {
            this.keepAliveTimeNanos = Long.MAX_VALUE;
        }
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        Preconditions.checkArgument(keepAliveTimeout > 0L, "keepalive timeout must be positive");
        this.keepAliveTimeoutNanos = timeUnit.toNanos(keepAliveTimeout);
        this.keepAliveTimeoutNanos = KeepAliveManager.clampKeepAliveTimeoutInNanos(this.keepAliveTimeoutNanos);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder keepAliveWithoutCalls(boolean enable) {
        this.keepAliveWithoutCalls = enable;
        return this;
    }

    @CanIgnoreReturnValue
    public NettyChannelBuilder localSocketPicker(@Nullable LocalSocketPicker localSocketPicker) {
        this.localSocketPicker = localSocketPicker;
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public NettyChannelBuilder maxInboundMessageSize(int max) {
        Preconditions.checkArgument(max >= 0, "negative max");
        this.maxInboundMessageSize = max;
        return this;
    }

    ClientTransportFactory buildTransportFactory() {
        this.assertEventLoopAndChannelType();
        ProtocolNegotiator negotiator = this.protocolNegotiatorFactory.newNegotiator();
        return new NettyTransportFactory(negotiator, this.channelFactory, this.channelOptions, this.eventLoopGroupPool, this.autoFlowControl, this.flowControlWindow, this.maxInboundMessageSize, this.maxHeaderListSize, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls, this.transportTracerFactory, this.localSocketPicker, false, this.transportSocketType);
    }

    @VisibleForTesting
    void assertEventLoopAndChannelType() {
        boolean bothProvided = this.channelFactory != DEFAULT_CHANNEL_FACTORY && this.eventLoopGroupPool != DEFAULT_EVENT_LOOP_GROUP_POOL;
        boolean nonProvided = this.channelFactory == DEFAULT_CHANNEL_FACTORY && this.eventLoopGroupPool == DEFAULT_EVENT_LOOP_GROUP_POOL;
        Preconditions.checkState(bothProvided || nonProvided, "Both EventLoopGroup and ChannelType should be provided or neither should be");
    }

    int getDefaultPort() {
        return this.protocolNegotiatorFactory.getDefaultPort();
    }

    @VisibleForTesting
    static ProtocolNegotiator createProtocolNegotiatorByType(NegotiationType negotiationType, SslContext sslContext, ObjectPool<? extends Executor> executorPool) {
        switch (negotiationType) {
            case PLAINTEXT: {
                return ProtocolNegotiators.plaintext();
            }
            case PLAINTEXT_UPGRADE: {
                return ProtocolNegotiators.plaintextUpgrade();
            }
            case TLS: {
                return ProtocolNegotiators.tls(sslContext, executorPool);
            }
        }
        throw new IllegalArgumentException("Unsupported negotiationType: " + (Object)((Object)negotiationType));
    }

    @CanIgnoreReturnValue
    NettyChannelBuilder disableCheckAuthority() {
        this.managedChannelImplBuilder.disableCheckAuthority();
        return this;
    }

    @CanIgnoreReturnValue
    NettyChannelBuilder enableCheckAuthority() {
        this.managedChannelImplBuilder.enableCheckAuthority();
        return this;
    }

    void protocolNegotiatorFactory(ProtocolNegotiator.ClientFactory protocolNegotiatorFactory) {
        Preconditions.checkState(!this.freezeProtocolNegotiatorFactory, "Cannot change security when using ChannelCredentials");
        this.protocolNegotiatorFactory = Preconditions.checkNotNull(protocolNegotiatorFactory, "protocolNegotiatorFactory");
    }

    void setTracingEnabled(boolean value) {
        this.managedChannelImplBuilder.setTracingEnabled(value);
    }

    void setStatsEnabled(boolean value) {
        this.managedChannelImplBuilder.setStatsEnabled(value);
    }

    void setStatsRecordStartedRpcs(boolean value) {
        this.managedChannelImplBuilder.setStatsRecordStartedRpcs(value);
    }

    void setStatsRecordFinishedRpcs(boolean value) {
        this.managedChannelImplBuilder.setStatsRecordFinishedRpcs(value);
    }

    void setStatsRecordRealTimeMetrics(boolean value) {
        this.managedChannelImplBuilder.setStatsRecordRealTimeMetrics(value);
    }

    void setStatsRecordRetryMetrics(boolean value) {
        this.managedChannelImplBuilder.setStatsRecordRetryMetrics(value);
    }

    @CanIgnoreReturnValue
    @VisibleForTesting
    NettyChannelBuilder setTransportTracerFactory(TransportTracer.Factory transportTracerFactory) {
        this.transportTracerFactory = transportTracerFactory;
        return this;
    }

    static Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
        return Collections.singleton(InetSocketAddress.class);
    }

    static {
        AS_LARGE_AS_INFINITE = TimeUnit.DAYS.toNanos(1000L);
        DEFAULT_CHANNEL_FACTORY = new ReflectiveChannelFactory<Channel>(Utils.DEFAULT_CLIENT_CHANNEL_TYPE);
        DEFAULT_EVENT_LOOP_GROUP_POOL = SharedResourcePool.forResource(Utils.DEFAULT_WORKER_EVENT_LOOP_GROUP);
        String autoFlowControl = System.getenv("GRPC_EXPERIMENTAL_AUTOFLOWCONTROL");
        if (autoFlowControl == null) {
            autoFlowControl = "true";
        }
        DEFAULT_AUTO_FLOW_CONTROL = Boolean.parseBoolean(autoFlowControl);
    }

    private static final class NettyTransportFactory
    implements ClientTransportFactory {
        private final ProtocolNegotiator protocolNegotiator;
        private final ChannelFactory<? extends Channel> channelFactory;
        private final Map<ChannelOption<?>, ?> channelOptions;
        private final ObjectPool<? extends EventLoopGroup> groupPool;
        private final EventLoopGroup group;
        private final boolean autoFlowControl;
        private final int flowControlWindow;
        private final int maxMessageSize;
        private final int maxHeaderListSize;
        private final long keepAliveTimeNanos;
        private final AtomicBackoff keepAliveBackoff;
        private final long keepAliveTimeoutNanos;
        private final boolean keepAliveWithoutCalls;
        private final TransportTracer.Factory transportTracerFactory;
        private final LocalSocketPicker localSocketPicker;
        private final boolean useGetForSafeMethods;
        private boolean closed;
        private final Class<? extends SocketAddress> transportSocketType;

        NettyTransportFactory(ProtocolNegotiator protocolNegotiator, ChannelFactory<? extends Channel> channelFactory, Map<ChannelOption<?>, ?> channelOptions, ObjectPool<? extends EventLoopGroup> groupPool, boolean autoFlowControl, int flowControlWindow, int maxMessageSize, int maxHeaderListSize, long keepAliveTimeNanos, long keepAliveTimeoutNanos, boolean keepAliveWithoutCalls, TransportTracer.Factory transportTracerFactory, LocalSocketPicker localSocketPicker, boolean useGetForSafeMethods, Class<? extends SocketAddress> transportSocketType) {
            this.protocolNegotiator = Preconditions.checkNotNull(protocolNegotiator, "protocolNegotiator");
            this.channelFactory = channelFactory;
            this.channelOptions = new HashMap(channelOptions);
            this.groupPool = groupPool;
            this.group = groupPool.getObject();
            this.autoFlowControl = autoFlowControl;
            this.flowControlWindow = flowControlWindow;
            this.maxMessageSize = maxMessageSize;
            this.maxHeaderListSize = maxHeaderListSize;
            this.keepAliveTimeNanos = keepAliveTimeNanos;
            this.keepAliveBackoff = new AtomicBackoff("keepalive time nanos", keepAliveTimeNanos);
            this.keepAliveTimeoutNanos = keepAliveTimeoutNanos;
            this.keepAliveWithoutCalls = keepAliveWithoutCalls;
            this.transportTracerFactory = transportTracerFactory;
            this.localSocketPicker = localSocketPicker != null ? localSocketPicker : new LocalSocketPicker();
            this.useGetForSafeMethods = useGetForSafeMethods;
            this.transportSocketType = transportSocketType;
        }

        @Override
        public ConnectionClientTransport newClientTransport(SocketAddress serverAddress, ClientTransportFactory.ClientTransportOptions options, ChannelLogger channelLogger) {
            Preconditions.checkState(!this.closed, "The transport factory is closed.");
            ProtocolNegotiator localNegotiator = this.protocolNegotiator;
            HttpConnectProxiedSocketAddress proxiedAddr = options.getHttpConnectProxiedSocketAddress();
            if (proxiedAddr != null) {
                serverAddress = proxiedAddr.getTargetAddress();
                localNegotiator = ProtocolNegotiators.httpProxy(proxiedAddr.getProxyAddress(), proxiedAddr.getUsername(), proxiedAddr.getPassword(), this.protocolNegotiator);
            }
            final AtomicBackoff.State keepAliveTimeNanosState = this.keepAliveBackoff.getState();
            Runnable tooManyPingsRunnable = new Runnable(){

                @Override
                public void run() {
                    keepAliveTimeNanosState.backoff();
                }
            };
            NettyClientTransport transport = new NettyClientTransport(serverAddress, this.channelFactory, this.channelOptions, this.group, localNegotiator, this.autoFlowControl, this.flowControlWindow, this.maxMessageSize, this.maxHeaderListSize, keepAliveTimeNanosState.get(), this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls, options.getAuthority(), options.getUserAgent(), tooManyPingsRunnable, this.transportTracerFactory.create(), options.getEagAttributes(), this.localSocketPicker, channelLogger, this.useGetForSafeMethods, Ticker.systemTicker());
            return transport;
        }

        @Override
        public ScheduledExecutorService getScheduledExecutorService() {
            return this.group;
        }

        @Override
        public ClientTransportFactory.SwapChannelCredentialsResult swapChannelCredentials(ChannelCredentials channelCreds) {
            Preconditions.checkNotNull(channelCreds, "channelCreds");
            ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(channelCreds);
            if (result.error != null) {
                return null;
            }
            NettyTransportFactory factory2 = new NettyTransportFactory(result.negotiator.newNegotiator(), this.channelFactory, this.channelOptions, this.groupPool, this.autoFlowControl, this.flowControlWindow, this.maxMessageSize, this.maxHeaderListSize, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls, this.transportTracerFactory, this.localSocketPicker, this.useGetForSafeMethods, this.transportSocketType);
            return new ClientTransportFactory.SwapChannelCredentialsResult(factory2, result.callCredentials);
        }

        @Override
        public void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.protocolNegotiator.close();
            this.groupPool.returnObject(this.group);
        }

        @Override
        public Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
            return this.transportSocketType == null ? null : Collections.singleton(this.transportSocketType);
        }
    }

    private final class DefaultProtocolNegotiator
    implements ProtocolNegotiator.ClientFactory {
        private NegotiationType negotiationType = NegotiationType.TLS;
        private SslContext sslContext;

        private DefaultProtocolNegotiator() {
        }

        @Override
        public ProtocolNegotiator newNegotiator() {
            SslContext localSslContext = this.sslContext;
            if (this.negotiationType == NegotiationType.TLS && localSslContext == null) {
                try {
                    localSslContext = GrpcSslContexts.forClient().build();
                }
                catch (SSLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return NettyChannelBuilder.createProtocolNegotiatorByType(this.negotiationType, localSslContext, NettyChannelBuilder.this.managedChannelImplBuilder.getOffloadExecutorPool());
        }

        @Override
        public int getDefaultPort() {
            switch (this.negotiationType) {
                case PLAINTEXT: 
                case PLAINTEXT_UPGRADE: {
                    return 80;
                }
                case TLS: {
                    return 443;
                }
            }
            throw new AssertionError((Object)((Object)((Object)this.negotiationType) + " not handled"));
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4917")
    public static class LocalSocketPicker {
        @Nullable
        public SocketAddress createSocketAddress(SocketAddress remoteAddress, Attributes attrs) {
            return null;
        }
    }

    private final class NettyChannelDefaultPortProvider
    implements ManagedChannelImplBuilder.ChannelBuilderDefaultPortProvider {
        private NettyChannelDefaultPortProvider() {
        }

        @Override
        public int getDefaultPort() {
            return NettyChannelBuilder.this.protocolNegotiatorFactory.getDefaultPort();
        }
    }

    private final class NettyChannelTransportFactoryBuilder
    implements ManagedChannelImplBuilder.ClientTransportFactoryBuilder {
        private NettyChannelTransportFactoryBuilder() {
        }

        @Override
        public ClientTransportFactory buildClientTransportFactory() {
            return NettyChannelBuilder.this.buildTransportFactory();
        }
    }
}

