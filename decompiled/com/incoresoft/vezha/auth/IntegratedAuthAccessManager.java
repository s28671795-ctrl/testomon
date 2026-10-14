/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.sessions.api.ApiTokenManager;
import com.incoresoft.vezha.auth.JwtAuthClaims;
import com.incoresoft.vezha.auth.JwtTokenDecoder;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

public class IntegratedAuthAccessManager
implements AccessManager {
    private final JwtTokenDecoder jwtTokenDecoder;
    private final UsersManagerImpl usersManager;
    private final ApiTokenManager apiTokenManager;

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> permittedRoles) throws Exception {
        if (permittedRoles.isEmpty() || permittedRoles.contains(AppRole.ANYONE)) {
            handler.handle(ctx);
            return;
        }
        String token = this.getAuthToken(ctx);
        if (this.apiTokenManager.checkSession(token)) {
            this.validateUserByApiToken(token, permittedRoles);
        } else {
            this.validateUserByJwtToken(token, permittedRoles);
        }
        handler.handle(ctx);
    }

    private String getAuthToken(Context ctx) {
        String authorization = ctx.header("Authorization");
        if (authorization == null) {
            throw new UnauthorizedResponse();
        }
        if (!StringUtils.startsWith(authorization, "Bearer")) {
            throw new UnauthorizedResponse("Token should be preceded by \"Bearer\" keyword");
        }
        String[] split = authorization.split(" ");
        String token = split[1];
        if (split.length != 2 || TextUtils.isEmpty(token)) {
            throw new UnauthorizedResponse("Token is blank");
        }
        return token;
    }

    private void validateUserByApiToken(String token, Set<? extends RouteRole> permittedRoles) {
        Optional<ApiToken> session = this.apiTokenManager.checkApiKey(token);
        if (session.isEmpty()) {
            throw new UnauthorizedResponse();
        }
        User user = this.usersManager.get(session.orElseThrow().userId());
        if (user == null) {
            throw new BadRequestResponse("User by token does not exist");
        }
        if (!this.hasRequiredPermissions(user, permittedRoles)) {
            throw new ForbiddenResponse();
        }
    }

    private void validateUserByJwtToken(String token, Set<? extends RouteRole> permittedRoles) {
        JwtAuthClaims claims = this.jwtTokenDecoder.decode(token);
        Optional<UserImpl> userOptional = this.usersManager.findByEmail(claims.getEmail());
        if (userOptional.isEmpty()) {
            throw new BadRequestResponse("User by token does not exist");
        }
        if (Instant.now().isAfter(claims.getExpiration())) {
            throw new UnauthorizedResponse("Token is expired");
        }
        if (!this.hasRequiredPermissions(userOptional.get(), permittedRoles)) {
            throw new ForbiddenResponse();
        }
    }

    private boolean hasRequiredPermissions(User user, @NotNull Set<? extends RouteRole> permittedRoles) {
        List<Permission> permissions = permittedRoles.stream().filter(Permission.class::isInstance).map(Permission.class::cast).toList();
        return user.hasAllPermissions(permissions);
    }

    @Inject
    public IntegratedAuthAccessManager(JwtTokenDecoder jwtTokenDecoder, UsersManagerImpl usersManager, ApiTokenManager apiTokenManager) {
        this.jwtTokenDecoder = jwtTokenDecoder;
        this.usersManager = usersManager;
        this.apiTokenManager = apiTokenManager;
    }
}

