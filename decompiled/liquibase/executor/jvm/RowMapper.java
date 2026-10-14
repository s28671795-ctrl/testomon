/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
    public Object mapRow(ResultSet var1, int var2) throws SQLException;
}

