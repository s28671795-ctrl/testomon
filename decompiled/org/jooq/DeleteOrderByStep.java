/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.DeleteLimitStep;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Support;

public interface DeleteOrderByStep<R extends Record>
extends DeleteLimitStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public DeleteLimitStep<R> orderBy(OrderField<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteLimitStep<R> orderBy(Collection<? extends OrderField<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteLimitStep<R> orderBy(int ... var1);
}

