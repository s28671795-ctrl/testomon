/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.reactivestreams.Publisher;

public interface ConnectionFactory {
    public Publisher<? extends Connection> create();

    public ConnectionFactoryMetadata getMetadata();
}

