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
import org.jooq.SelectOrderByStep;
import org.jooq.Support;

public interface SelectQualifyConditionStep<R extends Record>
extends SelectOrderByStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQualifyConditionStep<R> orNotExists(Select<?> var1);
}

