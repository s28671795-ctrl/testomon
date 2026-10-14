/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.config.JavalinConfig;
import io.javalin.config.RoutingConfig;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.http.servlet.JavalinServlet;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.security.AccessManager;
import io.javalin.security.AccessManagerKt;
import io.javalin.security.RouteRole;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnection;
import io.javalin.websocket.WsEntry;
import io.javalin.websocket.WsExceptionMapper;
import io.javalin.websocket.WsHandlerType;
import io.javalin.websocket.WsPathMatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J2\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0014J\u0018\u0010(\u001a\u00020\u00142\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0014J\u0018\u0010-\u001a\u00020\u00142\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006."}, d2={"Lio/javalin/jetty/JavalinJettyServlet;", "Lorg/eclipse/jetty/websocket/server/JettyWebSocketServlet;", "cfg", "Lio/javalin/config/JavalinConfig;", "httpServlet", "Lio/javalin/http/servlet/JavalinServlet;", "(Lio/javalin/config/JavalinConfig;Lio/javalin/http/servlet/JavalinServlet;)V", "getCfg", "()Lio/javalin/config/JavalinConfig;", "setUpgradeAllowed", "Lio/javalin/http/Handler;", "wsExceptionMapper", "Lio/javalin/websocket/WsExceptionMapper;", "getWsExceptionMapper", "()Lio/javalin/websocket/WsExceptionMapper;", "wsPathMatcher", "Lio/javalin/websocket/WsPathMatcher;", "getWsPathMatcher", "()Lio/javalin/websocket/WsPathMatcher;", "addHandler", "", "handlerType", "Lio/javalin/websocket/WsHandlerType;", "path", "", "ws", "Ljava/util/function/Consumer;", "Lio/javalin/websocket/WsConfig;", "roles", "", "Lio/javalin/security/RouteRole;", "allowedByAccessManager", "", "entry", "Lio/javalin/websocket/WsEntry;", "ctx", "Lio/javalin/http/Context;", "configure", "factory", "Lorg/eclipse/jetty/websocket/server/JettyWebSocketServletFactory;", "service", "req", "Ljakarta/servlet/http/HttpServletRequest;", "res", "Ljakarta/servlet/http/HttpServletResponse;", "setWsProtocolHeader", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinJettyServlet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinJettyServlet.kt\nio/javalin/jetty/JavalinJettyServlet\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,97:1\n1#2:98\n1549#3:99\n1620#3,3:100\n759#4,2:103\n775#4,4:105\n*S KotlinDebug\n*F\n+ 1 JavalinJettyServlet.kt\nio/javalin/jetty/JavalinJettyServlet\n*L\n91#1:99\n91#1:100,3\n51#1:103,2\n51#1:105,4\n*E\n"})
public final class JavalinJettyServlet
extends JettyWebSocketServlet {
    @NotNull
    private final JavalinConfig cfg;
    @NotNull
    private final JavalinServlet httpServlet;
    @NotNull
    private final WsExceptionMapper wsExceptionMapper;
    @NotNull
    private final WsPathMatcher wsPathMatcher;
    @NotNull
    private final Handler setUpgradeAllowed;

    public JavalinJettyServlet(@NotNull JavalinConfig cfg, @NotNull JavalinServlet httpServlet) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        Intrinsics.checkNotNullParameter(httpServlet, "httpServlet");
        this.cfg = cfg;
        this.httpServlet = httpServlet;
        this.wsExceptionMapper = new WsExceptionMapper();
        this.wsPathMatcher = new WsPathMatcher();
        this.setUpgradeAllowed = JavalinJettyServlet::setUpgradeAllowed$lambda$3;
    }

    @NotNull
    public final JavalinConfig getCfg() {
        return this.cfg;
    }

    @NotNull
    public final WsExceptionMapper getWsExceptionMapper() {
        return this.wsExceptionMapper;
    }

    @NotNull
    public final WsPathMatcher getWsPathMatcher() {
        return this.wsPathMatcher;
    }

    /*
     * WARNING - void declaration
     */
    public final void addHandler(@NotNull WsHandlerType handlerType, @NotNull String path, @NotNull Consumer<WsConfig> ws, @NotNull Set<? extends RouteRole> roles) {
        void $this$addHandler_u24lambda_u240;
        WsConfig wsConfig;
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(ws, "ws");
        Intrinsics.checkNotNullParameter(roles, "roles");
        WsConfig wsConfig2 = wsConfig = new WsConfig();
        RoutingConfig routingConfig = this.cfg.routing;
        String string = path;
        WsHandlerType wsHandlerType = handlerType;
        WsPathMatcher wsPathMatcher = this.wsPathMatcher;
        boolean bl = false;
        ws.accept((WsConfig)$this$addHandler_u24lambda_u240);
        Unit unit = Unit.INSTANCE;
        Set<? extends RouteRole> set = roles;
        WsConfig wsConfig3 = wsConfig;
        RoutingConfig routingConfig2 = routingConfig;
        String string2 = string;
        WsHandlerType wsHandlerType2 = wsHandlerType;
        wsPathMatcher.add(new WsEntry(wsHandlerType2, string2, routingConfig2, wsConfig3, set));
    }

    @Override
    protected void configure(@NotNull JettyWebSocketServletFactory factory2) {
        Intrinsics.checkNotNullParameter(factory2, "factory");
        Consumer<JettyWebSocketServletFactory> consumer = this.cfg.pvt.wsFactoryConfig;
        if (consumer != null) {
            consumer.accept(factory2);
        }
        factory2.setCreator((arg_0, arg_1) -> JavalinJettyServlet.configure$lambda$2(this, arg_0, arg_1));
    }

    @Override
    protected void service(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res) {
        Map<String, String> map;
        Intrinsics.checkNotNullParameter(req, "req");
        Intrinsics.checkNotNullParameter(res, "res");
        if (req.getHeader("Sec-WebSocket-Key") == null) {
            this.httpServlet.service((ServletRequest)req, (ServletResponse)res);
            return;
        }
        String string = req.getRequestURI();
        Intrinsics.checkNotNullExpressionValue(string, "req.requestURI");
        String string2 = req.getContextPath();
        Intrinsics.checkNotNullExpressionValue(string2, "req.contextPath");
        String requestUri = StringsKt.removePrefix(string, (CharSequence)string2);
        WsEntry wsEntry = this.wsPathMatcher.findEndpointHandlerEntry(requestUri);
        if (wsEntry == null) {
            res.sendError(404, "WebSocket handler not found");
            return;
        }
        WsEntry entry = wsEntry;
        JavalinConfig javalinConfig = this.cfg;
        String string3 = entry.getPath();
        JavalinServletContext upgradeContext = new JavalinServletContext(javalinConfig, null, false, null, req, res, null, null, null, null, string3, map = entry.extractPathParams(requestUri), null, null, null, 29646, null);
        if (!this.allowedByAccessManager(entry, upgradeContext)) {
            res.sendError(HttpStatus.UNAUTHORIZED.getCode(), HttpStatus.UNAUTHORIZED.getMessage());
            return;
        }
        req.setAttribute("javalin-ws-upgrade-context", upgradeContext);
        this.setWsProtocolHeader(req, res);
        super.service(req, res);
    }

    private final boolean allowedByAccessManager(WsEntry entry, Context ctx) {
        boolean bl;
        try {
            if (this.cfg.pvt.accessManager != null) {
                AccessManager accessManager = this.cfg.pvt.accessManager;
                if (accessManager != null) {
                    accessManager.manage(this.setUpgradeAllowed, ctx, entry.getRoles());
                }
            } else {
                if (!((Collection)entry.getRoles()).isEmpty()) {
                    throw AccessManagerKt.accessManagerNotConfiguredException();
                }
                this.setUpgradeAllowed.handle(ctx);
            }
            bl = Intrinsics.areEqual(ctx.attribute("javalin-ws-upgrade-allowed"), true);
        }
        catch (Exception e) {
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final void setWsProtocolHeader(HttpServletRequest req, HttpServletResponse res) {
        Object v2;
        block4: {
            void $this$mapTo$iv$iv;
            String string = req.getHeader("Sec-WebSocket-Protocol");
            if (string == null) {
                return;
            }
            String wsProtocolHeader = string;
            char[] cArray = new char[]{','};
            Iterable $this$map$iv = StringsKt.split$default((CharSequence)wsProtocolHeader, cArray, false, 0, 6, null);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                String string2 = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(((Object)StringsKt.trim((CharSequence)it)).toString());
            }
            Iterable iterable2 = (List)destination$iv$iv;
            for (Object e : iterable2) {
                String it = (String)e;
                boolean bl = false;
                boolean bl2 = !StringsKt.isBlank(it);
                if (!bl2) continue;
                v2 = e;
                break block4;
            }
            v2 = null;
        }
        String string = v2;
        if (string == null) {
            return;
        }
        String firstProtocol = string;
        res.setHeader("Sec-WebSocket-Protocol", firstProtocol);
    }

    /*
     * WARNING - void declaration
     */
    private static final Object configure$lambda$2(JavalinJettyServlet this$0, JettyServerUpgradeRequest req, JettyServerUpgradeResponse jettyServerUpgradeResponse) {
        Map map;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Object object = req.getHttpServletRequest().getAttribute("javalin-ws-upgrade-context");
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type io.javalin.http.servlet.JavalinServletContext");
        JavalinServletContext preUpgradeContext = (JavalinServletContext)object;
        HttpServletRequest httpServletRequest = req.getHttpServletRequest();
        HttpServletRequest httpServletRequest2 = req.getHttpServletRequest();
        Intrinsics.checkNotNullExpressionValue(httpServletRequest2, "req.httpServletRequest");
        httpServletRequest.setAttribute("javalin-ws-upgrade-context", preUpgradeContext.changeBaseRequest(httpServletRequest2));
        Object object2 = req.getSession();
        Session session = object2 instanceof Session ? (Session)object2 : null;
        HttpServletRequest httpServletRequest3 = req.getHttpServletRequest();
        String string = "javalin-ws-upgrade-http-session";
        Object object3 = session;
        if (object3 != null && (object3 = ((Session)object3).getAttributeNames()) != null && (object3 = SequencesKt.asSequence(CollectionsKt.iterator(object3))) != null) {
            Map map2;
            void $this$associateWith$iv;
            Sequence sequence2 = object3;
            String string2 = string;
            HttpServletRequest httpServletRequest4 = httpServletRequest3;
            boolean $i$f$associateWith = false;
            LinkedHashMap result$iv = new LinkedHashMap();
            void $this$associateWithTo$iv$iv = $this$associateWith$iv;
            boolean $i$f$associateWithTo = false;
            for (Object element$iv$iv : $this$associateWithTo$iv$iv) {
                void it;
                String string3 = (String)element$iv$iv;
                Object t2 = element$iv$iv;
                map2 = result$iv;
                boolean bl = false;
                Object object4 = session.getAttribute((String)it);
                map2.put(t2, object4);
            }
            map2 = result$iv;
            httpServletRequest3 = httpServletRequest4;
            string = string2;
            map = map2;
        } else {
            map = null;
        }
        httpServletRequest3.setAttribute(string, map);
        return new WsConnection(this$0.wsPathMatcher, this$0.wsExceptionMapper, this$0.cfg.pvt.wsLogger);
    }

    private static final void setUpgradeAllowed$lambda$3(Context it) {
        Intrinsics.checkNotNullParameter(it, "it");
        it.attribute("javalin-ws-upgrade-allowed", true);
    }
}

