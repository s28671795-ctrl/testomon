/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row3;

public interface Record3<T1, T2, T3>
extends Record {
    @Override
    @NotNull
    public Row3<T1, T2, T3> fieldsRow();

    @Override
    @NotNull
    public Row3<T1, T2, T3> valuesRow();

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    @NotNull
    public Field<T3> field3();

    public T1 value1();

    public T2 value2();

    public T3 value3();

    @NotNull
    public Record3<T1, T2, T3> value1(T1 var1);

    @NotNull
    public Record3<T1, T2, T3> value2(T2 var1);

    @NotNull
    public Record3<T1, T2, T3> value3(T3 var1);

    @NotNull
    public Record3<T1, T2, T3> values(T1 var1, T2 var2, T3 var3);

    @Override
    @NotNull
    public <T> Record3<T1, T2, T3> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record3<T1, T2, T3> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();

    public T3 component3();
}

