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
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep4<R extends Record, T1, T2, T3, T4>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> values(T1 var1, T2 var2, T3 var3, T4 var4);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> values(Row4<T1, T2, T3, T4> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> values(Record4<T1, T2, T3, T4> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> valuesOfRows(Row4<T1, T2, T3, T4> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> valuesOfRows(Collection<? extends Row4<T1, T2, T3, T4>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> valuesOfRecords(Record4<T1, T2, T3, T4> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep4<R, T1, T2, T3, T4> valuesOfRecords(Collection<? extends Record4<T1, T2, T3, T4>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record4<T1, T2, T3, T4>> var1);
}

