/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSeekLimitStep;
import org.jooq.Support;

public interface SelectSeekStepN<R extends Record>
extends SelectLimitStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seek(Object ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seek(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekAfter(Object ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekAfter(Field<?> ... var1);

    @Support
    @Deprecated
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekBefore(Object ... var1);

    @Support
    @Deprecated
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekBefore(Field<?> ... var1);
}

