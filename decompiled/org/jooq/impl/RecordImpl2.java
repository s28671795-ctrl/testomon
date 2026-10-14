/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImpl2;
import org.jooq.impl.Tools;

final class RecordImpl2<T1, T2>
extends AbstractRecord
implements InternalRecord,
Record2<T1, T2> {
    RecordImpl2(AbstractRow<?> row) {
        super(row);
    }

    @Override
    public RowImpl2<T1, T2> fieldsRow() {
        return new RowImpl2<T1, T2>(this.field1(), this.field2());
    }

    @Override
    public final RowImpl2<T1, T2> valuesRow() {
        return new RowImpl2<T1, T2>(Tools.field(this.value1(), this.field1()), Tools.field(this.value2(), this.field2()));
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
    public final T1 value1() {
        return (T1)this.get(0);
    }

    @Override
    public final T2 value2() {
        return (T2)this.get(1);
    }

    @Override
    public final Record2<T1, T2> value1(T1 value) {
        this.set(0, value);
        return this;
    }

    @Override
    public final Record2<T1, T2> value2(T2 value) {
        this.set(1, value);
        return this;
    }

    @Override
    public final Record2<T1, T2> values(T1 t1, T2 t2) {
        this.set(0, t1);
        this.set(1, t2);
        return this;
    }

    @Override
    public <T> Record2<T1, T2> with(Field<T> field2, T value) {
        return (Record2)super.with(field2, value);
    }

    @Override
    public <T, U> Record2<T1, T2> with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (Record2)super.with(field2, value, converter);
    }

    @Override
    public final T1 component1() {
        return this.value1();
    }

    @Override
    public final T2 component2() {
        return this.value2();
    }
}

