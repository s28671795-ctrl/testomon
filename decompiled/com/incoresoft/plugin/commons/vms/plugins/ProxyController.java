/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins;

import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.vms.vezhaintegration.VezhaIntegrationManager;
import com.incoresoft.plugin.commons.utils.ContentTypeUtils;
import com.incoresoft.plugin.commons.vms.VezhaServerUnauthorizedException;
import com.incoresoft.plugin.commons.vms.exceptions.VezhaServerUnavailableException;
import com.incoresoft.plugin.commons.vms.plugins.ProxyWebSocketConnection;
import com.incoresoft.plugin.commons.vms.plugins.proxy.ForwardingRule;
import com.incoresoft.plugin.commons.vms.plugins.proxy.ProxySseEventListener;
import com.incoresoft.plugin.commons.vms.plugins.proxy.ProxyWebSocketClient;
import com.incoresoft.plugin.commons.vms.plugins.utils.ForwardPathUtils;
import com.incoresoft.plugin.commons.vms.plugins.utils.HeaderUtils;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.sse.SseClient;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyController {
    private static final Logger log = LoggerFactory.getLogger(ProxyController.class);
    private static final long WEB_SOCKET_DIAGNOSTICS_INTERVAL_HOURS = 1L;
    private static final String BEARER = "Bearer ";
    private static final String HTTP = "http";
    private static final String WS_PROTOCOL = "ws";
    private static final String WSS_PROTOCOL = "wss";
    private static final Map<String, String> cacheControl = Map.of("Cache-Control", "public, max-age=3600");
    private static final Set<String> filesExcludedFromCaching = Set.of("app.js");
    protected final UsersManager usersManager;
    private final VezhaIntegrationManager vezhaIntegrationManager;
    private final HttpClient httpClient;
    private final OkHttpClient okHttpClient;
    protected final String pluginName;
    private final List<ForwardingRule> httpForwardingRules;
    private final Map<String, String> wsRoutes;
    private final Map<String, String> sseRoutes;
    private final Map<String, ProxyWebSocketConnection> webSocketMapping = new ConcurrentHashMap<String, ProxyWebSocketConnection>();
    private final Map<SseClient, EventSource> activeSseClients = new ConcurrentHashMap<SseClient, EventSource>();
    private final ScheduledExecutorService webSocketDiagnosticsScheduler = Executors.newSingleThreadScheduledExecutor(Thread.ofPlatform().daemon().name("proxy-websocket-diagnostics").factory());
    private final AtomicBoolean webSocketDiagnosticsScheduled = new AtomicBoolean();

    public ProxyController(UsersManager usersManager, VezhaIntegrationManager vezhaIntegrationManager, HttpClient httpClient, OkHttpClient okHttpClient, List<ForwardingRule> httpForwardingRules, Map<String, String> wsRoutes, Map<String, String> sseRoutes) {
        this.usersManager = usersManager;
        this.vezhaIntegrationManager = vezhaIntegrationManager;
        this.httpClient = httpClient;
        this.okHttpClient = okHttpClient;
        this.httpForwardingRules = httpForwardingRules;
        this.wsRoutes = wsRoutes;
        this.sseRoutes = sseRoutes;
        this.pluginName = null;
    }

    public void init(Javalin app) {
        for (ForwardingRule forwardingRule : this.httpForwardingRules) {
            for (HandlerType methodType : forwardingRule.methods()) {
                if (methodType == HandlerType.AFTER || methodType == HandlerType.BEFORE) continue;
                app.addHandler(methodType, forwardingRule.matchedPath(), ctx -> this.proxyForwardingRestHandler(ctx, rule), AppRole.API_CALL, AppRole.LOGGED_IN);
            }
        }
        for (Map.Entry entry : this.wsRoutes.entrySet()) {
            app.ws((String)entry.getKey(), ws -> {
                ws.onConnect(wsContext -> this.proxyWsConnectHandler(wsContext, new ForwardingRule((String)routing.getKey(), (String)routing.getValue(), Set.of())));
                ws.onMessage(this::proxyWsMessageHandler);
                ws.onBinaryMessage(this::proxyWsBinaryMessageHandler);
                ws.onClose(this::proxyWsCloseHandler);
                ws.onError(this::proxyWsErrorHandler);
            }, AppRole.API_CALL, AppRole.LOGGED_IN);
        }
        if (!this.wsRoutes.isEmpty()) {
            this.setUpWebSocketDiagnostics(app);
        }
        for (Map.Entry entry : this.sseRoutes.entrySet()) {
            app.sse((String)entry.getKey(), sseClient -> this.proxyForwardingSseHandler((SseClient)sseClient, new ForwardingRule((String)routing.getKey(), (String)routing.getValue(), Set.of())), AppRole.API_CALL, AppRole.LOGGED_IN);
        }
        if (this.pluginName != null) {
            app.get("resources/" + this.pluginName + "/*", ctx -> {
                InputStream resource;
                String path = ctx.path().replaceFirst("/resources/" + this.pluginName + "/", "");
                String resourcePath = "public/" + path;
                ctx.contentType(ContentTypeUtils.guessContentType(resourcePath));
                if (this.fileMustBeCached(resourcePath)) {
                    cacheControl.forEach(ctx::header);
                }
                if ((resource = this.getClass().getClassLoader().getResourceAsStream(resourcePath)) == null) {
                    throw new NotFoundResponse();
                }
                ctx.result(resource);
            }, AppRole.ANYONE);
        }
    }

    protected void proxyForwardingRestHandler(Context ctx, ForwardingRule forwardingRule) {
        this.proxyForwardingRestHandler(ctx, ctx.bodyInputStream(), forwardingRule);
    }

    protected void proxyForwardingRestHandler(Context ctx, InputStream bodyInputStream, ForwardingRule forwardingRule) {
        try {
            User user = this.usersManager.getUser(ctx);
            String token = this.vezhaIntegrationManager.getToken(user);
            URI proxyUri = this.buildProxyUri(ctx, forwardingRule);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(proxyUri);
            ctx.headerMap().entrySet().stream().filter(it -> HeaderUtils.isAllowedHttpHeader((String)it.getKey())).forEach(it -> requestBuilder.setHeader((String)it.getKey(), (String)it.getValue()));
            requestBuilder.setHeader("Authorization", BEARER + token);
            try (InputStream body2 = bodyInputStream;){
                requestBuilder.method(ctx.method().name(), HttpRequest.BodyPublishers.ofInputStream(() -> body2));
                HttpResponse<InputStream> response = this.httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofInputStream());
                int responseStatus = response.statusCode();
                for (Map.Entry<String, List<String>> entry : response.headers().map().entrySet()) {
                    String header = entry.getKey();
                    List<String> value = entry.getValue();
                    if (!HeaderUtils.isAllowedHttpHeader(header)) continue;
                    value.forEach(it -> ctx.header(header, (String)it));
                }
                if (responseStatus == HttpStatus.UNAUTHORIZED.getCode()) {
                    throw new VezhaServerUnauthorizedException();
                }
                ctx.status(responseStatus).result(response.body());
            }
        }
        catch (IOException | InterruptedException e) {
            throw new VezhaServerUnavailableException(e);
        }
    }

    private void proxyWsConnectHandler(WsConnectContext wsContext, ForwardingRule rule) {
        String sessionId = wsContext.getSessionId();
        ProxyWebSocketConnection connection = new ProxyWebSocketConnection();
        this.webSocketMapping.put(sessionId, connection);
        try {
            User user = this.usersManager.getUser(wsContext);
            String token = this.vezhaIntegrationManager.getToken(user);
            URI wsProxyUri = this.buildProxyUri(wsContext, rule);
            WebSocket.Builder webSocketBuilder = this.httpClient.newWebSocketBuilder();
            wsContext.headerMap().entrySet().stream().filter(it -> HeaderUtils.isAllowedWsHeader((String)it.getKey())).forEach(it -> webSocketBuilder.header((String)it.getKey(), (String)it.getValue()));
            webSocketBuilder.header("Authorization", BEARER + token);
            ProxyWebSocketClient proxyWebSocketClient = new ProxyWebSocketClient(wsContext, wsProxyUri, webSocket -> this.terminateProxyWsConnection(sessionId, connection));
            webSocketBuilder.buildAsync(wsProxyUri, proxyWebSocketClient).whenComplete((proxyWebSocket, throwable) -> {
                if (throwable != null) {
                    log.error("Can't connect to remote server {}: {}", (Object)wsProxyUri, (Object)throwable.getMessage());
                    this.terminateProxyWsConnection(sessionId, connection);
                    wsContext.closeSession();
                    return;
                }
                connection.attach((WebSocket)proxyWebSocket);
            });
        }
        catch (Exception e) {
            this.terminateProxyWsConnection(sessionId, connection);
            wsContext.closeSession(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), e.getMessage());
        }
    }

    private void proxyWsMessageHandler(WsMessageContext wsContext) {
        WebSocket webSocket;
        ProxyWebSocketConnection connection = this.webSocketMapping.get(wsContext.getSessionId());
        WebSocket webSocket2 = webSocket = connection == null ? null : connection.webSocket();
        if (webSocket != null) {
            String message = wsContext.message();
            webSocket.sendText(message, true);
        }
    }

    private void proxyWsBinaryMessageHandler(WsBinaryMessageContext wsContext) {
        WebSocket webSocket;
        ProxyWebSocketConnection connection = this.webSocketMapping.get(wsContext.getSessionId());
        WebSocket webSocket2 = webSocket = connection == null ? null : connection.webSocket();
        if (webSocket != null) {
            byte[] binaryMsg = wsContext.data();
            webSocket.sendBinary(ByteBuffer.wrap(binaryMsg), true);
        }
    }

    private void proxyWsCloseHandler(WsCloseContext wsContext) {
        WebSocket webSocket;
        ProxyWebSocketConnection connection = this.webSocketMapping.remove(wsContext.getSessionId());
        WebSocket webSocket2 = webSocket = connection == null ? null : connection.terminate();
        if (webSocket != null) {
            webSocket.sendClose(wsContext.status(), Optional.ofNullable(wsContext.reason()).orElse("")).exceptionally(throwable -> {
                webSocket.abort();
                return null;
            });
        }
    }

    private void proxyWsErrorHandler(WsErrorContext wsContext) {
        WebSocket webSocket;
        ProxyWebSocketConnection connection = this.webSocketMapping.remove(wsContext.getSessionId());
        WebSocket webSocket2 = webSocket = connection == null ? null : connection.terminate();
        if (webSocket != null) {
            webSocket.abort();
        }
        wsContext.closeSession();
    }

    private void terminateProxyWsConnection(String sessionId, ProxyWebSocketConnection connection) {
        connection.terminate();
        this.webSocketMapping.remove(sessionId, connection);
    }

    private void proxyForwardingSseHandler(SseClient sseClient, ForwardingRule routing) {
        sseClient.keepAlive();
        sseClient.onClose(() -> this.closeRemoteSseConnection(sseClient));
        URI proxyUri = this.buildProxyUri(sseClient.ctx(), routing);
        try {
            User user = this.usersManager.getUser(sseClient.ctx());
            String token = this.vezhaIntegrationManager.getToken(user);
            Request.Builder requestBuilder = new Request.Builder().url(proxyUri.toURL());
            sseClient.ctx().headerMap().entrySet().stream().filter(it -> HeaderUtils.isAllowedHttpHeader((String)it.getKey())).forEach(it -> requestBuilder.header((String)it.getKey(), (String)it.getValue()));
            requestBuilder.header("Authorization", BEARER + token);
            EventSource.Factory eventSourceFactory = EventSources.createFactory(this.okHttpClient);
            EventSource eventSource = eventSourceFactory.newEventSource(requestBuilder.build(), new ProxySseEventListener(sseClient, proxyUri));
            this.activeSseClients.put(sseClient, eventSource);
        }
        catch (Exception e) {
            log.error("Remote connection error {}: {}", (Object)proxyUri, (Object)e.getMessage());
            sseClient.close();
        }
    }

    private void closeRemoteSseConnection(SseClient client) {
        EventSource eventSource = this.activeSseClients.remove(client);
        if (eventSource != null) {
            eventSource.cancel();
        }
    }

    private URI buildProxyUri(Context ctx, ForwardingRule rule) {
        try {
            URI vezhaApiUri = this.vezhaIntegrationManager.getVezhaApiUri();
            String finalPath = ForwardPathUtils.buildForwardPath(ctx.path(), rule.matchedPath(), rule.targetPath());
            String decodedPath = URLDecoder.decode(finalPath, StandardCharsets.UTF_8);
            URIBuilder uriBuilder = new URIBuilder().setScheme(vezhaApiUri.getScheme()).setHost(vezhaApiUri.getHost()).setPort(vezhaApiUri.getPort()).setPath(decodedPath);
            ctx.queryParamMap().forEach((key, values2) -> uriBuilder.addParameter((String)key, String.join((CharSequence)",", values2)));
            return uriBuilder.build();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Can't build proxy uri");
        }
    }

    private URI buildProxyUri(WsConnectContext ctx, ForwardingRule rule) {
        try {
            URI vezhaApiUri = this.vezhaIntegrationManager.getVezhaApiUri();
            String finalPath = ForwardPathUtils.buildForwardPath(ctx.getUpgradeCtx$javalin().path(), rule.matchedPath(), rule.targetPath());
            URIBuilder uriBuilder = new URIBuilder().setScheme(vezhaApiUri.getScheme().equalsIgnoreCase(HTTP) ? WS_PROTOCOL : WSS_PROTOCOL).setHost(vezhaApiUri.getHost()).setPort(vezhaApiUri.getPort()).setPath(finalPath);
            ctx.queryParamMap().forEach((key, values2) -> uriBuilder.addParameter((String)key, String.join((CharSequence)",", values2)));
            return uriBuilder.build();
        }
        catch (Exception e) {
            throw new RuntimeException("Can't build proxy ws URI", e);
        }
    }

    private boolean fileMustBeCached(String path) {
        return !filesExcludedFromCaching.contains(path);
    }

    private void setUpWebSocketDiagnostics(Javalin app) {
        if (!this.webSocketDiagnosticsScheduled.compareAndSet(false, true)) {
            return;
        }
        app.events(events -> events.serverStopped(this.webSocketDiagnosticsScheduler::shutdownNow));
        this.webSocketDiagnosticsScheduler.scheduleAtFixedRate(this::logWebSocketDiagnostics, 1L, 1L, TimeUnit.HOURS);
    }

    private void logWebSocketDiagnostics() {
        WebSocketMappingStats stats = ProxyController.calculateWebSocketMappingStats(this.webSocketMapping.values());
        log.info("{}, Proxy WebSocket mapping diagnostics: total={}, open={}, closed={}, halfClosed={}, pending={}", this.getClass().getSimpleName(), stats.total(), stats.open(), stats.closed(), stats.halfClosed(), stats.pending());
    }

    static WebSocketMappingStats calculateWebSocketMappingStats(Collection<ProxyWebSocketConnection> connections) {
        int total = 0;
        long open = 0L;
        long closed = 0L;
        long halfClosed = 0L;
        long pending = 0L;
        for (ProxyWebSocketConnection connection : connections) {
            ++total;
            WebSocket webSocket = connection.webSocket();
            if (webSocket == null) {
                ++pending;
                continue;
            }
            boolean inputClosed = webSocket.isInputClosed();
            boolean outputClosed = webSocket.isOutputClosed();
            if (inputClosed && outputClosed) {
                ++closed;
                continue;
            }
            if (inputClosed || outputClosed) {
                ++halfClosed;
                continue;
            }
            ++open;
        }
        return new WebSocketMappingStats(total, open, closed, halfClosed, pending);
    }

    public ProxyController(UsersManager usersManager, VezhaIntegrationManager vezhaIntegrationManager, HttpClient httpClient, OkHttpClient okHttpClient, String pluginName, List<ForwardingRule> httpForwardingRules, Map<String, String> wsRoutes, Map<String, String> sseRoutes) {
        this.usersManager = usersManager;
        this.vezhaIntegrationManager = vezhaIntegrationManager;
        this.httpClient = httpClient;
        this.okHttpClient = okHttpClient;
        this.pluginName = pluginName;
        this.httpForwardingRules = httpForwardingRules;
        this.wsRoutes = wsRoutes;
        this.sseRoutes = sseRoutes;
    }

    record WebSocketMappingStats(int total, long open, long closed, long halfClosed, long pending) {
    }
}

