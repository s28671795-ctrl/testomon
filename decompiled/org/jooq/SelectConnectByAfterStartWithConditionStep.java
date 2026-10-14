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
import org.jooq.SelectGroupByStep;
import org.jooq.Support;

@Pro
public interface SelectConnectByAfterStartWithConditionStep<R extends Record>
extends SelectGroupByStep<R> {
    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(Field<Boolean> var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(SQL var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(String var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(String var1, Object ... var2);

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectConnectByAfterStartWithConditionStep<R> and(String var1, QueryPart ... var2);
}

