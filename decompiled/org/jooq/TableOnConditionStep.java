/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Support;
import org.jooq.Table;

public interface TableOnConditionStep<R extends Record>
extends Table<R> {
    @Support
    @NotNull
    public TableOnConditionStep<R> and(Condition var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> and(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TableOnConditionStep<R> andNot(Condition var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> andNot(Field<Boolean> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> andExists(Select<?> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> andNotExists(Select<?> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> or(Condition var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> or(String var1);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public TableOnConditionStep<R> or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public TableOnConditionStep<R> orNot(Condition var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> orNot(Field<Boolean> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> orExists(Select<?> var1);

    @Support
    @NotNull
    public TableOnConditionStep<R> orNotExists(Select<?> var1);
}

