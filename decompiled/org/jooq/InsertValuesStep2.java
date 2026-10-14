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
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep2<R extends Record, T1, T2>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> values(T1 var1, T2 var2);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> values(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> values(Row2<T1, T2> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> values(Record2<T1, T2> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> valuesOfRows(Row2<T1, T2> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> valuesOfRows(Collection<? extends Row2<T1, T2>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> valuesOfRecords(Record2<T1, T2> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep2<R, T1, T2> valuesOfRecords(Collection<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record2<T1, T2>> var1);
}

