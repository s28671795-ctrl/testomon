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
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectWindowStep;
import org.jooq.Support;

public interface SelectHavingStep<R extends Record>
extends SelectWindowStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(Condition var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(Condition ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(Collection<? extends Condition> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectHavingConditionStep<R> having(String var1, QueryPart ... var2);
}

