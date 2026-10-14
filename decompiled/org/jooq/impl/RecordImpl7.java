/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImpl7;
import org.jooq.impl.Tools;

final class RecordImpl7<T1, T2, T3, T4, T5, T6, T7>
extends AbstractRecord
implements InternalRecord,
Record7<T1, T2, T3, T4, T5, T6, T7> {
    RecordImpl7(AbstractRow<?> row) {
        super(row);
    }

    @Override
    public RowImpl7<T1, T2, T3, T4, T5, T6, T7> fieldsRow() {
        return new RowImpl7<T1, T2, T3, T4, T5, T6, T7>(this.field1(), this.field2(), this.field3(), this.field4(), this.field5(), this.field6(), this.field7());
    }

    @Override
    public final RowImpl7<T1, T2, T3, T4, T5, T6, T7> valuesRow() {
        return new RowImpl7<T1, T2, T3, T4, T5, T6, T7>(Tools.field(this.value1(), this.field1()), Tools.field(this.value2(), this.field2()), Tools.field(this.value3(), this.field3()), Tools.field(this.value4(), this.field4()), Tools.field(this.value5(), this.field5()), Tools.field(this.value6(), this.field6()), Tools.field(this.value7(), this.field7()));
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
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value1(T1 value) {
        this.set(0, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value2(T2 value) {
        this.set(1, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value3(T3 value) {
        this.set(2, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value4(T4 value) {
        this.set(3, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value5(T5 value) {
        this.set(4, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value6(T6 value) {
        this.set(5, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> value7(T7 value) {
        this.set(6, value);
        return this;
    }

    @Override
    public final Record7<T1, T2, T3, T4, T5, T6, T7> values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        this.set(0, t1);
        this.set(1, t2);
        this.set(2, t3);
        this.set(3, t4);
        this.set(4, t5);
        this.set(5, t6);
        this.set(6, t7);
        return this;
    }

    @Override
    public <T> Record7<T1, T2, T3, T4, T5, T6, T7> with(Field<T> field2, T value) {
        return (Record7)super.with(field2, value);
    }

    @Override
    public <T, U> Record7<T1, T2, T3, T4, T5, T6, T7> with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (Record7)super.with(field2, value, converter);
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
}

