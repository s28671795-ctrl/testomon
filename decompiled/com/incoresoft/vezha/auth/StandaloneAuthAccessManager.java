/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.google.inject.Inject;
import com.incoresoft.Utils.MD5;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.sessions.api.ApiTokenManager;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

public class StandaloneAuthAccessManager
implements AccessManager {
    private final UsersManager usersManager;
    private final ApiTokenManager apiTokenManager;

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, Set<? extends RouteRole> permittedRoles) throws Exception {
        if (permittedRoles.isEmpty() || permittedRoles.contains(AppRole.ANYONE)) {
            handler.handle(ctx);
            return;
        }
        if (!this.isSessionValid(ctx) && !this.isAuthTokenValid(ctx)) {
            throw new UnauthorizedResponse();
        }
        if (!this.hasRequiredPermissions(ctx, permittedRoles)) {
            throw new ForbiddenResponse();
        }
        handler.handle(ctx);
    }

    private boolean hasRequiredPermissions(@NotNull Context ctx, @NotNull Set<? extends RouteRole> permittedRoles) {
        User user = this.usersManager.getUser(ctx);
        List<Permission> permissions = permittedRoles.stream().filter(Permission.class::isInstance).map(Permission.class::cast).toList();
        return user.hasAllPermissions(permissions);
    }

    private boolean isSessionValid(Context ctx) {
        boolean status = ctx.sessionAttribute("user_id") != null;
        String sessionHash = MD5.getMD5(String.format("%s", ctx.userAgent()));
        if (ctx.sessionAttribute("user_hash") == null) {
            ctx.sessionAttribute("user_hash", sessionHash);
        }
        if (sessionHash.equals(ctx.sessionAttribute("user_hash"))) {
            return status;
        }
        ctx.req().getSession().invalidate();
        return false;
    }

    private boolean isAuthTokenValid(Context ctx) {
        String authorization = ctx.header("Authorization");
        if (authorization == null) {
            return false;
        }
        if (!StringUtils.startsWith(authorization, "Bearer")) {
            return false;
        }
        String[] split = authorization.split(" ");
        if (split.length < 2 || TextUtils.isEmpty(split[1])) {
            return false;
        }
        return this.apiTokenManager.checkSession(split[1]);
    }

    @Inject
    public StandaloneAuthAccessManager(UsersManager usersManager, ApiTokenManager apiTokenManager) {
        this.usersManager = usersManager;
        this.apiTokenManager = apiTokenManager;
    }
}

