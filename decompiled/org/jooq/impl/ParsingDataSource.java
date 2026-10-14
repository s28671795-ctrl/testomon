/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.Configuration;
import org.jooq.impl.AbstractDataSource;
import org.jooq.impl.ParsingConnection;

final class ParsingDataSource
extends AbstractDataSource {
    private final Configuration configuration;

    ParsingDataSource(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final Connection getConnection() throws SQLException {
        return new ParsingConnection(this.configuration);
    }

    @Override
    public final Connection getConnection(String username, String password) throws SQLException {
        return new ParsingConnection(this.configuration);
    }
}

