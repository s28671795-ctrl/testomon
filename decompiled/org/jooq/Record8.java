/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row8;

public interface Record8<T1, T2, T3, T4, T5, T6, T7, T8>
extends Record {
    @Override
    @NotNull
    public Row8<T1, T2, T3, T4, T5, T6, T7, T8> fieldsRow();

    @Override
    @NotNull
    public Row8<T1, T2, T3, T4, T5, T6, T7, T8> valuesRow();

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

    @NotNull
    public Field<T6> field6();

    @NotNull
    public Field<T7> field7();

    @NotNull
    public Field<T8> field8();

    public T1 value1();

    public T2 value2();

    public T3 value3();

    public T4 value4();

    public T5 value5();

    public T6 value6();

    public T7 value7();

    public T8 value8();

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value1(T1 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value2(T2 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value3(T3 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value4(T4 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value5(T5 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value6(T6 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value7(T7 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> value8(T8 var1);

    @NotNull
    public Record8<T1, T2, T3, T4, T5, T6, T7, T8> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    @Override
    @NotNull
    public <T> Record8<T1, T2, T3, T4, T5, T6, T7, T8> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record8<T1, T2, T3, T4, T5, T6, T7, T8> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();

    public T3 component3();

    public T4 component4();

    public T5 component5();

    public T6 component6();

    public T7 component7();

    public T8 component8();
}

