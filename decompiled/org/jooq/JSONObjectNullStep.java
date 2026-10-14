/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.JSONObjectReturningStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONObjectNullStep<T>
extends JSONObjectReturningStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public JSONObjectReturningStep<T> nullOnNull();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public JSONObjectReturningStep<T> absentOnNull();
}

