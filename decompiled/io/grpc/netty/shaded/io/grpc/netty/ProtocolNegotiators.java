/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ChoiceChannelCredentials;
import io.grpc.ChoiceServerCredentials;
import io.grpc.CompositeCallCredentials;
import io.grpc.CompositeChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.InsecureServerCredentials;
import io.grpc.InternalChannelz;
import io.grpc.SecurityLevel;
import io.grpc.ServerCredentials;
import io.grpc.Status;
import io.grpc.TlsChannelCredentials;
import io.grpc.TlsServerCredentials;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ObjectPool;
import io.grpc.netty.shaded.io.grpc.netty.FixedKeyManagerFactory;
import io.grpc.netty.shaded.io.grpc.netty.FixedTrustManagerFactory;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2ConnectionHandler;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.JettyTlsUtil;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiationEvent;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.ChannelDuplexHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelInboundHandlerAdapter;
import io.grpc.netty.shaded.io.netty.handler.codec.http.DefaultHttpRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpClientCodec;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderNames;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMethod;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpVersion;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ClientUpgradeCodec;
import io.grpc.netty.shaded.io.netty.handler.proxy.HttpProxyHandler;
import io.grpc.netty.shaded.io.netty.handler.proxy.ProxyConnectionEvent;
import io.grpc.netty.shaded.io.netty.handler.ssl.ClientAuth;
import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSsl;
import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSslEngine;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslHandler;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslProvider;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import java.io.ByteArrayInputStream;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

final class ProtocolNegotiators {
    private static final Logger log = Logger.getLogger(ProtocolNegotiators.class.getName());
    private static final EnumSet<TlsChannelCredentials.Feature> understoodTlsFeatures = EnumSet.of(TlsChannelCredentials.Feature.MTLS, TlsChannelCredentials.Feature.CUSTOM_MANAGERS);
    private static final EnumSet<TlsServerCredentials.Feature> understoodServerTlsFeatures = EnumSet.of(TlsServerCredentials.Feature.MTLS, TlsServerCredentials.Feature.CUSTOM_MANAGERS);

    private ProtocolNegotiators() {
    }

    public static FromChannelCredentialsResult from(ChannelCredentials creds) {
        if (creds instanceof TlsChannelCredentials) {
            TlsChannelCredentials tlsCreds = (TlsChannelCredentials)creds;
            Set<TlsChannelCredentials.Feature> incomprehensible = tlsCreds.incomprehensible(understoodTlsFeatures);
            if (!incomprehensible.isEmpty()) {
                return FromChannelCredentialsResult.error("TLS features not understood: " + incomprehensible);
            }
            SslContextBuilder builder = GrpcSslContexts.forClient();
            if (tlsCreds.getKeyManagers() != null) {
                builder.keyManager(new FixedKeyManagerFactory(tlsCreds.getKeyManagers()));
            } else if (tlsCreds.getPrivateKey() != null) {
                builder.keyManager(new ByteArrayInputStream(tlsCreds.getCertificateChain()), new ByteArrayInputStream(tlsCreds.getPrivateKey()), tlsCreds.getPrivateKeyPassword());
            }
            if (tlsCreds.getTrustManagers() != null) {
                builder.trustManager(new FixedTrustManagerFactory(tlsCreds.getTrustManagers()));
            } else if (tlsCreds.getRootCertificates() != null) {
                builder.trustManager(new ByteArrayInputStream(tlsCreds.getRootCertificates()));
            }
            try {
                return FromChannelCredentialsResult.negotiator(ProtocolNegotiators.tlsClientFactory(builder.build()));
            }
            catch (SSLException ex) {
                log.log(Level.FINE, "Exception building SslContext", ex);
                return FromChannelCredentialsResult.error("Unable to create SslContext: " + ex.getMessage());
            }
        }
        if (creds instanceof InsecureChannelCredentials) {
            return FromChannelCredentialsResult.negotiator(ProtocolNegotiators.plaintextClientFactory());
        }
        if (creds instanceof CompositeChannelCredentials) {
            CompositeChannelCredentials compCreds = (CompositeChannelCredentials)creds;
            return ProtocolNegotiators.from(compCreds.getChannelCredentials()).withCallCredentials(compCreds.getCallCredentials());
        }
        if (creds instanceof NettyChannelCredentials) {
            NettyChannelCredentials nettyCreds = (NettyChannelCredentials)creds;
            return FromChannelCredentialsResult.negotiator(nettyCreds.getNegotiator());
        }
        if (creds instanceof ChoiceChannelCredentials) {
            ChoiceChannelCredentials choiceCreds = (ChoiceChannelCredentials)creds;
            StringBuilder error = new StringBuilder();
            for (ChannelCredentials innerCreds : choiceCreds.getCredentialsList()) {
                FromChannelCredentialsResult result = ProtocolNegotiators.from(innerCreds);
                if (result.error == null) {
                    return result;
                }
                error.append(", ");
                error.append(result.error);
            }
            return FromChannelCredentialsResult.error(error.substring(2));
        }
        return FromChannelCredentialsResult.error("Unsupported credential type: " + creds.getClass().getName());
    }

    public static FromServerCredentialsResult from(ServerCredentials creds) {
        if (creds instanceof TlsServerCredentials) {
            SslContext sslContext;
            SslContextBuilder builder;
            TlsServerCredentials tlsCreds = (TlsServerCredentials)creds;
            Set<TlsServerCredentials.Feature> incomprehensible = tlsCreds.incomprehensible(understoodServerTlsFeatures);
            if (!incomprehensible.isEmpty()) {
                return FromServerCredentialsResult.error("TLS features not understood: " + incomprehensible);
            }
            if (tlsCreds.getKeyManagers() != null) {
                builder = GrpcSslContexts.configure(SslContextBuilder.forServer(new FixedKeyManagerFactory(tlsCreds.getKeyManagers())));
            } else if (tlsCreds.getPrivateKey() != null) {
                builder = GrpcSslContexts.forServer(new ByteArrayInputStream(tlsCreds.getCertificateChain()), new ByteArrayInputStream(tlsCreds.getPrivateKey()), tlsCreds.getPrivateKeyPassword());
            } else {
                throw new AssertionError((Object)"BUG! No key");
            }
            if (tlsCreds.getTrustManagers() != null) {
                builder.trustManager(new FixedTrustManagerFactory(tlsCreds.getTrustManagers()));
            } else if (tlsCreds.getRootCertificates() != null) {
                builder.trustManager(new ByteArrayInputStream(tlsCreds.getRootCertificates()));
            }
            switch (tlsCreds.getClientAuth()) {
                case OPTIONAL: {
                    builder.clientAuth(ClientAuth.OPTIONAL);
                    break;
                }
                case REQUIRE: {
                    builder.clientAuth(ClientAuth.REQUIRE);
                    break;
                }
                case NONE: {
                    builder.clientAuth(ClientAuth.NONE);
                    break;
                }
                default: {
                    return FromServerCredentialsResult.error("Unknown TlsServerCredentials.ClientAuth value: " + (Object)((Object)tlsCreds.getClientAuth()));
                }
            }
            try {
                sslContext = builder.build();
            }
            catch (SSLException ex) {
                throw new IllegalArgumentException("Unexpected error converting ServerCredentials to Netty SslContext", ex);
            }
            return FromServerCredentialsResult.negotiator(ProtocolNegotiators.serverTlsFactory(sslContext));
        }
        if (creds instanceof InsecureServerCredentials) {
            return FromServerCredentialsResult.negotiator(ProtocolNegotiators.serverPlaintextFactory());
        }
        if (creds instanceof NettyServerCredentials) {
            NettyServerCredentials nettyCreds = (NettyServerCredentials)creds;
            return FromServerCredentialsResult.negotiator(nettyCreds.getNegotiator());
        }
        if (creds instanceof ChoiceServerCredentials) {
            ChoiceServerCredentials choiceCreds = (ChoiceServerCredentials)creds;
            StringBuilder error = new StringBuilder();
            for (ServerCredentials innerCreds : choiceCreds.getCredentialsList()) {
                FromServerCredentialsResult result = ProtocolNegotiators.from(innerCreds);
                if (result.error == null) {
                    return result;
                }
                error.append(", ");
                error.append(result.error);
            }
            return FromServerCredentialsResult.error(error.substring(2));
        }
        return FromServerCredentialsResult.error("Unsupported credential type: " + creds.getClass().getName());
    }

    public static ProtocolNegotiator.ServerFactory fixedServerFactory(ProtocolNegotiator negotiator) {
        return new FixedProtocolNegotiatorServerFactory(negotiator);
    }

    public static ProtocolNegotiator serverPlaintext() {
        return new PlaintextProtocolNegotiator();
    }

    public static ProtocolNegotiator.ServerFactory serverPlaintextFactory() {
        return new PlaintextProtocolNegotiatorServerFactory();
    }

    public static ProtocolNegotiator.ServerFactory serverTlsFactory(SslContext sslContext) {
        return new TlsProtocolNegotiatorServerFactory(sslContext);
    }

    public static ProtocolNegotiator serverTls(final SslContext sslContext, final ObjectPool<? extends Executor> executorPool) {
        Preconditions.checkNotNull(sslContext, "sslContext");
        final Executor executor2 = executorPool != null ? executorPool.getObject() : null;
        return new ProtocolNegotiator(){

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler handler) {
                GrpcNegotiationHandler gnh = new GrpcNegotiationHandler(handler);
                ServerTlsHandler sth = new ServerTlsHandler(gnh, sslContext, executorPool);
                return new WaitUntilActiveHandler(sth, handler.getNegotiationLogger());
            }

            @Override
            public void close() {
                if (executorPool != null && executor2 != null) {
                    executorPool.returnObject(executor2);
                }
            }

            @Override
            public AsciiString scheme() {
                return Utils.HTTPS;
            }
        };
    }

    public static ProtocolNegotiator serverTls(SslContext sslContext) {
        return ProtocolNegotiators.serverTls(sslContext, null);
    }

    public static ProtocolNegotiator httpProxy(final SocketAddress proxyAddress, final @Nullable String proxyUsername, final @Nullable String proxyPassword, final ProtocolNegotiator negotiator) {
        Preconditions.checkNotNull(negotiator, "negotiator");
        Preconditions.checkNotNull(proxyAddress, "proxyAddress");
        final AsciiString scheme2 = negotiator.scheme();
        class ProxyNegotiator
        implements ProtocolNegotiator {
            ProxyNegotiator() {
            }

            @Override
            public ChannelHandler newHandler(GrpcHttp2ConnectionHandler http2Handler) {
                ChannelHandler protocolNegotiationHandler = negotiator.newHandler(http2Handler);
                ChannelLogger negotiationLogger = http2Handler.getNegotiationLogger();
                return new ProxyProtocolNegotiationHandler(proxyAddress, proxyUsername, proxyPassword, protocolNegotiationHandler, negotiationLogger);
            }

            @Override
            public AsciiString scheme() {
                return scheme2;
            }

            @Override
            public void close() {
                negotiator.close();
            }
        }
        return new ProxyNegotiator();
    }

    @VisibleForTesting
    static HostPort parseAuthority(String authority) {
        int port;
        String host2;
        URI uri = GrpcUtil.authorityToUri(Preconditions.checkNotNull(authority, "authority"));
        if (uri.getHost() != null) {
            host2 = uri.getHost();
            port = uri.getPort();
        } else {
            host2 = authority;
            port = -1;
        }
        return new HostPort(host2, port);
    }

    public static ProtocolNegotiator tls(SslContext sslContext, ObjectPool<? extends Executor> executorPool) {
        return new ClientTlsProtocolNegotiator(sslContext, executorPool);
    }

    public static ProtocolNegotiator tls(SslContext sslContext) {
        return ProtocolNegotiators.tls(sslContext, null);
    }

    public static ProtocolNegotiator.ClientFactory tlsClientFactory(SslContext sslContext) {
        return new TlsProtocolNegotiatorClientFactory(sslContext);
    }

    public static ProtocolNegotiator plaintextUpgrade() {
        return new PlaintextUpgradeProtocolNegotiator();
    }

    public static ProtocolNegotiator.ClientFactory plaintextUpgradeClientFactory() {
        return new PlaintextUpgradeProtocolNegotiatorClientFactory();
    }

    public static ProtocolNegotiator plaintext() {
        return new PlaintextProtocolNegotiator();
    }

    public static ProtocolNegotiator.ClientFactory plaintextClientFactory() {
        return new PlaintextProtocolNegotiatorClientFactory();
    }

    private static RuntimeException unavailableException(String msg) {
        return Status.UNAVAILABLE.withDescription(msg).asRuntimeException();
    }

    @VisibleForTesting
    static void logSslEngineDetails(Level level, ChannelHandlerContext ctx, String msg, @Nullable Throwable t2) {
        if (!log.isLoggable(level)) {
            return;
        }
        SslHandler sslHandler = ctx.pipeline().get(SslHandler.class);
        SSLEngine engine = sslHandler.engine();
        StringBuilder builder = new StringBuilder(msg);
        builder.append("\nSSLEngine Details: [\n");
        if (engine instanceof OpenSslEngine) {
            builder.append("    OpenSSL, ");
            builder.append("Version: 0x").append(Integer.toHexString(OpenSsl.version()));
            builder.append(" (").append(OpenSsl.versionString()).append("), ");
            builder.append("ALPN supported: ").append(SslProvider.isAlpnSupported(SslProvider.OPENSSL));
        } else if (JettyTlsUtil.isJettyAlpnConfigured()) {
            builder.append("    Jetty ALPN");
        } else if (JettyTlsUtil.isJettyNpnConfigured()) {
            builder.append("    Jetty NPN");
        } else if (JettyTlsUtil.isJava9AlpnAvailable()) {
            builder.append("    JDK9 ALPN");
        }
        builder.append("\n    TLS Protocol: ");
        builder.append(engine.getSession().getProtocol());
        builder.append("\n    Application Protocol: ");
        builder.append(sslHandler.applicationProtocol());
        builder.append("\n    Need Client Auth: ");
        builder.append(engine.getNeedClientAuth());
        builder.append("\n    Want Client Auth: ");
        builder.append(engine.getWantClientAuth());
        builder.append("\n    Supported protocols=");
        builder.append(Arrays.toString(engine.getSupportedProtocols()));
        builder.append("\n    Enabled protocols=");
        builder.append(Arrays.toString(engine.getEnabledProtocols()));
        builder.append("\n    Supported ciphers=");
        builder.append(Arrays.toString(engine.getSupportedCipherSuites()));
        builder.append("\n    Enabled ciphers=");
        builder.append(Arrays.toString(engine.getEnabledCipherSuites()));
        builder.append("\n]");
        log.log(level, builder.toString(), t2);
    }

    static class ProtocolNegotiationHandler
    extends ChannelDuplexHandler {
        private final ChannelHandler next;
        private final String negotiatorName;
        private ProtocolNegotiationEvent pne;
        private final ChannelLogger negotiationLogger;

        protected ProtocolNegotiationHandler(ChannelHandler next, String negotiatorName, ChannelLogger negotiationLogger) {
            this.next = Preconditions.checkNotNull(next, "next");
            this.negotiatorName = negotiatorName;
            this.negotiationLogger = Preconditions.checkNotNull(negotiationLogger, "negotiationLogger");
        }

        protected ProtocolNegotiationHandler(ChannelHandler next, ChannelLogger negotiationLogger) {
            this.next = Preconditions.checkNotNull(next, "next");
            this.negotiatorName = this.getClass().getSimpleName().replace("Handler", "");
            this.negotiationLogger = Preconditions.checkNotNull(negotiationLogger, "negotiationLogger");
        }

        @Override
        public final void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            this.negotiationLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "{0} started", this.negotiatorName);
            this.handlerAdded0(ctx);
        }

        @ForOverride
        protected void handlerAdded0(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
        }

        @Override
        public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof ProtocolNegotiationEvent) {
                Preconditions.checkState(this.pne == null, "pre-existing negotiation: %s < %s", (Object)this.pne, evt);
                this.pne = (ProtocolNegotiationEvent)evt;
                this.protocolNegotiationEventTriggered(ctx);
            } else {
                this.userEventTriggered0(ctx, evt);
            }
        }

        protected void userEventTriggered0(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }

        @ForOverride
        protected void protocolNegotiationEventTriggered(ChannelHandlerContext ctx) {
        }

        protected final ProtocolNegotiationEvent getProtocolNegotiationEvent() {
            Preconditions.checkState(this.pne != null, "previous protocol negotiation event hasn't triggered");
            return this.pne;
        }

        protected final void replaceProtocolNegotiationEvent(ProtocolNegotiationEvent pne) {
            Preconditions.checkState(this.pne != null, "previous protocol negotiation event hasn't triggered");
            this.pne = Preconditions.checkNotNull(pne);
        }

        protected final void fireProtocolNegotiationEvent(ChannelHandlerContext ctx) {
            Preconditions.checkState(this.pne != null, "previous protocol negotiation event hasn't triggered");
            this.negotiationLogger.log(ChannelLogger.ChannelLogLevel.INFO, "{0} completed", this.negotiatorName);
            ctx.pipeline().replace(ctx.name(), null, this.next);
            ctx.fireUserEventTriggered(this.pne);
        }
    }

    static final class WaitUntilActiveHandler
    extends ProtocolNegotiationHandler {
        boolean protocolNegotiationEventReceived;

        WaitUntilActiveHandler(ChannelHandler next, ChannelLogger negotiationLogger) {
            super(next, negotiationLogger);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            if (this.protocolNegotiationEventReceived) {
                this.replaceOnActive(ctx);
                this.fireProtocolNegotiationEvent(ctx);
            }
            super.channelActive(ctx);
        }

        @Override
        protected void protocolNegotiationEventTriggered(ChannelHandlerContext ctx) {
            this.protocolNegotiationEventReceived = true;
            if (ctx.channel().isActive()) {
                this.replaceOnActive(ctx);
                this.fireProtocolNegotiationEvent(ctx);
            }
        }

        private void replaceOnActive(ChannelHandlerContext ctx) {
            ProtocolNegotiationEvent existingPne = this.getProtocolNegotiationEvent();
            Attributes attrs = existingPne.getAttributes().toBuilder().set(Grpc.TRANSPORT_ATTR_LOCAL_ADDR, ctx.channel().localAddress()).set(Grpc.TRANSPORT_ATTR_REMOTE_ADDR, ctx.channel().remoteAddress()).set(GrpcAttributes.ATTR_SECURITY_LEVEL, SecurityLevel.NONE).build();
            this.replaceProtocolNegotiationEvent(existingPne.withAttributes(attrs));
        }
    }

    static final class PlaintextProtocolNegotiator
    implements ProtocolNegotiator {
        PlaintextProtocolNegotiator() {
        }

        @Override
        public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
            GrpcNegotiationHandler grpcNegotiationHandler = new GrpcNegotiationHandler(grpcHandler);
            WaitUntilActiveHandler activeHandler = new WaitUntilActiveHandler(grpcNegotiationHandler, grpcHandler.getNegotiationLogger());
            return activeHandler;
        }

        @Override
        public void close() {
        }

        @Override
        public AsciiString scheme() {
            return Utils.HTTP;
        }
    }

    static final class GrpcNegotiationHandler
    extends ChannelInboundHandlerAdapter {
        private final GrpcHttp2ConnectionHandler next;

        public GrpcNegotiationHandler(GrpcHttp2ConnectionHandler next) {
            this.next = Preconditions.checkNotNull(next, "next");
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof ProtocolNegotiationEvent) {
                ProtocolNegotiationEvent protocolNegotiationEvent = (ProtocolNegotiationEvent)evt;
                ctx.pipeline().replace(ctx.name(), null, (ChannelHandler)this.next);
                this.next.handleProtocolNegotiationCompleted(protocolNegotiationEvent.getAttributes(), protocolNegotiationEvent.getSecurity());
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    @VisibleForTesting
    static final class PlaintextProtocolNegotiatorClientFactory
    implements ProtocolNegotiator.ClientFactory {
        PlaintextProtocolNegotiatorClientFactory() {
        }

        @Override
        public ProtocolNegotiator newNegotiator() {
            return ProtocolNegotiators.plaintext();
        }

        @Override
        public int getDefaultPort() {
            return 80;
        }
    }

    static final class Http2UpgradeAndGrpcHandler
    extends ChannelInboundHandlerAdapter {
        private final String authority;
        private final GrpcHttp2ConnectionHandler next;
        private final ChannelLogger negotiationLogger;
        private ProtocolNegotiationEvent pne;

        Http2UpgradeAndGrpcHandler(String authority, GrpcHttp2ConnectionHandler next) {
            this.authority = Preconditions.checkNotNull(authority, "authority");
            this.next = Preconditions.checkNotNull(next, "next");
            this.negotiationLogger = next.getNegotiationLogger();
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            this.negotiationLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Http2Upgrade started");
            HttpClientCodec httpClientCodec = new HttpClientCodec();
            ctx.pipeline().addBefore(ctx.name(), null, httpClientCodec);
            Http2ClientUpgradeCodec upgradeCodec = new Http2ClientUpgradeCodec(this.next);
            HttpClientUpgradeHandler upgrader = new HttpClientUpgradeHandler(httpClientCodec, upgradeCodec, 1000);
            ctx.pipeline().addBefore(ctx.name(), null, upgrader);
            DefaultHttpRequest upgradeTrigger = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
            upgradeTrigger.headers().add((CharSequence)HttpHeaderNames.HOST, (Object)this.authority);
            ctx.writeAndFlush(upgradeTrigger).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            super.handlerAdded(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof ProtocolNegotiationEvent) {
                Preconditions.checkState(this.pne == null, "negotiation already started");
                this.pne = (ProtocolNegotiationEvent)evt;
            } else if (evt == HttpClientUpgradeHandler.UpgradeEvent.UPGRADE_SUCCESSFUL) {
                Preconditions.checkState(this.pne != null, "negotiation not yet complete");
                this.negotiationLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Http2Upgrade finished");
                ctx.pipeline().remove(ctx.name());
                this.next.handleProtocolNegotiationCompleted(this.pne.getAttributes(), this.pne.getSecurity());
            } else if (evt == HttpClientUpgradeHandler.UpgradeEvent.UPGRADE_REJECTED) {
                ctx.fireExceptionCaught(ProtocolNegotiators.unavailableException("HTTP/2 upgrade rejected"));
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    static final class PlaintextUpgradeProtocolNegotiator
    implements ProtocolNegotiator {
        PlaintextUpgradeProtocolNegotiator() {
        }

        @Override
        public AsciiString scheme() {
            return Utils.HTTP;
        }

        @Override
        public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
            Http2UpgradeAndGrpcHandler upgradeHandler = new Http2UpgradeAndGrpcHandler(grpcHandler.getAuthority(), grpcHandler);
            return new WaitUntilActiveHandler(upgradeHandler, grpcHandler.getNegotiationLogger());
        }

        @Override
        public void close() {
        }
    }

    private static final class PlaintextUpgradeProtocolNegotiatorClientFactory
    implements ProtocolNegotiator.ClientFactory {
        private PlaintextUpgradeProtocolNegotiatorClientFactory() {
        }

        @Override
        public ProtocolNegotiator newNegotiator() {
            return ProtocolNegotiators.plaintextUpgrade();
        }

        @Override
        public int getDefaultPort() {
            return 80;
        }
    }

    @VisibleForTesting
    static final class HostPort {
        final String host;
        final int port;

        public HostPort(String host2, int port) {
            this.host = host2;
            this.port = port;
        }
    }

    @VisibleForTesting
    static final class TlsProtocolNegotiatorClientFactory
    implements ProtocolNegotiator.ClientFactory {
        private final SslContext sslContext;

        public TlsProtocolNegotiatorClientFactory(SslContext sslContext) {
            this.sslContext = Preconditions.checkNotNull(sslContext, "sslContext");
        }

        @Override
        public ProtocolNegotiator newNegotiator() {
            return ProtocolNegotiators.tls(this.sslContext);
        }

        @Override
        public int getDefaultPort() {
            return 443;
        }
    }

    static final class ClientTlsHandler
    extends ProtocolNegotiationHandler {
        private final SslContext sslContext;
        private final String host;
        private final int port;
        private Executor executor;

        ClientTlsHandler(ChannelHandler next, SslContext sslContext, String authority, Executor executor2, ChannelLogger negotiationLogger) {
            super(next, negotiationLogger);
            this.sslContext = Preconditions.checkNotNull(sslContext, "sslContext");
            HostPort hostPort = ProtocolNegotiators.parseAuthority(authority);
            this.host = hostPort.host;
            this.port = hostPort.port;
            this.executor = executor2;
        }

        @Override
        protected void handlerAdded0(ChannelHandlerContext ctx) {
            SSLEngine sslEngine = this.sslContext.newEngine(ctx.alloc(), this.host, this.port);
            SSLParameters sslParams = sslEngine.getSSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("HTTPS");
            sslEngine.setSSLParameters(sslParams);
            ctx.pipeline().addBefore(ctx.name(), null, this.executor != null ? new SslHandler(sslEngine, false, this.executor) : new SslHandler(sslEngine, false));
        }

        @Override
        protected void userEventTriggered0(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof SslHandshakeCompletionEvent) {
                SslHandshakeCompletionEvent handshakeEvent = (SslHandshakeCompletionEvent)evt;
                if (handshakeEvent.isSuccess()) {
                    SslHandler handler = ctx.pipeline().get(SslHandler.class);
                    if (this.sslContext.applicationProtocolNegotiator().protocols().contains(handler.applicationProtocol())) {
                        ProtocolNegotiators.logSslEngineDetails(Level.FINER, ctx, "TLS negotiation succeeded.", null);
                        this.propagateTlsComplete(ctx, handler.engine().getSession());
                    } else {
                        RuntimeException ex = ProtocolNegotiators.unavailableException("Failed ALPN negotiation: Unable to find compatible protocol");
                        ProtocolNegotiators.logSslEngineDetails(Level.FINE, ctx, "TLS negotiation failed.", ex);
                        ctx.fireExceptionCaught(ex);
                    }
                } else {
                    Throwable t2 = handshakeEvent.cause();
                    if (t2 instanceof ClosedChannelException) {
                        t2 = Status.UNAVAILABLE.withDescription("Connection closed while performing TLS negotiation").withCause(t2).asRuntimeException();
                    }
                    ctx.fireExceptionCaught(t2);
                }
            } else {
                super.userEventTriggered0(ctx, evt);
            }
        }

        private void propagateTlsComplete(ChannelHandlerContext ctx, SSLSession session) {
            InternalChannelz.Security security = new InternalChannelz.Security(new InternalChannelz.Tls(session));
            ProtocolNegotiationEvent existingPne = this.getProtocolNegotiationEvent();
            Attributes attrs = existingPne.getAttributes().toBuilder().set(GrpcAttributes.ATTR_SECURITY_LEVEL, SecurityLevel.PRIVACY_AND_INTEGRITY).set(Grpc.TRANSPORT_ATTR_SSL_SESSION, session).build();
            this.replaceProtocolNegotiationEvent(existingPne.withAttributes(attrs).withSecurity(security));
            this.fireProtocolNegotiationEvent(ctx);
        }
    }

    static final class ClientTlsProtocolNegotiator
    implements ProtocolNegotiator {
        private final SslContext sslContext;
        private final ObjectPool<? extends Executor> executorPool;
        private Executor executor;

        public ClientTlsProtocolNegotiator(SslContext sslContext, ObjectPool<? extends Executor> executorPool) {
            this.sslContext = Preconditions.checkNotNull(sslContext, "sslContext");
            this.executorPool = executorPool;
            if (this.executorPool != null) {
                this.executor = this.executorPool.getObject();
            }
        }

        @Override
        public AsciiString scheme() {
            return Utils.HTTPS;
        }

        @Override
        public ChannelHandler newHandler(GrpcHttp2ConnectionHandler grpcHandler) {
            GrpcNegotiationHandler gnh = new GrpcNegotiationHandler(grpcHandler);
            ChannelLogger negotiationLogger = grpcHandler.getNegotiationLogger();
            ClientTlsHandler cth = new ClientTlsHandler(gnh, this.sslContext, grpcHandler.getAuthority(), this.executor, negotiationLogger);
            return new WaitUntilActiveHandler(cth, negotiationLogger);
        }

        @Override
        public void close() {
            if (this.executorPool != null && this.executor != null) {
                this.executorPool.returnObject(this.executor);
            }
        }
    }

    static final class ProxyProtocolNegotiationHandler
    extends ProtocolNegotiationHandler {
        private final SocketAddress address;
        @Nullable
        private final String userName;
        @Nullable
        private final String password;

        public ProxyProtocolNegotiationHandler(SocketAddress address, @Nullable String userName, @Nullable String password, ChannelHandler next, ChannelLogger negotiationLogger) {
            super(next, negotiationLogger);
            this.address = Preconditions.checkNotNull(address, "address");
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected void protocolNegotiationEventTriggered(ChannelHandlerContext ctx) {
            HttpProxyHandler nettyProxyHandler = this.userName == null || this.password == null ? new HttpProxyHandler(this.address) : new HttpProxyHandler(this.address, this.userName, this.password);
            ctx.pipeline().addBefore(ctx.name(), null, nettyProxyHandler);
        }

        @Override
        protected void userEventTriggered0(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof ProxyConnectionEvent) {
                this.fireProtocolNegotiationEvent(ctx);
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    static final class ServerTlsHandler
    extends ChannelInboundHandlerAdapter {
        private Executor executor;
        private final ChannelHandler next;
        private final SslContext sslContext;
        private ProtocolNegotiationEvent pne = ProtocolNegotiationEvent.DEFAULT;

        ServerTlsHandler(ChannelHandler next, SslContext sslContext, ObjectPool<? extends Executor> executorPool) {
            this.sslContext = Preconditions.checkNotNull(sslContext, "sslContext");
            this.next = Preconditions.checkNotNull(next, "next");
            if (executorPool != null) {
                this.executor = executorPool.getObject();
            }
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
            SSLEngine sslEngine = this.sslContext.newEngine(ctx.alloc());
            ctx.pipeline().addBefore(ctx.name(), null, this.executor != null ? new SslHandler(sslEngine, false, this.executor) : new SslHandler(sslEngine, false));
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof ProtocolNegotiationEvent) {
                this.pne = (ProtocolNegotiationEvent)evt;
            } else if (evt instanceof SslHandshakeCompletionEvent) {
                SslHandshakeCompletionEvent handshakeEvent = (SslHandshakeCompletionEvent)evt;
                if (!handshakeEvent.isSuccess()) {
                    ProtocolNegotiators.logSslEngineDetails(Level.FINE, ctx, "TLS negotiation failed for new client.", null);
                    ctx.fireExceptionCaught(handshakeEvent.cause());
                    return;
                }
                SslHandler sslHandler = ctx.pipeline().get(SslHandler.class);
                if (!this.sslContext.applicationProtocolNegotiator().protocols().contains(sslHandler.applicationProtocol())) {
                    ProtocolNegotiators.logSslEngineDetails(Level.FINE, ctx, "TLS negotiation failed for new client.", null);
                    ctx.fireExceptionCaught(ProtocolNegotiators.unavailableException("Failed protocol negotiation: Unable to find compatible protocol"));
                    return;
                }
                ctx.pipeline().replace(ctx.name(), null, this.next);
                this.fireProtocolNegotiationEvent(ctx, sslHandler.engine().getSession());
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }

        private void fireProtocolNegotiationEvent(ChannelHandlerContext ctx, SSLSession session) {
            InternalChannelz.Security security = new InternalChannelz.Security(new InternalChannelz.Tls(session));
            Attributes attrs = this.pne.getAttributes().toBuilder().set(GrpcAttributes.ATTR_SECURITY_LEVEL, SecurityLevel.PRIVACY_AND_INTEGRITY).set(Grpc.TRANSPORT_ATTR_SSL_SESSION, session).build();
            ctx.fireUserEventTriggered(this.pne.withAttributes(attrs).withSecurity(security));
        }
    }

    @VisibleForTesting
    static final class TlsProtocolNegotiatorServerFactory
    implements ProtocolNegotiator.ServerFactory {
        private final SslContext sslContext;

        public TlsProtocolNegotiatorServerFactory(SslContext sslContext) {
            this.sslContext = Preconditions.checkNotNull(sslContext, "sslContext");
        }

        @Override
        public ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> offloadExecutorPool) {
            return ProtocolNegotiators.serverTls(this.sslContext, offloadExecutorPool);
        }
    }

    @VisibleForTesting
    static final class PlaintextProtocolNegotiatorServerFactory
    implements ProtocolNegotiator.ServerFactory {
        PlaintextProtocolNegotiatorServerFactory() {
        }

        @Override
        public ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> offloadExecutorPool) {
            return ProtocolNegotiators.serverPlaintext();
        }
    }

    private static final class FixedProtocolNegotiatorServerFactory
    implements ProtocolNegotiator.ServerFactory {
        private final ProtocolNegotiator protocolNegotiator;

        public FixedProtocolNegotiatorServerFactory(ProtocolNegotiator protocolNegotiator) {
            this.protocolNegotiator = Preconditions.checkNotNull(protocolNegotiator, "protocolNegotiator");
        }

        @Override
        public ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> offloadExecutorPool) {
            return this.protocolNegotiator;
        }
    }

    public static final class FromServerCredentialsResult {
        public final ProtocolNegotiator.ServerFactory negotiator;
        public final String error;

        private FromServerCredentialsResult(ProtocolNegotiator.ServerFactory negotiator, String error) {
            this.negotiator = negotiator;
            this.error = error;
        }

        public static FromServerCredentialsResult error(String error) {
            return new FromServerCredentialsResult(null, Preconditions.checkNotNull(error, "error"));
        }

        public static FromServerCredentialsResult negotiator(ProtocolNegotiator.ServerFactory factory2) {
            return new FromServerCredentialsResult(Preconditions.checkNotNull(factory2, "factory"), null);
        }
    }

    public static final class FromChannelCredentialsResult {
        public final ProtocolNegotiator.ClientFactory negotiator;
        public final CallCredentials callCredentials;
        public final String error;

        private FromChannelCredentialsResult(ProtocolNegotiator.ClientFactory negotiator, CallCredentials creds, String error) {
            this.negotiator = negotiator;
            this.callCredentials = creds;
            this.error = error;
        }

        public static FromChannelCredentialsResult error(String error) {
            return new FromChannelCredentialsResult(null, null, Preconditions.checkNotNull(error, "error"));
        }

        public static FromChannelCredentialsResult negotiator(ProtocolNegotiator.ClientFactory factory2) {
            return new FromChannelCredentialsResult(Preconditions.checkNotNull(factory2, "factory"), null, null);
        }

        public FromChannelCredentialsResult withCallCredentials(CallCredentials callCreds) {
            Preconditions.checkNotNull(callCreds, "callCreds");
            if (this.error != null) {
                return this;
            }
            if (this.callCredentials != null) {
                callCreds = new CompositeCallCredentials(this.callCredentials, callCreds);
            }
            return new FromChannelCredentialsResult(this.negotiator, callCreds, null);
        }
    }
}

