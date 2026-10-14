/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.WindowExcludeStep;
import org.jooq.WindowFinalStep;
import org.jooq.WindowRowsAndStep;

public interface WindowRowsStep<T>
extends WindowFinalStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rowsUnboundedPreceding();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rowsPreceding(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rowsCurrentRow();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rowsUnboundedFollowing();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rowsFollowing(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rowsBetweenUnboundedPreceding();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rowsBetweenPreceding(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rowsBetweenCurrentRow();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rowsBetweenUnboundedFollowing();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rowsBetweenFollowing(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rangeUnboundedPreceding();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rangePreceding(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rangeCurrentRow();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rangeUnboundedFollowing();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> rangeFollowing(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rangeBetweenUnboundedPreceding();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rangeBetweenPreceding(int var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rangeBetweenCurrentRow();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rangeBetweenUnboundedFollowing();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> rangeBetweenFollowing(int var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> groupsUnboundedPreceding();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> groupsPreceding(int var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> groupsCurrentRow();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> groupsUnboundedFollowing();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowExcludeStep<T> groupsFollowing(int var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> groupsBetweenUnboundedPreceding();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> groupsBetweenPreceding(int var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> groupsBetweenCurrentRow();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> groupsBetweenUnboundedFollowing();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE20C, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowRowsAndStep<T> groupsBetweenFollowing(int var1);
}

