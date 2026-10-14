/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.PrivateConfig;
import io.javalin.http.RequestLogger;
import io.javalin.websocket.WsConfig;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\t\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lio/javalin/config/RequestLoggerConfig;", "", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "http", "", "requestLogger", "Lio/javalin/http/RequestLogger;", "ws", "Ljava/util/function/Consumer;", "Lio/javalin/websocket/WsConfig;", "javalin"})
public final class RequestLoggerConfig {
    @NotNull
    private final PrivateConfig pvt;

    public RequestLoggerConfig(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
    }

    public final void http(@NotNull RequestLogger requestLogger) {
        Intrinsics.checkNotNullParameter(requestLogger, "requestLogger");
        this.pvt.requestLogger = requestLogger;
    }

    public final void ws(@NotNull Consumer<WsConfig> ws) {
        Intrinsics.checkNotNullParameter(ws, "ws");
        WsConfig logger2 = new WsConfig();
        ws.accept(logger2);
        this.pvt.wsLogger = logger2;
    }
}

