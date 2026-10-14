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
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStep1<R extends Record, T1>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> values(T1 var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> values(Field<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> values(Row1<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> values(Record1<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> valuesOfRows(Row1<T1> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> valuesOfRows(Collection<? extends Row1<T1>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> valuesOfRecords(Record1<T1> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStep1<R, T1> valuesOfRecords(Collection<? extends Record1<T1>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record1<T1>> var1);
}

