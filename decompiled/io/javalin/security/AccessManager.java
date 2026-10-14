/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.security;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH&\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u000b\u00c0\u0006\u0001"}, d2={"Lio/javalin/security/AccessManager;", "", "manage", "", "handler", "Lio/javalin/http/Handler;", "ctx", "Lio/javalin/http/Context;", "routeRoles", "", "Lio/javalin/security/RouteRole;", "javalin"})
public interface AccessManager {
    public void manage(@NotNull Handler var1, @NotNull Context var2, @NotNull Set<? extends RouteRole> var3) throws Exception;
}

