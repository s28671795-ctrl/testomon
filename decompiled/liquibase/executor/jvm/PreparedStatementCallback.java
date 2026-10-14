/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.PreparedStatement;

interface PreparedStatementCallback {
    public Object doInPreparedStatement(PreparedStatement var1);
}

