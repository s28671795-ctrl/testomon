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
import org.jooq.SelectConditionStep;
import org.jooq.SelectConnectByStep;
import org.jooq.Support;

public interface SelectWhereStep<R extends Record>
extends SelectConnectByStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(Condition ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(Collection<? extends Condition> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> where(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> whereExists(Select<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectConditionStep<R> whereNotExists(Select<?> var1);
}

