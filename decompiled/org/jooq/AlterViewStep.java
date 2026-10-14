/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterViewFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Comment;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Support;
import org.jooq.Table;

public interface AlterViewStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep comment(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep comment(Comment var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep renameTo(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep renameTo(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewFinalStep as(Select<?> var1);
}

