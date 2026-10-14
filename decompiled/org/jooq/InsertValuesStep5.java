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
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep5<R extends Record, T1, T2, T3, T4, T5>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Row5<T1, T2, T3, T4, T5> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Record5<T1, T2, T3, T4, T5> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> valuesOfRows(Row5<T1, T2, T3, T4, T5> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> valuesOfRows(Collection<? extends Row5<T1, T2, T3, T4, T5>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> valuesOfRecords(Record5<T1, T2, T3, T4, T5> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep5<R, T1, T2, T3, T4, T5> valuesOfRecords(Collection<? extends Record5<T1, T2, T3, T4, T5>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);
}

