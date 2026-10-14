/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row16;

public interface Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
extends Record {
    @Override
    @NotNull
    public Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> fieldsRow();

    @Override
    @NotNull
    public Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> valuesRow();

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

    @NotNull
    public Field<T9> field9();

    @NotNull
    public Field<T10> field10();

    @NotNull
    public Field<T11> field11();

    @NotNull
    public Field<T12> field12();

    @NotNull
    public Field<T13> field13();

    @NotNull
    public Field<T14> field14();

    @NotNull
    public Field<T15> field15();

    @NotNull
    public Field<T16> field16();

    public T1 value1();

    public T2 value2();

    public T3 value3();

    public T4 value4();

    public T5 value5();

    public T6 value6();

    public T7 value7();

    public T8 value8();

    public T9 value9();

    public T10 value10();

    public T11 value11();

    public T12 value12();

    public T13 value13();

    public T14 value14();

    public T15 value15();

    public T16 value16();

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value1(T1 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value2(T2 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value3(T3 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value4(T4 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value5(T5 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value6(T6 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value7(T7 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value8(T8 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value9(T9 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value10(T10 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value11(T11 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value12(T12 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value13(T13 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value14(T14 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value15(T15 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value16(T16 var1);

    @NotNull
    public Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16);

    @Override
    @NotNull
    public <T> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();

    public T3 component3();

    public T4 component4();

    public T5 component5();

    public T6 component6();

    public T7 component7();

    public T8 component8();

    public T9 component9();

    public T10 component10();

    public T11 component11();

    public T12 component12();

    public T13 component13();

    public T14 component14();

    public T15 component15();

    public T16 component16();
}

