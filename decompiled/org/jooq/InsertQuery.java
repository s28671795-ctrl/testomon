/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Constraint;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Insert;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.StoreQuery;
import org.jooq.Support;
import org.jooq.UniqueKey;

public interface InsertQuery<R extends Record>
extends StoreQuery<R>,
Insert<R>,
ConditionProvider {
    @Support
    public void newRecord();

    @Support
    public void addRecord(R var1);

    @Support
    public void onConflict(Field<?> ... var1);

    @Support
    public void onConflict(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    public void onConflictOnConstraint(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    public void onConflictOnConstraint(Constraint var1);

    @Support
    public void onConflictOnConstraint(UniqueKey<R> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public void onDuplicateKeyUpdate(boolean var1);

    @Support
    public void onDuplicateKeyIgnore(boolean var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public <T> void addValueForUpdate(Field<T> var1, T var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public <T> void addValueForUpdate(Field<T> var1, Field<T> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public void addValuesForUpdate(Map<?, ?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES_9_5, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    public void onConflictWhere(Condition var1);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Condition var1);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Condition ... var1);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Collection<? extends Condition> var1);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Operator var1, Condition var2);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Operator var1, Condition ... var2);

    @Override
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void addConditions(Operator var1, Collection<? extends Condition> var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    public void setDefaultValues();

    @Support
    public void setSelect(Field<?>[] var1, Select<?> var2);

    @Support
    public void setSelect(Collection<? extends Field<?>> var1, Select<?> var2);

    @Override
    @Support
    public void setReturning();

    @Override
    @Support
    public void setReturning(Identity<R, ?> var1);

    @Override
    @Support
    public void setReturning(SelectFieldOrAsterisk ... var1);

    @Override
    @Support
    public void setReturning(Collection<? extends SelectFieldOrAsterisk> var1);

    @Override
    @Support
    @Nullable
    public R getReturnedRecord();

    @Override
    @Support
    @NotNull
    public Result<R> getReturnedRecords();
}

