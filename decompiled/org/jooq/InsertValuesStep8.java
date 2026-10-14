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
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep8<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> valuesOfRows(Row8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> valuesOfRows(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> valuesOfRecords(Record8<T1, T2, T3, T4, T5, T6, T7, T8> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> valuesOfRecords(Collection<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);
}

