/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Support;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateOrderByStep;

public interface UpdateWhereStep<R extends Record>
extends UpdateOrderByStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(Condition ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(Collection<? extends Condition> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> where(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> whereExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateConditionStep<R> whereNotExists(Select<?> var1);
}

