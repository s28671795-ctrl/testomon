/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

public class DataSourceConnectionProvider
implements ConnectionProvider {
    private final DataSource dataSource;

    public DataSourceConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NotNull
    public DataSource dataSource() {
        return this.dataSource;
    }

    @Override
    @NotNull
    public Connection acquire() {
        try {
            return this.dataSource.getConnection();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error getting connection from data source " + String.valueOf(this.dataSource), e);
        }
    }

    @Override
    public void release(Connection connection) {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error closing connection " + String.valueOf(connection), e);
        }
    }
}

