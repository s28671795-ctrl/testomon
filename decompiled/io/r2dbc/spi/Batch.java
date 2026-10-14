/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Result;
import org.reactivestreams.Publisher;

public interface Batch {
    public Batch add(String var1);

    public Publisher<? extends Result> execute();
}

