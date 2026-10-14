/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.config.RoutingConfig;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.routing.PathParser;
import io.javalin.security.RouteRole;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\fH\u00c6\u0003JA\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u001a\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050$2\u0006\u0010%\u001a\u00020\u0005J\u0016\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010%\u001a\u00020\u0005J\t\u0010*\u001a\u00020+H\u00d6\u0001J\u000e\u0010,\u001a\u00020!2\u0006\u0010%\u001a\u00020\u0005J\t\u0010-\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006."}, d2={"Lio/javalin/routing/HandlerEntry;", "", "type", "Lio/javalin/http/HandlerType;", "path", "", "routingConfig", "Lio/javalin/config/RoutingConfig;", "roles", "", "Lio/javalin/security/RouteRole;", "handler", "Lio/javalin/http/Handler;", "(Lio/javalin/http/HandlerType;Ljava/lang/String;Lio/javalin/config/RoutingConfig;Ljava/util/Set;Lio/javalin/http/Handler;)V", "getHandler", "()Lio/javalin/http/Handler;", "getPath", "()Ljava/lang/String;", "pathParser", "Lio/javalin/routing/PathParser;", "getRoles", "()Ljava/util/Set;", "getRoutingConfig", "()Lio/javalin/config/RoutingConfig;", "getType", "()Lio/javalin/http/HandlerType;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "extractPathParams", "", "requestUri", "handle", "", "ctx", "Lio/javalin/http/servlet/JavalinServletContext;", "hashCode", "", "matches", "toString", "javalin"})
public final class HandlerEntry {
    @NotNull
    private final HandlerType type;
    @NotNull
    private final String path;
    @NotNull
    private final RoutingConfig routingConfig;
    @NotNull
    private final Set<RouteRole> roles;
    @NotNull
    private final Handler handler;
    @NotNull
    private final PathParser pathParser;

    public HandlerEntry(@NotNull HandlerType type, @NotNull String path, @NotNull RoutingConfig routingConfig, @NotNull Set<? extends RouteRole> roles, @NotNull Handler handler) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(routingConfig, "routingConfig");
        Intrinsics.checkNotNullParameter(roles, "roles");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.type = type;
        this.path = path;
        this.routingConfig = routingConfig;
        this.roles = roles;
        this.handler = handler;
        this.pathParser = new PathParser(this.path, this.routingConfig);
    }

    @NotNull
    public final HandlerType getType() {
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
    public final Set<RouteRole> getRoles() {
        return this.roles;
    }

    @NotNull
    public final Handler getHandler() {
        return this.handler;
    }

    public final boolean matches(@NotNull String requestUri) {
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        return this.pathParser.matches(requestUri);
    }

    @NotNull
    public final Map<String, String> extractPathParams(@NotNull String requestUri) {
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        return this.pathParser.extractPathParams(requestUri);
    }

    public final void handle(@NotNull JavalinServletContext ctx, @NotNull String requestUri) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        this.handler.handle(ctx.update(this, requestUri));
    }

    @NotNull
    public final HandlerType component1() {
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
    public final Set<RouteRole> component4() {
        return this.roles;
    }

    @NotNull
    public final Handler component5() {
        return this.handler;
    }

    @NotNull
    public final HandlerEntry copy(@NotNull HandlerType type, @NotNull String path, @NotNull RoutingConfig routingConfig, @NotNull Set<? extends RouteRole> roles, @NotNull Handler handler) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(routingConfig, "routingConfig");
        Intrinsics.checkNotNullParameter(roles, "roles");
        Intrinsics.checkNotNullParameter(handler, "handler");
        return new HandlerEntry(type, path, routingConfig, roles, handler);
    }

    public static /* synthetic */ HandlerEntry copy$default(HandlerEntry handlerEntry, HandlerType handlerType, String string, RoutingConfig routingConfig, Set set, Handler handler, int n, Object object) {
        if ((n & 1) != 0) {
            handlerType = handlerEntry.type;
        }
        if ((n & 2) != 0) {
            string = handlerEntry.path;
        }
        if ((n & 4) != 0) {
            routingConfig = handlerEntry.routingConfig;
        }
        if ((n & 8) != 0) {
            set = handlerEntry.roles;
        }
        if ((n & 0x10) != 0) {
            handler = handlerEntry.handler;
        }
        return handlerEntry.copy(handlerType, string, routingConfig, set, handler);
    }

    @NotNull
    public String toString() {
        return "HandlerEntry(type=" + this.type + ", path=" + this.path + ", routingConfig=" + this.routingConfig + ", roles=" + this.roles + ", handler=" + this.handler + ")";
    }

    public int hashCode() {
        int result = this.type.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + this.routingConfig.hashCode();
        result = result * 31 + ((Object)this.roles).hashCode();
        result = result * 31 + this.handler.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HandlerEntry)) {
            return false;
        }
        HandlerEntry handlerEntry = (HandlerEntry)other;
        if (this.type != handlerEntry.type) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, handlerEntry.path)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.routingConfig, handlerEntry.routingConfig)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.roles, handlerEntry.roles)) {
            return false;
        }
        return Intrinsics.areEqual(this.handler, handlerEntry.handler);
    }
}

