/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.EmbeddableRecord;
import org.jooq.Field;
import org.jooq.Internal;
import org.jooq.Row;
import org.jooq.TableField;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.Tools;

@Internal
public class EmbeddableRecordImpl<R extends EmbeddableRecord<R>>
extends AbstractRecord
implements EmbeddableRecord<R> {
    @Deprecated
    public EmbeddableRecordImpl(Field<?> ... fields) {
        super(fields);
    }

    @Deprecated
    public EmbeddableRecordImpl(TableField<?, ?> ... fields) {
        super(fields);
    }

    public EmbeddableRecordImpl(Row fields) {
        super((AbstractRow)fields);
    }

    @Override
    public final <T> R with(Field<T> field2, T value) {
        return (R)((EmbeddableRecord)super.with(field2, value));
    }

    @Override
    public final <T, U> R with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (R)((EmbeddableRecord)super.with(field2, value, converter));
    }

    @Override
    public Row fieldsRow() {
        return this.fields;
    }

    @Override
    public Row valuesRow() {
        return Tools.row0(Tools.fieldsArray(this.intoArray(), this.fields.fields.fields()));
    }

    @Override
    public final R original() {
        return (R)((EmbeddableRecord)super.original());
    }
}

