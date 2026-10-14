/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

abstract class AbstractDataSource
implements DataSource {
    AbstractDataSource() {
    }

    @Override
    public final PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("getLogWriter");
    }

    @Override
    public final void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("setLogWriter");
    }

    @Override
    public final void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("setLoginTimeout");
    }

    @Override
    public final int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger("global");
    }

    @Override
    public final <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T)this;
        }
        throw new SQLException("DataSource of type [" + this.getClass().getName() + "] cannot be unwrapped as [" + iface.getName() + "]");
    }

    @Override
    public final boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}

