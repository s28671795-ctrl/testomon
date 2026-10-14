/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;
import java.util.concurrent.Executor;

@Deprecated
public abstract class JDBC41Connection {
    public void setSchema(String s2) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abort(Executor executor2) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNetworkTimeout(Executor executor2, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

