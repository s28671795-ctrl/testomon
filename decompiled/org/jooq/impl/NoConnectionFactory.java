/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.reactivestreams.Publisher;

final class NoConnectionFactory
implements ConnectionFactory {
    NoConnectionFactory() {
    }

    @Override
    public Publisher<? extends Connection> create() {
        throw new UnsupportedOperationException("No ConnectionFactory configured");
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return () -> "jOOQ NoConnectionFactory";
    }
}

