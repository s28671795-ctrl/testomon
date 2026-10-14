/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsExceptionHandler;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0006j\u0002`\u00072\u0006\u0010\u000e\u001a\u00020\u000fR5\u0010\u0003\u001a&\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u00060\u0006j\u0002`\u00070\u0005\u0012\u0010\u0012\u000e\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0018\u00010\b0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0010"}, d2={"Lio/javalin/websocket/WsExceptionMapper;", "", "()V", "handlers", "", "Ljava/lang/Class;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "Lio/javalin/websocket/WsExceptionHandler;", "getHandlers", "()Ljava/util/Map;", "handle", "", "exception", "ctx", "Lio/javalin/websocket/WsContext;", "javalin"})
public final class WsExceptionMapper {
    @NotNull
    private final Map<Class<? extends Exception>, WsExceptionHandler<Exception>> handlers = new LinkedHashMap();

    @NotNull
    public final Map<Class<? extends Exception>, WsExceptionHandler<Exception>> getHandlers() {
        return this.handlers;
    }

    public final void handle(@NotNull Exception exception, @NotNull WsContext ctx) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        WsExceptionHandler<Exception> handler = Util.INSTANCE.findByClass(this.handlers, exception.getClass());
        if (handler != null) {
            handler.handle(exception, ctx);
        } else {
            JavalinLogger.warn("Uncaught exception in WebSocket handler", exception);
            ctx.session.close(1011, exception.getMessage());
        }
    }
}

