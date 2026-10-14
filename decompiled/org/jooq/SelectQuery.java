/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.Variable;
import org.jooq.WindowDefinition;
import org.jooq.exception.DataAccessException;

public interface SelectQuery<R extends Record>
extends Select<R>,
ConditionProvider {
    @Support
    public void addSelect(SelectFieldOrAsterisk ... var1);

    @Support
    public void addSelect(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    public void setDistinct(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addDistinctOn(SelectFieldOrAsterisk ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addDistinctOn(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void setInto(Table<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setInto(Collection<? extends Variable<?>> var1);

    @Support
    public void addFrom(TableLike<?> var1);

    @Support
    public void addFrom(TableLike<?> ... var1);

    @Support
    public void addFrom(Collection<? extends TableLike<?>> var1);

    @Support
    public void addJoin(TableLike<?> var1, Condition var2);

    @Support
    public void addJoin(TableLike<?> var1, Condition ... var2);

    @Support
    public void addJoin(TableLike<?> var1, JoinType var2, Condition var3);

    @Support
    public void addJoin(TableLike<?> var1, JoinType var2, Condition ... var3);

    @Support(value={SQLDialect.ORACLE})
    @Pro
    public void addJoin(TableLike<?> var1, JoinType var2, Condition var3, Field<?>[] var4);

    @Support(value={SQLDialect.ORACLE})
    @Pro
    public void addJoin(TableLike<?> var1, JoinType var2, Condition[] var3, Field<?>[] var4);

    @Support
    public void addJoinUsing(TableLike<?> var1, Collection<? extends Field<?>> var2);

    @Support
    public void addJoinUsing(TableLike<?> var1, JoinType var2, Collection<? extends Field<?>> var3);

    @Support
    public void addJoinOnKey(TableLike<?> var1, JoinType var2) throws DataAccessException;

    @Support
    public void addJoinOnKey(TableLike<?> var1, JoinType var2, TableField<?, ?> ... var3) throws DataAccessException;

    @Support
    public void addJoinOnKey(TableLike<?> var1, JoinType var2, ForeignKey<?, ?> var3);

    @Support
    public void addGroupBy(GroupField ... var1);

    @Support
    public void addGroupBy(Collection<? extends GroupField> var1);

    @Support(value={SQLDialect.POSTGRES_14})
    public void setGroupByDistinct(boolean var1);

    @Support
    public void addHaving(Condition var1);

    @Support
    public void addHaving(Condition ... var1);

    @Support
    public void addHaving(Collection<? extends Condition> var1);

    @Support
    public void addHaving(Operator var1, Condition var2);

    @Support
    public void addHaving(Operator var1, Condition ... var2);

    @Support
    public void addHaving(Operator var1, Collection<? extends Condition> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addWindow(WindowDefinition ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addWindow(Collection<? extends WindowDefinition> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Condition var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Condition ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Collection<? extends Condition> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Operator var1, Condition var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Operator var1, Condition ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addQualify(Operator var1, Collection<? extends Condition> var2);

    @Support
    public void addHint(String var1);

    @Support
    public void addOption(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @Pro
    public void addConnectBy(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @Pro
    public void addConnectByNoCycle(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @Pro
    public void setConnectByStartWith(Condition var1);

    @Override
    @Support
    public void addConditions(Condition var1);

    @Override
    @Support
    public void addConditions(Condition ... var1);

    @Override
    @Support
    public void addConditions(Collection<? extends Condition> var1);

    @Override
    @Support
    public void addConditions(Operator var1, Condition var2);

    @Override
    @Support
    public void addConditions(Operator var1, Condition ... var2);

    @Override
    @Support
    public void addConditions(Operator var1, Collection<? extends Condition> var2);

    @Support
    public void addOrderBy(OrderField<?> ... var1);

    @Support
    public void addOrderBy(Collection<? extends OrderField<?>> var1);

    @Support
    public void addOrderBy(int ... var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @Pro
    public void setOrderBySiblings(boolean var1);

    @Support
    public void addSeekAfter(Field<?> ... var1);

    @Support
    public void addSeekAfter(Collection<? extends Field<?>> var1);

    @Deprecated
    @Support
    public void addSeekBefore(Field<?> ... var1);

    @Deprecated
    @Support
    public void addSeekBefore(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addOffset(Number var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addOffset(Field<? extends Number> var1);

    @Support
    public void addLimit(Number var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addLimit(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addLimit(Number var1, Number var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addLimit(Field<? extends Number> var1, Number var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addLimit(Number var1, Field<? extends Number> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void addLimit(Field<? extends Number> var1, Field<? extends Number> var2);

    @Support(value={SQLDialect.H2, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    public void setLimitPercent(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void setWithTies(boolean var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public void setForUpdate(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    public void setForNoKeyUpdate(boolean var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SYBASE})
    public void setForUpdateOf(Field<?> ... var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SYBASE})
    public void setForUpdateOf(Collection<? extends Field<?>> var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    public void setForUpdateOf(Table<?> ... var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.MARIADB_10_3, SQLDialect.ORACLE})
    public void setForUpdateWait(int var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setForUpdateNoWait();

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setForUpdateSkipLocked();

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    public void setForShare(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    public void setForKeyShare(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SYBASE})
    public void setForLockModeOf(Field<?> ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SYBASE})
    public void setForLockModeOf(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    public void setForLockModeOf(Table<?> ... var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES})
    public void setForLockModeWait(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setForLockModeNoWait();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setForLockModeSkipLocked();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLRaw(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLRaw(boolean var1, String var2);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLAuto(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLPath(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLPath(boolean var1, String var2);

    @Support(value={SQLDialect.SQLSERVER})
    @Pro
    public void setForXMLExplicit(boolean var1);

    @Support(value={SQLDialect.SQLSERVER})
    @Pro
    public void setForXMLBinaryBase64(boolean var1);

    @Support(value={SQLDialect.SQLSERVER})
    @Pro
    public void setForXMLType(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLRoot(boolean var1);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLRoot(boolean var1, String var2);

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @Pro
    public void setForXMLElements(boolean var1);

    @Support(value={SQLDialect.SQLSERVER})
    @Pro
    public void setForXMLElementsXsinil(boolean var1);

    @Support(value={SQLDialect.SQLSERVER})
    @Pro
    public void setForXMLElementsAbsent(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONB(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONAuto(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONPath(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONRoot(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONRoot(boolean var1, String var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONIncludeNullValues(boolean var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Pro
    public void setForJSONWithoutArrayWrapper(boolean var1);

    @Support(value={SQLDialect.ASE, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void setWithCheckOption();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    public void setWithReadOnly();
}

