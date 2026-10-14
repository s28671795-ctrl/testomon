/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.JSONEntry;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Support;

public interface JSONEntryValueStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T> JSONEntry<T> value(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T> JSONEntry<T> value(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T> JSONEntry<T> value(Select<? extends Record1<T>> var1);
}

