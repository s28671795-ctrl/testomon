/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryProvider;
import io.r2dbc.spi.Nullable;
import java.util.ServiceLoader;

public final class ConnectionFactories {
    private ConnectionFactories() {
    }

    @Nullable
    public static ConnectionFactory find(ConnectionFactoryOptions connectionFactoryOptions) {
        Assert.requireNonNull(connectionFactoryOptions, "connectionFactoryOptions must not be null");
        for (ConnectionFactoryProvider provider : ConnectionFactories.loadProviders()) {
            if (!provider.supports(connectionFactoryOptions)) continue;
            return provider.create(connectionFactoryOptions);
        }
        return null;
    }

    public static ConnectionFactory get(String url2) {
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(Assert.requireNonNull(url2, "R2DBC Connection URL must not be null")));
    }

    public static ConnectionFactory get(ConnectionFactoryOptions connectionFactoryOptions) {
        ConnectionFactory connectionFactory = ConnectionFactories.find(connectionFactoryOptions);
        if (connectionFactory == null) {
            throw new IllegalStateException(String.format("Unable to create a ConnectionFactory for '%s'. Available drivers: [ %s ]", connectionFactoryOptions, ConnectionFactories.getAvailableDrivers()));
        }
        return connectionFactory;
    }

    public static boolean supports(ConnectionFactoryOptions connectionFactoryOptions) {
        Assert.requireNonNull(connectionFactoryOptions, "connectionFactoryOptions must not be null");
        for (ConnectionFactoryProvider provider : ConnectionFactories.loadProviders()) {
            if (!provider.supports(connectionFactoryOptions)) continue;
            return true;
        }
        return false;
    }

    private static String getAvailableDrivers() {
        StringBuilder availableDrivers = new StringBuilder();
        for (ConnectionFactoryProvider provider : ConnectionFactories.loadProviders()) {
            if (availableDrivers.length() != 0) {
                availableDrivers.append(", ");
            }
            availableDrivers.append(provider.getDriver());
        }
        if (availableDrivers.length() == 0) {
            availableDrivers.append("None");
        }
        return availableDrivers.toString();
    }

    private static ServiceLoader<ConnectionFactoryProvider> loadProviders() {
        return ServiceLoader.load(ConnectionFactoryProvider.class, ConnectionFactoryProvider.class.getClassLoader());
    }
}

