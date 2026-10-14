/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

@Deprecated(since="25.1.22")
public class NotificationsWS {
    private final Set<WsContext> sessions = Collections.newSetFromMap(new ConcurrentHashMap());
    private final UsersManager usersManager;

    @Inject
    public NotificationsWS(@NotNull UsersManager usersManager) {
        this.usersManager = Objects.requireNonNull(usersManager);
    }

    public void subscribe(WsContext wsContext) {
        this.sessions.add(wsContext);
    }

    public void unsubscribe(WsContext wsContext) {
        this.sessions.remove(wsContext);
    }

    public void broadcast(Object object) {
        if (!(object instanceof JSONObject)) {
            object = JSON.toJSON(object);
        }
        JSONObject jsonObject = (JSONObject)object;
        String message = jsonObject.toJSONString();
        int clientId = jsonObject.getIntValue("client_id");
        this.sessions.stream().filter(it -> {
            try {
                User user = this.usersManager.getUser((WsContext)it);
                return clientId == 0 || user.getClientId() == clientId;
            }
            catch (UnauthorizedResponse e) {
                return false;
            }
        }).forEach(session -> session.send(message));
    }
}

