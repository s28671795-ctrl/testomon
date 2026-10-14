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
import org.jooq.SelectJoinStep;
import org.jooq.Support;

public interface SelectOnConditionStep<R extends Record>
extends SelectJoinStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectOnConditionStep<R> orNotExists(Select<?> var1);
}

