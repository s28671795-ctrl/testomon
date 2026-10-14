/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class RawSqlStatement
extends AbstractSqlStatement {
    private final String sql;
    private String endDelimiter = ";";

    public RawSqlStatement(String sql) {
        this.sql = sql;
    }

    public RawSqlStatement(String sql, String endDelimiter) {
        this(sql);
        if (endDelimiter != null) {
            this.endDelimiter = endDelimiter;
        }
    }

    public String getSql() {
        return this.sql;
    }

    public String getEndDelimiter() {
        return this.endDelimiter.replace("\\r", "\r").replace("\\n", "\n");
    }

    public String toString() {
        return this.sql;
    }
}

