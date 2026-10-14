/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.sse;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.sse.events.CloseSessionEvent;
import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.tuple.Pair;

public abstract class SSEParamsConfigurableBroadcaster<T> {
    protected final UsersManager usersManager;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory());
    private final Map<SseClient, Pair<User, T>> clientsUsersMap = new ConcurrentHashMap<SseClient, Pair<User, T>>();

    @Inject
    public SSEParamsConfigurableBroadcaster(UsersManager usersManager, boolean setUpHeathCheckScheduler) {
        this.usersManager = usersManager;
        if (setUpHeathCheckScheduler) {
            this.setUpHeathCheckScheduler();
        }
    }

    @Inject
    public void init(Javalin app) {
        app.sse(this.getPath(), this::establishConnection, AppRole.LOGGED_IN);
    }

    private void establishConnection(SseClient client) {
        client.onClose(() -> {
            this.removeClient(client);
            this.onConnectionClosed(client);
        });
        this.addClient(client);
        this.onConnectionOpened(client);
    }

    protected abstract void onConnectionOpened(SseClient var1);

    protected abstract void onConnectionClosed(SseClient var1);

    protected abstract String getPath();

    protected abstract T getRequestParams(SseClient var1);

    private void addClient(SseClient client) {
        this.clientsUsersMap.put(client, Pair.of(this.usersManager.getUser(client.ctx()), this.getRequestParams(client)));
    }

    private void removeClient(SseClient client) {
        this.clientsUsersMap.remove(client);
    }

    public final void broadcast(Object data, Predicate<User> userFilter, Predicate<T> paramsFilter) {
        this.broadcast((User user) -> data, userFilter, paramsFilter);
    }

    public final void broadcast(Function<User, Object> dataFactory, Predicate<User> userFilter, Predicate<T> paramsFilter) {
        for (Map.Entry<SseClient, Pair<User, T>> entry : this.clientsUsersMap.entrySet()) {
            User user = entry.getValue().getLeft();
            T params = entry.getValue().getRight();
            if (user == null || !userFilter.test(user) || !paramsFilter.test(params)) continue;
            Object data = dataFactory.apply(user);
            entry.getKey().sendEvent("data", data);
        }
    }

    public void sendCloseEvent(SseClient sseClient) {
        for (Map.Entry<SseClient, Pair<User, T>> entry : this.clientsUsersMap.entrySet()) {
            if (!entry.getKey().equals(sseClient)) continue;
            entry.getKey().sendEvent("data", new CloseSessionEvent());
        }
    }

    protected void broadcast(Object data, SseClient sseClient) {
        for (Map.Entry<SseClient, Pair<User, T>> entry : this.clientsUsersMap.entrySet()) {
            if (!entry.getKey().equals(sseClient)) continue;
            entry.getKey().sendEvent("data", data);
        }
    }

    protected void setUpHeathCheckScheduler() {
        this.scheduler.scheduleAtFixedRate(() -> this.broadcast(() -> "health_check", (User it) -> true, (T it) -> true), 0L, 10L, TimeUnit.SECONDS);
    }
}

