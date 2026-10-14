/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Delete;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;
import org.jooq.TableLike;

public interface DeleteQuery<R extends Record>
extends ConditionProvider,
Delete<R> {
    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void addUsing(TableLike<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void addUsing(TableLike<?> ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    public void addUsing(Collection<? extends TableLike<?>> var1);

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
    public void addLimit(Number var1);

    @Support
    public void addLimit(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning(SelectFieldOrAsterisk ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Nullable
    public R getReturnedRecord();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Result<R> getReturnedRecords();
}

