/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.eventbus;

import com.google.common.eventbus.Dispatcher;
import com.google.common.eventbus.ElementTypesAreNonnullByDefault;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
public class AsyncEventBus
extends EventBus {
    public AsyncEventBus(String identifier, Executor executor2) {
        super(identifier, executor2, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
    }

    public AsyncEventBus(Executor executor2, SubscriberExceptionHandler subscriberExceptionHandler) {
        super("default", executor2, Dispatcher.legacyAsync(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor2) {
        super("default", executor2, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
    }
}

