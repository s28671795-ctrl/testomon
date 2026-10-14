/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import org.reactivestreams.Publisher;

@FunctionalInterface
public interface Closeable {
    public Publisher<Void> close();
}

