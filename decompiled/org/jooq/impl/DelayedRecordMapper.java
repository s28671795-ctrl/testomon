/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Function;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.FieldsImpl;

final class DelayedRecordMapper<R extends Record, E>
implements RecordMapper<R, E> {
    final Function<FieldsImpl<R>, RecordMapper<R, E>> init;
    RecordMapper<R, E> delegate;

    DelayedRecordMapper(Function<FieldsImpl<R>, RecordMapper<R, E>> init2) {
        this.init = init2;
    }

    @Override
    public final E map(R record) {
        if (this.delegate == null) {
            this.delegate = this.init.apply(((AbstractRecord)record).fields.fields);
        }
        return this.delegate.map(record);
    }
}

