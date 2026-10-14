/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.AggregateFilterStep;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface ArrayAggOrderByStep<T>
extends AggregateFilterStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public AggregateFilterStep<T> orderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public AggregateFilterStep<T> orderBy(Collection<? extends OrderField<?>> var1);
}

