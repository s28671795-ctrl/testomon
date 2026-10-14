/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsBinaryMessageHandler;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsConnectionKt;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsEntry;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsErrorHandler;
import io.javalin.websocket.WsExceptionMapper;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import io.javalin.websocket.WsPathMatcher;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@WebSocket
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\"\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000eH\u0007J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u001a\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J(\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0016H\u0007J\u0018\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u000eH\u0007J$\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00120&H\u0002J$\u0010(\u001a\u00020\u00122\u0006\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00120&H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006)"}, d2={"Lio/javalin/websocket/WsConnection;", "", "matcher", "Lio/javalin/websocket/WsPathMatcher;", "exceptionMapper", "Lio/javalin/websocket/WsExceptionMapper;", "wsLogger", "Lio/javalin/websocket/WsConfig;", "(Lio/javalin/websocket/WsPathMatcher;Lio/javalin/websocket/WsExceptionMapper;Lio/javalin/websocket/WsConfig;)V", "getExceptionMapper", "()Lio/javalin/websocket/WsExceptionMapper;", "getMatcher", "()Lio/javalin/websocket/WsPathMatcher;", "sessionId", "", "getWsLogger", "()Lio/javalin/websocket/WsConfig;", "onClose", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "statusCode", "", "reason", "onConnect", "onError", "throwable", "", "onMessage", "buffer", "", "offset", "length", "message", "tryAfterHandlers", "ctx", "Lio/javalin/websocket/WsContext;", "handle", "Lkotlin/Function1;", "Lio/javalin/websocket/WsEntry;", "tryBeforeAndEndpointHandlers", "javalin"})
@SourceDebugExtension(value={"SMAP\nWsConnection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WsConnection.kt\nio/javalin/websocket/WsConnection\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1855#2,2:95\n1855#2,2:98\n1#3:97\n*S KotlinDebug\n*F\n+ 1 WsConnection.kt\nio/javalin/websocket/WsConnection\n*L\n71#1:95,2\n81#1:98,2\n*E\n"})
public final class WsConnection {
    @NotNull
    private final WsPathMatcher matcher;
    @NotNull
    private final WsExceptionMapper exceptionMapper;
    @Nullable
    private final WsConfig wsLogger;
    @NotNull
    private final String sessionId;

    public WsConnection(@NotNull WsPathMatcher matcher, @NotNull WsExceptionMapper exceptionMapper, @Nullable WsConfig wsLogger) {
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        Intrinsics.checkNotNullParameter(exceptionMapper, "exceptionMapper");
        this.matcher = matcher;
        this.exceptionMapper = exceptionMapper;
        this.wsLogger = wsLogger;
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "randomUUID().toString()");
        this.sessionId = string;
    }

    @NotNull
    public final WsPathMatcher getMatcher() {
        return this.matcher;
    }

    @NotNull
    public final WsExceptionMapper getExceptionMapper() {
        return this.exceptionMapper;
    }

    @Nullable
    public final WsConfig getWsLogger() {
        return this.wsLogger;
    }

    @OnWebSocketConnect
    public final void onConnect(@NotNull Session session) {
        block0: {
            Intrinsics.checkNotNullParameter(session, "session");
            WsConnectContext ctx = new WsConnectContext(this.sessionId, session);
            this.tryBeforeAndEndpointHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsConnectContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsConnectHandler wsConnectHandler = it.getWsConfig().wsConnectHandler;
                        if (wsConnectHandler == null) break block0;
                        wsConnectHandler.handleConnect(this.$ctx);
                    }
                }
            });
            this.tryAfterHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsConnectContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsConnectHandler wsConnectHandler = it.getWsConfig().wsConnectHandler;
                        if (wsConnectHandler == null) break block0;
                        wsConnectHandler.handleConnect(this.$ctx);
                    }
                }
            });
            Object object = this.wsLogger;
            if (object == null || (object = ((WsConfig)object).wsConnectHandler) == null) break block0;
            object.handleConnect(ctx);
        }
    }

    @OnWebSocketMessage
    public final void onMessage(@NotNull Session session, @NotNull String message) {
        block0: {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(message, "message");
            WsMessageContext ctx = new WsMessageContext(this.sessionId, session, message);
            this.tryBeforeAndEndpointHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsMessageContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsMessageHandler wsMessageHandler = it.getWsConfig().wsMessageHandler;
                        if (wsMessageHandler == null) break block0;
                        wsMessageHandler.handleMessage(this.$ctx);
                    }
                }
            });
            this.tryAfterHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsMessageContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsMessageHandler wsMessageHandler = it.getWsConfig().wsMessageHandler;
                        if (wsMessageHandler == null) break block0;
                        wsMessageHandler.handleMessage(this.$ctx);
                    }
                }
            });
            Object object = this.wsLogger;
            if (object == null || (object = ((WsConfig)object).wsMessageHandler) == null) break block0;
            object.handleMessage(ctx);
        }
    }

    @OnWebSocketMessage
    public final void onMessage(@NotNull Session session, @NotNull byte[] buffer, int offset, int length) {
        block0: {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(buffer, "buffer");
            WsBinaryMessageContext ctx = new WsBinaryMessageContext(this.sessionId, session, buffer, offset, length);
            this.tryBeforeAndEndpointHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsBinaryMessageContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsBinaryMessageHandler wsBinaryMessageHandler = it.getWsConfig().wsBinaryMessageHandler;
                        if (wsBinaryMessageHandler == null) break block0;
                        wsBinaryMessageHandler.handleBinaryMessage(this.$ctx);
                    }
                }
            });
            this.tryAfterHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsBinaryMessageContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsBinaryMessageHandler wsBinaryMessageHandler = it.getWsConfig().wsBinaryMessageHandler;
                        if (wsBinaryMessageHandler == null) break block0;
                        wsBinaryMessageHandler.handleBinaryMessage(this.$ctx);
                    }
                }
            });
            Object object = this.wsLogger;
            if (object == null || (object = ((WsConfig)object).wsBinaryMessageHandler) == null) break block0;
            object.handleBinaryMessage(ctx);
        }
    }

    @OnWebSocketClose
    public final void onClose(@NotNull Session session, int statusCode, @Nullable String reason) {
        Intrinsics.checkNotNullParameter(session, "session");
        WsCloseContext ctx = new WsCloseContext(this.sessionId, session, statusCode, reason);
        this.tryBeforeAndEndpointHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
            final /* synthetic */ WsCloseContext $ctx;
            {
                this.$ctx = $ctx;
                super(1);
            }

            public final void invoke(@NotNull WsEntry it) {
                block0: {
                    Intrinsics.checkNotNullParameter(it, "it");
                    WsCloseHandler wsCloseHandler = it.getWsConfig().wsCloseHandler;
                    if (wsCloseHandler == null) break block0;
                    wsCloseHandler.handleClose(this.$ctx);
                }
            }
        });
        this.tryAfterHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
            final /* synthetic */ WsCloseContext $ctx;
            {
                this.$ctx = $ctx;
                super(1);
            }

            public final void invoke(@NotNull WsEntry it) {
                block0: {
                    Intrinsics.checkNotNullParameter(it, "it");
                    WsCloseHandler wsCloseHandler = it.getWsConfig().wsCloseHandler;
                    if (wsCloseHandler == null) break block0;
                    wsCloseHandler.handleClose(this.$ctx);
                }
            }
        });
        Object object = this.wsLogger;
        if (object != null && (object = ((WsConfig)object).wsCloseHandler) != null) {
            object.handleClose(ctx);
        }
        ctx.disableAutomaticPings();
    }

    @OnWebSocketError
    public final void onError(@NotNull Session session, @Nullable Throwable throwable) {
        block0: {
            Intrinsics.checkNotNullParameter(session, "session");
            WsErrorContext ctx = new WsErrorContext(this.sessionId, session, throwable);
            this.tryBeforeAndEndpointHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsErrorContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsErrorHandler wsErrorHandler = it.getWsConfig().wsErrorHandler;
                        if (wsErrorHandler == null) break block0;
                        wsErrorHandler.handleError(this.$ctx);
                    }
                }
            });
            this.tryAfterHandlers(ctx, (Function1<? super WsEntry, Unit>)new Function1<WsEntry, Unit>(ctx){
                final /* synthetic */ WsErrorContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(1);
                }

                public final void invoke(@NotNull WsEntry it) {
                    block0: {
                        Intrinsics.checkNotNullParameter(it, "it");
                        WsErrorHandler wsErrorHandler = it.getWsConfig().wsErrorHandler;
                        if (wsErrorHandler == null) break block0;
                        wsErrorHandler.handleError(this.$ctx);
                    }
                }
            });
            Object object = this.wsLogger;
            if (object == null || (object = ((WsConfig)object).wsErrorHandler) == null) break block0;
            object.handleError(ctx);
        }
    }

    private final void tryBeforeAndEndpointHandlers(WsContext ctx, Function1<? super WsEntry, Unit> handle2) {
        String requestUri = WsConnectionKt.access$uriNoContextPath(ctx.session);
        try {
            Iterable $this$forEach$iv = this.matcher.findBeforeHandlerEntries(requestUri);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                WsEntry it = (WsEntry)element$iv;
                boolean bl = false;
                handle2.invoke(it);
            }
            WsEntry wsEntry = this.matcher.findEndpointHandlerEntry(requestUri);
            Intrinsics.checkNotNull(wsEntry);
            WsEntry it = wsEntry;
            boolean bl = false;
            handle2.invoke(it);
        }
        catch (Exception e) {
            this.exceptionMapper.handle(e, ctx);
        }
    }

    private final void tryAfterHandlers(WsContext ctx, Function1<? super WsEntry, Unit> handle2) {
        String requestUri = WsConnectionKt.access$uriNoContextPath(ctx.session);
        try {
            Iterable $this$forEach$iv = this.matcher.findAfterHandlerEntries(requestUri);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                WsEntry it = (WsEntry)element$iv;
                boolean bl = false;
                handle2.invoke(it);
            }
        }
        catch (Exception e) {
            this.exceptionMapper.handle(e, ctx);
        }
    }
}

