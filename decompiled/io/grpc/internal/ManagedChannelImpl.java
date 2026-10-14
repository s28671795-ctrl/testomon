/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ClientStreamTracer;
import io.grpc.ClientTransportFilter;
import io.grpc.CompressorRegistry;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ForwardingChannelBuilder2;
import io.grpc.ForwardingClientCall;
import io.grpc.Grpc;
import io.grpc.InternalChannelz;
import io.grpc.InternalConfigSelector;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalWithLogId;
import io.grpc.LoadBalancer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MetricInstrumentRegistry;
import io.grpc.MetricRecorder;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.NameResolverRegistry;
import io.grpc.ProxyDetector;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.AbstractSubchannel;
import io.grpc.internal.AutoConfiguredLoadBalancerFactory;
import io.grpc.internal.BackoffPolicy;
import io.grpc.internal.BackoffPolicyRetryScheduler;
import io.grpc.internal.CallCredentialsApplyingTransportFactory;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ChannelLoggerImpl;
import io.grpc.internal.ChannelTracer;
import io.grpc.internal.ClientCallImpl;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ConnectivityStateManager;
import io.grpc.internal.ContextRunnable;
import io.grpc.internal.DelayedClientCall;
import io.grpc.internal.DelayedClientTransport;
import io.grpc.internal.ExponentialBackoffPolicy;
import io.grpc.internal.ForwardingNameResolver;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.HedgingPolicy;
import io.grpc.internal.InUseStateAggregator;
import io.grpc.internal.InternalSubchannel;
import io.grpc.internal.LogExceptionRunnable;
import io.grpc.internal.ManagedChannelImplBuilder;
import io.grpc.internal.ManagedChannelServiceConfig;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.MetricRecorderImpl;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.OobChannel;
import io.grpc.internal.PickSubchannelArgsImpl;
import io.grpc.internal.Rescheduler;
import io.grpc.internal.RetriableStream;
import io.grpc.internal.RetryPolicy;
import io.grpc.internal.RetryingNameResolver;
import io.grpc.internal.ScParser;
import io.grpc.internal.SubchannelChannel;
import io.grpc.internal.TimeProvider;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class ManagedChannelImpl
extends ManagedChannel
implements InternalInstrumented<InternalChannelz.ChannelStats> {
    @VisibleForTesting
    static final Logger logger = Logger.getLogger(ManagedChannelImpl.class.getName());
    static final long IDLE_TIMEOUT_MILLIS_DISABLE = -1L;
    static final long SUBCHANNEL_SHUTDOWN_DELAY_SECONDS = 5L;
    @VisibleForTesting
    static final Status SHUTDOWN_NOW_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdownNow invoked");
    @VisibleForTesting
    static final Status SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdown invoked");
    @VisibleForTesting
    static final Status SUBCHANNEL_SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Subchannel shutdown invoked");
    private static final ManagedChannelServiceConfig EMPTY_SERVICE_CONFIG = ManagedChannelServiceConfig.empty();
    private static final InternalConfigSelector INITIAL_PENDING_SELECTOR = new InternalConfigSelector(){

        @Override
        public InternalConfigSelector.Result selectConfig(LoadBalancer.PickSubchannelArgs args2) {
            throw new IllegalStateException("Resolution is pending");
        }
    };
    private static final LoadBalancer.PickDetailsConsumer NOOP_PICK_DETAILS_CONSUMER = new LoadBalancer.PickDetailsConsumer(){};
    private final InternalLogId logId;
    private final String target;
    @Nullable
    private final String authorityOverride;
    private final NameResolverRegistry nameResolverRegistry;
    private final URI targetUri;
    private final NameResolverProvider nameResolverProvider;
    private final NameResolver.Args nameResolverArgs;
    private final AutoConfiguredLoadBalancerFactory loadBalancerFactory;
    private final ClientTransportFactory originalTransportFactory;
    @Nullable
    private final ChannelCredentials originalChannelCreds;
    private final ClientTransportFactory transportFactory;
    private final ClientTransportFactory oobTransportFactory;
    private final RestrictedScheduledExecutor scheduledExecutor;
    private final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    private final ObjectPool<? extends Executor> balancerRpcExecutorPool;
    private final ExecutorHolder balancerRpcExecutorHolder;
    private final ExecutorHolder offloadExecutorHolder;
    private final TimeProvider timeProvider;
    private final int maxTraceEvents;
    @VisibleForTesting
    final SynchronizationContext syncContext = new SynchronizationContext(new Thread.UncaughtExceptionHandler(){

        @Override
        public void uncaughtException(Thread t2, Throwable e) {
            logger.log(Level.SEVERE, "[" + ManagedChannelImpl.this.getLogId() + "] Uncaught exception in the SynchronizationContext. Panic!", e);
            ManagedChannelImpl.this.panic(e);
        }
    });
    private boolean fullStreamDecompression;
    private final DecompressorRegistry decompressorRegistry;
    private final CompressorRegistry compressorRegistry;
    private final Supplier<Stopwatch> stopwatchSupplier;
    private final long idleTimeoutMillis;
    private final ConnectivityStateManager channelStateManager = new ConnectivityStateManager();
    private final BackoffPolicy.Provider backoffPolicyProvider;
    private final Channel interceptorChannel;
    private final List<ClientTransportFilter> transportFilters;
    @Nullable
    private final String userAgent;
    private NameResolver nameResolver;
    private boolean nameResolverStarted;
    @Nullable
    private LbHelperImpl lbHelper;
    @Nullable
    private volatile LoadBalancer.SubchannelPicker subchannelPicker;
    private boolean panicMode;
    private final Set<InternalSubchannel> subchannels = new HashSet<InternalSubchannel>(16, 0.75f);
    @Nullable
    private Collection<RealChannel.PendingCall<?, ?>> pendingCalls;
    private final Object pendingCallsInUseObject = new Object();
    private final Set<OobChannel> oobChannels = new HashSet<OobChannel>(1, 0.75f);
    private final DelayedClientTransport delayedTransport;
    private final UncommittedRetriableStreamsRegistry uncommittedRetriableStreamsRegistry = new UncommittedRetriableStreamsRegistry();
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private boolean shutdownNowed;
    private boolean terminating;
    private volatile boolean terminated;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    private final CallTracer.Factory callTracerFactory;
    private final CallTracer channelCallTracer;
    private final ChannelTracer channelTracer;
    private final ChannelLogger channelLogger;
    private final InternalChannelz channelz;
    private final RealChannel realChannel;
    private ResolutionState lastResolutionState = ResolutionState.NO_RESOLUTION;
    private ManagedChannelServiceConfig lastServiceConfig = EMPTY_SERVICE_CONFIG;
    @Nullable
    private final ManagedChannelServiceConfig defaultServiceConfig;
    private boolean serviceConfigUpdated = false;
    private final boolean lookUpServiceConfig;
    private final RetriableStream.ChannelBufferMeter channelBufferUsed = new RetriableStream.ChannelBufferMeter();
    private final long perRpcBufferLimit;
    private final long channelBufferLimit;
    private final boolean retryEnabled;
    private final Deadline.Ticker ticker = Deadline.getSystemTicker();
    private final ManagedClientTransport.Listener delayedTransportListener = new DelayedTransportListener();
    @VisibleForTesting
    final InUseStateAggregator<Object> inUseStateAggregator = new IdleModeStateAggregator();
    private final ChannelStreamProvider transportProvider = new ChannelStreamProvider();
    private final Rescheduler idleTimer;
    private final MetricRecorder metricRecorder;
    private static final ClientCall<Object, Object> NOOP_CALL = new ClientCall<Object, Object>(){

        @Override
        public void start(ClientCall.Listener<Object> responseListener, Metadata headers) {
        }

        @Override
        public void request(int numMessages) {
        }

        @Override
        public void cancel(String message, Throwable cause) {
        }

        @Override
        public void halfClose() {
        }

        @Override
        public void sendMessage(Object message) {
        }

        @Override
        public boolean isReady() {
            return false;
        }
    };

    private void maybeShutdownNowSubchannels() {
        if (this.shutdownNowed) {
            for (InternalSubchannel subchannel : this.subchannels) {
                subchannel.shutdownNow(SHUTDOWN_NOW_STATUS);
            }
            for (OobChannel oobChannel : this.oobChannels) {
                oobChannel.getInternalSubchannel().shutdownNow(SHUTDOWN_NOW_STATUS);
            }
        }
    }

    @Override
    public ListenableFuture<InternalChannelz.ChannelStats> getStats() {
        final SettableFuture<InternalChannelz.ChannelStats> ret = SettableFuture.create();
        final class StatsFetcher
        implements Runnable {
            StatsFetcher() {
            }

            @Override
            public void run() {
                InternalChannelz.ChannelStats.Builder builder = new InternalChannelz.ChannelStats.Builder();
                ManagedChannelImpl.this.channelCallTracer.updateBuilder(builder);
                ManagedChannelImpl.this.channelTracer.updateBuilder(builder);
                builder.setTarget(ManagedChannelImpl.this.target).setState(ManagedChannelImpl.this.channelStateManager.getState());
                ArrayList<InternalWithLogId> children = new ArrayList<InternalWithLogId>();
                children.addAll(ManagedChannelImpl.this.subchannels);
                children.addAll(ManagedChannelImpl.this.oobChannels);
                builder.setSubchannels(children);
                ret.set(builder.build());
            }
        }
        this.syncContext.execute(new StatsFetcher());
        return ret;
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    private void shutdownNameResolverAndLoadBalancer(boolean channelIsActive) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (channelIsActive) {
            Preconditions.checkState(this.nameResolverStarted, "nameResolver is not started");
            Preconditions.checkState(this.lbHelper != null, "lbHelper is null");
        }
        if (this.nameResolver != null) {
            this.nameResolver.shutdown();
            this.nameResolverStarted = false;
            this.nameResolver = channelIsActive ? ManagedChannelImpl.getNameResolver(this.targetUri, this.authorityOverride, this.nameResolverProvider, this.nameResolverArgs) : null;
        }
        if (this.lbHelper != null) {
            this.lbHelper.lb.shutdown();
            this.lbHelper = null;
        }
        this.subchannelPicker = null;
    }

    @VisibleForTesting
    void exitIdleMode() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.shutdown.get() || this.panicMode) {
            return;
        }
        if (this.inUseStateAggregator.isInUse()) {
            this.cancelIdleTimer(false);
        } else {
            this.rescheduleIdleTimer();
        }
        if (this.lbHelper != null) {
            return;
        }
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Exiting idle mode");
        LbHelperImpl lbHelper = new LbHelperImpl();
        lbHelper.lb = this.loadBalancerFactory.newLoadBalancer(lbHelper);
        this.lbHelper = lbHelper;
        this.channelStateManager.gotoState(ConnectivityState.CONNECTING);
        NameResolverListener listener = new NameResolverListener(lbHelper, this.nameResolver);
        this.nameResolver.start(listener);
        this.nameResolverStarted = true;
    }

    private void enterIdleMode() {
        this.shutdownNameResolverAndLoadBalancer(true);
        this.delayedTransport.reprocess(null);
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering IDLE state");
        this.channelStateManager.gotoState(ConnectivityState.IDLE);
        if (this.inUseStateAggregator.anyObjectInUse(this.pendingCallsInUseObject, this.delayedTransport)) {
            this.exitIdleMode();
        }
    }

    private void cancelIdleTimer(boolean permanent) {
        this.idleTimer.cancel(permanent);
    }

    private void rescheduleIdleTimer() {
        if (this.idleTimeoutMillis == -1L) {
            return;
        }
        this.idleTimer.reschedule(this.idleTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    private void refreshNameResolution() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.nameResolverStarted) {
            this.nameResolver.refresh();
        }
    }

    ManagedChannelImpl(ManagedChannelImplBuilder builder, ClientTransportFactory clientTransportFactory, URI targetUri, NameResolverProvider nameResolverProvider, BackoffPolicy.Provider backoffPolicyProvider, ObjectPool<? extends Executor> balancerRpcExecutorPool, Supplier<Stopwatch> stopwatchSupplier, List<ClientInterceptor> interceptors, final TimeProvider timeProvider) {
        this.target = Preconditions.checkNotNull(builder.target, "target");
        this.logId = InternalLogId.allocate("Channel", this.target);
        this.timeProvider = Preconditions.checkNotNull(timeProvider, "timeProvider");
        this.executorPool = Preconditions.checkNotNull(builder.executorPool, "executorPool");
        this.executor = Preconditions.checkNotNull(this.executorPool.getObject(), "executor");
        this.originalChannelCreds = builder.channelCredentials;
        this.originalTransportFactory = clientTransportFactory;
        this.offloadExecutorHolder = new ExecutorHolder(Preconditions.checkNotNull(builder.offloadExecutorPool, "offloadExecutorPool"));
        this.transportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory, builder.callCredentials, this.offloadExecutorHolder);
        this.oobTransportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory, null, this.offloadExecutorHolder);
        this.scheduledExecutor = new RestrictedScheduledExecutor(this.transportFactory.getScheduledExecutorService());
        this.maxTraceEvents = builder.maxTraceEvents;
        this.channelTracer = new ChannelTracer(this.logId, builder.maxTraceEvents, timeProvider.currentTimeNanos(), "Channel for '" + this.target + "'");
        this.channelLogger = new ChannelLoggerImpl(this.channelTracer, timeProvider);
        ProxyDetector proxyDetector = builder.proxyDetector != null ? builder.proxyDetector : GrpcUtil.DEFAULT_PROXY_DETECTOR;
        this.retryEnabled = builder.retryEnabled;
        this.loadBalancerFactory = new AutoConfiguredLoadBalancerFactory(builder.defaultLbPolicy);
        this.nameResolverRegistry = builder.nameResolverRegistry;
        this.targetUri = Preconditions.checkNotNull(targetUri, "targetUri");
        this.nameResolverProvider = Preconditions.checkNotNull(nameResolverProvider, "nameResolverProvider");
        ScParser serviceConfigParser = new ScParser(this.retryEnabled, builder.maxRetryAttempts, builder.maxHedgedAttempts, this.loadBalancerFactory);
        this.authorityOverride = builder.authorityOverride;
        this.nameResolverArgs = NameResolver.Args.newBuilder().setDefaultPort(builder.getDefaultPort()).setProxyDetector(proxyDetector).setSynchronizationContext(this.syncContext).setScheduledExecutorService(this.scheduledExecutor).setServiceConfigParser(serviceConfigParser).setChannelLogger(this.channelLogger).setOffloadExecutor(this.offloadExecutorHolder).setOverrideAuthority(this.authorityOverride).build();
        this.nameResolver = ManagedChannelImpl.getNameResolver(targetUri, this.authorityOverride, nameResolverProvider, this.nameResolverArgs);
        this.balancerRpcExecutorPool = Preconditions.checkNotNull(balancerRpcExecutorPool, "balancerRpcExecutorPool");
        this.balancerRpcExecutorHolder = new ExecutorHolder(balancerRpcExecutorPool);
        this.delayedTransport = new DelayedClientTransport(this.executor, this.syncContext);
        this.delayedTransport.start(this.delayedTransportListener);
        this.backoffPolicyProvider = backoffPolicyProvider;
        if (builder.defaultServiceConfig != null) {
            NameResolver.ConfigOrError parsedDefaultServiceConfig = serviceConfigParser.parseServiceConfig(builder.defaultServiceConfig);
            Preconditions.checkState(parsedDefaultServiceConfig.getError() == null, "Default config is invalid: %s", (Object)parsedDefaultServiceConfig.getError());
            this.lastServiceConfig = this.defaultServiceConfig = (ManagedChannelServiceConfig)parsedDefaultServiceConfig.getConfig();
        } else {
            this.defaultServiceConfig = null;
        }
        this.lookUpServiceConfig = builder.lookUpServiceConfig;
        Channel channel = this.realChannel = new RealChannel(this.nameResolver.getServiceAuthority());
        if (builder.binlog != null) {
            channel = builder.binlog.wrapChannel(channel);
        }
        this.interceptorChannel = ClientInterceptors.intercept(channel, interceptors);
        this.transportFilters = new ArrayList<ClientTransportFilter>(builder.transportFilters);
        this.stopwatchSupplier = Preconditions.checkNotNull(stopwatchSupplier, "stopwatchSupplier");
        if (builder.idleTimeoutMillis == -1L) {
            this.idleTimeoutMillis = builder.idleTimeoutMillis;
        } else {
            Preconditions.checkArgument(builder.idleTimeoutMillis >= ManagedChannelImplBuilder.IDLE_MODE_MIN_TIMEOUT_MILLIS, "invalid idleTimeoutMillis %s", builder.idleTimeoutMillis);
            this.idleTimeoutMillis = builder.idleTimeoutMillis;
        }
        this.idleTimer = new Rescheduler(new IdleModeTimer(), this.syncContext, this.transportFactory.getScheduledExecutorService(), stopwatchSupplier.get());
        this.fullStreamDecompression = builder.fullStreamDecompression;
        this.decompressorRegistry = Preconditions.checkNotNull(builder.decompressorRegistry, "decompressorRegistry");
        this.compressorRegistry = Preconditions.checkNotNull(builder.compressorRegistry, "compressorRegistry");
        this.userAgent = builder.userAgent;
        this.channelBufferLimit = builder.retryBufferSize;
        this.perRpcBufferLimit = builder.perRpcBufferLimit;
        final class ChannelCallTracerFactory
        implements CallTracer.Factory {
            ChannelCallTracerFactory() {
            }

            @Override
            public CallTracer create() {
                return new CallTracer(timeProvider);
            }
        }
        this.callTracerFactory = new ChannelCallTracerFactory();
        this.channelCallTracer = this.callTracerFactory.create();
        this.channelz = Preconditions.checkNotNull(builder.channelz);
        this.channelz.addRootChannel(this);
        if (!this.lookUpServiceConfig) {
            if (this.defaultServiceConfig != null) {
                this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config look-up disabled, using default service config");
            }
            this.serviceConfigUpdated = true;
        }
        this.metricRecorder = new MetricRecorderImpl(builder.metricSinks, MetricInstrumentRegistry.getDefaultRegistry());
    }

    @VisibleForTesting
    static NameResolver getNameResolver(URI targetUri, final @Nullable String overrideAuthority, NameResolverProvider provider, NameResolver.Args nameResolverArgs) {
        NameResolver resolver2 = provider.newNameResolver(targetUri, nameResolverArgs);
        if (resolver2 == null) {
            throw new IllegalArgumentException("cannot create a NameResolver for " + targetUri);
        }
        RetryingNameResolver usedNameResolver = new RetryingNameResolver(resolver2, new BackoffPolicyRetryScheduler(new ExponentialBackoffPolicy.Provider(), nameResolverArgs.getScheduledExecutorService(), nameResolverArgs.getSynchronizationContext()), nameResolverArgs.getSynchronizationContext());
        if (overrideAuthority == null) {
            return usedNameResolver;
        }
        return new ForwardingNameResolver(usedNameResolver){

            @Override
            public String getServiceAuthority() {
                return overrideAuthority;
            }
        };
    }

    @VisibleForTesting
    InternalConfigSelector getConfigSelector() {
        return (InternalConfigSelector)this.realChannel.configSelector.get();
    }

    @Override
    public ManagedChannelImpl shutdown() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdown() called");
        if (!this.shutdown.compareAndSet(false, true)) {
            return this;
        }
        final class Shutdown
        implements Runnable {
            Shutdown() {
            }

            @Override
            public void run() {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering SHUTDOWN state");
                ManagedChannelImpl.this.channelStateManager.gotoState(ConnectivityState.SHUTDOWN);
            }
        }
        this.syncContext.execute(new Shutdown());
        this.realChannel.shutdown();
        final class CancelIdleTimer
        implements Runnable {
            CancelIdleTimer() {
            }

            @Override
            public void run() {
                ManagedChannelImpl.this.cancelIdleTimer(true);
            }
        }
        this.syncContext.execute(new CancelIdleTimer());
        return this;
    }

    @Override
    public ManagedChannelImpl shutdownNow() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdownNow() called");
        this.shutdown();
        this.realChannel.shutdownNow();
        final class ShutdownNow
        implements Runnable {
            ShutdownNow() {
            }

            @Override
            public void run() {
                if (ManagedChannelImpl.this.shutdownNowed) {
                    return;
                }
                ManagedChannelImpl.this.shutdownNowed = true;
                ManagedChannelImpl.this.maybeShutdownNowSubchannels();
            }
        }
        this.syncContext.execute(new ShutdownNow());
        return this;
    }

    @VisibleForTesting
    void panic(final Throwable t2) {
        if (this.panicMode) {
            return;
        }
        this.panicMode = true;
        this.cancelIdleTimer(true);
        this.shutdownNameResolverAndLoadBalancer(false);
        final class PanicSubchannelPicker
        extends LoadBalancer.SubchannelPicker {
            private final LoadBalancer.PickResult panicPickResult;

            PanicSubchannelPicker() {
                this.panicPickResult = LoadBalancer.PickResult.withDrop(Status.INTERNAL.withDescription("Panic! This is a bug!").withCause(t2));
            }

            @Override
            public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
                return this.panicPickResult;
            }

            public String toString() {
                return MoreObjects.toStringHelper(PanicSubchannelPicker.class).add("panicPickResult", this.panicPickResult).toString();
            }
        }
        this.updateSubchannelPicker(new PanicSubchannelPicker());
        this.realChannel.updateConfigSelector(null);
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.ERROR, "PANIC! Entering TRANSIENT_FAILURE");
        this.channelStateManager.gotoState(ConnectivityState.TRANSIENT_FAILURE);
    }

    @VisibleForTesting
    boolean isInPanicMode() {
        return this.panicMode;
    }

    private void updateSubchannelPicker(LoadBalancer.SubchannelPicker newPicker) {
        this.subchannelPicker = newPicker;
        this.delayedTransport.reprocess(newPicker);
    }

    @Override
    public boolean isShutdown() {
        return this.shutdown.get();
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
        return this.terminatedLatch.await(timeout2, unit);
    }

    @Override
    public boolean isTerminated() {
        return this.terminated;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
        return this.interceptorChannel.newCall(method2, callOptions);
    }

    @Override
    public String authority() {
        return this.interceptorChannel.authority();
    }

    private Executor getCallExecutor(CallOptions callOptions) {
        Executor executor2 = callOptions.getExecutor();
        if (executor2 == null) {
            executor2 = this.executor;
        }
        return executor2;
    }

    private void maybeTerminateChannel() {
        if (this.terminated) {
            return;
        }
        if (this.shutdown.get() && this.subchannels.isEmpty() && this.oobChannels.isEmpty()) {
            this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Terminated");
            this.channelz.removeRootChannel(this);
            this.executorPool.returnObject(this.executor);
            this.balancerRpcExecutorHolder.release();
            this.offloadExecutorHolder.release();
            this.transportFactory.close();
            this.terminated = true;
            this.terminatedLatch.countDown();
        }
    }

    private void handleInternalSubchannelState(ConnectivityStateInfo newState) {
        if (newState.getState() == ConnectivityState.TRANSIENT_FAILURE || newState.getState() == ConnectivityState.IDLE) {
            this.refreshNameResolution();
        }
    }

    @Override
    public ConnectivityState getState(boolean requestConnection) {
        ConnectivityState savedChannelState = this.channelStateManager.getState();
        if (requestConnection && savedChannelState == ConnectivityState.IDLE) {
            final class RequestConnection
            implements Runnable {
                RequestConnection() {
                }

                @Override
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                    if (ManagedChannelImpl.this.subchannelPicker != null) {
                        ManagedChannelImpl.this.subchannelPicker.requestConnection();
                    }
                    if (ManagedChannelImpl.this.lbHelper != null) {
                        ((ManagedChannelImpl)ManagedChannelImpl.this).lbHelper.lb.requestConnection();
                    }
                }
            }
            this.syncContext.execute(new RequestConnection());
        }
        return savedChannelState;
    }

    @Override
    public void notifyWhenStateChanged(final ConnectivityState source2, final Runnable callback) {
        final class NotifyStateChanged
        implements Runnable {
            NotifyStateChanged() {
            }

            @Override
            public void run() {
                ManagedChannelImpl.this.channelStateManager.notifyWhenStateChanged(callback, ManagedChannelImpl.this.executor, source2);
            }
        }
        this.syncContext.execute(new NotifyStateChanged());
    }

    @Override
    public void resetConnectBackoff() {
        final class ResetConnectBackoff
        implements Runnable {
            ResetConnectBackoff() {
            }

            @Override
            public void run() {
                if (ManagedChannelImpl.this.shutdown.get()) {
                    return;
                }
                if (ManagedChannelImpl.this.nameResolverStarted) {
                    ManagedChannelImpl.this.refreshNameResolution();
                }
                for (InternalSubchannel subchannel : ManagedChannelImpl.this.subchannels) {
                    subchannel.resetConnectBackoff();
                }
                for (OobChannel oobChannel : ManagedChannelImpl.this.oobChannels) {
                    oobChannel.resetConnectBackoff();
                }
            }
        }
        this.syncContext.execute(new ResetConnectBackoff());
    }

    @Override
    public void enterIdle() {
        final class PrepareToLoseNetworkRunnable
        implements Runnable {
            PrepareToLoseNetworkRunnable() {
            }

            @Override
            public void run() {
                if (ManagedChannelImpl.this.shutdown.get() || ManagedChannelImpl.this.lbHelper == null) {
                    return;
                }
                ManagedChannelImpl.this.cancelIdleTimer(false);
                ManagedChannelImpl.this.enterIdleMode();
            }
        }
        this.syncContext.execute(new PrepareToLoseNetworkRunnable());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("target", this.target).toString();
    }

    static enum ResolutionState {
        NO_RESOLUTION,
        SUCCESS,
        ERROR;

    }

    private static final class RestrictedScheduledExecutor
    implements ScheduledExecutorService {
        final ScheduledExecutorService delegate;

        private RestrictedScheduledExecutor(ScheduledExecutorService delegate) {
            this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        }

        @Override
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return this.delegate.schedule(callable, delay, unit);
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable cmd, long delay, TimeUnit unit) {
            return this.delegate.schedule(cmd, delay, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return this.delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return this.delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        @Override
        public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout2, unit);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2) throws InterruptedException {
            return this.delegate.invokeAll(tasks2);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException {
            return this.delegate.invokeAll(tasks2, timeout2, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks2) throws InterruptedException, ExecutionException {
            return this.delegate.invokeAny(tasks2);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.delegate.invokeAny(tasks2, timeout2, unit);
        }

        @Override
        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        @Override
        public void shutdown() {
            throw new UnsupportedOperationException("Restricted: shutdown() is not allowed");
        }

        @Override
        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException("Restricted: shutdownNow() is not allowed");
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return this.delegate.submit(task);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return this.delegate.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return this.delegate.submit(task, result);
        }

        @Override
        public void execute(Runnable command) {
            this.delegate.execute(command);
        }
    }

    @VisibleForTesting
    static final class ExecutorHolder
    implements Executor {
        private final ObjectPool<? extends Executor> pool;
        private Executor executor;

        ExecutorHolder(ObjectPool<? extends Executor> executorPool) {
            this.pool = Preconditions.checkNotNull(executorPool, "executorPool");
        }

        synchronized Executor getExecutor() {
            if (this.executor == null) {
                this.executor = Preconditions.checkNotNull(this.pool.getObject(), "%s.getObject()", (Object)this.executor);
            }
            return this.executor;
        }

        synchronized void release() {
            if (this.executor != null) {
                this.executor = this.pool.returnObject(this.executor);
            }
        }

        @Override
        public void execute(Runnable command) {
            this.getExecutor().execute(command);
        }
    }

    private final class IdleModeStateAggregator
    extends InUseStateAggregator<Object> {
        private IdleModeStateAggregator() {
        }

        @Override
        protected void handleInUse() {
            ManagedChannelImpl.this.exitIdleMode();
        }

        @Override
        protected void handleNotInUse() {
            if (ManagedChannelImpl.this.shutdown.get()) {
                return;
            }
            ManagedChannelImpl.this.rescheduleIdleTimer();
        }
    }

    private final class DelayedTransportListener
    implements ManagedClientTransport.Listener {
        private DelayedTransportListener() {
        }

        @Override
        public void transportShutdown(Status s2) {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
        }

        @Override
        public void transportReady() {
        }

        @Override
        public Attributes filterTransport(Attributes attributes) {
            return attributes;
        }

        @Override
        public void transportInUse(boolean inUse) {
            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.delayedTransport, inUse);
            if (inUse) {
                ManagedChannelImpl.this.exitIdleMode();
            }
        }

        @Override
        public void transportTerminated() {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
            ManagedChannelImpl.this.terminating = true;
            ManagedChannelImpl.this.shutdownNameResolverAndLoadBalancer(false);
            ManagedChannelImpl.this.maybeShutdownNowSubchannels();
            ManagedChannelImpl.this.maybeTerminateChannel();
        }
    }

    private final class SubchannelImpl
    extends AbstractSubchannel {
        final LoadBalancer.CreateSubchannelArgs args;
        final InternalLogId subchannelLogId;
        final ChannelLoggerImpl subchannelLogger;
        final ChannelTracer subchannelTracer;
        List<EquivalentAddressGroup> addressGroups;
        InternalSubchannel subchannel;
        boolean started;
        boolean shutdown;
        SynchronizationContext.ScheduledHandle delayedShutdownTask;

        SubchannelImpl(LoadBalancer.CreateSubchannelArgs args2) {
            Preconditions.checkNotNull(args2, "args");
            this.addressGroups = args2.getAddresses();
            if (ManagedChannelImpl.this.authorityOverride != null) {
                List<EquivalentAddressGroup> eagsWithoutOverrideAttr = this.stripOverrideAuthorityAttributes(args2.getAddresses());
                args2 = args2.toBuilder().setAddresses(eagsWithoutOverrideAttr).build();
            }
            this.args = args2;
            this.subchannelLogId = InternalLogId.allocate("Subchannel", ManagedChannelImpl.this.authority());
            this.subchannelTracer = new ChannelTracer(this.subchannelLogId, ManagedChannelImpl.this.maxTraceEvents, ManagedChannelImpl.this.timeProvider.currentTimeNanos(), "Subchannel for " + args2.getAddresses());
            this.subchannelLogger = new ChannelLoggerImpl(this.subchannelTracer, ManagedChannelImpl.this.timeProvider);
        }

        @Override
        public void start(final LoadBalancer.SubchannelStateListener listener) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(!this.started, "already started");
            Preconditions.checkState(!this.shutdown, "already shutdown");
            Preconditions.checkState(!ManagedChannelImpl.this.terminating, "Channel is being terminated");
            this.started = true;
            final class ManagedInternalSubchannelCallback
            extends InternalSubchannel.Callback {
                ManagedInternalSubchannelCallback() {
                }

                @Override
                void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.subchannels.remove(is);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                @Override
                void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    Preconditions.checkState(listener != null, "listener is null");
                    listener.onSubchannelState(newState);
                }

                @Override
                void onInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, true);
                }

                @Override
                void onNotInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, false);
                }
            }
            InternalSubchannel internalSubchannel = new InternalSubchannel(this.args.getAddresses(), ManagedChannelImpl.this.authority(), ManagedChannelImpl.this.userAgent, ManagedChannelImpl.this.backoffPolicyProvider, ManagedChannelImpl.this.transportFactory, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.stopwatchSupplier, ManagedChannelImpl.this.syncContext, new ManagedInternalSubchannelCallback(), ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.callTracerFactory.create(), this.subchannelTracer, this.subchannelLogId, this.subchannelLogger, ManagedChannelImpl.this.transportFilters);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel started").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).setSubchannelRef(internalSubchannel).build());
            this.subchannel = internalSubchannel;
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            ManagedChannelImpl.this.subchannels.add(internalSubchannel);
        }

        @Override
        InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel() {
            Preconditions.checkState(this.started, "not started");
            return this.subchannel;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void shutdown() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            if (this.subchannel == null) {
                this.shutdown = true;
                return;
            }
            if (this.shutdown) {
                if (!ManagedChannelImpl.this.terminating || this.delayedShutdownTask == null) return;
                this.delayedShutdownTask.cancel();
                this.delayedShutdownTask = null;
            } else {
                this.shutdown = true;
            }
            if (!ManagedChannelImpl.this.terminating) {
                final class ShutdownSubchannel
                implements Runnable {
                    ShutdownSubchannel() {
                    }

                    @Override
                    public void run() {
                        SubchannelImpl.this.subchannel.shutdown(SUBCHANNEL_SHUTDOWN_STATUS);
                    }
                }
                this.delayedShutdownTask = ManagedChannelImpl.this.syncContext.schedule(new LogExceptionRunnable(new ShutdownSubchannel()), 5L, TimeUnit.SECONDS, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService());
                return;
            }
            this.subchannel.shutdown(SHUTDOWN_STATUS);
        }

        @Override
        public void requestConnection() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(this.started, "not started");
            this.subchannel.obtainActiveTransport();
        }

        @Override
        public List<EquivalentAddressGroup> getAllAddresses() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(this.started, "not started");
            return this.addressGroups;
        }

        @Override
        public Attributes getAttributes() {
            return this.args.getAttributes();
        }

        public String toString() {
            return this.subchannelLogId.toString();
        }

        @Override
        public Channel asChannel() {
            Preconditions.checkState(this.started, "not started");
            return new SubchannelChannel(this.subchannel, ManagedChannelImpl.this.balancerRpcExecutorHolder.getExecutor(), ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.callTracerFactory.create(), new AtomicReference<Object>(null));
        }

        @Override
        public Object getInternalSubchannel() {
            Preconditions.checkState(this.started, "Subchannel is not started");
            return this.subchannel;
        }

        @Override
        public ChannelLogger getChannelLogger() {
            return this.subchannelLogger;
        }

        @Override
        public void updateAddresses(List<EquivalentAddressGroup> addrs) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            this.addressGroups = addrs;
            if (ManagedChannelImpl.this.authorityOverride != null) {
                addrs = this.stripOverrideAuthorityAttributes(addrs);
            }
            this.subchannel.updateAddresses(addrs);
        }

        private List<EquivalentAddressGroup> stripOverrideAuthorityAttributes(List<EquivalentAddressGroup> eags) {
            ArrayList<EquivalentAddressGroup> eagsWithoutOverrideAttr = new ArrayList<EquivalentAddressGroup>();
            for (EquivalentAddressGroup eag : eags) {
                EquivalentAddressGroup eagWithoutOverrideAttr = new EquivalentAddressGroup(eag.getAddresses(), eag.getAttributes().toBuilder().discard(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE).build());
                eagsWithoutOverrideAttr.add(eagWithoutOverrideAttr);
            }
            return Collections.unmodifiableList(eagsWithoutOverrideAttr);
        }
    }

    final class NameResolverListener
    extends NameResolver.Listener2 {
        final LbHelperImpl helper;
        final NameResolver resolver;

        NameResolverListener(LbHelperImpl helperImpl, NameResolver resolver2) {
            this.helper = Preconditions.checkNotNull(helperImpl, "helperImpl");
            this.resolver = Preconditions.checkNotNull(resolver2, "resolver");
        }

        @Override
        public void onResult(final NameResolver.ResolutionResult resolutionResult) {
            final class NamesResolved
            implements Runnable {
                NamesResolved() {
                }

                @Override
                public void run() {
                    ManagedChannelServiceConfig effectiveServiceConfig;
                    Status serviceConfigError;
                    if (ManagedChannelImpl.this.nameResolver != NameResolverListener.this.resolver) {
                        return;
                    }
                    List<EquivalentAddressGroup> servers = resolutionResult.getAddresses();
                    ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Resolved address: {0}, config={1}", servers, resolutionResult.getAttributes());
                    if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.SUCCESS) {
                        ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Address resolved: {0}", servers);
                        ManagedChannelImpl.this.lastResolutionState = ResolutionState.SUCCESS;
                    }
                    NameResolver.ConfigOrError configOrError = resolutionResult.getServiceConfig();
                    RetryingNameResolver.ResolutionResultListener resolutionResultListener = resolutionResult.getAttributes().get(RetryingNameResolver.RESOLUTION_RESULT_LISTENER_KEY);
                    InternalConfigSelector resolvedConfigSelector = resolutionResult.getAttributes().get(InternalConfigSelector.KEY);
                    ManagedChannelServiceConfig validServiceConfig = configOrError != null && configOrError.getConfig() != null ? (ManagedChannelServiceConfig)configOrError.getConfig() : null;
                    Status status = serviceConfigError = configOrError != null ? configOrError.getError() : null;
                    if (!ManagedChannelImpl.this.lookUpServiceConfig) {
                        if (validServiceConfig != null) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config from name resolver discarded by channel settings");
                        }
                        ManagedChannelServiceConfig managedChannelServiceConfig = effectiveServiceConfig = ManagedChannelImpl.this.defaultServiceConfig == null ? EMPTY_SERVICE_CONFIG : ManagedChannelImpl.this.defaultServiceConfig;
                        if (resolvedConfigSelector != null) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Config selector from name resolver discarded by channel settings");
                        }
                        ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig.getDefaultConfigSelector());
                    } else {
                        if (validServiceConfig != null) {
                            effectiveServiceConfig = validServiceConfig;
                            if (resolvedConfigSelector != null) {
                                ManagedChannelImpl.this.realChannel.updateConfigSelector(resolvedConfigSelector);
                                if (effectiveServiceConfig.getDefaultConfigSelector() != null) {
                                    ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Method configs in service config will be discarded due to presence ofconfig-selector");
                                }
                            } else {
                                ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig.getDefaultConfigSelector());
                            }
                        } else if (ManagedChannelImpl.this.defaultServiceConfig != null) {
                            effectiveServiceConfig = ManagedChannelImpl.this.defaultServiceConfig;
                            ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig.getDefaultConfigSelector());
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Received no service config, using default service config");
                        } else if (serviceConfigError != null) {
                            if (!ManagedChannelImpl.this.serviceConfigUpdated) {
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Fallback to error due to invalid first service config without default config");
                                NameResolverListener.this.onError(configOrError.getError());
                                if (resolutionResultListener != null) {
                                    resolutionResultListener.resolutionAttempted(configOrError.getError());
                                }
                                return;
                            }
                            effectiveServiceConfig = ManagedChannelImpl.this.lastServiceConfig;
                        } else {
                            effectiveServiceConfig = EMPTY_SERVICE_CONFIG;
                            ManagedChannelImpl.this.realChannel.updateConfigSelector(null);
                        }
                        if (!effectiveServiceConfig.equals(ManagedChannelImpl.this.lastServiceConfig)) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config changed{0}", effectiveServiceConfig == EMPTY_SERVICE_CONFIG ? " to empty" : "");
                            ManagedChannelImpl.this.lastServiceConfig = effectiveServiceConfig;
                            ((ManagedChannelImpl)ManagedChannelImpl.this).transportProvider.throttle = effectiveServiceConfig.getRetryThrottling();
                        }
                        try {
                            ManagedChannelImpl.this.serviceConfigUpdated = true;
                        }
                        catch (RuntimeException re) {
                            logger.log(Level.WARNING, "[" + ManagedChannelImpl.this.getLogId() + "] Unexpected exception from parsing service config", re);
                        }
                    }
                    Attributes effectiveAttrs = resolutionResult.getAttributes();
                    if (NameResolverListener.this.helper == ManagedChannelImpl.this.lbHelper) {
                        Attributes.Builder attrBuilder = effectiveAttrs.toBuilder().discard(InternalConfigSelector.KEY);
                        Map<String, ?> healthCheckingConfig = effectiveServiceConfig.getHealthCheckingConfig();
                        if (healthCheckingConfig != null) {
                            attrBuilder.set(LoadBalancer.ATTR_HEALTH_CHECKING_CONFIG, healthCheckingConfig).build();
                        }
                        Attributes attributes = attrBuilder.build();
                        Status addressAcceptanceStatus = NameResolverListener.this.helper.lb.tryAcceptResolvedAddresses(LoadBalancer.ResolvedAddresses.newBuilder().setAddresses(servers).setAttributes(attributes).setLoadBalancingPolicyConfig(effectiveServiceConfig.getLoadBalancingConfig()).build());
                        if (resolutionResultListener != null) {
                            resolutionResultListener.resolutionAttempted(addressAcceptanceStatus);
                        }
                    }
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new NamesResolved());
        }

        @Override
        public void onError(final Status error) {
            Preconditions.checkArgument(!error.isOk(), "the error status must not be OK");
            final class NameResolverErrorHandler
            implements Runnable {
                NameResolverErrorHandler() {
                }

                @Override
                public void run() {
                    NameResolverListener.this.handleErrorInSyncContext(error);
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new NameResolverErrorHandler());
        }

        private void handleErrorInSyncContext(Status error) {
            logger.log(Level.WARNING, "[{0}] Failed to resolve name. status={1}", new Object[]{ManagedChannelImpl.this.getLogId(), error});
            ManagedChannelImpl.this.realChannel.onConfigError();
            if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.ERROR) {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.WARNING, "Failed to resolve name: {0}", error);
                ManagedChannelImpl.this.lastResolutionState = ResolutionState.ERROR;
            }
            if (this.helper != ManagedChannelImpl.this.lbHelper) {
                return;
            }
            this.helper.lb.handleNameResolutionError(error);
        }
    }

    private final class LbHelperImpl
    extends LoadBalancer.Helper {
        AutoConfiguredLoadBalancerFactory.AutoConfiguredLoadBalancer lb;

        private LbHelperImpl() {
        }

        @Override
        public AbstractSubchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args2) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(!ManagedChannelImpl.this.terminating, "Channel is being terminated");
            return new SubchannelImpl(args2);
        }

        @Override
        public void updateBalancingState(final ConnectivityState newState, final LoadBalancer.SubchannelPicker newPicker) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkNotNull(newState, "newState");
            Preconditions.checkNotNull(newPicker, "newPicker");
            final class UpdateBalancingState
            implements Runnable {
                UpdateBalancingState() {
                }

                @Override
                public void run() {
                    if (LbHelperImpl.this != ManagedChannelImpl.this.lbHelper) {
                        return;
                    }
                    ManagedChannelImpl.this.updateSubchannelPicker(newPicker);
                    if (newState != ConnectivityState.SHUTDOWN) {
                        ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering {0} state with picker: {1}", new Object[]{newState, newPicker});
                        ManagedChannelImpl.this.channelStateManager.gotoState(newState);
                    }
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new UpdateBalancingState());
        }

        @Override
        public void refreshNameResolution() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            final class LoadBalancerRefreshNameResolution
            implements Runnable {
                LoadBalancerRefreshNameResolution() {
                }

                @Override
                public void run() {
                    ManagedChannelImpl.this.refreshNameResolution();
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new LoadBalancerRefreshNameResolution());
        }

        @Override
        public ManagedChannel createOobChannel(EquivalentAddressGroup addressGroup, String authority) {
            return this.createOobChannel(Collections.singletonList(addressGroup), authority);
        }

        @Override
        public ManagedChannel createOobChannel(List<EquivalentAddressGroup> addressGroup, String authority) {
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            long oobChannelCreationTime = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            InternalLogId oobLogId = InternalLogId.allocate("OobChannel", null);
            InternalLogId subchannelLogId = InternalLogId.allocate("Subchannel-OOB", authority);
            ChannelTracer oobChannelTracer = new ChannelTracer(oobLogId, ManagedChannelImpl.this.maxTraceEvents, oobChannelCreationTime, "OobChannel for " + addressGroup);
            final OobChannel oobChannel = new OobChannel(authority, ManagedChannelImpl.this.balancerRpcExecutorPool, ManagedChannelImpl.this.oobTransportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.syncContext, ManagedChannelImpl.this.callTracerFactory.create(), oobChannelTracer, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.timeProvider);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child OobChannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime).setChannelRef(oobChannel).build());
            ChannelTracer subchannelTracer = new ChannelTracer(subchannelLogId, ManagedChannelImpl.this.maxTraceEvents, oobChannelCreationTime, "Subchannel for " + addressGroup);
            ChannelLoggerImpl subchannelLogger = new ChannelLoggerImpl(subchannelTracer, ManagedChannelImpl.this.timeProvider);
            final class ManagedOobChannelCallback
            extends InternalSubchannel.Callback {
                ManagedOobChannelCallback() {
                }

                @Override
                void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.oobChannels.remove(oobChannel);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    oobChannel.handleSubchannelTerminated();
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                @Override
                void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    ManagedChannelImpl.this.handleInternalSubchannelState(newState);
                    oobChannel.handleSubchannelStateChange(newState);
                }
            }
            InternalSubchannel internalSubchannel = new InternalSubchannel(addressGroup, authority, ManagedChannelImpl.this.userAgent, ManagedChannelImpl.this.backoffPolicyProvider, ManagedChannelImpl.this.oobTransportFactory, ManagedChannelImpl.this.oobTransportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.stopwatchSupplier, ManagedChannelImpl.this.syncContext, new ManagedOobChannelCallback(), ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.callTracerFactory.create(), subchannelTracer, subchannelLogId, subchannelLogger, ManagedChannelImpl.this.transportFilters);
            oobChannelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime).setSubchannelRef(internalSubchannel).build());
            ManagedChannelImpl.this.channelz.addSubchannel(oobChannel);
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            oobChannel.setSubchannel(internalSubchannel);
            final class AddOobChannel
            implements Runnable {
                AddOobChannel() {
                }

                @Override
                public void run() {
                    if (ManagedChannelImpl.this.terminating) {
                        oobChannel.shutdown();
                    }
                    if (!ManagedChannelImpl.this.terminated) {
                        ManagedChannelImpl.this.oobChannels.add(oobChannel);
                    }
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new AddOobChannel());
            return oobChannel;
        }

        @Override
        @Deprecated
        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target) {
            return this.createResolvingOobChannelBuilder(target, new DefaultChannelCreds()).overrideAuthority(this.getAuthority());
        }

        @Override
        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(final String target, final ChannelCredentials channelCreds) {
            Preconditions.checkNotNull(channelCreds, "channelCreds");
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            final class ResolvingOobChannelBuilder
            extends ForwardingChannelBuilder2<ResolvingOobChannelBuilder> {
                final ManagedChannelBuilder<?> delegate;

                ResolvingOobChannelBuilder() {
                    CallCredentials callCredentials;
                    ClientTransportFactory transportFactory;
                    if (channelCreds instanceof DefaultChannelCreds) {
                        transportFactory = ManagedChannelImpl.this.originalTransportFactory;
                        callCredentials = null;
                    } else {
                        ClientTransportFactory.SwapChannelCredentialsResult swapResult = ManagedChannelImpl.this.originalTransportFactory.swapChannelCredentials(channelCreds);
                        if (swapResult == null) {
                            this.delegate = Grpc.newChannelBuilder(target, channelCreds);
                            return;
                        }
                        transportFactory = swapResult.transportFactory;
                        callCredentials = swapResult.callCredentials;
                    }
                    ManagedChannelImplBuilder.ClientTransportFactoryBuilder transportFactoryBuilder = new ManagedChannelImplBuilder.ClientTransportFactoryBuilder(){

                        @Override
                        public ClientTransportFactory buildClientTransportFactory() {
                            return transportFactory;
                        }
                    };
                    this.delegate = new ManagedChannelImplBuilder(target, channelCreds, callCredentials, transportFactoryBuilder, new ManagedChannelImplBuilder.FixedPortProvider(ManagedChannelImpl.this.nameResolverArgs.getDefaultPort())).nameResolverRegistry(ManagedChannelImpl.this.nameResolverRegistry);
                }

                @Override
                protected ManagedChannelBuilder<?> delegate() {
                    return this.delegate;
                }
            }
            ResolvingOobChannelBuilder builder = new ResolvingOobChannelBuilder();
            return ((ResolvingOobChannelBuilder)((ResolvingOobChannelBuilder)((ResolvingOobChannelBuilder)((ResolvingOobChannelBuilder)builder.executor(ManagedChannelImpl.this.executor)).offloadExecutor(ManagedChannelImpl.this.offloadExecutorHolder.getExecutor())).maxTraceEvents(ManagedChannelImpl.this.maxTraceEvents)).proxyDetector(ManagedChannelImpl.this.nameResolverArgs.getProxyDetector())).userAgent(ManagedChannelImpl.this.userAgent);
        }

        @Override
        public ChannelCredentials getUnsafeChannelCredentials() {
            if (ManagedChannelImpl.this.originalChannelCreds == null) {
                return new DefaultChannelCreds();
            }
            return ManagedChannelImpl.this.originalChannelCreds;
        }

        @Override
        public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
            this.updateOobChannelAddresses(channel, Collections.singletonList(eag));
        }

        @Override
        public void updateOobChannelAddresses(ManagedChannel channel, List<EquivalentAddressGroup> eag) {
            Preconditions.checkArgument(channel instanceof OobChannel, "channel must have been returned from createOobChannel");
            ((OobChannel)channel).updateAddresses(eag);
        }

        @Override
        public String getAuthority() {
            return ManagedChannelImpl.this.authority();
        }

        @Override
        public String getChannelTarget() {
            return ManagedChannelImpl.this.targetUri.toString();
        }

        @Override
        public SynchronizationContext getSynchronizationContext() {
            return ManagedChannelImpl.this.syncContext;
        }

        @Override
        public ScheduledExecutorService getScheduledExecutorService() {
            return ManagedChannelImpl.this.scheduledExecutor;
        }

        @Override
        public ChannelLogger getChannelLogger() {
            return ManagedChannelImpl.this.channelLogger;
        }

        @Override
        public NameResolver.Args getNameResolverArgs() {
            return ManagedChannelImpl.this.nameResolverArgs;
        }

        @Override
        public NameResolverRegistry getNameResolverRegistry() {
            return ManagedChannelImpl.this.nameResolverRegistry;
        }

        @Override
        public MetricRecorder getMetricRecorder() {
            return ManagedChannelImpl.this.metricRecorder;
        }

        final class DefaultChannelCreds
        extends ChannelCredentials {
            DefaultChannelCreds() {
            }

            @Override
            public ChannelCredentials withoutBearerTokens() {
                return this;
            }
        }
    }

    private final class UncommittedRetriableStreamsRegistry {
        final Object lock = new Object();
        @GuardedBy(value="lock")
        Collection<ClientStream> uncommittedRetriableStreams = new HashSet<ClientStream>();
        @GuardedBy(value="lock")
        Status shutdownStatus;

        private UncommittedRetriableStreamsRegistry() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void onShutdown(Status reason) {
            boolean shouldShutdownDelayedTransport = false;
            Object object = this.lock;
            synchronized (object) {
                if (this.shutdownStatus != null) {
                    return;
                }
                this.shutdownStatus = reason;
                if (this.uncommittedRetriableStreams.isEmpty()) {
                    shouldShutdownDelayedTransport = true;
                }
            }
            if (shouldShutdownDelayedTransport) {
                ManagedChannelImpl.this.delayedTransport.shutdown(reason);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void onShutdownNow(Status reason) {
            ArrayList<ClientStream> streams;
            this.onShutdown(reason);
            Iterator iterator2 = this.lock;
            synchronized (iterator2) {
                streams = new ArrayList<ClientStream>(this.uncommittedRetriableStreams);
            }
            for (ClientStream stream : streams) {
                stream.cancel(reason);
            }
            ManagedChannelImpl.this.delayedTransport.shutdownNow(reason);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        Status add(RetriableStream<?> retriableStream) {
            Object object = this.lock;
            synchronized (object) {
                if (this.shutdownStatus != null) {
                    return this.shutdownStatus;
                }
                this.uncommittedRetriableStreams.add(retriableStream);
                return null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void remove(RetriableStream<?> retriableStream) {
            Status shutdownStatusCopy = null;
            Object object = this.lock;
            synchronized (object) {
                this.uncommittedRetriableStreams.remove(retriableStream);
                if (this.uncommittedRetriableStreams.isEmpty()) {
                    shutdownStatusCopy = this.shutdownStatus;
                    this.uncommittedRetriableStreams = new HashSet<ClientStream>();
                }
            }
            if (shutdownStatusCopy != null) {
                ManagedChannelImpl.this.delayedTransport.shutdown(shutdownStatusCopy);
            }
        }
    }

    static final class ConfigSelectingClientCall<ReqT, RespT>
    extends ForwardingClientCall<ReqT, RespT> {
        private final InternalConfigSelector configSelector;
        private final Channel channel;
        private final Executor callExecutor;
        private final MethodDescriptor<ReqT, RespT> method;
        private final Context context;
        private CallOptions callOptions;
        private ClientCall<ReqT, RespT> delegate;

        ConfigSelectingClientCall(InternalConfigSelector configSelector, Channel channel, Executor channelExecutor, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
            this.configSelector = configSelector;
            this.channel = channel;
            this.method = method2;
            this.callExecutor = callOptions.getExecutor() == null ? channelExecutor : callOptions.getExecutor();
            this.callOptions = callOptions.withExecutor(this.callExecutor);
            this.context = Context.current();
        }

        @Override
        protected ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }

        @Override
        public void start(ClientCall.Listener<RespT> observer, Metadata headers) {
            PickSubchannelArgsImpl args2 = new PickSubchannelArgsImpl(this.method, headers, this.callOptions, NOOP_PICK_DETAILS_CONSUMER);
            InternalConfigSelector.Result result = this.configSelector.selectConfig(args2);
            Status status = result.getStatus();
            if (!status.isOk()) {
                this.executeCloseObserverInContext(observer, GrpcUtil.replaceInappropriateControlPlaneStatus(status));
                this.delegate = NOOP_CALL;
                return;
            }
            ClientInterceptor interceptor = result.getInterceptor();
            ManagedChannelServiceConfig config = (ManagedChannelServiceConfig)result.getConfig();
            ManagedChannelServiceConfig.MethodInfo methodInfo = config.getMethodConfig(this.method);
            if (methodInfo != null) {
                this.callOptions = this.callOptions.withOption(ManagedChannelServiceConfig.MethodInfo.KEY, methodInfo);
            }
            this.delegate = interceptor != null ? interceptor.interceptCall(this.method, this.callOptions, this.channel) : this.channel.newCall(this.method, this.callOptions);
            this.delegate.start(observer, headers);
        }

        private void executeCloseObserverInContext(final ClientCall.Listener<RespT> observer, final Status status) {
            class CloseInContext
            extends ContextRunnable {
                CloseInContext() {
                    super(ConfigSelectingClientCall.this.context);
                }

                @Override
                public void runInContext() {
                    observer.onClose(status, new Metadata());
                }
            }
            this.callExecutor.execute(new CloseInContext());
        }

        @Override
        public void cancel(@Nullable String message, @Nullable Throwable cause) {
            if (this.delegate != null) {
                this.delegate.cancel(message, cause);
            }
        }
    }

    private class RealChannel
    extends Channel {
        private final AtomicReference<InternalConfigSelector> configSelector = new AtomicReference<InternalConfigSelector>(ManagedChannelImpl.access$2900());
        private final String authority;
        private final Channel clientCallImplChannel = new Channel(){

            @Override
            public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> method2, CallOptions callOptions) {
                return new ClientCallImpl<RequestT, ResponseT>(method2, ManagedChannelImpl.this.getCallExecutor(callOptions), callOptions, ManagedChannelImpl.this.transportProvider, ManagedChannelImpl.this.terminated ? null : ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.channelCallTracer, null).setFullStreamDecompression(ManagedChannelImpl.this.fullStreamDecompression).setDecompressorRegistry(ManagedChannelImpl.this.decompressorRegistry).setCompressorRegistry(ManagedChannelImpl.this.compressorRegistry);
            }

            @Override
            public String authority() {
                return RealChannel.this.authority;
            }
        };

        private RealChannel(String authority) {
            this.authority = Preconditions.checkNotNull(authority, "authority");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
            if (this.configSelector.get() != INITIAL_PENDING_SELECTOR) {
                return this.newClientCall(method2, callOptions);
            }
            ManagedChannelImpl.this.syncContext.execute(new Runnable(){

                @Override
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                }
            });
            if (this.configSelector.get() != INITIAL_PENDING_SELECTOR) {
                return this.newClientCall(method2, callOptions);
            }
            if (ManagedChannelImpl.this.shutdown.get()) {
                return new ClientCall<ReqT, RespT>(){

                    @Override
                    public void start(ClientCall.Listener<RespT> responseListener, Metadata headers) {
                        responseListener.onClose(SHUTDOWN_STATUS, new Metadata());
                    }

                    @Override
                    public void request(int numMessages) {
                    }

                    @Override
                    public void cancel(@Nullable String message, @Nullable Throwable cause) {
                    }

                    @Override
                    public void halfClose() {
                    }

                    @Override
                    public void sendMessage(ReqT message) {
                    }
                };
            }
            Context context = Context.current();
            final PendingCall<ReqT, RespT> pendingCall = new PendingCall<ReqT, RespT>(context, method2, callOptions);
            ManagedChannelImpl.this.syncContext.execute(new Runnable(){

                @Override
                public void run() {
                    if (RealChannel.this.configSelector.get() == INITIAL_PENDING_SELECTOR) {
                        if (ManagedChannelImpl.this.pendingCalls == null) {
                            ManagedChannelImpl.this.pendingCalls = new LinkedHashSet();
                            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.pendingCallsInUseObject, true);
                        }
                        ManagedChannelImpl.this.pendingCalls.add(pendingCall);
                    } else {
                        pendingCall.reprocess();
                    }
                }
            });
            return pendingCall;
        }

        void updateConfigSelector(@Nullable InternalConfigSelector config) {
            InternalConfigSelector prevConfig = this.configSelector.get();
            this.configSelector.set(config);
            if (prevConfig == INITIAL_PENDING_SELECTOR && ManagedChannelImpl.this.pendingCalls != null) {
                for (PendingCall pendingCall : ManagedChannelImpl.this.pendingCalls) {
                    pendingCall.reprocess();
                }
            }
        }

        void onConfigError() {
            if (this.configSelector.get() == INITIAL_PENDING_SELECTOR) {
                this.updateConfigSelector(null);
            }
        }

        void shutdown() {
            final class RealChannelShutdown
            implements Runnable {
                RealChannelShutdown() {
                }

                @Override
                public void run() {
                    if (ManagedChannelImpl.this.pendingCalls == null) {
                        if (RealChannel.this.configSelector.get() == INITIAL_PENDING_SELECTOR) {
                            RealChannel.this.configSelector.set(null);
                        }
                        ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdown(SHUTDOWN_STATUS);
                    }
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new RealChannelShutdown());
        }

        void shutdownNow() {
            final class RealChannelShutdownNow
            implements Runnable {
                RealChannelShutdownNow() {
                }

                @Override
                public void run() {
                    if (RealChannel.this.configSelector.get() == INITIAL_PENDING_SELECTOR) {
                        RealChannel.this.configSelector.set(null);
                    }
                    if (ManagedChannelImpl.this.pendingCalls != null) {
                        for (PendingCall pendingCall : ManagedChannelImpl.this.pendingCalls) {
                            pendingCall.cancel("Channel is forcefully shutdown", null);
                        }
                    }
                    ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdownNow(SHUTDOWN_NOW_STATUS);
                }
            }
            ManagedChannelImpl.this.syncContext.execute(new RealChannelShutdownNow());
        }

        @Override
        public String authority() {
            return this.authority;
        }

        private <ReqT, RespT> ClientCall<ReqT, RespT> newClientCall(MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
            InternalConfigSelector selector = this.configSelector.get();
            if (selector == null) {
                return this.clientCallImplChannel.newCall(method2, callOptions);
            }
            if (selector instanceof ManagedChannelServiceConfig.ServiceConfigConvertedSelector) {
                ManagedChannelServiceConfig.MethodInfo methodInfo = ((ManagedChannelServiceConfig.ServiceConfigConvertedSelector)selector).config.getMethodConfig(method2);
                if (methodInfo != null) {
                    callOptions = callOptions.withOption(ManagedChannelServiceConfig.MethodInfo.KEY, methodInfo);
                }
                return this.clientCallImplChannel.newCall(method2, callOptions);
            }
            return new ConfigSelectingClientCall<ReqT, RespT>(selector, this.clientCallImplChannel, ManagedChannelImpl.this.executor, method2, callOptions);
        }

        private final class PendingCall<ReqT, RespT>
        extends DelayedClientCall<ReqT, RespT> {
            final Context context;
            final MethodDescriptor<ReqT, RespT> method;
            final CallOptions callOptions;
            private final long callCreationTime;

            PendingCall(Context context, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
                super(ManagedChannelImpl.this.getCallExecutor(callOptions), ManagedChannelImpl.this.scheduledExecutor, callOptions.getDeadline());
                this.context = context;
                this.method = method2;
                this.callOptions = callOptions;
                this.callCreationTime = ManagedChannelImpl.this.ticker.nanoTime();
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            void reprocess() {
                ClientCall realCall;
                Context previous = this.context.attach();
                try {
                    CallOptions delayResolutionOption = this.callOptions.withOption(ClientStreamTracer.NAME_RESOLUTION_DELAYED, ManagedChannelImpl.this.ticker.nanoTime() - this.callCreationTime);
                    realCall = RealChannel.this.newClientCall(this.method, delayResolutionOption);
                }
                finally {
                    this.context.detach(previous);
                }
                final Runnable toRun = this.setCall(realCall);
                if (toRun == null) {
                    ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
                } else {
                    ManagedChannelImpl.this.getCallExecutor(this.callOptions).execute(new Runnable(){

                        @Override
                        public void run() {
                            toRun.run();
                            ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
                        }
                    });
                }
            }

            @Override
            protected void callCancelled() {
                super.callCancelled();
                ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
            }

            final class PendingCallRemoval
            implements Runnable {
                PendingCallRemoval() {
                }

                @Override
                public void run() {
                    if (ManagedChannelImpl.this.pendingCalls != null) {
                        ManagedChannelImpl.this.pendingCalls.remove(PendingCall.this);
                        if (ManagedChannelImpl.this.pendingCalls.isEmpty()) {
                            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.pendingCallsInUseObject, false);
                            ManagedChannelImpl.this.pendingCalls = null;
                            if (ManagedChannelImpl.this.shutdown.get()) {
                                ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdown(SHUTDOWN_STATUS);
                            }
                        }
                    }
                }
            }
        }
    }

    private final class ChannelStreamProvider
    implements ClientCallImpl.ClientStreamProvider {
        volatile RetriableStream.Throttle throttle;

        private ChannelStreamProvider() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ClientStream newStream(final MethodDescriptor<?, ?> method2, final CallOptions callOptions, final Metadata headers, final Context context) {
            if (!ManagedChannelImpl.this.retryEnabled) {
                ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(callOptions, headers, 0, false);
                Context origContext = context.attach();
                try {
                    ClientStream clientStream = ManagedChannelImpl.this.delayedTransport.newStream(method2, headers, callOptions, tracers);
                    return clientStream;
                }
                finally {
                    context.detach(origContext);
                }
            }
            ManagedChannelServiceConfig.MethodInfo methodInfo = callOptions.getOption(ManagedChannelServiceConfig.MethodInfo.KEY);
            final RetryPolicy retryPolicy = methodInfo == null ? null : methodInfo.retryPolicy;
            final HedgingPolicy hedgingPolicy = methodInfo == null ? null : methodInfo.hedgingPolicy;
            final class RetryStream<ReqT>
            extends RetriableStream<ReqT> {
                RetryStream() {
                    super(methodDescriptor, metadata, ManagedChannelImpl.this.channelBufferUsed, ManagedChannelImpl.this.perRpcBufferLimit, ManagedChannelImpl.this.channelBufferLimit, ManagedChannelImpl.this.getCallExecutor(callOptions2), ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), retryPolicy2, hedgingPolicy2, ChannelStreamProvider.this.throttle);
                }

                @Override
                Status prestart() {
                    return ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.add(this);
                }

                @Override
                void postCommit() {
                    ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.remove(this);
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                ClientStream newSubstream(Metadata newHeaders, ClientStreamTracer.Factory factory2, int previousAttempts, boolean isTransparentRetry) {
                    CallOptions newOptions = callOptions.withStreamTracerFactory(factory2);
                    ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(newOptions, newHeaders, previousAttempts, isTransparentRetry);
                    Context origContext = context.attach();
                    try {
                        ClientStream clientStream = ManagedChannelImpl.this.delayedTransport.newStream(method2, newHeaders, newOptions, tracers);
                        return clientStream;
                    }
                    finally {
                        context.detach(origContext);
                    }
                }
            }
            return new RetryStream();
        }
    }

    private class IdleModeTimer
    implements Runnable {
        private IdleModeTimer() {
        }

        @Override
        public void run() {
            if (ManagedChannelImpl.this.lbHelper == null) {
                return;
            }
            ManagedChannelImpl.this.enterIdleMode();
        }
    }
}

