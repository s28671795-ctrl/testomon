/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

import io.javalin.event.EventHandler;
import io.javalin.event.EventManager;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.JavalinEvent;
import io.javalin.event.WsHandlerMetaInfo;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class EventListener {
    private final EventManager eventManager;

    public EventListener(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void serverStarting(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_STARTING, eventHandler);
    }

    public void serverStarted(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_STARTED, eventHandler);
    }

    public void serverStartFailed(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_START_FAILED, eventHandler);
    }

    public void serverStopFailed(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_STOP_FAILED, eventHandler);
    }

    public void serverStopping(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_STOPPING, eventHandler);
    }

    public void serverStopped(@NotNull EventHandler eventHandler) {
        this.addLifecycleEvent(JavalinEvent.SERVER_STOPPED, eventHandler);
    }

    public void handlerAdded(@NotNull Consumer<HandlerMetaInfo> callback) {
        this.eventManager.getHandlerAddedHandlers().add(callback);
    }

    public void wsHandlerAdded(@NotNull Consumer<WsHandlerMetaInfo> callback) {
        this.eventManager.getWsHandlerAddedHandlers().add(callback);
    }

    private void addLifecycleEvent(@NotNull JavalinEvent event, @NotNull EventHandler eventHandler) {
        this.eventManager.getLifecycleHandlers().get((Object)event).add(eventHandler);
    }
}

