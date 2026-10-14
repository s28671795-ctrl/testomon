/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;

@Deprecated
public abstract class JDBC41Statement {
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

