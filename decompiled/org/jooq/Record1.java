/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row1;

public interface Record1<T1>
extends Record {
    @Override
    @NotNull
    public Row1<T1> fieldsRow();

    @Override
    @NotNull
    public Row1<T1> valuesRow();

    @NotNull
    public Field<T1> field1();

    public T1 value1();

    @NotNull
    public Record1<T1> value1(T1 var1);

    @NotNull
    public Record1<T1> values(T1 var1);

    @Override
    @NotNull
    public <T> Record1<T1> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record1<T1> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();
}

