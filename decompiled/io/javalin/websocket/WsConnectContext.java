/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/websocket/WsConnectContext;", "Lio/javalin/websocket/WsContext;", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;)V", "javalin"})
public final class WsConnectContext
extends WsContext {
    public WsConnectContext(@NotNull String sessionId, @NotNull Session session) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        super(sessionId, session);
    }
}

