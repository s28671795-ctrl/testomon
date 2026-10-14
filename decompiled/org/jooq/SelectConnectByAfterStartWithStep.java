/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SelectConnectByAfterStartWithConditionStep;
import org.jooq.Support;

@Pro
public interface SelectConnectByAfterStartWithStep<R extends Record> {
    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(Field<Boolean> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(SQL var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(String var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(String var1, Object ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectBy(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(Field<Boolean> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(SQL var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(String var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(String var1, Object ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> connectByNoCycle(String var1, QueryPart ... var2);
}

