/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.DeleteReturningStep;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Support;

public interface DeleteLimitStep<R extends Record>
extends DeleteReturningStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public DeleteReturningStep<R> limit(Number var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DeleteReturningStep<R> limit(Field<? extends Number> var1);
}

