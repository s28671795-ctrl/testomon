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
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep7<R extends Record, T1, T2, T3, T4, T5, T6, T7>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> valuesOfRows(Row7<T1, T2, T3, T4, T5, T6, T7> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> valuesOfRows(Collection<? extends Row7<T1, T2, T3, T4, T5, T6, T7>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> valuesOfRecords(Record7<T1, T2, T3, T4, T5, T6, T7> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> valuesOfRecords(Collection<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);
}

