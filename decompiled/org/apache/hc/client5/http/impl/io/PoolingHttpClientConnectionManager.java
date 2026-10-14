/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.impl.io;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.SchemePortResolver;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.ConnPoolSupport;
import org.apache.hc.client5.http.impl.ConnectionShutdownException;
import org.apache.hc.client5.http.impl.PrefixedIncrementingId;
import org.apache.hc.client5.http.impl.io.DefaultHttpClientConnectionOperator;
import org.apache.hc.client5.http.impl.io.ManagedHttpClientConnectionFactory;
import org.apache.hc.client5.http.io.ConnectionEndpoint;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionOperator;
import org.apache.hc.client5.http.io.LeaseRequest;
import org.apache.hc.client5.http.io.ManagedHttpClientConnection;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.Internal;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.function.Resolver;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.impl.io.HttpRequestExecutor;
import org.apache.hc.core5.http.io.HttpConnectionFactory;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.pool.ConnPoolControl;
import org.apache.hc.core5.pool.LaxConnPool;
import org.apache.hc.core5.pool.ManagedConnPool;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolEntry;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.pool.PoolStats;
import org.apache.hc.core5.pool.StrictConnPool;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.Asserts;
import org.apache.hc.core5.util.Deadline;
import org.apache.hc.core5.util.Identifiable;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class PoolingHttpClientConnectionManager
implements HttpClientConnectionManager,
ConnPoolControl<HttpRoute> {
    private static final Logger LOG = LoggerFactory.getLogger(PoolingHttpClientConnectionManager.class);
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 25;
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
    private final HttpClientConnectionOperator connectionOperator;
    private final ManagedConnPool<HttpRoute, ManagedHttpClientConnection> pool;
    private final HttpConnectionFactory<ManagedHttpClientConnection> connFactory;
    private final AtomicBoolean closed;
    private volatile Resolver<HttpRoute, SocketConfig> socketConfigResolver;
    private volatile Resolver<HttpRoute, ConnectionConfig> connectionConfigResolver;
    private volatile Resolver<HttpHost, TlsConfig> tlsConfigResolver;
    private static final PrefixedIncrementingId INCREMENTING_ID = new PrefixedIncrementingId("ep-");

    public PoolingHttpClientConnectionManager() {
        this(RegistryBuilder.create().register(URIScheme.HTTP.id, PlainConnectionSocketFactory.getSocketFactory()).register(URIScheme.HTTPS.id, (PlainConnectionSocketFactory)((Object)SSLConnectionSocketFactory.getSocketFactory())).build());
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry) {
        this(socketFactoryRegistry, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this(socketFactoryRegistry, PoolConcurrencyPolicy.STRICT, TimeValue.NEG_ONE_MILLISECOND, connFactory);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry, PoolConcurrencyPolicy poolConcurrencyPolicy, TimeValue timeToLive, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this(socketFactoryRegistry, poolConcurrencyPolicy, PoolReusePolicy.LIFO, timeToLive, connFactory);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry, PoolConcurrencyPolicy poolConcurrencyPolicy, PoolReusePolicy poolReusePolicy, TimeValue timeToLive) {
        this(socketFactoryRegistry, poolConcurrencyPolicy, poolReusePolicy, timeToLive, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry, PoolConcurrencyPolicy poolConcurrencyPolicy, PoolReusePolicy poolReusePolicy, TimeValue timeToLive, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this(socketFactoryRegistry, poolConcurrencyPolicy, poolReusePolicy, timeToLive, null, null, connFactory);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> socketFactoryRegistry, PoolConcurrencyPolicy poolConcurrencyPolicy, PoolReusePolicy poolReusePolicy, TimeValue timeToLive, SchemePortResolver schemePortResolver, DnsResolver dnsResolver, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this(new DefaultHttpClientConnectionOperator(socketFactoryRegistry, schemePortResolver, dnsResolver), poolConcurrencyPolicy, poolReusePolicy, timeToLive, connFactory);
    }

    @Internal
    protected PoolingHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, PoolConcurrencyPolicy poolConcurrencyPolicy, PoolReusePolicy poolReusePolicy, TimeValue timeToLive, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this.connectionOperator = Args.notNull(httpClientConnectionOperator, "Connection operator");
        switch (poolConcurrencyPolicy != null ? poolConcurrencyPolicy : PoolConcurrencyPolicy.STRICT) {
            case STRICT: {
                this.pool = new StrictConnPool<HttpRoute, ManagedHttpClientConnection>(5, 25, timeToLive, poolReusePolicy, null){

                    @Override
                    public void closeExpired() {
                        this.enumAvailable(e -> PoolingHttpClientConnectionManager.this.closeIfExpired((PoolEntry<HttpRoute, ManagedHttpClientConnection>)e));
                    }
                };
                break;
            }
            case LAX: {
                this.pool = new LaxConnPool<HttpRoute, ManagedHttpClientConnection>(5, timeToLive, poolReusePolicy, null){

                    @Override
                    public void closeExpired() {
                        this.enumAvailable(e -> PoolingHttpClientConnectionManager.this.closeIfExpired((PoolEntry<HttpRoute, ManagedHttpClientConnection>)e));
                    }
                };
                break;
            }
            default: {
                throw new IllegalArgumentException("Unexpected PoolConcurrencyPolicy value: " + (Object)((Object)poolConcurrencyPolicy));
            }
        }
        this.connFactory = connFactory != null ? connFactory : ManagedHttpClientConnectionFactory.INSTANCE;
        this.closed = new AtomicBoolean(false);
    }

    @Internal
    protected PoolingHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, ManagedConnPool<HttpRoute, ManagedHttpClientConnection> pool, HttpConnectionFactory<ManagedHttpClientConnection> connFactory) {
        this.connectionOperator = Args.notNull(httpClientConnectionOperator, "Connection operator");
        this.pool = Args.notNull(pool, "Connection pool");
        this.connFactory = connFactory != null ? connFactory : ManagedHttpClientConnectionFactory.INSTANCE;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public void close() {
        this.close(CloseMode.GRACEFUL);
    }

    @Override
    public void close(CloseMode closeMode) {
        if (this.closed.compareAndSet(false, true)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Shutdown connection pool {}", (Object)closeMode);
            }
            this.pool.close(closeMode);
            LOG.debug("Connection pool shut down");
        }
    }

    private InternalConnectionEndpoint cast(ConnectionEndpoint endpoint) {
        if (endpoint instanceof InternalConnectionEndpoint) {
            return (InternalConnectionEndpoint)endpoint;
        }
        throw new IllegalStateException("Unexpected endpoint class: " + endpoint.getClass());
    }

    private SocketConfig resolveSocketConfig(HttpRoute route) {
        Resolver<HttpRoute, SocketConfig> resolver2 = this.socketConfigResolver;
        SocketConfig socketConfig = resolver2 != null ? resolver2.resolve(route) : null;
        return socketConfig != null ? socketConfig : SocketConfig.DEFAULT;
    }

    private ConnectionConfig resolveConnectionConfig(HttpRoute route) {
        Resolver<HttpRoute, ConnectionConfig> resolver2 = this.connectionConfigResolver;
        ConnectionConfig connectionConfig = resolver2 != null ? resolver2.resolve(route) : null;
        return connectionConfig != null ? connectionConfig : ConnectionConfig.DEFAULT;
    }

    private TlsConfig resolveTlsConfig(HttpHost host2) {
        Resolver<HttpHost, TlsConfig> resolver2 = this.tlsConfigResolver;
        TlsConfig tlsConfig = resolver2 != null ? resolver2.resolve(host2) : null;
        return tlsConfig != null ? tlsConfig : TlsConfig.DEFAULT;
    }

    private TimeValue resolveValidateAfterInactivity(ConnectionConfig connectionConfig) {
        TimeValue timeValue = connectionConfig.getValidateAfterInactivity();
        return timeValue != null ? timeValue : TimeValue.ofSeconds(2L);
    }

    public LeaseRequest lease(String id, HttpRoute route, Object state) {
        return this.lease(id, route, Timeout.DISABLED, state);
    }

    @Override
    public LeaseRequest lease(final String id, final HttpRoute route, Timeout requestTimeout, final Object state) {
        Args.notNull(route, "HTTP route");
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} endpoint lease request ({}) {}", id, requestTimeout, ConnPoolSupport.formatStats(route, state, this.pool));
        }
        final Future leaseFuture = this.pool.lease(route, state, requestTimeout, null);
        return new LeaseRequest(){
            private volatile ConnectionEndpoint endpoint;

            @Override
            public synchronized ConnectionEndpoint get(Timeout timeout2) throws InterruptedException, ExecutionException, TimeoutException {
                PoolEntry poolEntry;
                Args.notNull(timeout2, "Operation timeout");
                if (this.endpoint != null) {
                    return this.endpoint;
                }
                try {
                    poolEntry = (PoolEntry)leaseFuture.get(timeout2.getDuration(), timeout2.getTimeUnit());
                }
                catch (TimeoutException ex) {
                    leaseFuture.cancel(true);
                    throw ex;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} endpoint leased {}", (Object)id, (Object)ConnPoolSupport.formatStats(route, state, PoolingHttpClientConnectionManager.this.pool));
                }
                ConnectionConfig connectionConfig = PoolingHttpClientConnectionManager.this.resolveConnectionConfig(route);
                try {
                    ManagedHttpClientConnection conn;
                    TimeValue timeValue;
                    Deadline deadline;
                    TimeValue timeToLive;
                    if (poolEntry.hasConnection() && TimeValue.isNonNegative(timeToLive = connectionConfig.getTimeToLive()) && (deadline = Deadline.calculate(poolEntry.getCreated(), timeToLive)).isExpired()) {
                        poolEntry.discardConnection(CloseMode.GRACEFUL);
                    }
                    if (poolEntry.hasConnection() && TimeValue.isNonNegative(timeValue = PoolingHttpClientConnectionManager.this.resolveValidateAfterInactivity(connectionConfig)) && (deadline = Deadline.calculate(poolEntry.getUpdated(), timeValue)).isExpired()) {
                        boolean stale;
                        ManagedHttpClientConnection conn2 = (ManagedHttpClientConnection)poolEntry.getConnection();
                        try {
                            stale = conn2.isStale();
                        }
                        catch (IOException ignore) {
                            stale = true;
                        }
                        if (stale) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("{} connection {} is stale", (Object)id, (Object)ConnPoolSupport.getId(conn2));
                            }
                            poolEntry.discardConnection(CloseMode.IMMEDIATE);
                        }
                    }
                    if ((conn = (ManagedHttpClientConnection)poolEntry.getConnection()) != null) {
                        conn.activate();
                    } else {
                        poolEntry.assignConnection(PoolingHttpClientConnectionManager.this.connFactory.createConnection(null));
                    }
                    this.endpoint = new InternalConnectionEndpoint(poolEntry);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} acquired {}", (Object)id, (Object)ConnPoolSupport.getId(this.endpoint));
                    }
                    return this.endpoint;
                }
                catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} endpoint lease failed", (Object)id);
                    }
                    PoolingHttpClientConnectionManager.this.pool.release(poolEntry, false);
                    throw new ExecutionException(ex.getMessage(), ex);
                }
            }

            @Override
            public boolean cancel() {
                return leaseFuture.cancel(true);
            }
        };
    }

    @Override
    public void release(ConnectionEndpoint endpoint, Object state, TimeValue keepAlive) {
        ManagedHttpClientConnection conn;
        Args.notNull(endpoint, "Managed endpoint");
        PoolEntry<HttpRoute, ManagedHttpClientConnection> entry = this.cast(endpoint).detach();
        if (entry == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} releasing endpoint", (Object)ConnPoolSupport.getId(endpoint));
        }
        if ((conn = entry.getConnection()) != null && keepAlive == null) {
            conn.close(CloseMode.GRACEFUL);
        }
        boolean reusable = conn != null && conn.isOpen() && conn.isConsistent();
        try {
            if (reusable) {
                entry.updateState(state);
                entry.updateExpiry(keepAlive);
                conn.passivate();
                if (LOG.isDebugEnabled()) {
                    String s2 = TimeValue.isPositive(keepAlive) ? "for " + keepAlive : "indefinitely";
                    LOG.debug("{} connection {} can be kept alive {}", ConnPoolSupport.getId(endpoint), ConnPoolSupport.getId(conn), s2);
                }
            } else if (LOG.isDebugEnabled()) {
                LOG.debug("{} connection is not kept alive", (Object)ConnPoolSupport.getId(endpoint));
            }
        }
        catch (RuntimeException ex) {
            reusable = false;
            throw ex;
        }
        finally {
            this.pool.release(entry, reusable);
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} connection released {}", (Object)ConnPoolSupport.getId(endpoint), (Object)ConnPoolSupport.formatStats(entry.getRoute(), entry.getState(), this.pool));
            }
        }
    }

    @Override
    public void connect(ConnectionEndpoint endpoint, TimeValue timeout2, HttpContext context) throws IOException {
        Timeout socketTimeout;
        Timeout connectTimeout;
        HttpRoute route;
        Args.notNull(endpoint, "Managed endpoint");
        InternalConnectionEndpoint internalEndpoint = this.cast(endpoint);
        if (internalEndpoint.isConnected()) {
            return;
        }
        PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = internalEndpoint.getPoolEntry();
        if (!poolEntry.hasConnection()) {
            poolEntry.assignConnection(this.connFactory.createConnection(null));
        }
        HttpHost host2 = (route = poolEntry.getRoute()).getProxyHost() != null ? route.getProxyHost() : route.getTargetHost();
        SocketConfig socketConfig = this.resolveSocketConfig(route);
        ConnectionConfig connectionConfig = this.resolveConnectionConfig(route);
        TlsConfig tlsConfig = this.resolveTlsConfig(host2);
        Timeout timeout3 = connectTimeout = timeout2 != null ? Timeout.of(timeout2.getDuration(), timeout2.getTimeUnit()) : connectionConfig.getConnectTimeout();
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} connecting endpoint to {} ({})", ConnPoolSupport.getId(endpoint), host2, connectTimeout);
        }
        ManagedHttpClientConnection conn = poolEntry.getConnection();
        this.connectionOperator.connect(conn, host2, route.getLocalSocketAddress(), connectTimeout, socketConfig, tlsConfig, context);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} connected {}", (Object)ConnPoolSupport.getId(endpoint), (Object)ConnPoolSupport.getId(conn));
        }
        if ((socketTimeout = connectionConfig.getSocketTimeout()) != null) {
            conn.setSocketTimeout(socketTimeout);
        }
    }

    @Override
    public void upgrade(ConnectionEndpoint endpoint, HttpContext context) throws IOException {
        Args.notNull(endpoint, "Managed endpoint");
        InternalConnectionEndpoint internalEndpoint = this.cast(endpoint);
        PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = internalEndpoint.getValidatedPoolEntry();
        HttpRoute route = poolEntry.getRoute();
        HttpHost host2 = route.getProxyHost() != null ? route.getProxyHost() : route.getTargetHost();
        TlsConfig tlsConfig = this.resolveTlsConfig(host2);
        this.connectionOperator.upgrade(poolEntry.getConnection(), route.getTargetHost(), tlsConfig, context);
    }

    @Override
    public void closeIdle(TimeValue idleTime) {
        Args.notNull(idleTime, "Idle time");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Closing connections idle longer than {}", (Object)idleTime);
        }
        this.pool.closeIdle(idleTime);
    }

    @Override
    public void closeExpired() {
        LOG.debug("Closing expired connections");
        this.pool.closeExpired();
    }

    @Override
    public Set<HttpRoute> getRoutes() {
        return this.pool.getRoutes();
    }

    @Override
    public int getMaxTotal() {
        return this.pool.getMaxTotal();
    }

    @Override
    public void setMaxTotal(int max) {
        this.pool.setMaxTotal(max);
    }

    @Override
    public int getDefaultMaxPerRoute() {
        return this.pool.getDefaultMaxPerRoute();
    }

    @Override
    public void setDefaultMaxPerRoute(int max) {
        this.pool.setDefaultMaxPerRoute(max);
    }

    @Override
    public int getMaxPerRoute(HttpRoute route) {
        return this.pool.getMaxPerRoute(route);
    }

    @Override
    public void setMaxPerRoute(HttpRoute route, int max) {
        this.pool.setMaxPerRoute(route, max);
    }

    @Override
    public PoolStats getTotalStats() {
        return this.pool.getTotalStats();
    }

    @Override
    public PoolStats getStats(HttpRoute route) {
        return this.pool.getStats(route);
    }

    public void setDefaultSocketConfig(SocketConfig config) {
        this.socketConfigResolver = route -> config;
    }

    public void setSocketConfigResolver(Resolver<HttpRoute, SocketConfig> socketConfigResolver) {
        this.socketConfigResolver = socketConfigResolver;
    }

    public void setDefaultConnectionConfig(ConnectionConfig config) {
        this.connectionConfigResolver = route -> config;
    }

    public void setConnectionConfigResolver(Resolver<HttpRoute, ConnectionConfig> connectionConfigResolver) {
        this.connectionConfigResolver = connectionConfigResolver;
    }

    public void setDefaultTlsConfig(TlsConfig config) {
        this.tlsConfigResolver = host2 -> config;
    }

    public void setTlsConfigResolver(Resolver<HttpHost, TlsConfig> tlsConfigResolver) {
        this.tlsConfigResolver = tlsConfigResolver;
    }

    void closeIfExpired(PoolEntry<HttpRoute, ManagedHttpClientConnection> entry) {
        long now = System.currentTimeMillis();
        if (entry.getExpiryDeadline().isBefore(now)) {
            entry.discardConnection(CloseMode.GRACEFUL);
        } else {
            ConnectionConfig connectionConfig = this.resolveConnectionConfig(entry.getRoute());
            TimeValue timeToLive = connectionConfig.getTimeToLive();
            if (timeToLive != null && Deadline.calculate(entry.getCreated(), timeToLive).isBefore(now)) {
                entry.discardConnection(CloseMode.GRACEFUL);
            }
        }
    }

    @Deprecated
    public SocketConfig getDefaultSocketConfig() {
        return SocketConfig.DEFAULT;
    }

    @Deprecated
    public TimeValue getValidateAfterInactivity() {
        return ConnectionConfig.DEFAULT.getValidateAfterInactivity();
    }

    @Deprecated
    public void setValidateAfterInactivity(TimeValue validateAfterInactivity) {
        this.setDefaultConnectionConfig(ConnectionConfig.custom().setValidateAfterInactivity(validateAfterInactivity).build());
    }

    class InternalConnectionEndpoint
    extends ConnectionEndpoint
    implements Identifiable {
        private final AtomicReference<PoolEntry<HttpRoute, ManagedHttpClientConnection>> poolEntryRef;
        private final String id;

        InternalConnectionEndpoint(PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry) {
            this.poolEntryRef = new AtomicReference<PoolEntry<HttpRoute, ManagedHttpClientConnection>>(poolEntry);
            this.id = INCREMENTING_ID.getNextId();
        }

        @Override
        public String getId() {
            return this.id;
        }

        PoolEntry<HttpRoute, ManagedHttpClientConnection> getPoolEntry() {
            PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = this.poolEntryRef.get();
            if (poolEntry == null) {
                throw new ConnectionShutdownException();
            }
            return poolEntry;
        }

        PoolEntry<HttpRoute, ManagedHttpClientConnection> getValidatedPoolEntry() {
            PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = this.getPoolEntry();
            ManagedHttpClientConnection connection = poolEntry.getConnection();
            Asserts.check(connection != null && connection.isOpen(), "Endpoint is not connected");
            return poolEntry;
        }

        PoolEntry<HttpRoute, ManagedHttpClientConnection> detach() {
            return this.poolEntryRef.getAndSet(null);
        }

        @Override
        public void close(CloseMode closeMode) {
            PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = this.poolEntryRef.get();
            if (poolEntry != null) {
                poolEntry.discardConnection(closeMode);
            }
        }

        @Override
        public void close() throws IOException {
            PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = this.poolEntryRef.get();
            if (poolEntry != null) {
                poolEntry.discardConnection(CloseMode.GRACEFUL);
            }
        }

        @Override
        public boolean isConnected() {
            PoolEntry<HttpRoute, ManagedHttpClientConnection> poolEntry = this.getPoolEntry();
            ManagedHttpClientConnection connection = poolEntry.getConnection();
            return connection != null && connection.isOpen();
        }

        @Override
        public void setSocketTimeout(Timeout timeout2) {
            this.getValidatedPoolEntry().getConnection().setSocketTimeout(timeout2);
        }

        @Override
        public ClassicHttpResponse execute(String exchangeId, ClassicHttpRequest request, HttpRequestExecutor requestExecutor, HttpContext context) throws IOException, HttpException {
            Args.notNull(request, "HTTP request");
            Args.notNull(requestExecutor, "Request executor");
            ManagedHttpClientConnection connection = this.getValidatedPoolEntry().getConnection();
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} executing exchange {} over {}", this.id, exchangeId, ConnPoolSupport.getId(connection));
            }
            return requestExecutor.execute(request, connection, context);
        }
    }
}

