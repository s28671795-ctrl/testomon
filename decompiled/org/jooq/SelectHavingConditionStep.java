/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectWindowStep;
import org.jooq.Support;

public interface SelectHavingConditionStep<R extends Record>
extends SelectWindowStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> orNotExists(Select<?> var1);
}

