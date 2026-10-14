/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.GroupField;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SortField;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.WindowDefinition;
import org.jooq.impl.QOM;

public interface Select<R extends Record>
extends ResultQuery<R>,
TableLike<R>,
FieldLike,
FieldOrRowOrSelect {
    @Support
    @NotNull
    @CheckReturnValue
    public Select<R> union(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Select<R> unionAll(Select<? extends R> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Select<R> except(Select<? extends R> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE20C, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Select<R> exceptAll(Select<? extends R> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Select<R> intersect(Select<? extends R> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_8_0_31, SQLDialect.ORACLE20C, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Select<R> intersectAll(Select<? extends R> var1);

    @NotNull
    @CheckReturnValue
    public List<Field<?>> getSelect();

    @ApiStatus.Experimental
    @Nullable
    public QOM.With $with();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<?> $with(QOM.With var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $select();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<?> $select(Collection<? extends SelectFieldOrAsterisk> var1);

    @ApiStatus.Experimental
    public boolean $distinct();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $distinct(boolean var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $distinctOn();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $distinctOn(Collection<? extends SelectFieldOrAsterisk> var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Table<?>> $from();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $from(Collection<? extends Table<?>> var1);

    @ApiStatus.Experimental
    @Nullable
    public Condition $where();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $where(Condition var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends GroupField> $groupBy();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $groupBy(Collection<? extends GroupField> var1);

    @ApiStatus.Experimental
    public boolean $groupByDistinct();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $groupByDistinct(boolean var1);

    @ApiStatus.Experimental
    @Nullable
    public Condition $having();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $having(Condition var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends WindowDefinition> $window();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $window(Collection<? extends WindowDefinition> var1);

    @ApiStatus.Experimental
    @Nullable
    public Condition $qualify();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $qualify(Condition var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends SortField<?>> $orderBy();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $orderBy(Collection<? extends SortField<?>> var1);

    @ApiStatus.Experimental
    @Nullable
    public Field<? extends Number> $limit();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $limit(Field<? extends Number> var1);

    @ApiStatus.Experimental
    public boolean $limitPercent();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $limitPercent(boolean var1);

    @ApiStatus.Experimental
    public boolean $limitWithTies();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $limitWithTies(boolean var1);

    @ApiStatus.Experimental
    @Nullable
    public Field<? extends Number> $offset();

    @ApiStatus.Experimental
    @CheckReturnValue
    @NotNull
    public Select<R> $offset(Field<? extends Number> var1);

    @ApiStatus.Experimental
    @Pro
    @Nullable
    public Condition $connectBy();

    @ApiStatus.Experimental
    @CheckReturnValue
    @Pro
    @NotNull
    public Select<R> $connectBy(Condition var1);

    @ApiStatus.Experimental
    @Pro
    public boolean $connectByNoCycle();

    @ApiStatus.Experimental
    @CheckReturnValue
    @Pro
    @NotNull
    public Select<R> $connectByNoCycle(boolean var1);

    @ApiStatus.Experimental
    @Pro
    @Nullable
    public Condition $connectByStartWith();

    @ApiStatus.Experimental
    @CheckReturnValue
    @Pro
    @NotNull
    public Select<R> $connectByStartWith(Condition var1);
}

