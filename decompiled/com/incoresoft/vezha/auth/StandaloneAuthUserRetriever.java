/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.google.inject.Inject;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.sessions.api.ApiTokenManager;
import com.incoresoft.vezha.auth.AuthUserRetriever;
import com.incoresoft.vezha.models.users.UserImpl;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;
import java.util.List;
import java.util.Optional;
import org.apache.http.util.TextUtils;

public class StandaloneAuthUserRetriever
implements AuthUserRetriever {
    private final ApiTokenManager apiTokenManager;

    @Override
    public UserImpl retrieve(Context ctx, List<UserImpl> allUsers) {
        Integer sidUserId = (Integer)ctx.sessionAttribute("user_id");
        if (sidUserId != null) {
            return allUsers.stream().filter(user -> sidUserId.equals(user.getId())).findFirst().orElseThrow(() -> {
                ctx.req().getSession().invalidate();
                return new UnauthorizedResponse();
            });
        }
        Integer tokenUserId = this.getUserId(ctx.header("Authorization"));
        if (tokenUserId != null) {
            return allUsers.stream().filter(user -> tokenUserId.equals(user.getId())).findFirst().orElseThrow(() -> {
                ctx.req().getSession().invalidate();
                return new UnauthorizedResponse();
            });
        }
        ctx.req().getSession().invalidate();
        throw new UnauthorizedResponse();
    }

    @Override
    public UserImpl retrieve(WsContext ctx, List<UserImpl> allUsers) {
        Integer sidUserId = (Integer)ctx.sessionAttribute("user_id");
        if (sidUserId != null) {
            return allUsers.stream().filter(user -> sidUserId.equals(user.getId())).findFirst().orElse(null);
        }
        Integer tokenUserId = this.getUserId(ctx.header("Authorization"));
        if (tokenUserId != null) {
            return allUsers.stream().filter(user -> tokenUserId.equals(user.getId())).findFirst().orElse(null);
        }
        return null;
    }

    private Integer getUserId(String authToken) {
        if (TextUtils.isEmpty(authToken)) {
            return null;
        }
        String[] split = authToken.split(" ");
        if (split.length != 2 && TextUtils.isEmpty(split[1])) {
            return null;
        }
        Optional<ApiToken> apiSession = this.apiTokenManager.checkApiKey(split[1]);
        if (apiSession.isEmpty()) {
            return null;
        }
        return apiSession.orElseThrow().userId();
    }

    @Inject
    public StandaloneAuthUserRetriever(ApiTokenManager apiTokenManager) {
        this.apiTokenManager = apiTokenManager;
    }
}

