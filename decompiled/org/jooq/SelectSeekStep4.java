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

public interface SelectSeekStep4<R extends Record, T1, T2, T3, T4>
extends SelectLimitStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seek(T1 var1, T2 var2, T3 var3, T4 var4);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seek(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekAfter(T1 var1, T2 var2, T3 var3, T4 var4);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekAfter(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @Deprecated
    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekBefore(T1 var1, T2 var2, T3 var3, T4 var4);

    @Deprecated
    @Support
    @NotNull
    @CheckReturnValue
    public SelectSeekLimitStep<R> seekBefore(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);
}

