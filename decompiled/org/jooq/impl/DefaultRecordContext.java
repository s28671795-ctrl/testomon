/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;

class DefaultRecordContext
extends AbstractScope
implements RecordContext {
    private final ExecuteType type;
    private final Record record;
    Exception exception;

    DefaultRecordContext(Configuration configuration, ExecuteType type, Record record) {
        super(configuration);
        this.type = type;
        this.record = record;
    }

    @Override
    public final ExecuteType type() {
        return this.type;
    }

    @Override
    public final Record record() {
        return this.record;
    }

    @Override
    public final Record[] batchRecords() {
        return new Record[]{this.record};
    }

    @Override
    public final RecordType<?> recordType() {
        return new FieldsImpl(this.record.fields());
    }

    @Override
    public final Exception exception() {
        return this.exception;
    }

    public String toString() {
        Result<Record> result = DSL.using(this.configuration).newResult(this.record.fields());
        result.add(this.record);
        return result.toString();
    }
}

