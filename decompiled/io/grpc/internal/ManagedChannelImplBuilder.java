/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.errorprone.annotations.DoNotCall;
import io.grpc.Attributes;
import io.grpc.BinaryLog;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ChannelCredentials;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientTransportFilter;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalConfiguratorRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import io.grpc.MetricSink;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.NameResolverRegistry;
import io.grpc.ProxyDetector;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ExponentialBackoffPolicy;
import io.grpc.internal.FixedObjectPool;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ManagedChannelImpl;
import io.grpc.internal.ManagedChannelOrphanWrapper;
import io.grpc.internal.NameResolverFactoryToProviderFacade;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.SharedResourcePool;
import io.grpc.internal.TimeProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public final class ManagedChannelImplBuilder
extends ManagedChannelBuilder<ManagedChannelImplBuilder> {
    private static final String DIRECT_ADDRESS_SCHEME = "directaddress";
    private static final Logger log = Logger.getLogger(ManagedChannelImplBuilder.class.getName());
    @VisibleForTesting
    static final long IDLE_MODE_MAX_TIMEOUT_DAYS = 30L;
    @VisibleForTesting
    static final long IDLE_MODE_DEFAULT_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(30L);
    static final long IDLE_MODE_MIN_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(1L);
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final long DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES = 0x1000000L;
    private static final long DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES = 0x100000L;
    @VisibleForTesting
    static final Pattern URI_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+.-]*:/.*");
    private static final Method GET_CLIENT_INTERCEPTOR_METHOD;
    ObjectPool<? extends Executor> executorPool = DEFAULT_EXECUTOR_POOL;
    ObjectPool<? extends Executor> offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
    private final List<ClientInterceptor> interceptors = new ArrayList<ClientInterceptor>();
    NameResolverRegistry nameResolverRegistry = NameResolverRegistry.getDefaultRegistry();
    final List<ClientTransportFilter> transportFilters = new ArrayList<ClientTransportFilter>();
    final String target;
    @Nullable
    final ChannelCredentials channelCredentials;
    @Nullable
    final CallCredentials callCredentials;
    @Nullable
    private final SocketAddress directServerAddress;
    @Nullable
    String userAgent;
    @Nullable
    String authorityOverride;
    String defaultLbPolicy = "pick_first";
    boolean fullStreamDecompression;
    DecompressorRegistry decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
    CompressorRegistry compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
    long idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
    int maxRetryAttempts = 5;
    int maxHedgedAttempts = 5;
    long retryBufferSize = 0x1000000L;
    long perRpcBufferLimit = 0x100000L;
    boolean retryEnabled = true;
    InternalChannelz channelz = InternalChannelz.instance();
    int maxTraceEvents;
    @Nullable
    Map<String, ?> defaultServiceConfig;
    boolean lookUpServiceConfig = true;
    @Nullable
    BinaryLog binlog;
    @Nullable
    ProxyDetector proxyDetector;
    private boolean authorityCheckerDisabled;
    private boolean statsEnabled = true;
    private boolean recordStartedRpcs = true;
    private boolean recordFinishedRpcs = true;
    private boolean recordRealTimeMetrics = false;
    private boolean recordRetryMetrics = true;
    private boolean tracingEnabled = true;
    List<MetricSink> metricSinks = new ArrayList<MetricSink>();
    private final ClientTransportFactoryBuilder clientTransportFactoryBuilder;
    private final ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider;

    @DoNotCall(value="ClientTransportFactoryBuilder is required, use a constructor")
    public static ManagedChannelBuilder<?> forAddress(String name, int port) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    @DoNotCall(value="ClientTransportFactoryBuilder is required, use a constructor")
    public static ManagedChannelBuilder<?> forTarget(String target) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    public ManagedChannelImplBuilder(String target, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this(target, null, null, clientTransportFactoryBuilder, channelBuilderDefaultPortProvider);
    }

    public ManagedChannelImplBuilder(String target, @Nullable ChannelCredentials channelCreds, @Nullable CallCredentials callCreds, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this.target = Preconditions.checkNotNull(target, "target");
        this.channelCredentials = channelCreds;
        this.callCredentials = callCreds;
        this.clientTransportFactoryBuilder = Preconditions.checkNotNull(clientTransportFactoryBuilder, "clientTransportFactoryBuilder");
        this.directServerAddress = null;
        this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider != null ? channelBuilderDefaultPortProvider : new ManagedChannelDefaultPortProvider();
        InternalConfiguratorRegistry.configureChannelBuilder(this);
    }

    @VisibleForTesting
    static String makeTargetStringForDirectAddress(SocketAddress address) {
        try {
            return new URI(DIRECT_ADDRESS_SCHEME, "", "/" + address, null).toString();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ManagedChannelImplBuilder(SocketAddress directServerAddress, String authority, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this(directServerAddress, authority, null, null, clientTransportFactoryBuilder, channelBuilderDefaultPortProvider);
    }

    public ManagedChannelImplBuilder(SocketAddress directServerAddress, String authority, @Nullable ChannelCredentials channelCreds, @Nullable CallCredentials callCreds, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this.target = ManagedChannelImplBuilder.makeTargetStringForDirectAddress(directServerAddress);
        this.channelCredentials = channelCreds;
        this.callCredentials = callCreds;
        this.clientTransportFactoryBuilder = Preconditions.checkNotNull(clientTransportFactoryBuilder, "clientTransportFactoryBuilder");
        this.directServerAddress = directServerAddress;
        NameResolverRegistry reg = new NameResolverRegistry();
        reg.register(new DirectAddressNameResolverProvider(directServerAddress, authority));
        this.nameResolverRegistry = reg;
        this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider != null ? channelBuilderDefaultPortProvider : new ManagedChannelDefaultPortProvider();
        InternalConfiguratorRegistry.configureChannelBuilder(this);
    }

    @Override
    public ManagedChannelImplBuilder directExecutor() {
        return this.executor(MoreExecutors.directExecutor());
    }

    @Override
    public ManagedChannelImplBuilder executor(Executor executor2) {
        this.executorPool = executor2 != null ? new FixedObjectPool<Executor>(executor2) : DEFAULT_EXECUTOR_POOL;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder offloadExecutor(Executor executor2) {
        this.offloadExecutorPool = executor2 != null ? new FixedObjectPool<Executor>(executor2) : DEFAULT_EXECUTOR_POOL;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder intercept(List<ClientInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
        return this;
    }

    @Override
    public ManagedChannelImplBuilder intercept(ClientInterceptor ... interceptors) {
        return this.intercept((List)Arrays.asList(interceptors));
    }

    @Override
    protected ManagedChannelImplBuilder interceptWithTarget(ManagedChannelBuilder.InterceptorFactory factory2) {
        this.interceptors.add(new InterceptorFactoryWrapper(factory2));
        return this;
    }

    @Override
    public ManagedChannelImplBuilder addTransportFilter(ClientTransportFilter hook) {
        this.transportFilters.add(Preconditions.checkNotNull(hook, "transport filter"));
        return this;
    }

    @Override
    @Deprecated
    public ManagedChannelImplBuilder nameResolverFactory(NameResolver.Factory resolverFactory) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of NameResolverFactory", (Object)this.directServerAddress);
        if (resolverFactory != null) {
            NameResolverRegistry reg = new NameResolverRegistry();
            if (resolverFactory instanceof NameResolverProvider) {
                reg.register((NameResolverProvider)resolverFactory);
            } else {
                reg.register(new NameResolverFactoryToProviderFacade(resolverFactory));
            }
            this.nameResolverRegistry = reg;
        } else {
            this.nameResolverRegistry = NameResolverRegistry.getDefaultRegistry();
        }
        return this;
    }

    ManagedChannelImplBuilder nameResolverRegistry(NameResolverRegistry resolverRegistry) {
        this.nameResolverRegistry = resolverRegistry;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder defaultLoadBalancingPolicy(String policy) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of load-balancing policy", (Object)this.directServerAddress);
        Preconditions.checkArgument(policy != null, "policy cannot be null");
        this.defaultLbPolicy = policy;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder decompressorRegistry(DecompressorRegistry registry) {
        this.decompressorRegistry = registry != null ? registry : DEFAULT_DECOMPRESSOR_REGISTRY;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder compressorRegistry(CompressorRegistry registry) {
        this.compressorRegistry = registry != null ? registry : DEFAULT_COMPRESSOR_REGISTRY;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder userAgent(@Nullable String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder overrideAuthority(String authority) {
        this.authorityOverride = this.checkAuthority(authority);
        return this;
    }

    @Override
    public ManagedChannelImplBuilder idleTimeout(long value, TimeUnit unit) {
        Preconditions.checkArgument(value > 0L, "idle timeout is %s, but must be positive", value);
        this.idleTimeoutMillis = unit.toDays(value) >= 30L ? -1L : Math.max(unit.toMillis(value), IDLE_MODE_MIN_TIMEOUT_MILLIS);
        return this;
    }

    @Override
    public ManagedChannelImplBuilder maxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder maxHedgedAttempts(int maxHedgedAttempts) {
        this.maxHedgedAttempts = maxHedgedAttempts;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder retryBufferSize(long bytes) {
        Preconditions.checkArgument(bytes > 0L, "retry buffer size must be positive");
        this.retryBufferSize = bytes;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder perRpcBufferLimit(long bytes) {
        Preconditions.checkArgument(bytes > 0L, "per RPC buffer limit must be positive");
        this.perRpcBufferLimit = bytes;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder disableRetry() {
        this.retryEnabled = false;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder enableRetry() {
        this.retryEnabled = true;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder setBinaryLog(BinaryLog binlog) {
        this.binlog = binlog;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder maxTraceEvents(int maxTraceEvents) {
        Preconditions.checkArgument(maxTraceEvents >= 0, "maxTraceEvents must be non-negative");
        this.maxTraceEvents = maxTraceEvents;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder proxyDetector(@Nullable ProxyDetector proxyDetector) {
        this.proxyDetector = proxyDetector;
        return this;
    }

    @Override
    public ManagedChannelImplBuilder defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        this.defaultServiceConfig = ManagedChannelImplBuilder.checkMapEntryTypes(serviceConfig);
        return this;
    }

    @Nullable
    private static Map<String, ?> checkMapEntryTypes(@Nullable Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        LinkedHashMap<String, Object> parsedMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Preconditions.checkArgument(entry.getKey() instanceof String, "The key of the entry '%s' is not of String type", entry);
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                parsedMap.put(key, null);
                continue;
            }
            if (value instanceof Map) {
                parsedMap.put(key, ManagedChannelImplBuilder.checkMapEntryTypes((Map)value));
                continue;
            }
            if (value instanceof List) {
                parsedMap.put(key, ManagedChannelImplBuilder.checkListEntryTypes((List)value));
                continue;
            }
            if (value instanceof String) {
                parsedMap.put(key, value);
                continue;
            }
            if (value instanceof Double) {
                parsedMap.put(key, value);
                continue;
            }
            if (value instanceof Boolean) {
                parsedMap.put(key, value);
                continue;
            }
            throw new IllegalArgumentException("The value of the map entry '" + entry + "' is of type '" + value.getClass() + "', which is not supported");
        }
        return Collections.unmodifiableMap(parsedMap);
    }

    private static List<?> checkListEntryTypes(List<?> list) {
        ArrayList<Object> parsedList = new ArrayList<Object>(list.size());
        for (Object value : list) {
            if (value == null) {
                parsedList.add(null);
                continue;
            }
            if (value instanceof Map) {
                parsedList.add(ManagedChannelImplBuilder.checkMapEntryTypes((Map)value));
                continue;
            }
            if (value instanceof List) {
                parsedList.add(ManagedChannelImplBuilder.checkListEntryTypes((List)value));
                continue;
            }
            if (value instanceof String) {
                parsedList.add(value);
                continue;
            }
            if (value instanceof Double) {
                parsedList.add(value);
                continue;
            }
            if (value instanceof Boolean) {
                parsedList.add(value);
                continue;
            }
            throw new IllegalArgumentException("The entry '" + value + "' is of type '" + value.getClass() + "', which is not supported");
        }
        return Collections.unmodifiableList(parsedList);
    }

    @Override
    public ManagedChannelImplBuilder disableServiceConfigLookUp() {
        this.lookUpServiceConfig = false;
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

    public void setStatsRecordRetryMetrics(boolean value) {
        this.recordRetryMetrics = value;
    }

    public void setTracingEnabled(boolean value) {
        this.tracingEnabled = value;
    }

    @VisibleForTesting
    String checkAuthority(String authority) {
        if (this.authorityCheckerDisabled) {
            return authority;
        }
        return GrpcUtil.checkAuthority(authority);
    }

    public ManagedChannelImplBuilder disableCheckAuthority() {
        this.authorityCheckerDisabled = true;
        return this;
    }

    public ManagedChannelImplBuilder enableCheckAuthority() {
        this.authorityCheckerDisabled = false;
        return this;
    }

    @Override
    protected ManagedChannelImplBuilder addMetricSink(MetricSink metricSink) {
        this.metricSinks.add(Preconditions.checkNotNull(metricSink, "metric sink"));
        return this;
    }

    @Override
    public ManagedChannel build() {
        ClientTransportFactory clientTransportFactory = this.clientTransportFactoryBuilder.buildClientTransportFactory();
        ResolvedNameResolver resolvedResolver = ManagedChannelImplBuilder.getNameResolverProvider(this.target, this.nameResolverRegistry, clientTransportFactory.getSupportedSocketAddressTypes());
        return new ManagedChannelOrphanWrapper(new ManagedChannelImpl(this, clientTransportFactory, resolvedResolver.targetUri, resolvedResolver.provider, new ExponentialBackoffPolicy.Provider(), SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR), GrpcUtil.STOPWATCH_SUPPLIER, this.getEffectiveInterceptors(resolvedResolver.targetUri.toString()), TimeProvider.SYSTEM_TIME_PROVIDER));
    }

    @VisibleForTesting
    List<ClientInterceptor> getEffectiveInterceptors(String computedTarget) {
        ArrayList<ClientInterceptor> effectiveInterceptors = new ArrayList<ClientInterceptor>(this.interceptors);
        for (int i = 0; i < effectiveInterceptors.size(); ++i) {
            if (!(effectiveInterceptors.get(i) instanceof InterceptorFactoryWrapper)) continue;
            ManagedChannelBuilder.InterceptorFactory factory2 = ((InterceptorFactoryWrapper)effectiveInterceptors.get((int)i)).factory;
            ClientInterceptor interceptor = factory2.newInterceptor(computedTarget);
            if (interceptor == null) {
                throw new NullPointerException("Factory returned null interceptor: " + factory2);
            }
            effectiveInterceptors.set(i, interceptor);
        }
        boolean disableImplicitCensus = InternalConfiguratorRegistry.wasSetConfiguratorsCalled();
        if (disableImplicitCensus) {
            return effectiveInterceptors;
        }
        if (this.statsEnabled) {
            ClientInterceptor statsInterceptor = null;
            if (GET_CLIENT_INTERCEPTOR_METHOD != null) {
                try {
                    statsInterceptor = (ClientInterceptor)GET_CLIENT_INTERCEPTOR_METHOD.invoke(null, this.recordStartedRpcs, this.recordFinishedRpcs, this.recordRealTimeMetrics, this.recordRetryMetrics);
                }
                catch (IllegalAccessException e) {
                    log.log(Level.FINE, "Unable to apply census stats", e);
                }
                catch (InvocationTargetException e) {
                    log.log(Level.FINE, "Unable to apply census stats", e);
                }
            }
            if (statsInterceptor != null) {
                effectiveInterceptors.add(0, statsInterceptor);
            }
        }
        if (this.tracingEnabled) {
            ClientInterceptor tracingInterceptor = null;
            try {
                Class<?> censusTracingAccessor = Class.forName("io.grpc.census.InternalCensusTracingAccessor");
                Method getClientInterceptroMethod = censusTracingAccessor.getDeclaredMethod("getClientInterceptor", new Class[0]);
                tracingInterceptor = (ClientInterceptor)getClientInterceptroMethod.invoke(null, new Object[0]);
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
            if (tracingInterceptor != null) {
                effectiveInterceptors.add(0, tracingInterceptor);
            }
        }
        return effectiveInterceptors;
    }

    int getDefaultPort() {
        return this.channelBuilderDefaultPortProvider.getDefaultPort();
    }

    @VisibleForTesting
    static ResolvedNameResolver getNameResolverProvider(String target, NameResolverRegistry nameResolverRegistry, Collection<Class<? extends SocketAddress>> channelTransportSocketAddressTypes) {
        Collection<Class<? extends SocketAddress>> nameResolverSocketAddressTypes;
        NameResolverProvider provider = null;
        URI targetUri = null;
        StringBuilder uriSyntaxErrors = new StringBuilder();
        try {
            targetUri = new URI(target);
        }
        catch (URISyntaxException e) {
            uriSyntaxErrors.append(e.getMessage());
        }
        if (targetUri != null) {
            provider = nameResolverRegistry.getProviderForScheme(targetUri.getScheme());
        }
        if (provider == null && !URI_PATTERN.matcher(target).matches()) {
            try {
                targetUri = new URI(nameResolverRegistry.getDefaultScheme(), "", "/" + target, null);
            }
            catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
            provider = nameResolverRegistry.getProviderForScheme(targetUri.getScheme());
        }
        if (provider == null) {
            throw new IllegalArgumentException(String.format("Could not find a NameResolverProvider for %s%s", target, uriSyntaxErrors.length() > 0 ? " (" + uriSyntaxErrors + ")" : ""));
        }
        if (channelTransportSocketAddressTypes != null && !channelTransportSocketAddressTypes.containsAll(nameResolverSocketAddressTypes = provider.getProducedSocketAddressTypes())) {
            throw new IllegalArgumentException(String.format("Address types of NameResolver '%s' for '%s' not supported by transport", targetUri.getScheme(), target));
        }
        return new ResolvedNameResolver(targetUri, provider);
    }

    public ObjectPool<? extends Executor> getOffloadExecutorPool() {
        return this.offloadExecutorPool;
    }

    static {
        Method getClientInterceptorMethod = null;
        try {
            Class<?> censusStatsAccessor = Class.forName("io.grpc.census.InternalCensusStatsAccessor");
            getClientInterceptorMethod = censusStatsAccessor.getDeclaredMethod("getClientInterceptor", Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE);
        }
        catch (ClassNotFoundException e) {
            log.log(Level.FINE, "Unable to apply census stats", e);
        }
        catch (NoSuchMethodException e) {
            log.log(Level.FINE, "Unable to apply census stats", e);
        }
        GET_CLIENT_INTERCEPTOR_METHOD = getClientInterceptorMethod;
    }

    private static final class InterceptorFactoryWrapper
    implements ClientInterceptor {
        final ManagedChannelBuilder.InterceptorFactory factory;

        public InterceptorFactoryWrapper(ManagedChannelBuilder.InterceptorFactory factory2) {
            this.factory = Preconditions.checkNotNull(factory2, "factory");
        }

        @Override
        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions, Channel next) {
            throw new AssertionError((Object)"Should have been replaced with real instance");
        }
    }

    private static class DirectAddressNameResolverProvider
    extends NameResolverProvider {
        final SocketAddress address;
        final String authority;
        final Collection<Class<? extends SocketAddress>> producedSocketAddressTypes;

        DirectAddressNameResolverProvider(SocketAddress address, String authority) {
            this.address = address;
            this.authority = authority;
            this.producedSocketAddressTypes = Collections.singleton(address.getClass());
        }

        @Override
        public NameResolver newNameResolver(URI notUsedUri, NameResolver.Args args2) {
            return new NameResolver(){

                @Override
                public String getServiceAuthority() {
                    return authority;
                }

                @Override
                public void start(NameResolver.Listener2 listener) {
                    listener.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup(address))).setAttributes(Attributes.EMPTY).build());
                }

                @Override
                public void shutdown() {
                }
            };
        }

        @Override
        public String getDefaultScheme() {
            return ManagedChannelImplBuilder.DIRECT_ADDRESS_SCHEME;
        }

        @Override
        protected boolean isAvailable() {
            return true;
        }

        @Override
        protected int priority() {
            return 5;
        }

        @Override
        public Collection<Class<? extends SocketAddress>> getProducedSocketAddressTypes() {
            return this.producedSocketAddressTypes;
        }
    }

    @VisibleForTesting
    static class ResolvedNameResolver {
        public final URI targetUri;
        public final NameResolverProvider provider;

        public ResolvedNameResolver(URI targetUri, NameResolverProvider provider) {
            this.targetUri = Preconditions.checkNotNull(targetUri, "targetUri");
            this.provider = Preconditions.checkNotNull(provider, "provider");
        }
    }

    private static final class ManagedChannelDefaultPortProvider
    implements ChannelBuilderDefaultPortProvider {
        private ManagedChannelDefaultPortProvider() {
        }

        @Override
        public int getDefaultPort() {
            return 443;
        }
    }

    public static final class FixedPortProvider
    implements ChannelBuilderDefaultPortProvider {
        private final int port;

        public FixedPortProvider(int port) {
            this.port = port;
        }

        @Override
        public int getDefaultPort() {
            return this.port;
        }
    }

    public static interface ChannelBuilderDefaultPortProvider {
        public int getDefaultPort();
    }

    public static class UnsupportedClientTransportFactoryBuilder
    implements ClientTransportFactoryBuilder {
        @Override
        public ClientTransportFactory buildClientTransportFactory() {
            throw new UnsupportedOperationException();
        }
    }

    public static interface ClientTransportFactoryBuilder {
        public ClientTransportFactory buildClientTransportFactory();
    }
}

