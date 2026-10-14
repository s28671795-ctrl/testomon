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
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep3<R extends Record, T1, T2, T3>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> values(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> values(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> values(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> values(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> valuesOfRows(Row3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> valuesOfRows(Collection<? extends Row3<T1, T2, T3>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> valuesOfRecords(Record3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep3<R, T1, T2, T3> valuesOfRecords(Collection<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record3<T1, T2, T3>> var1);
}

