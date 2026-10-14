/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

public interface ConnectionFactoryProvider {
    public ConnectionFactory create(ConnectionFactoryOptions var1);

    public boolean supports(ConnectionFactoryOptions var1);

    public String getDriver();
}

