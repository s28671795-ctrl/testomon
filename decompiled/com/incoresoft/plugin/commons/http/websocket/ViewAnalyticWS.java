/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.websocket;

import com.alibaba.fastjson.JSON;
import io.javalin.websocket.WsContext;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Deprecated(since="25.1.22")
public class ViewAnalyticWS {
    private final ConcurrentHashMap<Integer, Set<WsContext>> subscribers = new ConcurrentHashMap();

    public void subscribe(WsContext session, int vaId) {
        this.subscribers.computeIfAbsent(vaId, id -> Collections.newSetFromMap(new ConcurrentHashMap())).add(session);
    }

    public boolean hasSubscribers(int vaId) {
        Set<WsContext> sessions = this.subscribers.get(vaId);
        return sessions != null && !sessions.isEmpty();
    }

    public void broadcast(Integer vaId, Object object) {
        this.cleanDisconnectedSessions();
        Set<WsContext> sessions = this.subscribers.get(vaId);
        if (sessions != null) {
            sessions.forEach(session -> session.send(JSON.toJSONString(object)));
        }
    }

    public void broadcast(Integer vaId, Function<WsContext, Object> messageProvider) {
        this.cleanDisconnectedSessions();
        Set<WsContext> sessions = this.subscribers.get(vaId);
        if (sessions != null) {
            sessions.forEach(session -> session.send(JSON.toJSONString(messageProvider.apply((WsContext)session))));
        }
    }

    private void cleanDisconnectedSessions() {
        this.subscribers.forEach((id, listeners) -> listeners.removeIf(item -> !item.session.isOpen()));
        this.subscribers.entrySet().removeIf(it -> ((Set)it.getValue()).isEmpty());
    }
}

