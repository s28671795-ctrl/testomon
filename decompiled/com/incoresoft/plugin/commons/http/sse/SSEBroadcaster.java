/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.sse;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.sse.EmptySSERequestParameters;
import com.incoresoft.plugin.commons.http.sse.SSEParamsConfigurableBroadcaster;
import io.javalin.http.sse.SseClient;
import java.util.function.Predicate;

public abstract class SSEBroadcaster
extends SSEParamsConfigurableBroadcaster<EmptySSERequestParameters> {
    public SSEBroadcaster(UsersManager usersManager, boolean setUpHeathCheckScheduler) {
        super(usersManager, setUpHeathCheckScheduler);
    }

    @Override
    protected EmptySSERequestParameters getRequestParams(SseClient client) {
        return new EmptySSERequestParameters();
    }

    public void broadcast(Object data, Predicate<User> usersFilter) {
        this.broadcast(data, usersFilter, (T paras) -> true);
    }
}

