/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.sql.PreparedStatement;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

public final class PreparedStatementFactory {
    private final JdbcConnection con;

    public PreparedStatementFactory(JdbcConnection con) {
        if (con == null) {
            throw new IllegalArgumentException("connection must not be null");
        }
        this.con = con;
    }

    public PreparedStatement create(String sql) throws DatabaseException {
        return this.con.prepareStatement(sql);
    }

    public String toString() {
        return "[con: " + this.con + "]";
    }
}

