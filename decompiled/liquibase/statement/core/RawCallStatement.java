/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.CallableSqlStatement;
import liquibase.statement.core.RawSqlStatement;

public class RawCallStatement
extends RawSqlStatement
implements CallableSqlStatement {
    public RawCallStatement(String sql) {
        super(sql);
    }

    public RawCallStatement(String sql, String endDelimiter) {
        super(sql, endDelimiter);
    }
}

