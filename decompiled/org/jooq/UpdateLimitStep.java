/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Support;
import org.jooq.UpdateReturningStep;

public interface UpdateLimitStep<R extends Record>
extends UpdateReturningStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public UpdateReturningStep<R> limit(Number var1);

    @Support
    @NotNull
    @CheckReturnValue
    public UpdateReturningStep<R> limit(Field<? extends Number> var1);
}

