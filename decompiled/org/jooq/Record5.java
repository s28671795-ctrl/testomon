/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row5;

public interface Record5<T1, T2, T3, T4, T5>
extends Record {
    @Override
    @NotNull
    public Row5<T1, T2, T3, T4, T5> fieldsRow();

    @Override
    @NotNull
    public Row5<T1, T2, T3, T4, T5> valuesRow();

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    @NotNull
    public Field<T3> field3();

    @NotNull
    public Field<T4> field4();

    @NotNull
    public Field<T5> field5();

    public T1 value1();

    public T2 value2();

    public T3 value3();

    public T4 value4();

    public T5 value5();

    @NotNull
    public Record5<T1, T2, T3, T4, T5> value1(T1 var1);

    @NotNull
    public Record5<T1, T2, T3, T4, T5> value2(T2 var1);

    @NotNull
    public Record5<T1, T2, T3, T4, T5> value3(T3 var1);

    @NotNull
    public Record5<T1, T2, T3, T4, T5> value4(T4 var1);

    @NotNull
    public Record5<T1, T2, T3, T4, T5> value5(T5 var1);

    @NotNull
    public Record5<T1, T2, T3, T4, T5> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    @Override
    @NotNull
    public <T> Record5<T1, T2, T3, T4, T5> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record5<T1, T2, T3, T4, T5> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();

    public T3 component3();

    public T4 component4();

    public T5 component5();
}

