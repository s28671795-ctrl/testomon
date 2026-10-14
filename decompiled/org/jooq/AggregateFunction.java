/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.AggregateFilterStep;
import org.jooq.Condition;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AggregateFunction<T>
extends AggregateFilterStep<T> {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public AggregateFilterStep<T> keepDenseRankFirstOrderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public AggregateFilterStep<T> keepDenseRankFirstOrderBy(Collection<? extends OrderField<?>> var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public AggregateFilterStep<T> keepDenseRankLastOrderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public AggregateFilterStep<T> keepDenseRankLastOrderBy(Collection<? extends OrderField<?>> var1);

    @ApiStatus.Experimental
    @Nullable
    public Condition $filterWhere();
}

