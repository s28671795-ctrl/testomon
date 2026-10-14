/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.GroupField;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectHavingStep;
import org.jooq.Support;

public interface SelectGroupByStep<R extends Record>
extends SelectHavingStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingStep<R> groupBy(GroupField ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectHavingStep<R> groupBy(Collection<? extends GroupField> var1);

    @Support(value={SQLDialect.POSTGRES_14})
    @NotNull
    @CheckReturnValue
    public SelectHavingStep<R> groupByDistinct(GroupField ... var1);

    @Support(value={SQLDialect.POSTGRES_14})
    @NotNull
    @CheckReturnValue
    public SelectHavingStep<R> groupByDistinct(Collection<? extends GroupField> var1);
}

