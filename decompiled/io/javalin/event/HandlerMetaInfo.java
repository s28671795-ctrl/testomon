/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

import io.javalin.http.HandlerType;
import io.javalin.security.RouteRole;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0001\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0001H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, d2={"Lio/javalin/event/HandlerMetaInfo;", "", "httpMethod", "Lio/javalin/http/HandlerType;", "path", "", "handler", "roles", "", "Lio/javalin/security/RouteRole;", "(Lio/javalin/http/HandlerType;Ljava/lang/String;Ljava/lang/Object;Ljava/util/Set;)V", "getHandler", "()Ljava/lang/Object;", "getHttpMethod", "()Lio/javalin/http/HandlerType;", "getPath", "()Ljava/lang/String;", "getRoles", "()Ljava/util/Set;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "javalin"})
public final class HandlerMetaInfo {
    @NotNull
    private final HandlerType httpMethod;
    @NotNull
    private final String path;
    @NotNull
    private final Object handler;
    @NotNull
    private final Set<RouteRole> roles;

    public HandlerMetaInfo(@NotNull HandlerType httpMethod, @NotNull String path, @NotNull Object handler, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)httpMethod, "httpMethod");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(roles, "roles");
        this.httpMethod = httpMethod;
        this.path = path;
        this.handler = handler;
        this.roles = roles;
    }

    @NotNull
    public final HandlerType getHttpMethod() {
        return this.httpMethod;
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
    public final HandlerType component1() {
        return this.httpMethod;
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
    public final HandlerMetaInfo copy(@NotNull HandlerType httpMethod, @NotNull String path, @NotNull Object handler, @NotNull Set<? extends RouteRole> roles) {
        Intrinsics.checkNotNullParameter((Object)httpMethod, "httpMethod");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(roles, "roles");
        return new HandlerMetaInfo(httpMethod, path, handler, roles);
    }

    public static /* synthetic */ HandlerMetaInfo copy$default(HandlerMetaInfo handlerMetaInfo, HandlerType handlerType, String string, Object object, Set set, int n, Object object2) {
        if ((n & 1) != 0) {
            handlerType = handlerMetaInfo.httpMethod;
        }
        if ((n & 2) != 0) {
            string = handlerMetaInfo.path;
        }
        if ((n & 4) != 0) {
            object = handlerMetaInfo.handler;
        }
        if ((n & 8) != 0) {
            set = handlerMetaInfo.roles;
        }
        return handlerMetaInfo.copy(handlerType, string, object, set);
    }

    @NotNull
    public String toString() {
        return "HandlerMetaInfo(httpMethod=" + this.httpMethod + ", path=" + this.path + ", handler=" + this.handler + ", roles=" + this.roles + ")";
    }

    public int hashCode() {
        int result = this.httpMethod.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + this.handler.hashCode();
        result = result * 31 + ((Object)this.roles).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HandlerMetaInfo)) {
            return false;
        }
        HandlerMetaInfo handlerMetaInfo = (HandlerMetaInfo)other;
        if (this.httpMethod != handlerMetaInfo.httpMethod) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, handlerMetaInfo.path)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.handler, handlerMetaInfo.handler)) {
            return false;
        }
        return Intrinsics.areEqual(this.roles, handlerMetaInfo.roles);
    }
}

