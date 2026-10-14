/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.DeleteOrderByStep;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Support;

public interface DeleteConditionStep<R extends Record>
extends DeleteOrderByStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteConditionStep<R> orNotExists(Select<?> var1);
}

