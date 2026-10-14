/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.WindowOverStep;

public interface WindowIgnoreNullsStep<T>
extends WindowOverStep<T> {
    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public WindowOverStep<T> ignoreNulls();

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public WindowOverStep<T> respectNulls();
}

