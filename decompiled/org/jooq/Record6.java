/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row6;

public interface Record6<T1, T2, T3, T4, T5, T6>
extends Record {
    @Override
    @NotNull
    public Row6<T1, T2, T3, T4, T5, T6> fieldsRow();

    @Override
    @NotNull
    public Row6<T1, T2, T3, T4, T5, T6> valuesRow();

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

    public T1 value1();

    public T2 value2();

    public T3 value3();

    public T4 value4();

    public T5 value5();

    public T6 value6();

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value1(T1 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value2(T2 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value3(T3 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value4(T4 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value5(T5 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> value6(T6 var1);

    @NotNull
    public Record6<T1, T2, T3, T4, T5, T6> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

    @Override
    @NotNull
    public <T> Record6<T1, T2, T3, T4, T5, T6> with(Field<T> var1, T var2);

    @Override
    @NotNull
    public <T, U> Record6<T1, T2, T3, T4, T5, T6> with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

    public T1 component1();

    public T2 component2();

    public T3 component3();

    public T4 component4();

    public T5 component5();

    public T6 component6();
}

