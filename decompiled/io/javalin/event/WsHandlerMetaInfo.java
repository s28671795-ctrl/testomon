/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

import io.javalin.security.RouteRole;
import io.javalin.websocket.WsHandlerType;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0001\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0001H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, d2={"Lio/javalin/event/WsHandlerMetaInfo;", "", "handlerType", "Lio/javalin/websocket/WsHandlerType;", "path", "", "handler", "roles", "", "Lio/javalin/security/RouteRole;", "(Lio/javalin/websocket/WsHandlerType;Ljava/lang/String;Ljava/lang/Object;Ljava/util/Set;)V", "getHandler", "()Ljava/lang/Object;", "getHandlerType", "()Lio/javalin/websocket/WsHandlerType;", "getPath", "()Ljava/lang/String;", "getRoles", "()Ljava/util/Set;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "javalin"})
public final class WsHandlerMetaInfo {
    @NotNull
    private final WsHandlerType handlerType;
    @NotNull
    private final String path;
    @NotNull
    private final Object handler;
    @NotNull
    private final Set<RouteRole> roles;

    public WsHandlerMetaInfo(@NotNull WsHandlerType handlerType, @NotNull String path, @NotNull Object handler, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(roles, "roles");
        this.handlerType = handlerType;
        this.path = path;
        this.handler = handler;
        this.roles = roles;
    }

    @NotNull
    public final WsHandlerType getHandlerType() {
        return this.handlerType;
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    @NotNull
    public final Object getHandler() {
        return this.handler;
    }

    @NotNull
    public final Set<RouteRole> getRoles() {
        return this.roles;
    }

    @NotNull
    public final WsHandlerType component1() {
        return this.handlerType;
    }

    @NotNull
    public final String component2() {
        return this.path;
    }

    @NotNull
    public final Object component3() {
        return this.handler;
    }

    @NotNull
    public final Set<RouteRole> component4() {
        return this.roles;
    }

    @NotNull
    public final WsHandlerMetaInfo copy(@NotNull WsHandlerType handlerType, @NotNull String path, @NotNull Object handler, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(roles, "roles");
        return new WsHandlerMetaInfo(handlerType, path, handler, roles);
    }

    public static /* synthetic */ WsHandlerMetaInfo copy$default(WsHandlerMetaInfo wsHandlerMetaInfo, WsHandlerType wsHandlerType, String string, Object object, Set set, int n, Object object2) {
        if ((n & 1) != 0) {
            wsHandlerType = wsHandlerMetaInfo.handlerType;
        }
        if ((n & 2) != 0) {
            string = wsHandlerMetaInfo.path;
        }
        if ((n & 4) != 0) {
            object = wsHandlerMetaInfo.handler;
        }
        if ((n & 8) != 0) {
            set = wsHandlerMetaInfo.roles;
        }
        return wsHandlerMetaInfo.copy(wsHandlerType, string, object, set);
    }

    @NotNull
    public String toString() {
        return "WsHandlerMetaInfo(handlerType=" + this.handlerType + ", path=" + this.path + ", handler=" + this.handler + ", roles=" + this.roles + ")";
    }

    public int hashCode() {
        int result = this.handlerType.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + this.handler.hashCode();
        result = result * 31 + ((Object)this.roles).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WsHandlerMetaInfo)) {
            return false;
        }
        WsHandlerMetaInfo wsHandlerMetaInfo = (WsHandlerMetaInfo)other;
        if (this.handlerType != wsHandlerMetaInfo.handlerType) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, wsHandlerMetaInfo.path)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.handler, wsHandlerMetaInfo.handler)) {
            return false;
        }
        return Intrinsics.areEqual(this.roles, wsHandlerMetaInfo.roles);
    }
}

