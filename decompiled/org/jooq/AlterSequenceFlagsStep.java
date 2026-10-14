/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterSequenceFinalStep;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterSequenceFlagsStep<T extends Number>
extends AlterSequenceFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> restart();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> restartWith(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> restartWith(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_3, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> startWith(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_3, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> startWith(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> incrementBy(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> incrementBy(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> minvalue(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> minvalue(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> noMinvalue();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> maxvalue(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> maxvalue(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> noMaxvalue();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> cycle();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> noCycle();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> cache(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> cache(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceFlagsStep<T> noCache();
}

