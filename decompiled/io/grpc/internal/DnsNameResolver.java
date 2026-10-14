/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import io.grpc.ProxiedSocketAddress;
import io.grpc.ProxyDetector;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.JsonParser;
import io.grpc.internal.JsonUtil;
import io.grpc.internal.SharedResourceHolder;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class DnsNameResolver
extends NameResolver {
    private static final Logger logger = Logger.getLogger(DnsNameResolver.class.getName());
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY = "clientLanguage";
    private static final String SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY = "percentage";
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY = "clientHostname";
    private static final String SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY = "serviceConfig";
    static final String SERVICE_CONFIG_PREFIX = "grpc_config=";
    private static final Set<String> SERVICE_CONFIG_CHOICE_KEYS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("clientLanguage", "percentage", "clientHostname", "serviceConfig")));
    private static final String SERVICE_CONFIG_NAME_PREFIX = "_grpc_config.";
    private static final String JNDI_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi", "true");
    private static final String JNDI_LOCALHOST_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi_localhost", "false");
    private static final String JNDI_TXT_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_service_config", "false");
    @VisibleForTesting
    static final String NETWORKADDRESS_CACHE_TTL_PROPERTY = "networkaddress.cache.ttl";
    @VisibleForTesting
    static final long DEFAULT_NETWORK_CACHE_TTL_SECONDS = 30L;
    @VisibleForTesting
    static boolean enableJndi = Boolean.parseBoolean(JNDI_PROPERTY);
    @VisibleForTesting
    static boolean enableJndiLocalhost = Boolean.parseBoolean(JNDI_LOCALHOST_PROPERTY);
    @VisibleForTesting
    protected static boolean enableTxt = Boolean.parseBoolean(JNDI_TXT_PROPERTY);
    private static final ResourceResolverFactory resourceResolverFactory = DnsNameResolver.getResourceResolverFactory(DnsNameResolver.class.getClassLoader());
    @VisibleForTesting
    final ProxyDetector proxyDetector;
    private static String localHostname;
    private final Random random = new Random();
    protected volatile AddressResolver addressResolver = JdkAddressResolver.INSTANCE;
    private final AtomicReference<ResourceResolver> resourceResolver = new AtomicReference();
    private final String authority;
    private final String host;
    private final int port;
    private final SharedResourceHolder.Resource<Executor> executorResource;
    private final long cacheTtlNanos;
    private final SynchronizationContext syncContext;
    private final Stopwatch stopwatch;
    protected boolean resolved;
    private boolean shutdown;
    private Executor executor;
    private final boolean usingExecutorResource;
    private final NameResolver.ServiceConfigParser serviceConfigParser;
    private boolean resolving;
    private NameResolver.Listener2 listener;

    protected DnsNameResolver(@Nullable String nsAuthority, String name, NameResolver.Args args2, SharedResourceHolder.Resource<Executor> executorResource, Stopwatch stopwatch, boolean isAndroid) {
        Preconditions.checkNotNull(args2, "args");
        this.executorResource = executorResource;
        URI nameUri = URI.create("//" + Preconditions.checkNotNull(name, "name"));
        Preconditions.checkArgument(nameUri.getHost() != null, "Invalid DNS name: %s", (Object)name);
        this.authority = Preconditions.checkNotNull(nameUri.getAuthority(), "nameUri (%s) doesn't have an authority", (Object)nameUri);
        this.host = nameUri.getHost();
        this.port = nameUri.getPort() == -1 ? args2.getDefaultPort() : nameUri.getPort();
        this.proxyDetector = Preconditions.checkNotNull(args2.getProxyDetector(), "proxyDetector");
        this.cacheTtlNanos = DnsNameResolver.getNetworkAddressCacheTtlNanos(isAndroid);
        this.stopwatch = Preconditions.checkNotNull(stopwatch, "stopwatch");
        this.syncContext = Preconditions.checkNotNull(args2.getSynchronizationContext(), "syncContext");
        this.executor = args2.getOffloadExecutor();
        this.usingExecutorResource = this.executor == null;
        this.serviceConfigParser = Preconditions.checkNotNull(args2.getServiceConfigParser(), "serviceConfigParser");
    }

    @Override
    public String getServiceAuthority() {
        return this.authority;
    }

    @VisibleForTesting
    protected String getHost() {
        return this.host;
    }

    @Override
    public void start(NameResolver.Listener2 listener) {
        Preconditions.checkState(this.listener == null, "already started");
        if (this.usingExecutorResource) {
            this.executor = SharedResourceHolder.get(this.executorResource);
        }
        this.listener = Preconditions.checkNotNull(listener, "listener");
        this.resolve();
    }

    @Override
    public void refresh() {
        Preconditions.checkState(this.listener != null, "not started");
        this.resolve();
    }

    private List<EquivalentAddressGroup> resolveAddresses() {
        List<InetAddress> addresses;
        Exception addressesException = null;
        try {
            addresses = this.addressResolver.resolveAddress(this.host);
        }
        catch (Exception e) {
            addressesException = e;
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        finally {
            if (addressesException != null) {
                logger.log(Level.FINE, "Address resolution failure", addressesException);
            }
        }
        ArrayList<EquivalentAddressGroup> servers = new ArrayList<EquivalentAddressGroup>(addresses.size());
        for (InetAddress inetAddr : addresses) {
            servers.add(new EquivalentAddressGroup(new InetSocketAddress(inetAddr, this.port)));
        }
        return Collections.unmodifiableList(servers);
    }

    @Nullable
    private NameResolver.ConfigOrError resolveServiceConfig() {
        List<String> txtRecords = Collections.emptyList();
        ResourceResolver resourceResolver = this.getResourceResolver();
        if (resourceResolver != null) {
            try {
                txtRecords = resourceResolver.resolveTxt(SERVICE_CONFIG_NAME_PREFIX + this.host);
            }
            catch (Exception e) {
                logger.log(Level.FINE, "ServiceConfig resolution failure", e);
            }
        }
        if (!txtRecords.isEmpty()) {
            NameResolver.ConfigOrError rawServiceConfig = DnsNameResolver.parseServiceConfig(txtRecords, this.random, DnsNameResolver.getLocalHostname());
            if (rawServiceConfig != null) {
                if (rawServiceConfig.getError() != null) {
                    return NameResolver.ConfigOrError.fromError(rawServiceConfig.getError());
                }
                Map verifiedRawServiceConfig = (Map)rawServiceConfig.getConfig();
                return this.serviceConfigParser.parseServiceConfig(verifiedRawServiceConfig);
            }
        } else {
            logger.log(Level.FINE, "No TXT records found for {0}", new Object[]{this.host});
        }
        return null;
    }

    @Nullable
    private EquivalentAddressGroup detectProxy() throws IOException {
        InetSocketAddress destination = InetSocketAddress.createUnresolved(this.host, this.port);
        ProxiedSocketAddress proxiedAddr = this.proxyDetector.proxyFor(destination);
        if (proxiedAddr != null) {
            return new EquivalentAddressGroup(proxiedAddr);
        }
        return null;
    }

    protected InternalResolutionResult doResolve(boolean forceTxt) {
        InternalResolutionResult result;
        block3: {
            result = new InternalResolutionResult();
            try {
                result.addresses = this.resolveAddresses();
            }
            catch (Exception e) {
                if (forceTxt) break block3;
                result.error = Status.UNAVAILABLE.withDescription("Unable to resolve host " + this.host).withCause(e);
                return result;
            }
        }
        if (enableTxt) {
            result.config = this.resolveServiceConfig();
        }
        return result;
    }

    @Nullable
    static NameResolver.ConfigOrError parseServiceConfig(List<String> rawTxtRecords, Random random, String localHostname) {
        List<Map<String, ?>> possibleServiceConfigChoices;
        try {
            possibleServiceConfigChoices = DnsNameResolver.parseTxtResults(rawTxtRecords);
        }
        catch (IOException | RuntimeException e) {
            return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to parse TXT records").withCause(e));
        }
        Map<String, ?> possibleServiceConfig = null;
        for (Map<String, ?> possibleServiceConfigChoice : possibleServiceConfigChoices) {
            try {
                possibleServiceConfig = DnsNameResolver.maybeChooseServiceConfig(possibleServiceConfigChoice, random, localHostname);
            }
            catch (RuntimeException e) {
                return NameResolver.ConfigOrError.fromError(Status.UNKNOWN.withDescription("failed to pick service config choice").withCause(e));
            }
            if (possibleServiceConfig == null) continue;
            break;
        }
        if (possibleServiceConfig == null) {
            return null;
        }
        return NameResolver.ConfigOrError.fromConfig(possibleServiceConfig);
    }

    private void resolve() {
        if (this.resolving || this.shutdown || !this.cacheRefreshRequired()) {
            return;
        }
        this.resolving = true;
        this.executor.execute(new Resolve(this.listener));
    }

    private boolean cacheRefreshRequired() {
        return !this.resolved || this.cacheTtlNanos == 0L || this.cacheTtlNanos > 0L && this.stopwatch.elapsed(TimeUnit.NANOSECONDS) > this.cacheTtlNanos;
    }

    @Override
    public void shutdown() {
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        if (this.executor != null && this.usingExecutorResource) {
            this.executor = SharedResourceHolder.release(this.executorResource, this.executor);
        }
    }

    final int getPort() {
        return this.port;
    }

    @VisibleForTesting
    static List<Map<String, ?>> parseTxtResults(List<String> txtRecords) throws IOException {
        ArrayList possibleServiceConfigChoices = new ArrayList();
        for (String txtRecord : txtRecords) {
            if (!txtRecord.startsWith(SERVICE_CONFIG_PREFIX)) {
                logger.log(Level.FINE, "Ignoring non service config {0}", new Object[]{txtRecord});
                continue;
            }
            Object rawChoices = JsonParser.parse(txtRecord.substring(SERVICE_CONFIG_PREFIX.length()));
            if (!(rawChoices instanceof List)) {
                throw new ClassCastException("wrong type " + rawChoices);
            }
            List listChoices = (List)rawChoices;
            possibleServiceConfigChoices.addAll(JsonUtil.checkObjectList(listChoices));
        }
        return possibleServiceConfigChoices;
    }

    @Nullable
    private static final Double getPercentageFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getNumberAsDouble(serviceConfigChoice, SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY);
    }

    @Nullable
    private static final List<String> getClientLanguagesFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getListOfStrings(serviceConfigChoice, SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY);
    }

    @Nullable
    private static final List<String> getHostnamesFromChoice(Map<String, ?> serviceConfigChoice) {
        return JsonUtil.getListOfStrings(serviceConfigChoice, SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY);
    }

    private static long getNetworkAddressCacheTtlNanos(boolean isAndroid) {
        if (isAndroid) {
            return 0L;
        }
        String cacheTtlPropertyValue = System.getProperty(NETWORKADDRESS_CACHE_TTL_PROPERTY);
        long cacheTtl = 30L;
        if (cacheTtlPropertyValue != null) {
            try {
                cacheTtl = Long.parseLong(cacheTtlPropertyValue);
            }
            catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Property({0}) valid is not valid number format({1}), fall back to default({2})", new Object[]{NETWORKADDRESS_CACHE_TTL_PROPERTY, cacheTtlPropertyValue, cacheTtl});
            }
        }
        return cacheTtl > 0L ? TimeUnit.SECONDS.toNanos(cacheTtl) : cacheTtl;
    }

    @Nullable
    @VisibleForTesting
    static Map<String, ?> maybeChooseServiceConfig(Map<String, ?> choice, Random random, String hostname) {
        Map<String, ?> sc;
        List<String> clientHostnames;
        Double percentage;
        for (Map.Entry<String, ?> entry : choice.entrySet()) {
            Verify.verify(SERVICE_CONFIG_CHOICE_KEYS.contains(entry.getKey()), "Bad key: %s", entry);
        }
        List<String> clientLanguages = DnsNameResolver.getClientLanguagesFromChoice(choice);
        if (clientLanguages != null && !clientLanguages.isEmpty()) {
            boolean javaPresent = false;
            for (String lang : clientLanguages) {
                if (!"java".equalsIgnoreCase(lang)) continue;
                javaPresent = true;
                break;
            }
            if (!javaPresent) {
                return null;
            }
        }
        if ((percentage = DnsNameResolver.getPercentageFromChoice(choice)) != null) {
            int pct = percentage.intValue();
            Verify.verify(pct >= 0 && pct <= 100, "Bad percentage: %s", (Object)percentage);
            if (random.nextInt(100) >= pct) {
                return null;
            }
        }
        if ((clientHostnames = DnsNameResolver.getHostnamesFromChoice(choice)) != null && !clientHostnames.isEmpty()) {
            boolean hostnamePresent = false;
            for (String clientHostname : clientHostnames) {
                if (!clientHostname.equals(hostname)) continue;
                hostnamePresent = true;
                break;
            }
            if (!hostnamePresent) {
                return null;
            }
        }
        if ((sc = JsonUtil.getObject(choice, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY)) == null) {
            throw new VerifyException(String.format("key '%s' missing in '%s'", choice, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY));
        }
        return sc;
    }

    @VisibleForTesting
    protected void setAddressResolver(AddressResolver addressResolver) {
        this.addressResolver = addressResolver;
    }

    @VisibleForTesting
    protected void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver.set(resourceResolver);
    }

    @Nullable
    protected ResourceResolver getResourceResolver() {
        if (!DnsNameResolver.shouldUseJndi(enableJndi, enableJndiLocalhost, this.host)) {
            return null;
        }
        ResourceResolver rr = this.resourceResolver.get();
        if (rr == null && resourceResolverFactory != null) {
            assert (resourceResolverFactory.unavailabilityCause() == null);
            rr = resourceResolverFactory.newResourceResolver();
        }
        return rr;
    }

    @Nullable
    @VisibleForTesting
    static ResourceResolverFactory getResourceResolverFactory(ClassLoader loader) {
        ResourceResolverFactory rrf;
        Constructor<ResourceResolverFactory> jndiCtor;
        Class<ResourceResolverFactory> jndiClazz;
        try {
            jndiClazz = Class.forName("io.grpc.internal.JndiResourceResolverFactory", true, loader).asSubclass(ResourceResolverFactory.class);
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find JndiResourceResolverFactory, skipping.", e);
            return null;
        }
        catch (ClassCastException e) {
            logger.log(Level.FINE, "Unable to cast JndiResourceResolverFactory, skipping.", e);
            return null;
        }
        try {
            jndiCtor = jndiClazz.getConstructor(new Class[0]);
        }
        catch (Exception e) {
            logger.log(Level.FINE, "Can't find JndiResourceResolverFactory ctor, skipping.", e);
            return null;
        }
        try {
            rrf = jndiCtor.newInstance(new Object[0]);
        }
        catch (Exception e) {
            logger.log(Level.FINE, "Can't construct JndiResourceResolverFactory, skipping.", e);
            return null;
        }
        if (rrf.unavailabilityCause() != null) {
            logger.log(Level.FINE, "JndiResourceResolverFactory not available, skipping.", rrf.unavailabilityCause());
            return null;
        }
        return rrf;
    }

    private static String getLocalHostname() {
        if (localHostname == null) {
            try {
                localHostname = InetAddress.getLocalHost().getHostName();
            }
            catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return localHostname;
    }

    @VisibleForTesting
    protected static boolean shouldUseJndi(boolean jndiEnabled, boolean jndiLocalhostEnabled, String target) {
        if (!jndiEnabled) {
            return false;
        }
        if ("localhost".equalsIgnoreCase(target)) {
            return jndiLocalhostEnabled;
        }
        if (target.contains(":")) {
            return false;
        }
        boolean alldigits = true;
        for (int i = 0; i < target.length(); ++i) {
            char c = target.charAt(i);
            if (c == '.') continue;
            alldigits &= c >= '0' && c <= '9';
        }
        return !alldigits;
    }

    static /* synthetic */ Logger access$400() {
        return logger;
    }

    static /* synthetic */ String access$500(DnsNameResolver x0) {
        return x0.host;
    }

    static /* synthetic */ EquivalentAddressGroup access$600(DnsNameResolver x0) throws IOException {
        return x0.detectProxy();
    }

    static /* synthetic */ SynchronizationContext access$1000(DnsNameResolver x0) {
        return x0.syncContext;
    }

    @VisibleForTesting
    public static interface ResourceResolver {
        public List<String> resolveTxt(String var1) throws Exception;

        public List<SrvRecord> resolveSrv(String var1) throws Exception;
    }

    private static enum JdkAddressResolver implements AddressResolver
    {
        INSTANCE;


        @Override
        public List<InetAddress> resolveAddress(String host2) throws UnknownHostException {
            return Collections.unmodifiableList(Arrays.asList(InetAddress.getAllByName(host2)));
        }
    }

    @VisibleForTesting
    public static interface AddressResolver {
        public List<InetAddress> resolveAddress(String var1) throws Exception;
    }

    static interface ResourceResolverFactory {
        @Nullable
        public ResourceResolver newResourceResolver();

        @Nullable
        public Throwable unavailabilityCause();
    }

    @VisibleForTesting
    public static final class SrvRecord {
        public final String host;
        public final int port;

        public SrvRecord(String host2, int port) {
            this.host = host2;
            this.port = port;
        }

        public int hashCode() {
            return Objects.hashCode(this.host, this.port);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            SrvRecord that = (SrvRecord)obj;
            return this.port == that.port && this.host.equals(that.host);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("host", this.host).add("port", this.port).toString();
        }
    }

    protected static final class InternalResolutionResult {
        private Status error;
        private List<EquivalentAddressGroup> addresses;
        private NameResolver.ConfigOrError config;
        public Attributes attributes;

        private InternalResolutionResult() {
        }

        static /* synthetic */ Status access$200(InternalResolutionResult x0) {
            return x0.error;
        }

        static /* synthetic */ List access$100(InternalResolutionResult x0) {
            return x0.addresses;
        }

        static /* synthetic */ NameResolver.ConfigOrError access$300(InternalResolutionResult x0) {
            return x0.config;
        }
    }

    private final class Resolve
    implements Runnable {
        private final NameResolver.Listener2 savedListener;

        Resolve(NameResolver.Listener2 savedListener) {
            this.savedListener = Preconditions.checkNotNull(savedListener, "savedListener");
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Unable to fully structure code
         */
        @Override
        public void run() {
            block10: {
                block12: {
                    if (DnsNameResolver.access$400().isLoggable(Level.FINER)) {
                        DnsNameResolver.access$400().finer("Attempting DNS resolution of " + DnsNameResolver.access$500(DnsNameResolver.this));
                    }
                    result = null;
                    proxiedAddr = DnsNameResolver.access$600(DnsNameResolver.this);
                    resolutionResultBuilder = NameResolver.ResolutionResult.newBuilder();
                    if (proxiedAddr == null) break block12;
                    if (DnsNameResolver.access$400().isLoggable(Level.FINER)) {
                        DnsNameResolver.access$400().finer("Using proxy address " + proxiedAddr);
                    }
                    resolutionResultBuilder.setAddresses(Collections.singletonList(proxiedAddr));
                    ** GOTO lbl31
                }
                result = DnsNameResolver.this.doResolve(false);
                if (InternalResolutionResult.access$200(result) == null) break block10;
                this.savedListener.onError(InternalResolutionResult.access$200(result));
                succeed = result != null && InternalResolutionResult.access$200(result) == null;
                DnsNameResolver.access$1000(DnsNameResolver.this).execute(new Runnable(succeed){
                    final /* synthetic */ boolean val$succeed;
                    {
                        this.val$succeed = bl;
                    }

                    @Override
                    public void run() {
                        if (this.val$succeed) {
                            DnsNameResolver.this.resolved = true;
                            if (DnsNameResolver.this.cacheTtlNanos > 0L) {
                                DnsNameResolver.this.stopwatch.reset().start();
                            }
                        }
                        DnsNameResolver.this.resolving = false;
                    }
                });
                return;
            }
            try {
                if (InternalResolutionResult.access$100(result) != null) {
                    resolutionResultBuilder.setAddresses(InternalResolutionResult.access$100(result));
                }
                if (InternalResolutionResult.access$300(result) != null) {
                    resolutionResultBuilder.setServiceConfig(InternalResolutionResult.access$300(result));
                }
                if (result.attributes != null) {
                    resolutionResultBuilder.setAttributes(result.attributes);
                }
lbl31:
                // 4 sources

                this.savedListener.onResult(resolutionResultBuilder.build());
            }
            catch (IOException e) {
                try {
                    this.savedListener.onError(Status.UNAVAILABLE.withDescription("Unable to resolve host " + DnsNameResolver.access$500(DnsNameResolver.this)).withCause(e));
                }
                catch (Throwable var5_8) {
                    succeed = result != null && InternalResolutionResult.access$200(result) == null;
                    DnsNameResolver.access$1000(DnsNameResolver.this).execute(new /* invalid duplicate definition of identical inner class */);
                    throw var5_8;
                }
                succeed = result != null && InternalResolutionResult.access$200(result) == null;
                DnsNameResolver.access$1000(DnsNameResolver.this).execute(new /* invalid duplicate definition of identical inner class */);
            }
            succeed = result != null && InternalResolutionResult.access$200(result) == null;
            DnsNameResolver.access$1000(DnsNameResolver.this).execute(new /* invalid duplicate definition of identical inner class */);
        }
    }
}

