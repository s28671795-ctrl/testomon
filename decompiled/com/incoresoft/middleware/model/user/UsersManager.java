/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.user;

import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.user.User;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;

public interface UsersManager
extends ItemManager<User> {
    public User getUser(Context var1) throws UnauthorizedResponse;

    public User getUser(WsContext var1) throws UnauthorizedResponse;

    default public User getUser(String apiToken) throws UnauthorizedResponse {
        return null;
    }
}

