/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus;

import com.google.inject.Inject;
import com.incoresoft.middleware.eventbus.Bus;
import com.incoresoft.plugin.commons.eventbus.JsonTransferStrategy;
import com.incoresoft.plugin.commons.eventbus.ObjectTransferStrategy;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public class EventBusTypedService {
    private final Bus bus;
    private final ObjectTransferStrategy objectTransferStrategy;

    @Inject
    public EventBusTypedService(@NotNull Bus bus) {
        this(bus, new JsonTransferStrategy());
    }

    public EventBusTypedService(@NotNull Bus bus, @NotNull ObjectTransferStrategy objectTransferStrategy) {
        this.bus = Objects.requireNonNull(bus);
        this.objectTransferStrategy = Objects.requireNonNull(objectTransferStrategy);
    }

    public <T> void publish(@NotNull String subject, @NotNull T object) {
        this.bus.publish(subject, this.objectTransferStrategy.toByteArray(object, object.getClass()));
    }

    public <R> CompletableFuture<R> request(@NotNull String subject, @NotNull Class<R> responseType) {
        return this.bus.request(subject, (byte[])null).thenApply(it -> this.objectTransferStrategy.toObject(it.data(), responseType));
    }

    public <T, R> CompletableFuture<R> request(@NotNull String subject, @NotNull T object, @NotNull Class<R> responseType) {
        return this.bus.request(subject, this.objectTransferStrategy.toByteArray(object, object.getClass())).thenApply(it -> this.objectTransferStrategy.toObject(it.data(), responseType));
    }
}

