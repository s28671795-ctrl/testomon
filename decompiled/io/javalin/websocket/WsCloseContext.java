/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\tJ\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0006\u0010\n\u001a\u00020\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/javalin/websocket/WsCloseContext;", "Lio/javalin/websocket/WsContext;", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "statusCode", "", "reason", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;ILjava/lang/String;)V", "status", "javalin"})
public final class WsCloseContext
extends WsContext {
    private final int statusCode;
    @Nullable
    private final String reason;

    public WsCloseContext(@NotNull String sessionId, @NotNull Session session, int statusCode, @Nullable String reason) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        super(sessionId, session);
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public final int status() {
        return this.statusCode;
    }

    @Nullable
    public final String reason() {
        return this.reason;
    }
}

