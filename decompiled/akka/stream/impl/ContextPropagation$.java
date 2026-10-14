/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalStableApi;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagationImpl;

@InternalStableApi
public final class ContextPropagation$ {
    public static final ContextPropagation$ MODULE$ = new ContextPropagation$();

    @InternalStableApi
    public ContextPropagation apply() {
        return new ContextPropagationImpl();
    }

    private ContextPropagation$() {
    }
}

