/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import org.reactivestreams.Publisher;

public interface Lifecycle {
    public Publisher<Void> postAllocate();

    public Publisher<Void> preRelease();
}

