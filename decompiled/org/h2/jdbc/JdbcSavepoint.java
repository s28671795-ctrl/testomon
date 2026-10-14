/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceObject;
import org.h2.util.StringUtils;

public final class JdbcSavepoint
extends TraceObject
implements Savepoint {
    private static final String SYSTEM_SAVEPOINT_PREFIX = "SYSTEM_SAVEPOINT_";
    private final int savepointId;
    private final String name;
    private JdbcConnection conn;

    JdbcSavepoint(JdbcConnection jdbcConnection, int n, String string, Trace trace, int n2) {
        this.setTrace(trace, 6, n2);
        this.conn = jdbcConnection;
        this.savepointId = n;
        this.name = string;
    }

    void release() {
        this.conn = null;
    }

    static String getName(String string, int n) {
        if (string != null) {
            return StringUtils.quoteJavaString(string);
        }
        return SYSTEM_SAVEPOINT_PREFIX + n;
    }

    void rollback() {
        this.checkValid();
        this.conn.prepareCommand("ROLLBACK TO SAVEPOINT " + JdbcSavepoint.getName(this.name, this.savepointId), Integer.MAX_VALUE).executeUpdate(null);
    }

    private void checkValid() {
        if (this.conn == null) {
            throw DbException.get(90063, JdbcSavepoint.getName(this.name, this.savepointId));
        }
    }

    @Override
    public int getSavepointId() throws SQLException {
        try {
            this.debugCodeCall("getSavepointId");
            this.checkValid();
            if (this.name != null) {
                throw DbException.get(90065);
            }
            return this.savepointId;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSavepointName() throws SQLException {
        try {
            this.debugCodeCall("getSavepointName");
            this.checkValid();
            if (this.name == null) {
                throw DbException.get(90064);
            }
            return this.name;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public String toString() {
        return this.getTraceObjectName() + ": id=" + this.savepointId + " name=" + this.name;
    }
}

