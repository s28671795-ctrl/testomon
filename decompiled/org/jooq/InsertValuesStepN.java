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
import org.jooq.RowN;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertValuesStepN<R extends Record>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> values(Object ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> values(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> values(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> values(RowN var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> values(Record var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> valuesOfRows(RowN ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> valuesOfRows(Collection<? extends RowN> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> valuesOfRecords(Record ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertValuesStepN<R> valuesOfRecords(Collection<? extends Record> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertOnDuplicateStep<R> select(Select<? extends Record> var1);
}

