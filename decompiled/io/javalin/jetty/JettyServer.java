/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.config.JavalinConfig;
import io.javalin.config.PrivateConfig;
import io.javalin.jetty.JavalinJettyServlet;
import io.javalin.jetty.JettyResourceHandler;
import io.javalin.jetty.JettyUtil;
import io.javalin.util.JavalinLogger;
import io.javalin.util.LoomUtil;
import io.javalin.util.Util;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.UriCompliance;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.AbstractNetworkConnector;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandlerContainer;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.websocket.server.JettyWebSocketServerContainer;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u0014\u0010\"\u001a\u00020#*\u00020$2\u0006\u0010%\u001a\u00020&H\u0002J\f\u0010'\u001a\u00020$*\u00020(H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0015\u001a\u00020\b*\u00020\u00168BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006)"}, d2={"Lio/javalin/jetty/JettyServer;", "", "cfg", "Lio/javalin/config/JavalinConfig;", "(Lio/javalin/config/JavalinConfig;)V", "getCfg", "()Lio/javalin/config/JavalinConfig;", "serverHost", "", "getServerHost", "()Ljava/lang/String;", "setServerHost", "(Ljava/lang/String;)V", "serverPort", "", "getServerPort", "()I", "setServerPort", "(I)V", "started", "", "protocol", "Lorg/eclipse/jetty/server/ServerConnector;", "getProtocol", "(Lorg/eclipse/jetty/server/ServerConnector;)Ljava/lang/String;", "defaultConnector", "server", "Lorg/eclipse/jetty/server/Server;", "defaultSessionHandler", "Lorg/eclipse/jetty/server/session/SessionHandler;", "start", "", "wsAndHttpServlet", "Lio/javalin/jetty/JavalinJettyServlet;", "attachHandler", "Lorg/eclipse/jetty/server/handler/AbstractHandlerContainer;", "Lorg/eclipse/jetty/server/Handler;", "servletContextHandler", "Lorg/eclipse/jetty/servlet/ServletContextHandler;", "unwrap", "Lorg/eclipse/jetty/server/handler/HandlerWrapper;", "javalin"})
@SourceDebugExtension(value={"SMAP\nJettyServer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JettyServer.kt\nio/javalin/jetty/JettyServer\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,145:1\n4098#2,11:146\n3792#2:159\n4307#2,2:160\n1855#3,2:157\n1855#3,2:162\n1855#3,2:164\n1#4:166\n*S KotlinDebug\n*F\n+ 1 JettyServer.kt\nio/javalin/jetty/JettyServer\n*L\n90#1:146,11\n94#1:159\n94#1:160,2\n90#1:157,2\n94#1:162,2\n112#1:164,2\n*E\n"})
public final class JettyServer {
    @NotNull
    private final JavalinConfig cfg;
    @JvmField
    public boolean started;
    private int serverPort;
    @Nullable
    private String serverHost;

    public JettyServer(@NotNull JavalinConfig cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        this.cfg = cfg;
        this.serverPort = -1;
    }

    @NotNull
    public final JavalinConfig getCfg() {
        return this.cfg;
    }

    public final int getServerPort() {
        return this.serverPort;
    }

    public final void setServerPort(int n) {
        this.serverPort = n;
    }

    @Nullable
    public final String getServerHost() {
        return this.serverHost;
    }

    public final void setServerHost(@Nullable String string) {
        this.serverHost = string;
    }

    @NotNull
    public final Server server() {
        PrivateConfig privateConfig = this.cfg.pvt;
        Server server = this.cfg.pvt.server;
        if (server == null) {
            server = JettyUtil.getOrDefault(this.cfg.pvt.server);
        }
        privateConfig.server = server;
        Server server2 = this.cfg.pvt.server;
        Intrinsics.checkNotNull(server2);
        return server2;
    }

    /*
     * WARNING - void declaration
     */
    public final void start(@NotNull JavalinJettyServlet wsAndHttpServlet) throws BindException {
        void $this$filterTo$iv$iv;
        void $this$forEach$iv;
        Object[] $this$filterIsInstanceTo$iv$iv;
        Handler handler;
        Intrinsics.checkNotNullParameter(wsAndHttpServlet, "wsAndHttpServlet");
        if (this.serverPort == -1 && this.cfg.pvt.server == null) {
            this.serverPort = 8080;
            JavalinLogger.startup("No port specified, starting on port " + this.serverPort + ". Call start(port) to change ports.");
        }
        Map<String, String> encodingMap = MimeTypes.getInferredEncodings();
        encodingMap.put("text/plain", "utf-8");
        PrivateConfig privateConfig = this.cfg.pvt;
        SessionHandler sessionHandler = this.cfg.pvt.sessionHandler;
        if (sessionHandler == null) {
            sessionHandler = this.defaultSessionHandler();
        }
        privateConfig.sessionHandler = sessionHandler;
        Void nullParent = null;
        Object object = Util.normalizeContextPath(this.cfg.routing.contextPath);
        Object $this$start_u24lambda_u241 = object = new ServletContextHandler(nullParent, (String)object){

            public void doHandle(@NotNull String target, @NotNull Request jettyRequest, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(jettyRequest, "jettyRequest");
                Intrinsics.checkNotNullParameter(request, "request");
                Intrinsics.checkNotNullParameter(response, "response");
                request.setAttribute("jetty-target-and-request", new Pair<String, Request>(target, jettyRequest));
                super.doHandle(target, jettyRequest, request, response);
            }
        };
        boolean bl = false;
        ((ServletContextHandler)$this$start_u24lambda_u241).setSessionHandler(this.cfg.pvt.sessionHandler);
        Consumer<ServletContextHandler> consumer = this.cfg.pvt.servletContextHandlerConsumer;
        if (consumer != null) {
            consumer.accept((ServletContextHandler)$this$start_u24lambda_u241);
        }
        ((ServletContextHandler)$this$start_u24lambda_u241).addServlet(new ServletHolder(wsAndHttpServlet), "/*");
        JettyWebSocketServletContainerInitializer.configure((ServletContextHandler)$this$start_u24lambda_u241, JettyServer::start$lambda$1$lambda$0);
        Object wsAndHttpHandler2 = object;
        Object $this$start_u24lambda_u242 = object = this.server();
        boolean $i$a$-apply-JettyServer$start$232 = false;
        if (((HandlerWrapper)$this$start_u24lambda_u242).getHandler() == null) {
            handler = (Handler)wsAndHttpHandler2;
        } else {
            Handler handler2 = ((HandlerWrapper)$this$start_u24lambda_u242).getHandler();
            Intrinsics.checkNotNullExpressionValue(handler2, "handler");
            handler = this.attachHandler(handler2, (ServletContextHandler)wsAndHttpHandler2);
        }
        ((HandlerWrapper)$this$start_u24lambda_u242).setHandler(handler);
        Connector[] connectorArray = ((Server)$this$start_u24lambda_u242).getConnectors();
        Intrinsics.checkNotNullExpressionValue(connectorArray, "connectors");
        if (((Object[])connectorArray).length == 0) {
            ServerConnector[] serverConnectorArray = new ServerConnector[]{this.defaultConnector((Server)$this$start_u24lambda_u242)};
            ((Server)$this$start_u24lambda_u242).setConnectors(serverConnectorArray);
        } else if (this.cfg.pvt.httpConfigurationConfig != null) {
            JavalinLogger.startup("Http Configuration added to the JettyConfig has not been applied as a custom Jetty server was provided");
        }
        ((AbstractLifeCycle)object).start();
        Util.logJavalinBanner(this.cfg.showJavalinBanner);
        LoomUtil.INSTANCE.logIfLoom(this.server());
        $this$start_u24lambda_u242 = this.cfg.pvt.resourceHandler;
        JettyResourceHandler jettyResourceHandler = $this$start_u24lambda_u242 instanceof JettyResourceHandler ? (JettyResourceHandler)$this$start_u24lambda_u242 : null;
        if (jettyResourceHandler != null) {
            jettyResourceHandler.init();
        }
        Connector[] connectorArray2 = this.server().getConnectors();
        Intrinsics.checkNotNullExpressionValue(connectorArray2, "server().connectors");
        Object $this$filterIsInstance$iv = connectorArray2;
        boolean $i$f$filterIsInstance = false;
        Object[] $i$a$-apply-JettyServer$start$232 = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (void var12_18 : $this$filterIsInstanceTo$iv$iv) {
            if (!(var12_18 instanceof ServerConnector)) continue;
            destination$iv$iv.add(var12_18);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String string;
            String string2;
            ServerConnector it = (ServerConnector)element$iv;
            int n = 0;
            String string3 = this.getProtocol(it);
            String string4 = string2 = it.getHost();
            if (string4 == null) {
                string = "localhost";
            } else {
                Intrinsics.checkNotNullExpressionValue(string4, "it.host ?: \"localhost\"");
                string = string2;
            }
            JavalinLogger.startup("Listening on " + string3 + "://" + string + ":" + it.getLocalPort() + this.cfg.routing.contextPath);
        }
        Connector[] connectorArray3 = this.server().getConnectors();
        Intrinsics.checkNotNullExpressionValue(connectorArray3, "server().connectors");
        Object $this$filter$iv = connectorArray3;
        boolean $i$f$filter = false;
        $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (void var12_20 : $this$filterTo$iv$iv) {
            Connector it = (Connector)var12_20;
            boolean bl2 = false;
            if (!(!(it instanceof ServerConnector))) continue;
            destination$iv$iv.add(var12_20);
        }
        $this$filter$iv = (List)destination$iv$iv;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Connector it = (Connector)element$iv;
            boolean bl3 = false;
            JavalinLogger.startup("Binding to: " + it);
        }
        Connector connector = this.server().getConnectors()[0];
        ServerConnector serverConnector = connector instanceof ServerConnector ? (ServerConnector)connector : null;
        this.serverPort = serverConnector != null ? serverConnector.getLocalPort() : -1;
    }

    private final ServerConnector defaultConnector(Server server) {
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setUriCompliance(UriCompliance.RFC3986);
        Consumer<HttpConfiguration> consumer = this.cfg.pvt.httpConfigurationConfig;
        if (consumer != null) {
            consumer.accept(httpConfiguration);
        }
        Object object = new ConnectionFactory[]{new HttpConnectionFactory(httpConfiguration)};
        Object $this$defaultConnector_u24lambda_u247 = object = new ServerConnector(server, (ConnectionFactory[])object);
        boolean bl = false;
        ((AbstractNetworkConnector)$this$defaultConnector_u24lambda_u247).setPort(this.serverPort);
        ((AbstractNetworkConnector)$this$defaultConnector_u24lambda_u247).setHost(this.serverHost);
        Collection<ConnectionFactory> collection = ((AbstractConnector)$this$defaultConnector_u24lambda_u247).getConnectionFactories();
        Intrinsics.checkNotNullExpressionValue(collection, "this.connectionFactories");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ConnectionFactory it = (ConnectionFactory)element$iv;
            boolean bl2 = false;
            if (!(it instanceof HttpConnectionFactory)) continue;
            ((HttpConnectionFactory)it).getHttpConfiguration().setSendServerVersion(false);
        }
        return object;
    }

    private final SessionHandler defaultSessionHandler() {
        SessionHandler sessionHandler;
        SessionHandler $this$defaultSessionHandler_u24lambda_u248 = sessionHandler = new SessionHandler();
        boolean bl = false;
        $this$defaultSessionHandler_u24lambda_u248.setHttpOnly(true);
        $this$defaultSessionHandler_u24lambda_u248.setSameSite(HttpCookie.SameSite.LAX);
        return sessionHandler;
    }

    private final String getProtocol(ServerConnector $this$protocol) {
        return $this$protocol.getProtocols().contains("ssl") ? "https" : "http";
    }

    private final AbstractHandlerContainer attachHandler(Handler $this$attachHandler, ServletContextHandler servletContextHandler) {
        AbstractHandlerContainer abstractHandlerContainer;
        Handler handler = $this$attachHandler;
        if (handler instanceof HandlerCollection) {
            Handler handler2 = $this$attachHandler;
            HandlerCollection $this$attachHandler_u24lambda_u249 = (HandlerCollection)handler2;
            boolean bl = false;
            $this$attachHandler_u24lambda_u249.addHandler(servletContextHandler);
            abstractHandlerContainer = (AbstractHandlerContainer)handler2;
        } else if (handler instanceof HandlerWrapper) {
            Handler handler3 = $this$attachHandler;
            HandlerWrapper $this$attachHandler_u24lambda_u2410 = (HandlerWrapper)handler3;
            boolean bl = false;
            Handler handler4 = this.unwrap($this$attachHandler_u24lambda_u2410);
            HandlerCollection handlerCollection = handler4 instanceof HandlerCollection ? (HandlerCollection)handler4 : null;
            if (handlerCollection != null) {
                handlerCollection.addHandler(servletContextHandler);
            }
            handler4 = this.unwrap($this$attachHandler_u24lambda_u2410);
            HandlerWrapper handlerWrapper = handler4 instanceof HandlerWrapper ? (HandlerWrapper)handler4 : null;
            if (handlerWrapper != null) {
                handlerWrapper.setHandler(servletContextHandler);
            }
            abstractHandlerContainer = (AbstractHandlerContainer)handler3;
        } else {
            throw new IllegalStateException("Server has unsupported Handler attached to it (must be HandlerCollection or HandlerWrapper)");
        }
        return abstractHandlerContainer;
    }

    private final Handler unwrap(HandlerWrapper $this$unwrap) {
        Handler handler;
        Handler handler2 = $this$unwrap.getHandler();
        if (handler2 == null) {
            handler = $this$unwrap;
        } else if (handler2 instanceof HandlerCollection) {
            Handler handler3 = $this$unwrap.getHandler();
            handler = handler3;
            Intrinsics.checkNotNullExpressionValue(handler3, "this.handler");
        } else if (handler2 instanceof HandlerWrapper) {
            Handler handler4 = $this$unwrap.getHandler();
            Intrinsics.checkNotNull(handler4, "null cannot be cast to non-null type org.eclipse.jetty.server.handler.HandlerWrapper");
            handler = this.unwrap((HandlerWrapper)handler4);
        } else {
            throw new IllegalStateException("HandlerWrapper has unsupported Handler type (must be HandlerCollection or HandlerWrapper");
        }
        return handler;
    }

    private static final void start$lambda$1$lambda$0(ServletContext servletContext, JettyWebSocketServerContainer jettyWebSocketServerContainer) {
    }
}

