/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectOptionStep;
import org.jooq.Support;

@Pro
public interface SelectForJSONCommonDirectivesStep<R extends Record>
extends SelectOptionStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONCommonDirectivesStep<R> root();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONCommonDirectivesStep<R> root(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONCommonDirectivesStep<R> includeNullValues();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForJSONCommonDirectivesStep<R> withoutArrayWrapper();
}

