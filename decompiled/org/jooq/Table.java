/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Check;
import org.jooq.Condition;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.GroupField;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.JoinType;
import org.jooq.Link;
import org.jooq.Name;
import org.jooq.PeriodSpecification;
import org.jooq.PivotForStep;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordQualifier;
import org.jooq.RecordType;
import org.jooq.RowId;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Support;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TableOptions;
import org.jooq.TableOuterJoinStep;
import org.jooq.TablePartitionByStep;
import org.jooq.UniqueKey;
import org.jooq.VersionsBetweenAndStep;

public interface Table<R extends Record>
extends TableLike<R>,
RecordQualifier<R>,
GroupField,
SelectField<R> {
    @NotNull
    public TableOptions.TableType getTableType();

    @NotNull
    public TableOptions getOptions();

    @NotNull
    public RecordType<R> recordType();

    @Nullable
    public Identity<R, ?> getIdentity();

    @Nullable
    public UniqueKey<R> getPrimaryKey();

    @Nullable
    public TableField<R, ?> getRecordVersion();

    @Nullable
    public TableField<R, ?> getRecordTimestamp();

    @NotNull
    public List<Index> getIndexes();

    @NotNull
    public List<UniqueKey<R>> getKeys();

    @NotNull
    public List<UniqueKey<R>> getUniqueKeys();

    @NotNull
    public <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> var1);

    @NotNull
    public List<ForeignKey<R, ?>> getReferences();

    @NotNull
    public <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> var1);

    @NotNull
    public List<Check<R>> getChecks();

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> at(String var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> at(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> at(Link var1);

    @Support
    @NotNull
    public QualifiedAsterisk asterisk();

    @Override
    @Support
    @NotNull
    public Table<R> as(String var1);

    @Support
    @NotNull
    public Table<R> as(String var1, String ... var2);

    @Support
    @NotNull
    public Table<R> as(String var1, Collection<? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Override
    @Support
    @NotNull
    public Table<R> as(Name var1);

    @Support
    @NotNull
    public Table<R> as(Name var1, Name ... var2);

    @Support
    @NotNull
    public Table<R> as(Name var1, Collection<? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(Name var1, Function<? super Field<?>, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

    @Override
    @Support
    @NotNull
    public Table<R> as(Table<?> var1);

    @Support
    @NotNull
    public Table<R> as(Table<?> var1, Field<?> ... var2);

    @Support
    @NotNull
    public Table<R> as(Table<?> var1, Collection<? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> as(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

    @Support
    @NotNull
    public Table<R> where(Condition var1);

    @Support
    @NotNull
    public Table<R> where(Condition ... var1);

    @Support
    @NotNull
    public Table<R> where(Collection<? extends Condition> var1);

    @Support
    @NotNull
    public Table<R> where(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<R> where(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<R> where(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<R> where(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Table<R> where(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Table<R> whereExists(Select<?> var1);

    @Support
    @NotNull
    public Table<R> whereNotExists(Select<?> var1);

    @Support
    @NotNull
    public TableOptionalOnStep<Record> join(TableLike<?> var1, JoinType var2);

    @Support
    @NotNull
    public TableOnStep<Record> join(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> join(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> join(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> join(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> join(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> join(Name var1);

    @Support
    @NotNull
    public TableOnStep<Record> innerJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> innerJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> innerJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> innerJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnStep<Record> innerJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TableOnStep<Record> innerJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TableOuterJoinStep<Record> partitionBy(Field<?> ... var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TableOuterJoinStep<Record> partitionBy(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    public TablePartitionByStep<Record> leftJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TablePartitionByStep<Record> leftJoin(Name var1);

    @Support
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TablePartitionByStep<Record> leftOuterJoin(Name var1);

    @Support
    @NotNull
    public TablePartitionByStep<Record> rightJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TablePartitionByStep<Record> rightJoin(Name var1);

    @Support
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TablePartitionByStep<Record> rightOuterJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public TablePartitionByStep<Record> fullJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public TablePartitionByStep<Record> fullJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public TablePartitionByStep<Record> fullOuterJoin(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> crossJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossJoin(SQL var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossJoin(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> crossJoin(Name var1);

    @Support
    @NotNull
    public Table<Record> naturalJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalJoin(String var1, Object ... var2);

    @Support
    @NotNull
    public Table<Record> naturalJoin(Name var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalJoin(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Table<Record> naturalLeftOuterJoin(TableLike<?> var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalLeftOuterJoin(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalLeftOuterJoin(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalLeftOuterJoin(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalLeftOuterJoin(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Table<Record> naturalLeftOuterJoin(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> naturalRightOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalRightOuterJoin(SQL var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalRightOuterJoin(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalRightOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalRightOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> naturalRightOuterJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> naturalFullOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalFullOuterJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalFullOuterJoin(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalFullOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> naturalFullOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_39, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> naturalFullOuterJoin(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> crossApply(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossApply(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossApply(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossApply(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> crossApply(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> crossApply(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> outerApply(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> outerApply(SQL var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> outerApply(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> outerApply(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public Table<Record> outerApply(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> outerApply(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    public TableOnStep<Record> straightJoin(TableLike<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    public TableOnStep<Record> straightJoin(SQL var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    public TableOnStep<Record> straightJoin(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    public TableOnStep<Record> straightJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    public TableOnStep<Record> straightJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @PlainSQL
    @NotNull
    public TableOnStep<Record> straightJoin(Name var1);

    @Support
    @NotNull
    public Condition eq(Table<R> var1);

    @Support
    @NotNull
    public Condition equal(Table<R> var1);

    @Support
    @NotNull
    public Condition ne(Table<R> var1);

    @Support
    @NotNull
    public Condition notEqual(Table<R> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER})
    @NotNull
    public Field<RowId> rowid();

    @Override
    public boolean equals(Object var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> useIndex(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> useIndexForJoin(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> useIndexForOrderBy(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> useIndexForGroupBy(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> ignoreIndex(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> ignoreIndexForJoin(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> ignoreIndexForOrderBy(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> ignoreIndexForGroupBy(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> forceIndex(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> forceIndexForJoin(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> forceIndexForOrderBy(String ... var1);

    @Support(value={SQLDialect.MARIADB, SQLDialect.MYSQL})
    @NotNull
    public Table<R> forceIndexForGroupBy(String ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @Pro
    public Table<R> with(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @Pro
    public Table<R> with(String var1, Object ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @Pro
    public Table<R> with(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    @Pro
    public Table<R> with(SQL var1);

    @Support(value={SQLDialect.ORACLE11G, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public PivotForStep pivot(Field<?> var1);

    @Support(value={SQLDialect.ORACLE11G})
    @NotNull
    @Pro
    public PivotForStep pivot(Field<?> ... var1);

    @Support(value={SQLDialect.ORACLE11G})
    @NotNull
    @Pro
    public PivotForStep pivot(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Table<Record> withOrdinality();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public DivideByOnStep divideBy(Table<?> var1);

    @Support
    @NotNull
    public TableOnStep<R> leftSemiJoin(TableLike<?> var1);

    @Support
    @NotNull
    public TableOnStep<R> leftAntiJoin(TableLike<?> var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public Table<R> for_(PeriodSpecification var1);

    @Support(value={SQLDialect.DB2, SQLDialect.MARIADB_10_3})
    @NotNull
    @Pro
    public Table<R> forPortionOf(PeriodSpecification var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Number> versionsBetweenScn(Number var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Number> versionsBetweenScn(Field<? extends Number> var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Number> versionsBetweenScnMinvalue();

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestamp(Timestamp var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestamp(Field<Timestamp> var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public VersionsBetweenAndStep<R, Timestamp> versionsBetweenTimestampMinvalue();

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> asOfScn(Number var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> asOfScn(Field<? extends Number> var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> asOfTimestamp(Timestamp var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public Table<R> asOfTimestamp(Field<Timestamp> var1);

    @NotNull
    public R from(Record var1);
}

