/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.config.RoutingConfig;
import io.javalin.routing.PathParser;
import io.javalin.security.RouteRole;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsHandlerType;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003JA\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u001a\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050$2\u0006\u0010\u0004\u001a\u00020\u0005J\t\u0010%\u001a\u00020&H\u00d6\u0001J\u000e\u0010'\u001a\u00020!2\u0006\u0010\u0004\u001a\u00020\u0005J\t\u0010(\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006)"}, d2={"Lio/javalin/websocket/WsEntry;", "", "type", "Lio/javalin/websocket/WsHandlerType;", "path", "", "routingConfig", "Lio/javalin/config/RoutingConfig;", "wsConfig", "Lio/javalin/websocket/WsConfig;", "roles", "", "Lio/javalin/security/RouteRole;", "(Lio/javalin/websocket/WsHandlerType;Ljava/lang/String;Lio/javalin/config/RoutingConfig;Lio/javalin/websocket/WsConfig;Ljava/util/Set;)V", "getPath", "()Ljava/lang/String;", "pathParser", "Lio/javalin/routing/PathParser;", "getRoles", "()Ljava/util/Set;", "getRoutingConfig", "()Lio/javalin/config/RoutingConfig;", "getType", "()Lio/javalin/websocket/WsHandlerType;", "getWsConfig", "()Lio/javalin/websocket/WsConfig;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "extractPathParams", "", "hashCode", "", "matches", "toString", "javalin"})
public final class WsEntry {
    @NotNull
    private final WsHandlerType type;
    @NotNull
    private final String path;
    @NotNull
    private final RoutingConfig routingConfig;
    @NotNull
    private final WsConfig wsConfig;
    @NotNull
    private final Set<RouteRole> roles;
    @NotNull
    private final PathParser pathParser;

    public WsEntry(@NotNull WsHandlerType type, @NotNull String path, @NotNull RoutingConfig routingConfig, @NotNull WsConfig wsConfig, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(routingConfig, "routingConfig");
        Intrinsics.checkNotNullParameter(wsConfig, "wsConfig");
        Intrinsics.checkNotNullParameter(roles, "roles");
        this.type = type;
        this.path = path;
        this.routingConfig = routingConfig;
        this.wsConfig = wsConfig;
        this.roles = roles;
        this.pathParser = new PathParser(this.path, this.routingConfig);
    }

    @NotNull
    public final WsHandlerType getType() {
        return this.type;
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    @NotNull
    public final RoutingConfig getRoutingConfig() {
        return this.routingConfig;
    }

    @NotNull
    public final WsConfig getWsConfig() {
        return this.wsConfig;
    }

    @NotNull
    public final Set<RouteRole> getRoles() {
        return this.roles;
    }

    public final boolean matches(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.pathParser.matches(path);
    }

    @NotNull
    public final Map<String, String> extractPathParams(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.pathParser.extractPathParams(path);
    }

    @NotNull
    public final WsHandlerType component1() {
        return this.type;
    }

    @NotNull
    public final String component2() {
        return this.path;
    }

    @NotNull
    public final RoutingConfig component3() {
        return this.routingConfig;
    }

    @NotNull
    public final WsConfig component4() {
        return this.wsConfig;
    }

    @NotNull
    public final Set<RouteRole> component5() {
        return this.roles;
    }

    @NotNull
    public final WsEntry copy(@NotNull WsHandlerType type, @NotNull String path, @NotNull RoutingConfig routingConfig, @NotNull WsConfig wsConfig, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(routingConfig, "routingConfig");
        Intrinsics.checkNotNullParameter(wsConfig, "wsConfig");
        Intrinsics.checkNotNullParameter(roles, "roles");
        return new WsEntry(type, path, routingConfig, wsConfig, roles);
    }

    public static /* synthetic */ WsEntry copy$default(WsEntry wsEntry, WsHandlerType wsHandlerType, String string, RoutingConfig routingConfig, WsConfig wsConfig, Set set, int n, Object object) {
        if ((n & 1) != 0) {
            wsHandlerType = wsEntry.type;
        }
        if ((n & 2) != 0) {
            string = wsEntry.path;
        }
        if ((n & 4) != 0) {
            routingConfig = wsEntry.routingConfig;
        }
        if ((n & 8) != 0) {
            wsConfig = wsEntry.wsConfig;
        }
        if ((n & 0x10) != 0) {
            set = wsEntry.roles;
        }
        return wsEntry.copy(wsHandlerType, string, routingConfig, wsConfig, set);
    }

    @NotNull
    public String toString() {
        return "WsEntry(type=" + this.type + ", path=" + this.path + ", routingConfig=" + this.routingConfig + ", wsConfig=" + this.wsConfig + ", roles=" + this.roles + ")";
    }

    public int hashCode() {
        int result = this.type.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + this.routingConfig.hashCode();
        result = result * 31 + this.wsConfig.hashCode();
        result = result * 31 + ((Object)this.roles).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WsEntry)) {
            return false;
        }
        WsEntry wsEntry = (WsEntry)other;
        if (this.type != wsEntry.type) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, wsEntry.path)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.routingConfig, wsEntry.routingConfig)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.wsConfig, wsEntry.wsConfig)) {
            return false;
        }
        return Intrinsics.areEqual(this.roles, wsEntry.roles);
    }
}

