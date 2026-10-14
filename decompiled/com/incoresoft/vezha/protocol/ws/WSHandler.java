/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.ws;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class WSHandler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory());
    private final boolean setUpHeathCheckScheduler;

    public void init(WsConfig wsConfig) {
        wsConfig.onConnect(ctx -> {
            this.onSessionConnect(ctx);
            if (this.setUpHeathCheckScheduler) {
                this.setUpHeathCheckScheduler(ctx);
            }
        });
        wsConfig.onMessage(this::onMessage);
        wsConfig.onClose(this::onClose);
        wsConfig.onError(this::onError);
    }

    private void setUpHeathCheckScheduler(WsContext ctx) {
        this.scheduler.scheduleAtFixedRate(() -> ctx.send((Object)"health_check"), 0L, 10L, TimeUnit.SECONDS);
    }

    protected abstract void onSessionConnect(WsConnectContext var1);

    protected abstract void onMessage(WsMessageContext var1);

    protected abstract void onClose(WsCloseContext var1);

    protected abstract void onError(WsErrorContext var1);

    public WSHandler(boolean setUpHeathCheckScheduler) {
        this.setUpHeathCheckScheduler = setUpHeathCheckScheduler;
    }
}

