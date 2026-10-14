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

public interface XMLAggOrderByStep<T>
extends AggregateFilterStep<T> {
    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public AggregateFilterStep<T> orderBy(OrderField<?> ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public AggregateFilterStep<T> orderBy(Collection<? extends OrderField<?>> var1);
}

