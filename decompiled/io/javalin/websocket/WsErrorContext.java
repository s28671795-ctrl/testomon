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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/websocket/WsErrorContext;", "Lio/javalin/websocket/WsContext;", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "error", "", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;Ljava/lang/Throwable;)V", "javalin"})
public final class WsErrorContext
extends WsContext {
    @Nullable
    private final Throwable error;

    public WsErrorContext(@NotNull String sessionId, @NotNull Session session, @Nullable Throwable error) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        super(sessionId, session);
        this.error = error;
    }

    @Nullable
    public final Throwable error() {
        return this.error;
    }
}

