/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.InsertOnDuplicateStep;
import org.jooq.InsertSetStep;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.Support;

public interface InsertSetMoreStep<R extends Record>
extends InsertOnDuplicateStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public <T> InsertSetMoreStep<R> set(Field<T> var1, T var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T> InsertSetMoreStep<R> set(Field<T> var1, Field<T> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T> InsertSetMoreStep<R> set(Field<T> var1, Select<? extends Record1<T>> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T> InsertSetMoreStep<R> setNull(Field<T> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertSetMoreStep<R> set(Map<?, ?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertSetMoreStep<R> set(Record var1);

    @Support
    @NotNull
    @CheckReturnValue
    public InsertSetStep<R> newRecord();
}

