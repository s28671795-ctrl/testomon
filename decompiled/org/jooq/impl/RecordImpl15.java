/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record15;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImpl15;
import org.jooq.impl.Tools;

final class RecordImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
extends AbstractRecord
implements InternalRecord,
Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {
    RecordImpl15(AbstractRow<?> row) {
        super(row);
    }

    @Override
    public RowImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> fieldsRow() {
        return new RowImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(this.field1(), this.field2(), this.field3(), this.field4(), this.field5(), this.field6(), this.field7(), this.field8(), this.field9(), this.field10(), this.field11(), this.field12(), this.field13(), this.field14(), this.field15());
    }

    @Override
    public final RowImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> valuesRow() {
        return new RowImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(Tools.field(this.value1(), this.field1()), Tools.field(this.value2(), this.field2()), Tools.field(this.value3(), this.field3()), Tools.field(this.value4(), this.field4()), Tools.field(this.value5(), this.field5()), Tools.field(this.value6(), this.field6()), Tools.field(this.value7(), this.field7()), Tools.field(this.value8(), this.field8()), Tools.field(this.value9(), this.field9()), Tools.field(this.value10(), this.field10()), Tools.field(this.value11(), this.field11()), Tools.field(this.value12(), this.field12()), Tools.field(this.value13(), this.field13()), Tools.field(this.value14(), this.field14()), Tools.field(this.value15(), this.field15()));
    }

    @Override
    public final Field<T1> field1() {
        return this.fields.field(0);
    }

    @Override
    public final Field<T2> field2() {
        return this.fields.field(1);
    }

    @Override
    public final Field<T3> field3() {
        return this.fields.field(2);
    }

    @Override
    public final Field<T4> field4() {
        return this.fields.field(3);
    }

    @Override
    public final Field<T5> field5() {
        return this.fields.field(4);
    }

    @Override
    public final Field<T6> field6() {
        return this.fields.field(5);
    }

    @Override
    public final Field<T7> field7() {
        return this.fields.field(6);
    }

    @Override
    public final Field<T8> field8() {
        return this.fields.field(7);
    }

    @Override
    public final Field<T9> field9() {
        return this.fields.field(8);
    }

    @Override
    public final Field<T10> field10() {
        return this.fields.field(9);
    }

    @Override
    public final Field<T11> field11() {
        return this.fields.field(10);
    }

    @Override
    public final Field<T12> field12() {
        return this.fields.field(11);
    }

    @Override
    public final Field<T13> field13() {
        return this.fields.field(12);
    }

    @Override
    public final Field<T14> field14() {
        return this.fields.field(13);
    }

    @Override
    public final Field<T15> field15() {
        return this.fields.field(14);
    }

    @Override
    public final T1 value1() {
        return (T1)this.get(0);
    }

    @Override
    public final T2 value2() {
        return (T2)this.get(1);
    }

    @Override
    public final T3 value3() {
        return (T3)this.get(2);
    }

    @Override
    public final T4 value4() {
        return (T4)this.get(3);
    }

    @Override
    public final T5 value5() {
        return (T5)this.get(4);
    }

    @Override
    public final T6 value6() {
        return (T6)this.get(5);
    }

    @Override
    public final T7 value7() {
        return (T7)this.get(6);
    }

    @Override
    public final T8 value8() {
        return (T8)this.get(7);
    }

    @Override
    public final T9 value9() {
        return (T9)this.get(8);
    }

    @Override
    public final T10 value10() {
        return (T10)this.get(9);
    }

    @Override
    public final T11 value11() {
        return (T11)this.get(10);
    }

    @Override
    public final T12 value12() {
        return (T12)this.get(11);
    }

    @Override
    public final T13 value13() {
        return (T13)this.get(12);
    }

    @Override
    public final T14 value14() {
        return (T14)this.get(13);
    }

    @Override
    public final T15 value15() {
        return (T15)this.get(14);
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value1(T1 value) {
        this.set(0, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value2(T2 value) {
        this.set(1, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value3(T3 value) {
        this.set(2, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value4(T4 value) {
        this.set(3, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value5(T5 value) {
        this.set(4, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value6(T6 value) {
        this.set(5, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value7(T7 value) {
        this.set(6, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value8(T8 value) {
        this.set(7, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value9(T9 value) {
        this.set(8, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value10(T10 value) {
        this.set(9, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value11(T11 value) {
        this.set(10, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value12(T12 value) {
        this.set(11, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value13(T13 value) {
        this.set(12, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value14(T14 value) {
        this.set(13, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value15(T15 value) {
        this.set(14, value);
        return this;
    }

    @Override
    public final Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        this.set(0, t1);
        this.set(1, t2);
        this.set(2, t3);
        this.set(3, t4);
        this.set(4, t5);
        this.set(5, t6);
        this.set(6, t7);
        this.set(7, t8);
        this.set(8, t9);
        this.set(9, t10);
        this.set(10, t11);
        this.set(11, t12);
        this.set(12, t13);
        this.set(13, t14);
        this.set(14, t15);
        return this;
    }

    @Override
    public <T> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> with(Field<T> field2, T value) {
        return (Record15)super.with(field2, value);
    }

    @Override
    public <T, U> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (Record15)super.with(field2, value, converter);
    }

    @Override
    public final T1 component1() {
        return this.value1();
    }

    @Override
    public final T2 component2() {
        return this.value2();
    }

    @Override
    public final T3 component3() {
        return this.value3();
    }

    @Override
    public final T4 component4() {
        return this.value4();
    }

    @Override
    public final T5 component5() {
        return this.value5();
    }

    @Override
    public final T6 component6() {
        return this.value6();
    }

    @Override
    public final T7 component7() {
        return this.value7();
    }

    @Override
    public final T8 component8() {
        return this.value8();
    }

    @Override
    public final T9 component9() {
        return this.value9();
    }

    @Override
    public final T10 component10() {
        return this.value10();
    }

    @Override
    public final T11 component11() {
        return this.value11();
    }

    @Override
    public final T12 component12() {
        return this.value12();
    }

    @Override
    public final T13 component13() {
        return this.value13();
    }

    @Override
    public final T14 component14() {
        return this.value14();
    }

    @Override
    public final T15 component15() {
        return this.value15();
    }
}

