/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.json.JsonMapperKt;
import io.javalin.websocket.WsContext;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.TypesJVMKt;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\u0006\u001a\u00020\u0003J\u001a\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\nH\u0086\b\u00a2\u0006\u0002\u0010\u000bJ\u001f\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\t0\r\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/javalin/websocket/WsMessageContext;", "Lio/javalin/websocket/WsContext;", "sessionId", "", "session", "Lorg/eclipse/jetty/websocket/api/Session;", "message", "(Ljava/lang/String;Lorg/eclipse/jetty/websocket/api/Session;Ljava/lang/String;)V", "messageAsClass", "T", "", "()Ljava/lang/Object;", "clazz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "type", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;", "javalin"})
public final class WsMessageContext
extends WsContext {
    @NotNull
    private final String message;

    public WsMessageContext(@NotNull String sessionId, @NotNull Session session, @NotNull String message) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(message, "message");
        super(sessionId, session);
        this.message = message;
    }

    @NotNull
    public final String message() {
        return this.message;
    }

    public final <T> T messageAsClass(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return JsonMapperKt.jsonMapper(this.getUpgradeCtx$javalin()).fromJsonString(this.message, type);
    }

    public final <T> T messageAsClass(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.messageAsClass((Type)clazz);
    }

    public final /* synthetic */ <T> T messageAsClass() {
        boolean $i$f$messageAsClass = false;
        Intrinsics.reifiedOperationMarker(6, "T");
        return this.messageAsClass(TypesJVMKt.getJavaType(null));
    }
}

