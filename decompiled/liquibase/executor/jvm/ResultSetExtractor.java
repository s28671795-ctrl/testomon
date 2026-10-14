/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;

interface ResultSetExtractor {
    public Object extractData(ResultSet var1) throws SQLException;
}

