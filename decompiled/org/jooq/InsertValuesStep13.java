/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.InsertOnDuplicateStep;
import org.jooq.Record;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep13<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> values(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> valuesOfRows(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> valuesOfRows(Collection<? extends Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> valuesOfRecords(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> valuesOfRecords(Collection<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var1);
}

