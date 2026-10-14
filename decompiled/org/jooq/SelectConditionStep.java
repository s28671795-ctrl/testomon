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
import org.jooq.SelectConnectByStep;
import org.jooq.Support;

public interface SelectConditionStep<R extends Record>
extends SelectConnectByStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> orNotExists(Select<?> var1);
}

