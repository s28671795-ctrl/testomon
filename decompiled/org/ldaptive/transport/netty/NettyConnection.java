/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.ldaptive.AbandonRequest;
import org.ldaptive.AddRequest;
import org.ldaptive.AddResponse;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.ClosedRetryMetadata;
import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.ConnectException;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.ConnectionValidator;
import org.ldaptive.DeleteRequest;
import org.ldaptive.DeleteResponse;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.LdapURL;
import org.ldaptive.Message;
import org.ldaptive.ModifyDnRequest;
import org.ldaptive.ModifyDnResponse;
import org.ldaptive.ModifyRequest;
import org.ldaptive.ModifyResponse;
import org.ldaptive.Result;
import org.ldaptive.ResultCode;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResultReference;
import org.ldaptive.UnbindRequest;
import org.ldaptive.control.RequestControl;
import org.ldaptive.extended.ExtendedRequest;
import org.ldaptive.extended.ExtendedResponse;
import org.ldaptive.extended.IntermediateResponse;
import org.ldaptive.extended.StartTLSRequest;
import org.ldaptive.extended.UnsolicitedNotification;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SaslClient;
import org.ldaptive.sasl.SaslClientRequest;
import org.ldaptive.ssl.HostnameResolver;
import org.ldaptive.ssl.HostnameVerifierAdapter;
import org.ldaptive.ssl.SSLContextInitializer;
import org.ldaptive.ssl.SslConfig;
import org.ldaptive.transport.DefaultCompareOperationHandle;
import org.ldaptive.transport.DefaultExtendedOperationHandle;
import org.ldaptive.transport.DefaultOperationHandle;
import org.ldaptive.transport.DefaultSaslClient;
import org.ldaptive.transport.DefaultSearchOperationHandle;
import org.ldaptive.transport.ResponseParser;
import org.ldaptive.transport.TransportConnection;
import org.ldaptive.transport.netty.AutoReadFlowControlHandler;
import org.ldaptive.transport.netty.EncodedRequest;
import org.ldaptive.transport.netty.HandleMap;
import org.ldaptive.transport.netty.MessageFrameDecoder;
import org.ldaptive.transport.netty.NettyDERBuffer;
import org.ldaptive.transport.netty.NettyUtils;
import org.ldaptive.transport.netty.SaslHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NettyConnection
extends TransportConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyConnection.class);
    private static final RequestEncoder REQUEST_ENCODER = new RequestEncoder();
    private static final AutoReadEventHandler READ_NEXT_MESSAGE = new AutoReadEventHandler();
    private final Class<? extends Channel> channelType;
    private final EventLoopGroup ioWorkerGroup;
    private final EventLoopGroup messageWorkerGroup;
    private boolean shutdownOnClose;
    private final Map<ChannelOption, Object> channelOptions;
    private final HandleMap pendingResponses;
    private final CloseFutureListener closeListener = new CloseFutureListener();
    private final AtomicInteger messageID = new AtomicInteger(1);
    private final ReentrantReadWriteLock reconnectLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock bindLock = new ReentrantReadWriteLock();
    private ExecutorService connectionExecutor;
    private LdapURL ldapURL;
    private Channel channel;
    private Instant connectTime;
    private Throwable inboundException;

    public NettyConnection(ConnectionConfig config, Class<? extends Channel> type, EventLoopGroup ioGroup, EventLoopGroup messageGroup, boolean shutdownGroups) {
        super(config);
        if (ioGroup == null) {
            throw new NullPointerException("I/O worker group cannot be null");
        }
        this.channelType = type;
        this.ioWorkerGroup = ioGroup;
        this.messageWorkerGroup = messageGroup;
        this.channelOptions = new HashMap<ChannelOption, Object>();
        this.channelOptions.put(ChannelOption.SO_KEEPALIVE, true);
        this.channelOptions.put(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int)config.getConnectTimeout().toMillis());
        if (config.getTransportOptions() != null && !config.getTransportOptions().isEmpty()) {
            for (Map.Entry<String, ?> e : config.getTransportOptions().entrySet()) {
                ChannelOption option = ChannelOption.valueOf(e.getKey());
                Object value = e.getValue();
                if (value instanceof String) {
                    this.channelOptions.put(option, this.convertChannelOption((String)value));
                    continue;
                }
                this.channelOptions.put(option, value);
            }
        }
        this.shutdownOnClose = shutdownGroups;
        this.pendingResponses = new HandleMap();
    }

    private Object convertChannelOption(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.valueOf(value);
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException numberFormatException) {
            return value;
        }
    }

    private Bootstrap createBootstrap(ClientInitializer initializer) {
        if (this.ioWorkerGroup.isShutdown()) {
            throw new IllegalStateException("Attempt to open connection with shutdown event loop on " + this);
        }
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.ioWorkerGroup);
        bootstrap.channel(this.channelType);
        this.channelOptions.forEach(bootstrap::option);
        bootstrap.handler(initializer);
        LOGGER.trace("created netty bootstrap {} with worker group {} for {}", bootstrap, this.ioWorkerGroup, this);
        return bootstrap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean test(LdapURL url2) {
        try (NettyConnection conn = new NettyConnection(this.connectionConfig, this.channelType, this.ioWorkerGroup, this.messageWorkerGroup, false);){
            conn.open(url2);
            LOGGER.debug("Test of {} successful", (Object)conn);
            boolean bl = true;
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void open(LdapURL url2) throws LdapException {
        if (this.isOpen()) {
            throw new IllegalStateException("Connection is already open");
        }
        LOGGER.trace("opening connection {}", (Object)this);
        if (this.openLock.tryLock()) {
            try {
                ConnectionInitializer[] result;
                this.inboundException = null;
                this.ldapURL = url2;
                if (this.connectionExecutor == null) {
                    this.connectionExecutor = Executors.newSingleThreadExecutor(r -> {
                        Thread t2 = new Thread(r, this.getClass().getSimpleName() + "@" + this.hashCode());
                        t2.setDaemon(true);
                        return t2;
                    });
                }
                this.channel = this.connectInternal();
                this.channel.closeFuture().addListener(this.closeListener);
                this.pendingResponses.open();
                if (this.connectionConfig.getUseStartTLS() && !(result = this.operation(new StartTLSRequest())).isSuccess()) {
                    throw new ConnectException(ResultCode.CONNECT_ERROR, "StartTLS returned response: " + (Result)result + " for URL " + url2);
                }
                if (this.connectionConfig.getConnectionInitializers() != null) {
                    for (ConnectionInitializer initializer : this.connectionConfig.getConnectionInitializers()) {
                        Result result2 = initializer.initialize(this);
                        if (result2.isSuccess()) continue;
                        throw new ConnectException(ResultCode.CONNECT_ERROR, "Connection initializer " + initializer + " returned response: " + result2 + " for URL " + url2);
                    }
                }
                this.connectTime = Instant.now();
                LOGGER.debug("Netty opened connection {}", (Object)this);
            }
            catch (Exception e) {
                LOGGER.error("Connection open failed for {}", (Object)this, (Object)e);
                try {
                    this.notifyOperationHandlesOfClose();
                    this.pendingResponses.close();
                    if (this.isOpen()) {
                        this.channel.closeFuture().removeListener(this.closeListener);
                        this.channel.close().addListener(new LogFutureListener());
                    }
                }
                finally {
                    this.pendingResponses.clear();
                    this.channel = null;
                }
                throw e;
            }
            finally {
                this.openLock.unlock();
            }
        }
        LOGGER.warn("Open lock {} could not be acquired by {}", (Object)this.openLock, (Object)Thread.currentThread());
        throw new ConnectException(ResultCode.CONNECT_ERROR, "Open in progress");
    }

    @Override
    public LdapURL getLdapURL() {
        return this.ldapURL;
    }

    private Channel connectInternal() throws ConnectException {
        SslHandler handler = null;
        if (this.ldapURL.getScheme().equals("ldaps")) {
            try {
                handler = this.createSslHandler(this.connectionConfig);
            }
            catch (SSLException e) {
                throw new ConnectException(ResultCode.CONNECT_ERROR, (Throwable)e);
            }
        }
        ClientInitializer initializer = new ClientInitializer(handler);
        Bootstrap bootstrap = this.createBootstrap(initializer);
        CountDownLatch channelLatch = new CountDownLatch(1);
        LOGGER.trace("connecting to bootstrap {} with URL {} for {}", bootstrap, this.ldapURL, this);
        ChannelFuture future = this.ldapURL.getInetAddress() != null ? bootstrap.connect(this.ldapURL.getInetAddress(), this.ldapURL.getPort()) : bootstrap.connect(new InetSocketAddress(this.ldapURL.getHostname(), this.ldapURL.getPort()));
        future.addListener(f -> channelLatch.countDown());
        try {
            if (!channelLatch.await(this.connectionConfig.getConnectTimeout().multipliedBy(2L).toMillis(), TimeUnit.MILLISECONDS)) {
                LOGGER.warn("Error connecting to {} for {}. connectTimeout was not honored, check number of available threads", (Object)this.ldapURL, (Object)this);
                future.cancel(true);
            }
        }
        catch (InterruptedException e) {
            future.cancel(true);
        }
        LOGGER.trace("bootstrap connect returned {} for {}", (Object)future, (Object)this);
        if (future.isCancelled()) {
            throw new ConnectException(ResultCode.CONNECT_ERROR, "Connection cancelled");
        }
        if (!future.isSuccess()) {
            if (future.cause() != null) {
                throw new ConnectException(ResultCode.SERVER_DOWN, future.cause());
            }
            throw new ConnectException(ResultCode.SERVER_DOWN, "Connection could not be opened");
        }
        if (initializer.isSsl()) {
            try {
                this.waitForSSLHandshake(future.channel());
            }
            catch (SSLException e) {
                future.channel().close();
                throw new ConnectException(ResultCode.CONNECT_ERROR, (Throwable)e);
            }
        }
        return future.channel();
    }

    private SslHandler createSslHandler(ConnectionConfig config) throws SSLException {
        SSLContext ctx;
        SslConfig sc = config.getSslConfig() != null ? SslConfig.copy(config.getSslConfig()) : new SslConfig();
        try {
            SSLContextInitializer initializer = sc.createSSLContextInitializer();
            ctx = initializer.initSSLContext("TLS");
        }
        catch (GeneralSecurityException e) {
            throw new SSLException("Could not initialize SSL context", e);
        }
        SSLEngine engine = ctx.createSSLEngine(this.ldapURL.getHostname(), this.ldapURL.getPort());
        engine.setUseClientMode(true);
        if (sc.getEnabledProtocols() != null) {
            engine.setEnabledProtocols(sc.getEnabledProtocols());
        }
        if (sc.getEnabledCipherSuites() != null) {
            engine.setEnabledCipherSuites(sc.getEnabledCipherSuites());
        }
        if (sc.getHostnameVerifier() == null) {
            SSLParameters sslParams = engine.getSSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("LDAPS");
            engine.setSSLParameters(sslParams);
        }
        SslHandler handler = new SslHandler(engine);
        handler.setHandshakeTimeout(sc.getHandshakeTimeout().toMillis(), TimeUnit.MILLISECONDS);
        return handler;
    }

    private void waitForSSLHandshake(Channel ch) throws SSLException {
        SSLSession session;
        HostnameResolver resolver2;
        String hostname;
        HostnameVerifierAdapter verifier;
        CountDownLatch sslLatch = new CountDownLatch(1);
        SslHandler handler = ch.pipeline().get(SslHandler.class);
        Future<Channel> sslFuture = handler.handshakeFuture();
        sslFuture.addListener(f -> sslLatch.countDown());
        try {
            if (!sslLatch.await(handler.getHandshakeTimeoutMillis() * 2L, TimeUnit.MILLISECONDS)) {
                LOGGER.warn("Error starting SSL with {} for {}. handShakeTimeout was not honored, check number of available threads", (Object)this.ldapURL, (Object)this);
                sslFuture.cancel(true);
            }
        }
        catch (InterruptedException e) {
            sslFuture.cancel(true);
        }
        if (sslFuture.isCancelled()) {
            throw new SSLException("SSL handshake cancelled");
        }
        if (!sslFuture.isSuccess()) {
            if (this.inboundException != null) {
                throw new SSLException(this.inboundException);
            }
            if (sslFuture.cause() != null) {
                throw new SSLException(sslFuture.cause());
            }
            throw new SSLException("SSL handshake failure");
        }
        if (this.connectionConfig.getSslConfig() != null && this.connectionConfig.getSslConfig().getHostnameVerifier() != null && !(verifier = new HostnameVerifierAdapter(this.connectionConfig.getSslConfig().getHostnameVerifier())).verify(hostname = (resolver2 = new HostnameResolver(session = handler.engine().getSession())).resolve(), session)) {
            throw new SSLPeerUnverifiedException("Hostname verification failed for " + hostname + " using " + verifier);
        }
    }

    Result operation(StartTLSRequest request) throws LdapException {
        Object result;
        this.throwIfClosed();
        if (this.channel.pipeline().get(SslHandler.class) != null) {
            throw new ConnectException(ResultCode.LOCAL_ERROR, "SslHandler is already in use");
        }
        DefaultExtendedOperationHandle handle2 = new DefaultExtendedOperationHandle(request, (TransportConnection)this, this.connectionConfig.getResponseTimeout());
        try {
            result = handle2.execute();
        }
        catch (LdapException e) {
            throw new ConnectException(ResultCode.CONNECT_ERROR, "StartTLS operation failed", e);
        }
        if (result.isSuccess()) {
            try {
                this.channel.pipeline().addFirst("ssl", (ChannelHandler)this.createSslHandler(this.connectionConfig));
                this.waitForSSLHandshake(this.channel);
            }
            catch (SSLException e) {
                throw new ConnectException(ResultCode.CONNECT_ERROR, (Throwable)e);
            }
        } else {
            throw new ConnectException(ResultCode.CONNECT_ERROR, "StartTLS operation failed with result " + result);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void operation(UnbindRequest request) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("unbind request {} with pending responses {} for {}", request, this.pendingResponses, this);
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Unbind request {} with {} pending responses for {}", request, this.pendingResponses.size(), this);
        }
        if (this.reconnectLock.readLock().tryLock()) {
            try {
                if (!this.isOpen()) {
                    LOGGER.warn("Attempt to unbind ignored, connection {} is not open", (Object)this);
                }
                if (this.bindLock.readLock().tryLock()) {
                    try {
                        EncodedRequest encodedRequest = new EncodedRequest(this.getAndIncrementMessageID(), request);
                        this.channel.writeAndFlush(encodedRequest).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }
                    finally {
                        this.bindLock.readLock().unlock();
                    }
                }
                throw new IllegalStateException("Bind in progress, cannot send unbind request");
            }
            finally {
                this.reconnectLock.readLock().unlock();
            }
        } else {
            LOGGER.warn("Attempt to unbind ignored, connection {} is reconnecting", (Object)this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BindResponse operation(SaslClientRequest request) throws LdapException {
        this.throwIfClosed();
        if (!this.bindLock.writeLock().tryLock()) {
            throw new LdapException(ResultCode.LOCAL_ERROR, "Operation in progress, cannot send bind request");
        }
        try {
            BindResponse result;
            SaslClient client = request.getSaslClient();
            try {
                result = client.bind(this, request);
            }
            catch (Exception e) {
                if (e instanceof LdapException) {
                    throw (LdapException)e;
                }
                throw new LdapException(ResultCode.LOCAL_ERROR, (Throwable)e);
            }
            if (result == null) {
                throw new LdapException(ResultCode.LOCAL_ERROR, "SASL operation failed");
            }
            BindResponse bindResponse = result;
            return bindResponse;
        }
        finally {
            this.bindLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public BindResponse operation(DefaultSaslClientRequest request) throws LdapException {
        BindResponse result;
        block20: {
            this.throwIfClosed();
            if (!this.bindLock.writeLock().tryLock()) {
                throw new LdapException(ResultCode.LOCAL_ERROR, "Operation in progress, cannot send bind request");
            }
            SaslClient client = request.getSaslClient();
            if (client instanceof DefaultSaslClient) {
                DefaultSaslClient defaultClient = (DefaultSaslClient)client;
                boolean saslSecurity = false;
                try {
                    Object qop;
                    BindResponse response = defaultClient.bind((TransportConnection)this, request);
                    if (response.getResultCode() == ResultCode.SUCCESS && (QualityOfProtection.AUTH_INT == (qop = defaultClient.getQualityOfProtection()) || QualityOfProtection.AUTH_CONF == qop)) {
                        if (this.channel.pipeline().get(SaslHandler.class) != null) {
                            this.channel.pipeline().remove(SaslHandler.class);
                        }
                        if (this.channel.pipeline().get(SslHandler.class) != null) {
                            this.channel.pipeline().addAfter("ssl", "sasl", new SaslHandler(defaultClient.getClient()));
                        } else {
                            this.channel.pipeline().addFirst("sasl", (ChannelHandler)new SaslHandler(defaultClient.getClient()));
                        }
                        saslSecurity = true;
                    }
                    qop = response;
                    return qop;
                }
                catch (Exception e) {
                    throw new LdapException(ResultCode.LOCAL_ERROR, "SASL bind operation failed", e);
                }
                finally {
                    if (!saslSecurity) {
                        defaultClient.dispose();
                    }
                }
            }
            result = client.bind(this, request);
            break block20;
            catch (Exception e) {
                if (e instanceof LdapException) {
                    throw (LdapException)e;
                }
                throw new LdapException(ResultCode.LOCAL_ERROR, (Throwable)e);
            }
        }
        if (result == null) {
            throw new LdapException(ResultCode.LOCAL_ERROR, "SASL GSSAPI operation failed");
        }
        BindResponse bindResponse = result;
        return bindResponse;
        finally {
            this.bindLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void operation(AbandonRequest request) {
        DefaultOperationHandle handle2 = this.pendingResponses.remove(request.getMessageID());
        if (handle2 == null && this.pendingResponses.isOpen()) {
            LOGGER.warn("Attempt to abandon message {} that no longer exists for {}", (Object)request.getMessageID(), (Object)this);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("abandon {} {} with pending responses {}", handle2 != null ? "handle" : "messageID", handle2 != null ? handle2 : Integer.valueOf(request.getMessageID()), this.pendingResponses);
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Abandon {} {} with {} pending responses", handle2 != null ? "handle" : "messageID", handle2 != null ? handle2 : Integer.valueOf(request.getMessageID()), this.pendingResponses.size());
        }
        if (this.reconnectLock.readLock().tryLock()) {
            try {
                if (!this.isOpen()) {
                    if (handle2 == null) return;
                    handle2.exception(new LdapException(ResultCode.SERVER_DOWN, "Connection is not open"));
                    return;
                }
                if (this.bindLock.readLock().tryLock()) {
                    try {
                        EncodedRequest encodedRequest = new EncodedRequest(this.getAndIncrementMessageID(), request);
                        this.channel.writeAndFlush(encodedRequest).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                        return;
                    }
                    finally {
                        this.bindLock.readLock().unlock();
                    }
                }
                if (handle2 == null) return;
                handle2.exception(new LdapException(ResultCode.LOCAL_ERROR, "Bind in progress"));
                return;
            }
            finally {
                this.reconnectLock.readLock().unlock();
            }
        } else {
            if (handle2 == null) return;
            handle2.exception(new LdapException(ResultCode.SERVER_DOWN, "Reconnect in progress"));
        }
    }

    public DefaultOperationHandle<AddRequest, AddResponse> operation(AddRequest request) {
        return new DefaultOperationHandle<AddRequest, AddResponse>(request, this, this.connectionConfig.getResponseTimeout());
    }

    public BindOperationHandle operation(BindRequest request) {
        return new BindOperationHandle(request, this, this.connectionConfig.getResponseTimeout());
    }

    @Override
    public DefaultCompareOperationHandle operation(CompareRequest request) {
        return new DefaultCompareOperationHandle(request, (TransportConnection)this, this.connectionConfig.getResponseTimeout());
    }

    public DefaultOperationHandle<DeleteRequest, DeleteResponse> operation(DeleteRequest request) {
        return new DefaultOperationHandle<DeleteRequest, DeleteResponse>(request, this, this.connectionConfig.getResponseTimeout());
    }

    @Override
    public DefaultExtendedOperationHandle operation(ExtendedRequest request) {
        if (request instanceof StartTLSRequest) {
            throw new IllegalArgumentException("StartTLS can only be invoked when the connection is opened");
        }
        return new DefaultExtendedOperationHandle(request, (TransportConnection)this, this.connectionConfig.getResponseTimeout());
    }

    public DefaultOperationHandle<ModifyRequest, ModifyResponse> operation(ModifyRequest request) {
        return new DefaultOperationHandle<ModifyRequest, ModifyResponse>(request, this, this.connectionConfig.getResponseTimeout());
    }

    public DefaultOperationHandle<ModifyDnRequest, ModifyDnResponse> operation(ModifyDnRequest request) {
        return new DefaultOperationHandle<ModifyDnRequest, ModifyDnResponse>(request, this, this.connectionConfig.getResponseTimeout());
    }

    @Override
    public DefaultSearchOperationHandle operation(SearchRequest request) {
        return new DefaultSearchOperationHandle(request, (TransportConnection)this, this.connectionConfig.getResponseTimeout());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void write(DefaultOperationHandle handle2) {
        block22: {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("write handle {} with pending responses {}", (Object)handle2, (Object)this.pendingResponses);
            } else if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Write handle {} with {} pending responses", (Object)handle2, (Object)this.pendingResponses.size());
            }
            try {
                boolean gotReconnectLock;
                if (Duration.ZERO.equals(this.connectionConfig.getReconnectTimeout())) {
                    this.reconnectLock.readLock().lock();
                    gotReconnectLock = true;
                } else {
                    gotReconnectLock = this.reconnectLock.readLock().tryLock(this.connectionConfig.getReconnectTimeout().toMillis(), TimeUnit.MILLISECONDS);
                }
                if (gotReconnectLock) {
                    try {
                        if (!this.isOpen()) {
                            handle2.exception(new LdapException(ResultCode.SERVER_DOWN, "Connection is closed, write aborted"));
                            break block22;
                        }
                        if (this.bindLock.readLock().tryLock()) {
                            try {
                                EncodedRequest encodedRequest = new EncodedRequest(this.getAndIncrementMessageID(), handle2.getRequest());
                                handle2.messageID(encodedRequest.getMessageID());
                                try {
                                    if (this.pendingResponses.put(encodedRequest.getMessageID(), handle2) != null) {
                                        throw new LdapException(ResultCode.ENCODING_ERROR, "Request already exists for ID " + encodedRequest.getMessageID());
                                    }
                                }
                                catch (LdapException e) {
                                    if (this.inboundException != null) {
                                        throw new LdapException(ResultCode.SERVER_DOWN, e.getMessage(), this.inboundException);
                                    }
                                    throw e;
                                }
                                this.channel.writeAndFlush(encodedRequest).addListeners(new GenericFutureListener[]{ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE, f -> {
                                    if (f.isSuccess()) {
                                        handle2.sent();
                                    }
                                }});
                                if (LOGGER.isTraceEnabled() && this.channel.eventLoop() instanceof SingleThreadEventLoop) {
                                    LOGGER.trace("event loop group {} has {} pending tasks for {}", this.channel.eventLoop().parent(), ((SingleThreadEventLoop)this.channel.eventLoop()).pendingTasks(), this);
                                }
                                break block22;
                            }
                            finally {
                                this.bindLock.readLock().unlock();
                            }
                        }
                        handle2.exception(new LdapException(ResultCode.LOCAL_ERROR, "Bind in progress"));
                        break block22;
                    }
                    finally {
                        this.reconnectLock.readLock().unlock();
                    }
                }
                handle2.exception(new LdapException(ResultCode.SERVER_DOWN, "Reconnect in progress"));
            }
            catch (Exception e) {
                if (e instanceof LdapException) {
                    handle2.exception((LdapException)e);
                }
                handle2.exception(new LdapException(ResultCode.LOCAL_ERROR, (Throwable)e));
            }
        }
    }

    @Override
    protected void complete(DefaultOperationHandle handle2) {
        if (handle2 != null && handle2.getMessageID() != null) {
            this.pendingResponses.remove(handle2.getMessageID());
        }
    }

    int getAndIncrementMessageID() {
        return this.messageID.getAndUpdate(i -> i < Integer.MAX_VALUE ? i + 1 : 1);
    }

    int getMessageID() {
        return this.messageID.get();
    }

    void setMessageID(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("messageID must be greater than zero");
        }
        this.messageID.set(i);
    }

    Map<ChannelOption, Object> getChannelOptions() {
        return this.channelOptions;
    }

    @Override
    public void close(RequestControl ... controls) {
        LOGGER.trace("closing connection {}", (Object)this);
        if (this.closeLock.tryLock()) {
            try {
                this.pendingResponses.close();
                if (this.connectionExecutor != null) {
                    this.connectionExecutor.shutdown();
                }
                if (this.isOpen()) {
                    LOGGER.trace("connection {} is open, initiate orderly shutdown", (Object)this);
                    this.channel.closeFuture().removeListener(this.closeListener);
                    if (this.pendingResponses.size() > 0) {
                        if (LOGGER.isTraceEnabled()) {
                            LOGGER.trace("abandoning requests {} for {} to close connection", (Object)this.pendingResponses, (Object)this);
                        } else if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("Abandoning {} requests for {} to close connection", (Object)this.pendingResponses.size(), (Object)this);
                        }
                        this.pendingResponses.abandonRequests();
                    }
                    UnbindRequest req = new UnbindRequest();
                    req.setControls(controls);
                    this.operation(req);
                    this.channel.close().addListener(new LogFutureListener());
                } else {
                    LOGGER.trace("connection {} already closed", (Object)this);
                    this.notifyOperationHandlesOfClose();
                }
                LOGGER.info("Closed connection {}", (Object)this);
            }
            finally {
                this.pendingResponses.clear();
                this.connectionExecutor = null;
                this.channel = null;
                this.connectTime = null;
                if (this.shutdownOnClose) {
                    NettyUtils.shutdownGracefully(this.ioWorkerGroup);
                    LOGGER.trace("shutdown worker group {} for {}", (Object)this.ioWorkerGroup, (Object)this);
                    if (this.messageWorkerGroup != null) {
                        NettyUtils.shutdownGracefully(this.messageWorkerGroup);
                        LOGGER.trace("shutdown worker group {} for {}", (Object)this.messageWorkerGroup, (Object)this);
                    }
                }
                this.closeLock.unlock();
            }
        } else {
            LOGGER.debug("Close lock {} could not be acquired by {}", (Object)this.closeLock, (Object)Thread.currentThread());
        }
    }

    protected void notifyOperationHandlesOfClose() {
        if (this.pendingResponses.size() > 0) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("notifying operation handles {} of connection close for {}", (Object)this.pendingResponses, (Object)this);
            } else if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Notifying {} operation handles of connection close for {}", (Object)this.pendingResponses.size(), (Object)this);
            }
            LdapException ex = this.inboundException == null ? new LdapException(ResultCode.SERVER_DOWN, "Connection closed") : (this.inboundException instanceof LdapException ? (LdapException)this.inboundException : new LdapException(ResultCode.SERVER_DOWN, this.inboundException));
            if (this.messageWorkerGroup != null) {
                this.messageWorkerGroup.execute(() -> this.pendingResponses.notifyOperationHandles(ex));
            } else {
                this.pendingResponses.notifyOperationHandles(ex);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void reconnect() {
        if (this.isOpen()) {
            throw new IllegalStateException("Reconnect cannot be invoked when the connection is open");
        }
        if (this.isOpening()) {
            LOGGER.debug("Open in progress, ignoring reconnect for connection {}", (Object)this);
            this.notifyOperationHandlesOfClose();
            return;
        }
        if (this.isClosing()) {
            LOGGER.debug("Close in progress, ignoring reconnect for connection {}", (Object)this);
            this.notifyOperationHandlesOfClose();
            return;
        }
        LOGGER.trace("reconnecting connection {}", (Object)this);
        if (!this.reconnectLock.isWriteLocked()) {
            boolean gotReconnectLock;
            try {
                if (Duration.ZERO.equals(this.connectionConfig.getReconnectTimeout())) {
                    this.reconnectLock.writeLock().lock();
                    gotReconnectLock = true;
                } else {
                    gotReconnectLock = this.reconnectLock.writeLock().tryLock(this.connectionConfig.getReconnectTimeout().toMillis(), TimeUnit.MILLISECONDS);
                }
            }
            catch (InterruptedException e) {
                LOGGER.warn("Interrupted waiting on reconnect lock", e);
                gotReconnectLock = false;
            }
            if (gotReconnectLock) {
                List<DefaultOperationHandle> replayOperations = null;
                try {
                    try {
                        this.reopen(new ClosedRetryMetadata(this.lastSuccessfulOpen, this.inboundException));
                        LOGGER.info("Auto reconnect finished for connection {}", (Object)this);
                    }
                    catch (Exception e) {
                        LOGGER.debug("Auto reconnect failed for connection {}", (Object)this, (Object)e);
                    }
                    if (this.isOpen() && this.connectionConfig.getAutoReplay()) {
                        replayOperations = this.pendingResponses.handles().stream().filter(h2 -> h2.getSentTime() != null && !h2.hasConsumedMessage()).collect(Collectors.toList());
                        replayOperations.forEach(h2 -> this.pendingResponses.remove(h2.getMessageID()));
                        this.notifyOperationHandlesOfClose();
                    } else {
                        this.notifyOperationHandlesOfClose();
                    }
                }
                finally {
                    this.reconnectLock.writeLock().unlock();
                }
                if (replayOperations != null && replayOperations.size() > 0) {
                    replayOperations.forEach(this::write);
                }
                LOGGER.debug("Reconnect for connection {} finished", (Object)this);
            } else {
                LOGGER.warn("Reconnect failed, could not acquire reconnect lock");
            }
        } else {
            throw new IllegalStateException("Reconnect is already in progress");
        }
    }

    @Override
    public boolean isOpen() {
        return this.channel != null && this.channel.isOpen();
    }

    private boolean isOpening() {
        if (this.openLock.tryLock()) {
            try {
                boolean bl = false;
                return bl;
            }
            finally {
                this.openLock.unlock();
            }
        }
        return true;
    }

    private boolean isClosing() {
        if (this.closeLock.tryLock()) {
            try {
                boolean bl = false;
                return bl;
            }
            finally {
                this.closeLock.unlock();
            }
        }
        return true;
    }

    private void throwIfClosed() throws LdapException {
        if (!this.isOpen()) {
            throw new LdapException(ResultCode.SERVER_DOWN, "Connection is closed");
        }
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapUrl=" + this.ldapURL + ", " + "isOpen=" + this.isOpen() + ", " + "connectTime=" + this.connectTime + ", " + "connectionConfig=" + this.connectionConfig + ", " + "channel=" + this.channel;
    }

    private class InboundExceptionHandler
    extends ChannelInboundHandlerAdapter {
        private InboundExceptionHandler() {
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            LOGGER.warn("Inbound handler caught exception for {}", (Object)NettyConnection.this, (Object)cause);
            NettyConnection.this.inboundException = cause;
            if (NettyConnection.this.channel != null && !NettyConnection.this.isClosing()) {
                NettyConnection.this.channel.close().addListener(new LogFutureListener());
            }
        }
    }

    private class ValidatorHandler
    extends ChannelInboundHandlerAdapter {
        private final ConnectionValidator connectionValidator;
        private ScheduledFuture sf;

        ValidatorHandler(ConnectionValidator validator) {
            this.connectionValidator = validator;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            LOGGER.trace("channel active on {}", (Object)ctx);
            this.sf = ctx.executor().scheduleAtFixedRate(() -> {
                if (ctx != null && !ctx.executor().isShuttingDown()) {
                    AtomicReference result = new AtomicReference();
                    ctx.executor().submit(() -> this.connectionValidator.applyAsync(NettyConnection.this, result::set));
                    ctx.executor().schedule(() -> {
                        LOGGER.trace("connection validation returned {} for {}", result.get(), (Object)NettyConnection.this);
                        boolean success = (Boolean)result.updateAndGet(b -> b == null ? false : b);
                        if (!success) {
                            ctx.fireExceptionCaught(new LdapException(ResultCode.SERVER_DOWN, "Connection validation failed for " + NettyConnection.this));
                        }
                    }, Duration.ZERO.equals(this.connectionValidator.getValidateTimeout()) ? NettyConnection.this.connectionConfig.getResponseTimeout().toMillis() : this.connectionValidator.getValidateTimeout().toMillis(), TimeUnit.MILLISECONDS);
                }
            }, this.connectionValidator.getValidatePeriod().toMillis(), this.connectionValidator.getValidatePeriod().toMillis(), TimeUnit.MILLISECONDS);
            ctx.fireChannelActive();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            LOGGER.trace("channel inactive on {}", (Object)ctx);
            if (this.sf != null) {
                this.sf.cancel(true);
            }
            ctx.fireChannelInactive();
        }
    }

    @ChannelHandler.Sharable
    protected static class AutoReadEventHandler
    extends SimpleUserEventChannelHandler<MessageStatus> {
        protected final Logger logger = LoggerFactory.getLogger(this.getClass());

        protected AutoReadEventHandler() {
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            this.logger.trace("channel active on {}", (Object)ctx);
            ctx.channel().read();
            ctx.fireChannelActive();
        }

        @Override
        protected void eventReceived(ChannelHandlerContext ctx, MessageStatus evt) {
            this.logger.trace("received event {} on {}", (Object)evt, (Object)ctx);
            if (MessageStatus.COMPLETE == evt) {
                this.logger.trace("invoking read on {}", (Object)ctx);
                ctx.read();
            }
        }
    }

    private class InboundMessageHandler
    extends SimpleChannelInboundHandler<Message> {
        private InboundMessageHandler() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
            LOGGER.trace("channel read message {} on {}", (Object)msg, (Object)ctx);
            try {
                DefaultOperationHandle handle2 = NettyConnection.this.pendingResponses.get(msg.getMessageID());
                LOGGER.debug("Received response message {} for handle {}", (Object)msg, (Object)handle2);
                if (handle2 != null) {
                    if (msg instanceof LdapEntry) {
                        ((SearchRequest)handle2.getRequest()).configureBinaryAttributes((LdapEntry)msg);
                        ((DefaultSearchOperationHandle)handle2).entry((LdapEntry)msg);
                        return;
                    } else if (msg instanceof SearchResultReference) {
                        ((DefaultSearchOperationHandle)handle2).reference((SearchResultReference)msg);
                        return;
                    } else if (msg instanceof Result) {
                        if (NettyConnection.this.pendingResponses.remove(msg.getMessageID()) == null) {
                            LOGGER.warn("Processed message {} that no longer exists for {}", (Object)msg.getMessageID(), (Object)NettyConnection.this);
                        }
                        if (msg instanceof ExtendedResponse) {
                            ((DefaultExtendedOperationHandle)handle2).extended((ExtendedResponse)msg);
                        } else if (msg instanceof CompareResponse) {
                            ((DefaultCompareOperationHandle)handle2).compare((CompareResponse)msg);
                        }
                        if (msg.getControls() != null && msg.getControls().length > 0) {
                            Stream.of(msg.getControls()).forEach(handle2::control);
                        }
                        if (((Result)msg).getReferralURLs() != null && ((Result)msg).getReferralURLs().length > 0) {
                            handle2.referral(((Result)msg).getReferralURLs());
                        }
                        handle2.result((Result)msg);
                        return;
                    } else {
                        if (!(msg instanceof IntermediateResponse)) throw new IllegalStateException("Unknown message type: " + msg);
                        handle2.intermediate((IntermediateResponse)msg);
                    }
                    return;
                } else if (msg instanceof UnsolicitedNotification) {
                    LOGGER.info("Received UnsolicitedNotification {} for {}", (Object)msg, (Object)NettyConnection.this);
                    NettyConnection.this.pendingResponses.notifyOperationHandles((UnsolicitedNotification)msg);
                    return;
                } else {
                    LOGGER.warn("Received response message {} without matching request in {} for {}", msg, NettyConnection.this.pendingResponses, this);
                }
                return;
            }
            finally {
                if (ctx != null) {
                    ctx.fireUserEventTriggered((Object)MessageStatus.COMPLETE);
                }
            }
        }
    }

    protected static class MessageDecoder
    extends ByteToMessageDecoder {
        protected MessageDecoder() {
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws LdapException {
            LOGGER.trace("received {} bytes on {}", (Object)in.readableBytes(), (Object)ctx);
            ResponseParser parser = new ResponseParser();
            Message message = parser.parse(new NettyDERBuffer(in)).orElseThrow(() -> new LdapException(ResultCode.DECODING_ERROR, "No response found"));
            out.add(message);
            LOGGER.trace("decoded response message {} on {}", (Object)message, (Object)ctx);
            if (ctx != null) {
                ctx.fireUserEventTriggered((Object)MessageStatus.DECODED);
            }
        }
    }

    @ChannelHandler.Sharable
    protected static class RequestEncoder
    extends MessageToByteEncoder<EncodedRequest> {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        protected RequestEncoder() {
        }

        @Override
        protected void encode(ChannelHandlerContext ctx, EncodedRequest msg, ByteBuf out) {
            this.logger.trace("encoding message {} on {}", (Object)msg, (Object)ctx);
            out.writeBytes(msg.getEncoded());
        }

        @Override
        protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, EncodedRequest msg, boolean preferDirect) {
            int msgSize = msg.getEncoded().length;
            if (preferDirect) {
                return ctx.alloc().ioBuffer(msgSize);
            }
            return ctx.alloc().heapBuffer(msgSize);
        }
    }

    protected static enum MessageStatus {
        READ,
        DECODED,
        COMPLETE;

    }

    private class ClientInitializer
    extends ChannelInitializer<SocketChannel> {
        private final SslHandler sslHandler;

        ClientInitializer(SslHandler handler) {
            this.sslHandler = handler;
        }

        @Override
        public void initChannel(SocketChannel ch) {
            if (this.sslHandler != null) {
                ch.pipeline().addFirst("ssl", (ChannelHandler)this.sslHandler);
            }
            if (LOGGER.isDebugEnabled()) {
                ch.pipeline().addLast("logger", (ChannelHandler)new LoggingHandler(LogLevel.DEBUG));
            } else if (LOGGER.isTraceEnabled()) {
                ch.pipeline().addLast("logger", (ChannelHandler)new LoggingHandler(LogLevel.TRACE));
            }
            ch.pipeline().addLast("frame_decoder", (ChannelHandler)new MessageFrameDecoder());
            ch.pipeline().addLast("response_decoder", (ChannelHandler)new MessageDecoder());
            if (!ch.config().isAutoRead()) {
                ch.pipeline().addLast("flow_control_handler", (ChannelHandler)new AutoReadFlowControlHandler());
            }
            if (NettyConnection.this.messageWorkerGroup != null) {
                ch.pipeline().addLast((EventExecutorGroup)NettyConnection.this.messageWorkerGroup, "message_handler", (ChannelHandler)new InboundMessageHandler());
            } else {
                ch.pipeline().addLast("message_handler", (ChannelHandler)new InboundMessageHandler());
            }
            if (!ch.config().isAutoRead()) {
                ch.pipeline().addLast("next_message_handler", (ChannelHandler)READ_NEXT_MESSAGE);
            }
            ch.pipeline().addLast("request_encoder", (ChannelHandler)REQUEST_ENCODER);
            if (NettyConnection.this.connectionConfig.getConnectionValidator() != null) {
                ch.pipeline().addLast("validate_conn", (ChannelHandler)new ValidatorHandler(NettyConnection.this.connectionConfig.getConnectionValidator()));
            }
            ch.pipeline().addLast("inbound_exception_handler", (ChannelHandler)new InboundExceptionHandler());
        }

        public boolean isSsl() {
            return this.sslHandler != null;
        }
    }

    private class CloseFutureListener
    implements ChannelFutureListener {
        private final AtomicBoolean reconnecting = new AtomicBoolean();

        private CloseFutureListener() {
        }

        @Override
        public void operationComplete(ChannelFuture future) {
            NettyConnection.this.inboundException = future.cause();
            LOGGER.debug("Close listener invoked for {} with future {} and cause {}", NettyConnection.this, future, NettyConnection.this.inboundException != null ? NettyConnection.this.inboundException.getClass() : null, NettyConnection.this.inboundException);
            if (NettyConnection.this.connectionConfig.getAutoReconnect() && !NettyConnection.this.isOpening() && !NettyConnection.this.isClosing()) {
                LOGGER.trace("scheduling reconnect thread for {}", (Object)NettyConnection.this);
                if (NettyConnection.this.connectionExecutor != null && !NettyConnection.this.connectionExecutor.isShutdown()) {
                    NettyConnection.this.connectionExecutor.execute(() -> {
                        if (this.reconnecting.compareAndSet(false, true)) {
                            try {
                                NettyConnection.this.reconnect();
                            }
                            catch (Exception e) {
                                LOGGER.warn("Reconnect attempt failed for {}", (Object)NettyConnection.this, (Object)e);
                            }
                            finally {
                                this.reconnecting.set(false);
                            }
                        } else {
                            LOGGER.debug("Ignoring reconnect attempt, reconnect already in progress for {}", (Object)NettyConnection.this);
                        }
                    });
                } else {
                    LOGGER.warn("Reconnect could not be scheduled on executor {} for {}", (Object)NettyConnection.this.connectionExecutor, (Object)NettyConnection.this);
                }
            } else {
                NettyConnection.this.notifyOperationHandlesOfClose();
            }
        }
    }

    private class LogFutureListener
    implements ChannelFutureListener {
        private LogFutureListener() {
        }

        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                LOGGER.trace("operation channel success for {}", (Object)NettyConnection.this);
            } else {
                LOGGER.warn("operation channel error for {}", (Object)NettyConnection.this, (Object)future.cause());
            }
        }
    }

    public class BindOperationHandle
    extends DefaultOperationHandle<BindRequest, BindResponse> {
        BindOperationHandle(BindRequest req, TransportConnection conn, Duration timeout2) {
            super(req, conn, timeout2);
        }

        @Override
        public BindOperationHandle send() {
            throw new UnsupportedOperationException("Bind requests are synchronous, invoke execute");
        }

        @Override
        public BindResponse await() {
            throw new UnsupportedOperationException("Bind requests are synchronous, invoke execute");
        }

        @Override
        public BindResponse execute() throws LdapException {
            if (NettyConnection.this.bindLock.writeLock().tryLock()) {
                try {
                    super.send();
                    BindResponse bindResponse = (BindResponse)super.await();
                    return bindResponse;
                }
                finally {
                    NettyConnection.this.bindLock.writeLock().unlock();
                }
            }
            throw new IllegalStateException("Operation in progress, cannot send bind request");
        }
    }
}

