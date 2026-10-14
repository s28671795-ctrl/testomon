/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateSequenceFlagsStep
extends CreateSequenceFinalStep {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep startWith(Number var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep startWith(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep incrementBy(Number var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep incrementBy(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep minvalue(Number var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep minvalue(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep noMinvalue();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep maxvalue(Number var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep maxvalue(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep noMaxvalue();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep cycle();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep noCycle();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep cache(Number var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep cache(Field<? extends Number> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep noCache();
}

