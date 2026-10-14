/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.PreparedStatement;
import java.sql.SQLException;

interface PreparedStatementSetter {
    public void setValues(PreparedStatement var1) throws SQLException;
}

