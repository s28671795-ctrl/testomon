/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row2;

public interface Record2<T1, T2>
extends Record {
    @Override
    @NotNull
    public Row2<T1, T2> fieldsRow();

    @Override
    @NotNull
    public Row2<T1, T2> valuesRow();

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    public T1 value1();

    public T2 value2();

    @NotNull
    public Record2<T1, T2> value1(T1 var1);

    @NotNull
    public Record2<T1, T2> value2(T2 var1);

    @NotNull
    public Record2<T1, T2> values(T1 var1, T2 var2);

    @Override
    @NotNull
    public <T> Record2<T1, T2> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record2<T1, T2> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();
}

