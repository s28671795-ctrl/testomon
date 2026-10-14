/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AggregateFilterStep;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface OrderedAggregateFunctionOfDeferredType {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T> AggregateFilterStep<T> withinGroupOrderBy(OrderField<T> var1);
}

