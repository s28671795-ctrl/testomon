/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;

@Deprecated
public abstract class JDBC41ResultSet {
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

