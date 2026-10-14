/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.errorprone.annotations.DoNotCall;
import io.grpc.BinaryLog;
import io.grpc.BindableService;
import io.grpc.CompressorRegistry;
import io.grpc.Context;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.HandlerRegistry;
import io.grpc.InternalChannelz;
import io.grpc.InternalConfiguratorRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCallExecutorSupplier;
import io.grpc.ServerInterceptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerStreamTracer;
import io.grpc.ServerTransportFilter;
import io.grpc.internal.CallTracer;
import io.grpc.internal.FixedObjectPool;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InternalHandlerRegistry;
import io.grpc.internal.InternalServer;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerImpl;
import io.grpc.internal.SharedResourcePool;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class ServerImplBuilder
extends ServerBuilder<ServerImplBuilder> {
    private static final Logger log = Logger.getLogger(ServerImplBuilder.class.getName());
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final HandlerRegistry DEFAULT_FALLBACK_REGISTRY = new DefaultFallbackRegistry();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final long DEFAULT_HANDSHAKE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(120L);
    final InternalHandlerRegistry.Builder registryBuilder = new InternalHandlerRegistry.Builder();
    final List<ServerTransportFilter> transportFilters = new ArrayList<ServerTransportFilter>();
    final List<ServerInterceptor> interceptors = new ArrayList<ServerInterceptor>();
    private final List<ServerStreamTracer.Factory> streamTracerFactories = new ArrayList<ServerStreamTracer.Factory>();
    private final ClientTransportServersBuilder clientTransportServersBuilder;
    HandlerRegistry fallbackRegistry = DEFAULT_FALLBACK_REGISTRY;
    ObjectPool<? extends Executor> executorPool = DEFAULT_EXECUTOR_POOL;
    DecompressorRegistry decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
    CompressorRegistry compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
    long handshakeTimeoutMillis = DEFAULT_HANDSHAKE_TIMEOUT_MILLIS;
    Deadline.Ticker ticker = Deadline.getSystemTicker();
    private boolean statsEnabled = true;
    private boolean recordStartedRpcs = true;
    private boolean recordFinishedRpcs = true;
    private boolean recordRealTimeMetrics = false;
    private boolean tracingEnabled = true;
    @Nullable
    BinaryLog binlog;
    InternalChannelz channelz = InternalChannelz.instance();
    CallTracer.Factory callTracerFactory = CallTracer.getDefaultFactory();
    @Nullable
    ServerCallExecutorSupplier executorSupplier;

    @DoNotCall(value="ClientTransportServersBuilder is required, use a constructor")
    public static ServerBuilder<?> forPort(int port) {
        throw new UnsupportedOperationException("ClientTransportServersBuilder is required, use a constructor");
    }

    public ServerImplBuilder(ClientTransportServersBuilder clientTransportServersBuilder) {
        this.clientTransportServersBuilder = Preconditions.checkNotNull(clientTransportServersBuilder, "clientTransportServersBuilder");
        InternalConfiguratorRegistry.configureServerBuilder(this);
    }

    @Override
    public ServerImplBuilder directExecutor() {
        return this.executor(MoreExecutors.directExecutor());
    }

    @Override
    public ServerImplBuilder executor(@Nullable Executor executor2) {
        this.executorPool = executor2 != null ? new FixedObjectPool<Executor>(executor2) : DEFAULT_EXECUTOR_POOL;
        return this;
    }

    @Override
    public ServerImplBuilder callExecutor(ServerCallExecutorSupplier executorSupplier) {
        this.executorSupplier = Preconditions.checkNotNull(executorSupplier);
        return this;
    }

    @Override
    public ServerImplBuilder addService(ServerServiceDefinition service2) {
        this.registryBuilder.addService(Preconditions.checkNotNull(service2, "service"));
        return this;
    }

    @Override
    public ServerImplBuilder addService(BindableService bindableService) {
        return this.addService(Preconditions.checkNotNull(bindableService, "bindableService").bindService());
    }

    @Override
    public ServerImplBuilder addTransportFilter(ServerTransportFilter filter2) {
        this.transportFilters.add(Preconditions.checkNotNull(filter2, "filter"));
        return this;
    }

    @Override
    public ServerImplBuilder intercept(ServerInterceptor interceptor) {
        this.interceptors.add(Preconditions.checkNotNull(interceptor, "interceptor"));
        return this;
    }

    @Override
    public ServerImplBuilder addStreamTracerFactory(ServerStreamTracer.Factory factory2) {
        this.streamTracerFactories.add(Preconditions.checkNotNull(factory2, "factory"));
        return this;
    }

    @Override
    public ServerImplBuilder fallbackHandlerRegistry(@Nullable HandlerRegistry registry) {
        this.fallbackRegistry = registry != null ? registry : DEFAULT_FALLBACK_REGISTRY;
        return this;
    }

    @Override
    public ServerImplBuilder decompressorRegistry(@Nullable DecompressorRegistry registry) {
        this.decompressorRegistry = registry != null ? registry : DEFAULT_DECOMPRESSOR_REGISTRY;
        return this;
    }

    @Override
    public ServerImplBuilder compressorRegistry(@Nullable CompressorRegistry registry) {
        this.compressorRegistry = registry != null ? registry : DEFAULT_COMPRESSOR_REGISTRY;
        return this;
    }

    @Override
    public ServerImplBuilder handshakeTimeout(long timeout2, TimeUnit unit) {
        Preconditions.checkArgument(timeout2 > 0L, "handshake timeout is %s, but must be positive", timeout2);
        this.handshakeTimeoutMillis = Preconditions.checkNotNull(unit, "unit").toMillis(timeout2);
        return this;
    }

    @Override
    public ServerImplBuilder setBinaryLog(@Nullable BinaryLog binaryLog) {
        this.binlog = binaryLog;
        return this;
    }

    public void setStatsEnabled(boolean value) {
        this.statsEnabled = value;
    }

    public void setStatsRecordStartedRpcs(boolean value) {
        this.recordStartedRpcs = value;
    }

    public void setStatsRecordFinishedRpcs(boolean value) {
        this.recordFinishedRpcs = value;
    }

    public void setStatsRecordRealTimeMetrics(boolean value) {
        this.recordRealTimeMetrics = value;
    }

    public void setTracingEnabled(boolean value) {
        this.tracingEnabled = value;
    }

    public void setDeadlineTicker(Deadline.Ticker ticker) {
        this.ticker = Preconditions.checkNotNull(ticker, "ticker");
    }

    @Override
    public Server build() {
        return new ServerImpl(this, this.clientTransportServersBuilder.buildClientTransportServers(this.getTracerFactories()), Context.ROOT);
    }

    @VisibleForTesting
    List<? extends ServerStreamTracer.Factory> getTracerFactories() {
        Method getServerStreamTracerFactoryMethod;
        boolean disableImplicitCensus = InternalConfiguratorRegistry.wasSetConfiguratorsCalled();
        if (disableImplicitCensus) {
            return this.streamTracerFactories;
        }
        ArrayList<ServerStreamTracer.Factory> tracerFactories = new ArrayList<ServerStreamTracer.Factory>();
        if (this.statsEnabled) {
            ServerStreamTracer.Factory censusStatsTracerFactory = null;
            try {
                Class<?> censusStatsAccessor = Class.forName("io.grpc.census.InternalCensusStatsAccessor");
                getServerStreamTracerFactoryMethod = censusStatsAccessor.getDeclaredMethod("getServerStreamTracerFactory", Boolean.TYPE, Boolean.TYPE, Boolean.TYPE);
                censusStatsTracerFactory = (ServerStreamTracer.Factory)getServerStreamTracerFactoryMethod.invoke(null, this.recordStartedRpcs, this.recordFinishedRpcs, this.recordRealTimeMetrics);
            }
            catch (ClassNotFoundException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (NoSuchMethodException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (IllegalAccessException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (InvocationTargetException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            if (censusStatsTracerFactory != null) {
                tracerFactories.add(censusStatsTracerFactory);
            }
        }
        if (this.tracingEnabled) {
            ServerStreamTracer.Factory tracingStreamTracerFactory = null;
            try {
                Class<?> censusTracingAccessor = Class.forName("io.grpc.census.InternalCensusTracingAccessor");
                getServerStreamTracerFactoryMethod = censusTracingAccessor.getDeclaredMethod("getServerStreamTracerFactory", new Class[0]);
                tracingStreamTracerFactory = (ServerStreamTracer.Factory)getServerStreamTracerFactoryMethod.invoke(null, new Object[0]);
            }
            catch (ClassNotFoundException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (NoSuchMethodException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (IllegalAccessException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            catch (InvocationTargetException e) {
                log.log(Level.FINE, "Unable to apply census stats", e);
            }
            if (tracingStreamTracerFactory != null) {
                tracerFactories.add(tracingStreamTracerFactory);
            }
        }
        tracerFactories.addAll(this.streamTracerFactories);
        tracerFactories.trimToSize();
        return Collections.unmodifiableList(tracerFactories);
    }

    public InternalChannelz getChannelz() {
        return this.channelz;
    }

    public ObjectPool<? extends Executor> getExecutorPool() {
        return this.executorPool;
    }

    @Override
    public ServerImplBuilder useTransportSecurity(File certChain, File privateKey) {
        throw new UnsupportedOperationException("TLS not supported in ServerImplBuilder");
    }

    private static final class DefaultFallbackRegistry
    extends HandlerRegistry {
        private DefaultFallbackRegistry() {
        }

        @Override
        public List<ServerServiceDefinition> getServices() {
            return Collections.emptyList();
        }

        @Override
        @Nullable
        public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
            return null;
        }
    }

    public static interface ClientTransportServersBuilder {
        public InternalServer buildClientTransportServers(List<? extends ServerStreamTracer.Factory> var1);
    }
}

