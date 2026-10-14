/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.MergeMatchedSetStep;
import org.jooq.MergeMatchedThenStep;
import org.jooq.MergeNotMatchedStep;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface MergeMatchedStep<R extends Record>
extends MergeNotMatchedStep<R> {
    @Support(value={SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedSetStep<R> whenMatchedThenUpdate();

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedStep<R> whenMatchedThenDelete();

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(Condition var1);

    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(Field<Boolean> var1);

    @PlainSQL
    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(SQL var1);

    @PlainSQL
    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(String var1);

    @PlainSQL
    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(String var1, Object ... var2);

    @PlainSQL
    @Support(value={SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES_15, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public MergeMatchedThenStep<R> whenMatchedAnd(String var1, QueryPart ... var2);
}

