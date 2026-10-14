/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImpl3;
import org.jooq.impl.Tools;

final class RecordImpl3<T1, T2, T3>
extends AbstractRecord
implements InternalRecord,
Record3<T1, T2, T3> {
    RecordImpl3(AbstractRow<?> row) {
        super(row);
    }

    @Override
    public RowImpl3<T1, T2, T3> fieldsRow() {
        return new RowImpl3<T1, T2, T3>(this.field1(), this.field2(), this.field3());
    }

    @Override
    public final RowImpl3<T1, T2, T3> valuesRow() {
        return new RowImpl3<T1, T2, T3>(Tools.field(this.value1(), this.field1()), Tools.field(this.value2(), this.field2()), Tools.field(this.value3(), this.field3()));
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
    public final Record3<T1, T2, T3> value1(T1 value) {
        this.set(0, value);
        return this;
    }

    @Override
    public final Record3<T1, T2, T3> value2(T2 value) {
        this.set(1, value);
        return this;
    }

    @Override
    public final Record3<T1, T2, T3> value3(T3 value) {
        this.set(2, value);
        return this;
    }

    @Override
    public final Record3<T1, T2, T3> values(T1 t1, T2 t2, T3 t3) {
        this.set(0, t1);
        this.set(1, t2);
        this.set(2, t3);
        return this;
    }

    @Override
    public <T> Record3<T1, T2, T3> with(Field<T> field2, T value) {
        return (Record3)super.with(field2, value);
    }

    @Override
    public <T, U> Record3<T1, T2, T3> with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (Record3)super.with(field2, value, converter);
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
}

