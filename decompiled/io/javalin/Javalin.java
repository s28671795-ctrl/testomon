/*
 * Decompiled with CFR 0.152.
 */
package io.javalin;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.config.JavalinConfig;
import io.javalin.event.EventListener;
import io.javalin.event.EventManager;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.JavalinEvent;
import io.javalin.event.WsHandlerMetaInfo;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.http.servlet.JavalinServlet;
import io.javalin.http.sse.SseClient;
import io.javalin.http.sse.SseHandler;
import io.javalin.jetty.JavalinJettyServlet;
import io.javalin.jetty.JettyServer;
import io.javalin.jetty.JettyUtil;
import io.javalin.routing.HandlerEntry;
import io.javalin.security.RouteRole;
import io.javalin.util.JavalinBindException;
import io.javalin.util.JavalinException;
import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsExceptionHandler;
import io.javalin.websocket.WsHandlerType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;
import org.eclipse.jetty.server.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Javalin
implements AutoCloseable {
    public JavalinConfig cfg = new JavalinConfig();
    protected JettyServer jettyServer;
    protected JavalinJettyServlet javalinJettyServlet;
    protected JavalinServlet javalinServlet = new JavalinServlet(this.cfg);
    protected EventManager eventManager = new EventManager();

    protected Javalin() {
        this.jettyServer = new JettyServer(this.cfg);
        this.javalinJettyServlet = new JavalinJettyServlet(this.cfg, this.javalinServlet);
    }

    public Javalin(JettyServer jettyServer, JavalinJettyServlet jettyServlet) {
        this.jettyServer = jettyServer;
        this.javalinJettyServlet = jettyServlet;
    }

    public static Javalin create() {
        return Javalin.create(config -> {});
    }

    public static Javalin create(Consumer<JavalinConfig> config) {
        Javalin app = new Javalin();
        JavalinConfig.applyUserConfig(app, app.cfg, config);
        JettyUtil.maybeLogIfServerNotStarted(app.jettyServer);
        return app;
    }

    public static Javalin createStandalone(Consumer<JavalinConfig> config) {
        Javalin app = new Javalin(null, null);
        JavalinConfig.applyUserConfig(app, app.cfg, config);
        return app;
    }

    public static Javalin createStandalone() {
        return Javalin.createStandalone(config -> {});
    }

    public JavalinServlet javalinServlet() {
        return this.javalinServlet;
    }

    @Nullable
    public JettyServer jettyServer() {
        return this.jettyServer;
    }

    public Javalin start(String host2, int port) {
        this.jettyServer.setServerHost(host2);
        return this.start(port);
    }

    public Javalin start(int port) {
        this.jettyServer.setServerPort(port);
        return this.start();
    }

    public Javalin start() {
        long startupTimer = System.currentTimeMillis();
        if (this.jettyServer.started) {
            String message = "Server already started. If you are trying to call start() on an instance of Javalin that was stopped using stop(), please create a new instance instead.";
            throw new IllegalStateException(message);
        }
        this.jettyServer.started = true;
        Util.printHelpfulMessageIfLoggerIsMissing();
        this.eventManager.fireEvent(JavalinEvent.SERVER_STARTING);
        try {
            JavalinLogger.startup("Starting Javalin ...");
            this.jettyServer.start(this.javalinJettyServlet);
            Util.logJavalinVersion();
            JavalinLogger.startup("Javalin started in " + (System.currentTimeMillis() - startupTimer) + "ms \\o/");
            this.eventManager.fireEvent(JavalinEvent.SERVER_STARTED);
        }
        catch (Exception e) {
            JavalinLogger.error("Failed to start Javalin");
            this.eventManager.fireEvent(JavalinEvent.SERVER_START_FAILED);
            if (Boolean.TRUE.equals(this.jettyServer.server().getAttribute("is-default-server"))) {
                this.stop();
            }
            if (e.getMessage() != null && e.getMessage().contains("Failed to bind to")) {
                throw new JavalinBindException("Port already in use. Make sure no other process is using port " + Util.getPort(e) + " and try again.", e);
            }
            if (e.getMessage() != null && e.getMessage().contains("Permission denied")) {
                throw new JavalinBindException("Port 1-1023 require elevated privileges (process must be started by admin).", e);
            }
            throw new JavalinException(e);
        }
        return this;
    }

    public Javalin stop() {
        JavalinLogger.info("Stopping Javalin ...");
        this.eventManager.fireEvent(JavalinEvent.SERVER_STOPPING);
        try {
            this.jettyServer.server().stop();
        }
        catch (Exception e) {
            this.eventManager.fireEvent(JavalinEvent.SERVER_STOP_FAILED);
            JavalinLogger.error("Javalin failed to stop gracefully", e);
            throw new JavalinException(e);
        }
        JavalinLogger.info("Javalin has stopped");
        this.eventManager.fireEvent(JavalinEvent.SERVER_STOPPED);
        return this;
    }

    @Override
    public void close() {
        Server server = this.jettyServer.server();
        if (server.isStopping() || server.isStopped()) {
            return;
        }
        this.stop();
    }

    public Javalin events(Consumer<EventListener> listener) {
        EventListener eventListener = new EventListener(this.eventManager);
        listener.accept(eventListener);
        return this;
    }

    public int port() {
        return this.jettyServer.getServerPort();
    }

    public Javalin attribute(String key, Object value) {
        this.cfg.pvt.appAttributes.put(key, value);
        return this;
    }

    public <T> T attribute(String key) {
        return (T)this.cfg.pvt.appAttributes.get(key);
    }

    public Javalin updateConfig(Consumer<JavalinConfig> userConfig) {
        userConfig.accept(this.cfg);
        this.cfg.plugins.getPluginManager().initializePlugins(this);
        return this;
    }

    public Javalin routes(@NotNull EndpointGroup endpointGroup) {
        ApiBuilder.setStaticJavalin(this);
        try {
            endpointGroup.addEndpoints();
        }
        finally {
            ApiBuilder.clearStaticJavalin();
        }
        return this;
    }

    public <T extends Exception> Javalin exception(@NotNull Class<T> exceptionClass, @NotNull ExceptionHandler<? super T> exceptionHandler) {
        this.javalinServlet.getExceptionMapper().getHandlers().put(exceptionClass, exceptionHandler);
        return this;
    }

    public Javalin error(HttpStatus status, @NotNull Handler handler) {
        return this.error(status.getCode(), "*", handler);
    }

    public Javalin error(int status, @NotNull Handler handler) {
        return this.error(status, "*", handler);
    }

    public Javalin error(HttpStatus status, @NotNull String contentType, @NotNull Handler handler) {
        return this.error(status.getCode(), contentType, handler);
    }

    public Javalin error(int status, @NotNull String contentType, @NotNull Handler handler) {
        this.javalinServlet.getErrorMapper().addHandler(status, contentType, handler);
        return this;
    }

    public Javalin addHandler(@NotNull HandlerType handlerType, @NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        HashSet<RouteRole> roleSet = new HashSet<RouteRole>(Arrays.asList(roles));
        this.javalinServlet.getMatcher().add(new HandlerEntry(handlerType, path, this.cfg.routing, roleSet, handler));
        this.eventManager.fireHandlerAddedEvent(new HandlerMetaInfo(handlerType, Util.prefixContextPath(this.cfg.routing.contextPath, path), handler, roleSet));
        return this;
    }

    public Javalin addHandler(@NotNull HandlerType httpMethod, @NotNull String path, @NotNull Handler handler) {
        return this.addHandler(httpMethod, path, handler, new RouteRole[0]);
    }

    public Javalin get(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.GET, path, handler);
    }

    public Javalin post(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.POST, path, handler);
    }

    public Javalin put(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.PUT, path, handler);
    }

    public Javalin patch(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.PATCH, path, handler);
    }

    public Javalin delete(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.DELETE, path, handler);
    }

    public Javalin head(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.HEAD, path, handler);
    }

    public Javalin options(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.OPTIONS, path, handler);
    }

    public Javalin get(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.GET, path, handler, roles);
    }

    public Javalin post(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.POST, path, handler, roles);
    }

    public Javalin put(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.PUT, path, handler, roles);
    }

    public Javalin patch(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.PATCH, path, handler, roles);
    }

    public Javalin delete(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.DELETE, path, handler, roles);
    }

    public Javalin head(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.HEAD, path, handler, roles);
    }

    public Javalin options(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        return this.addHandler(HandlerType.OPTIONS, path, handler, roles);
    }

    public Javalin sse(@NotNull String path, @NotNull Consumer<SseClient> client) {
        return this.sse(path, client, new RouteRole[0]);
    }

    public Javalin sse(@NotNull String path, @NotNull SseHandler handler) {
        return this.get(path, handler);
    }

    public Javalin sse(@NotNull String path, @NotNull Consumer<SseClient> client, RouteRole ... roles) {
        return this.get(path, new SseHandler(client), roles);
    }

    public Javalin before(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.BEFORE, path, handler);
    }

    public Javalin before(@NotNull Handler handler) {
        return this.before("*", handler);
    }

    public Javalin after(@NotNull String path, @NotNull Handler handler) {
        return this.addHandler(HandlerType.AFTER, path, handler);
    }

    public Javalin after(@NotNull Handler handler) {
        return this.after("*", handler);
    }

    public <T extends Exception> Javalin wsException(@NotNull Class<T> exceptionClass, @NotNull WsExceptionHandler<? super T> exceptionHandler) {
        this.javalinJettyServlet.getWsExceptionMapper().getHandlers().put(exceptionClass, exceptionHandler);
        return this;
    }

    private Javalin addWsHandler(@NotNull WsHandlerType handlerType, @NotNull String path, @NotNull Consumer<WsConfig> wsConfig, RouteRole ... roles) {
        HashSet<RouteRole> roleSet = new HashSet<RouteRole>(Arrays.asList(roles));
        this.javalinJettyServlet.addHandler(handlerType, path, wsConfig, roleSet);
        this.eventManager.fireWsHandlerAddedEvent(new WsHandlerMetaInfo(handlerType, Util.prefixContextPath(this.cfg.routing.contextPath, path), wsConfig, roleSet));
        return this;
    }

    private Javalin addWsHandler(@NotNull WsHandlerType handlerType, @NotNull String path, @NotNull Consumer<WsConfig> wsConfig) {
        return this.addWsHandler(handlerType, path, wsConfig, new RouteRole[0]);
    }

    public Javalin ws(@NotNull String path, @NotNull Consumer<WsConfig> ws) {
        return this.ws(path, ws, new RouteRole[0]);
    }

    public Javalin ws(@NotNull String path, @NotNull Consumer<WsConfig> ws, RouteRole ... roles) {
        return this.addWsHandler(WsHandlerType.WEBSOCKET, path, ws, roles);
    }

    public Javalin wsBefore(@NotNull String path, @NotNull Consumer<WsConfig> wsConfig) {
        return this.addWsHandler(WsHandlerType.WS_BEFORE, path, wsConfig);
    }

    public Javalin wsBefore(@NotNull Consumer<WsConfig> wsConfig) {
        return this.wsBefore("*", wsConfig);
    }

    public Javalin wsAfter(@NotNull String path, @NotNull Consumer<WsConfig> wsConfig) {
        return this.addWsHandler(WsHandlerType.WS_AFTER, path, wsConfig);
    }

    public Javalin wsAfter(@NotNull Consumer<WsConfig> wsConfig) {
        return this.wsAfter("*", wsConfig);
    }
}

