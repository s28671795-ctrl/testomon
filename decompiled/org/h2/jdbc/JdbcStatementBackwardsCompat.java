/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.SQLException;

public interface JdbcStatementBackwardsCompat {
    public String enquoteIdentifier(String var1, boolean var2) throws SQLException;

    public boolean isSimpleIdentifier(String var1) throws SQLException;
}

