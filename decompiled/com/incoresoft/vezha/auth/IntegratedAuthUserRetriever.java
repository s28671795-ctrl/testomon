/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.google.inject.Inject;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.sessions.api.ApiTokenManager;
import com.incoresoft.vezha.auth.AuthUserRetriever;
import com.incoresoft.vezha.auth.JwtAuthClaims;
import com.incoresoft.vezha.auth.JwtTokenDecoder;
import com.incoresoft.vezha.models.users.UserImpl;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegratedAuthUserRetriever
implements AuthUserRetriever {
    private static final Logger log = LoggerFactory.getLogger(IntegratedAuthUserRetriever.class);
    private final JwtTokenDecoder jwtTokenDecoder;
    private final ApiTokenManager apiTokenManager;

    @Override
    public UserImpl retrieve(Context ctx, List<UserImpl> allUsers) {
        String authHeader = ctx.header("Authorization");
        return this.retrieveUser(authHeader, allUsers);
    }

    @Override
    public UserImpl retrieve(WsContext ctx, List<UserImpl> allUsers) {
        try {
            String authHeader = ctx.header("Authorization");
            return this.retrieveUser(authHeader, allUsers);
        }
        catch (Exception e) {
            log.error("Error while decoding JWT token", e);
            return null;
        }
    }

    private UserImpl retrieveUser(String authHeader, List<UserImpl> allUsers) {
        if (authHeader == null) {
            throw new UnauthorizedResponse();
        }
        String token = authHeader.split(" ")[1];
        if (this.apiTokenManager.checkSession(token)) {
            return this.retrieveFromApiToken(token, allUsers);
        }
        return this.retrieveFromJwtToken(token, allUsers);
    }

    private UserImpl retrieveFromApiToken(String token, List<UserImpl> allUsers) {
        Optional<ApiToken> session = this.apiTokenManager.checkApiKey(token);
        if (session.isEmpty()) {
            throw new UnauthorizedResponse();
        }
        int tokenOwnerId = session.orElseThrow().userId();
        return allUsers.stream().filter(user -> user.getId() == tokenOwnerId).findFirst().orElseThrow(UnauthorizedResponse::new);
    }

    private UserImpl retrieveFromJwtToken(String token, List<UserImpl> allUsers) {
        JwtAuthClaims claims;
        try {
            claims = this.jwtTokenDecoder.decode(token);
        }
        catch (Exception e) {
            throw new BadRequestResponse();
        }
        return allUsers.stream().filter(user -> user.getEmail().equals(claims.getEmail())).findFirst().orElseThrow(UnauthorizedResponse::new);
    }

    @Inject
    public IntegratedAuthUserRetriever(JwtTokenDecoder jwtTokenDecoder, ApiTokenManager apiTokenManager) {
        this.jwtTokenDecoder = jwtTokenDecoder;
        this.apiTokenManager = apiTokenManager;
    }
}

