/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.WindowSpecificationFinalStep;

public interface WindowSpecificationExcludeStep
extends WindowSpecificationFinalStep {
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowSpecificationFinalStep excludeCurrentRow();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowSpecificationFinalStep excludeGroup();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowSpecificationFinalStep excludeTies();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowSpecificationFinalStep excludeNoOthers();
}

