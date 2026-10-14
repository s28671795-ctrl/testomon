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
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep6<R extends Record, T1, T2, T3, T4, T5, T6>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Row6<T1, T2, T3, T4, T5, T6> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Record6<T1, T2, T3, T4, T5, T6> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> valuesOfRows(Row6<T1, T2, T3, T4, T5, T6> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> valuesOfRows(Collection<? extends Row6<T1, T2, T3, T4, T5, T6>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> valuesOfRecords(Record6<T1, T2, T3, T4, T5, T6> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> valuesOfRecords(Collection<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);
}

