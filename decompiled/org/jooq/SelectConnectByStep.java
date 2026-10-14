/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SelectConnectByAfterStartWithStep;
import org.jooq.SelectConnectByConditionStep;
import org.jooq.SelectGroupByStep;
import org.jooq.Support;

public interface SelectConnectByStep<R extends Record>
extends SelectGroupByStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(Condition var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(Field<Boolean> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectBy(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(Field<Boolean> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(SQL var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(String var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(String var1, Object ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByConditionStep<R> connectByNoCycle(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(Field<Boolean> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(SQL var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(String var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(String var1, Object ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithStep<R> startWith(String var1, QueryPart ... var2);
}

