/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Named;
import org.jooq.PeriodSpecification;
import org.jooq.PeriodSpecificationBetweenAndStep;
import org.jooq.PeriodSpecificationFromToStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Typed;

@Pro
public interface Period<T>
extends Named,
Typed<T> {
    @Nullable
    public Field<T> getStart();

    @Nullable
    public Field<T> getEnd();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification asOf(T var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification asOf(Field<T> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecificationBetweenAndStep<T> between(T var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecificationBetweenAndStep<T> between(Field<T> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification between(T var1, T var2);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification between(Field<T> var1, Field<T> var2);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecificationFromToStep<T> from(T var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecificationFromToStep<T> from(Field<T> var1);

    @Support(value={SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification containedIn(T var1, T var2);

    @Support(value={SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification containedIn(Field<T> var1, Field<T> var2);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    public PeriodSpecification all();
}

