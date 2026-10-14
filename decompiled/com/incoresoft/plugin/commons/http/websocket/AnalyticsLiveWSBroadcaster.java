/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.websocket;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.websocket.WSBroadcaster;
import io.javalin.http.BadRequestResponse;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsErrorContext;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public abstract class AnalyticsLiveWSBroadcaster
extends WSBroadcaster {
    private final Map<Integer, Set<WsConnectContext>> analyticsIdToSubscribers = new ConcurrentHashMap<Integer, Set<WsConnectContext>>();

    public AnalyticsLiveWSBroadcaster(UsersManager usersManager, boolean setUpHeathCheckScheduler) {
        super(usersManager, setUpHeathCheckScheduler);
    }

    @Override
    public void subscribe(WsConnectContext ctx) {
        super.subscribe(ctx);
        Integer analyticsId = ctx.pathParamAsClass("analytics_id", Integer.TYPE).getOrThrow(e -> new BadRequestResponse("analytics_id"));
        this.analyticsIdToSubscribers.computeIfAbsent(analyticsId, key -> ConcurrentHashMap.newKeySet()).add(ctx);
    }

    @Override
    public void broadcast(Object message) {
        throw new UnsupportedOperationException("Use broadcast(int, Function) instead");
    }

    @Override
    public void broadcast(@NotNull Object message, @NotNull Predicate<User> accessFilter) {
        throw new UnsupportedOperationException("Use broadcast(int, Function) instead");
    }

    public void broadcast(int analyticsId, @NotNull Function<User, Object> messageSupplier) {
        for (WsConnectContext ctx : this.analyticsIdToSubscribers.getOrDefault(analyticsId, Collections.emptySet())) {
            User user = this.usersManager.getUser(ctx);
            if (user == null) continue;
            this.send(ctx, messageSupplier.apply(user));
        }
    }

    @Override
    public void unsubscribe(WsContext ctx) {
        super.unsubscribe(ctx);
        for (Set<WsConnectContext> subscribers : this.analyticsIdToSubscribers.values()) {
            subscribers.remove(ctx);
        }
    }

    @Override
    public void onError(WsErrorContext ctx) {
        super.onError(ctx);
        for (Set<WsConnectContext> subscribers : this.analyticsIdToSubscribers.values()) {
            subscribers.remove(ctx);
        }
    }
}

