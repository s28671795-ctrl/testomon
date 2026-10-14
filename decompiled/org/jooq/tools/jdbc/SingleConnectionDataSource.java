/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.jooq.tools.jdbc.DefaultConnection;

public class SingleConnectionDataSource
implements DataSource {
    private final Connection delegate;

    public SingleConnectionDataSource(Connection delegate) {
        this.delegate = delegate;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getAnonymousLogger().getParent();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (this.isWrapperFor(iface)) {
            return (T)this;
        }
        throw new SQLException("DataSource does not implement " + String.valueOf(iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new DefaultConnection(this.delegate){

            @Override
            public void close() throws SQLException {
            }
        };
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException("SingleConnectionDataSource cannot create new connections");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLFeatureNotSupportedException("getLogWriter");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLFeatureNotSupportedException("setLogWriter");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException("setLoginTimeout");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException("getLoginTimeout");
    }
}

