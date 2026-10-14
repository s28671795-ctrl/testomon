/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.profile.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;

public class EventContext<EventType>
extends BaseContext {
    private EventType event;

    @Nullable
    public EventType getEvent() {
        return this.event;
    }

    @Nonnull
    public EventContext setEvent(@Nullable EventType newEvent) {
        this.event = newEvent;
        return this;
    }
}

