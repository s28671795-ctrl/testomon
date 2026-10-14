/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\n\u001a\u00020\tJ\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lio/javalin/websocket/WsBinaryMessageContext;", "Lio/javalin/websocket/WsContext;", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "data", "", "offset", "", "length", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;[BII)V", "javalin"})
public final class WsBinaryMessageContext
extends WsContext {
    @NotNull
    private final byte[] data;
    private final int offset;
    private final int length;

    public WsBinaryMessageContext(@NotNull String sessionId, @NotNull Session session, @NotNull byte[] data, int offset, int length) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(data, "data");
        super(sessionId, session);
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    @NotNull
    public final byte[] data() {
        return this.data;
    }

    public final int offset() {
        return this.offset;
    }

    public final int length() {
        return this.length;
    }
}

