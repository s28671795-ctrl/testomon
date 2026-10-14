/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectCorrelatedSubqueryStep;
import org.jooq.SelectOrderByStep;
import org.jooq.Support;

public interface SelectUnionStep<R extends Record>
extends SelectCorrelatedSubqueryStep<R> {
    @Override
    @Support
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> union(Select<? extends R> var1);

    @Override
    @Support
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> unionAll(Select<? extends R> var1);

    @Override
    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> except(Select<? extends R> var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE20C, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> exceptAll(Select<? extends R> var1);

    @Override
    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> intersect(Select<? extends R> var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE20C, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOrderByStep<R> intersectAll(Select<? extends R> var1);
}

