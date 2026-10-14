/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SelectJoinPartitionByStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectOptionalOnStep;
import org.jooq.SelectWhereStep;
import org.jooq.Support;
import org.jooq.TableLike;

public interface SelectJoinStep<R extends Record>
extends SelectWhereStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectOptionalOnStep<R> join(TableLike<?> var1, JoinType var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> join(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> innerJoin(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(SQL var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> leftOuterJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinPartitionByStep<R> rightOuterJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> fullOuterJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalLeftOuterJoin(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(SQL var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalRightOuterJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> naturalFullOuterJoin(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> leftSemiJoin(TableLike<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> leftAntiJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> crossApply(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public SelectJoinStep<R> outerApply(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    @CheckReturnValue
    public SelectOnStep<R> straightJoin(Name var1);
}

