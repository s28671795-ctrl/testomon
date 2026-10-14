/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Support;
import org.jooq.UpdateLimitStep;

public interface UpdateOrderByStep<R extends Record>
extends UpdateLimitStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public UpdateLimitStep<R> orderBy(OrderField<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateLimitStep<R> orderBy(Collection<? extends OrderField<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateLimitStep<R> orderBy(int ... var1);
}

