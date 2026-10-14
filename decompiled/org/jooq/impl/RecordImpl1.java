/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImpl1;
import org.jooq.impl.Tools;

final class RecordImpl1<T1>
extends AbstractRecord
implements InternalRecord,
Record1<T1> {
    RecordImpl1(AbstractRow<?> row) {
        super(row);
    }

    @Override
    public RowImpl1<T1> fieldsRow() {
        return new RowImpl1<T1>(this.field1());
    }

    @Override
    public final RowImpl1<T1> valuesRow() {
        return new RowImpl1<T1>(Tools.field(this.value1(), this.field1()));
    }

    @Override
    public final Field<T1> field1() {
        return this.fields.field(0);
    }

    @Override
    public final T1 value1() {
        return (T1)this.get(0);
    }

    @Override
    public final Record1<T1> value1(T1 value) {
        this.set(0, value);
        return this;
    }

    @Override
    public final Record1<T1> values(T1 t1) {
        this.set(0, t1);
        return this;
    }

    @Override
    public <T> Record1<T1> with(Field<T> field2, T value) {
        return (Record1)super.with(field2, value);
    }

    @Override
    public <T, U> Record1<T1> with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (Record1)super.with(field2, value, converter);
    }

    @Override
    public final T1 component1() {
        return this.value1();
    }
}

