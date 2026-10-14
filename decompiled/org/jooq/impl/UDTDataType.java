/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.LazyName;

final class UDTDataType<R extends UDTRecord<R>>
extends DefaultDataType<R> {
    final UDT<R> udt;

    UDTDataType(UDT<R> udt) {
        super(SQLDialect.DEFAULT, udt.getRecordType(), new LazyName(() -> UDTDataType.lazyName(udt::getSchema, udt::getUnqualifiedName)));
        this.udt = udt;
    }

    @Override
    public final Row getRow() {
        return this.udt.fieldsRow();
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return this.udt.getRecordType();
    }
}

