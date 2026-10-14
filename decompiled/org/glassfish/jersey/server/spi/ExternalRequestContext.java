/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

public class ExternalRequestContext<T> {
    private final T context;

    public ExternalRequestContext(T context) {
        this.context = context;
    }

    public T getContext() {
        return this.context;
    }
}

