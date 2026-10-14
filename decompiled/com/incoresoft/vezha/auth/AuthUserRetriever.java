/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.incoresoft.vezha.models.users.UserImpl;
import io.javalin.http.Context;
import io.javalin.websocket.WsContext;
import java.util.List;

public interface AuthUserRetriever {
    public UserImpl retrieve(Context var1, List<UserImpl> var2);

    public UserImpl retrieve(WsContext var1, List<UserImpl> var2);
}

