/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus;

import com.incoresoft.middleware.eventbus.BusListener;
import com.incoresoft.middleware.eventbus.BusMessage;
import com.incoresoft.plugin.commons.eventbus.JsonTransferStrategy;
import com.incoresoft.plugin.commons.eventbus.ObjectTransferStrategy;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class EventBusTypedListener<T>
implements BusListener {
    private final String subject;
    private final Class<T> payloadType;
    private final ObjectTransferStrategy objectTransferStrategy;

    public EventBusTypedListener(@NotNull String subject, @NotNull Class<T> payloadType) {
        this(subject, payloadType, new JsonTransferStrategy());
    }

    public EventBusTypedListener(@NotNull String subject, @NotNull Class<T> payloadType, @NotNull ObjectTransferStrategy objectTransferStrategy) {
        this.subject = Objects.requireNonNull(subject);
        this.payloadType = Objects.requireNonNull(payloadType);
        this.objectTransferStrategy = Objects.requireNonNull(objectTransferStrategy);
    }

    @Override
    public String subject() {
        return this.subject;
    }

    @Override
    public void messageReceived(BusMessage busMessage) {
        this.messageReceived(this.objectTransferStrategy.toObject(busMessage.data(), this.payloadType));
    }

    public abstract void messageReceived(T var1);
}

