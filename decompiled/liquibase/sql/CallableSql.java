/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql;

import java.util.Collection;
import java.util.Collections;
import liquibase.sql.Sql;
import liquibase.structure.DatabaseObject;

public class CallableSql
implements Sql {
    private final String sql;
    private final String endDelimiter;
    private final String expectedStatus;

    public CallableSql(String sql, String expectedStatus) {
        this(sql, ";", expectedStatus);
    }

    public CallableSql(String sql, String endDelimiter, String expectedStatus) {
        this.sql = sql;
        this.endDelimiter = endDelimiter;
        this.expectedStatus = expectedStatus;
    }

    @Override
    public String toSql() {
        return this.sql;
    }

    @Override
    public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
        return Collections.emptyList();
    }

    @Override
    public String getEndDelimiter() {
        return this.endDelimiter;
    }

    public String getExpectedStatus() {
        return this.expectedStatus;
    }
}

