/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Record;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;

abstract class AbstractStoreQuery<R extends Record, K extends FieldOrRow, V extends FieldOrRowOrSelect>
extends AbstractDMLQuery<R>
implements StoreQuery<R> {
    AbstractStoreQuery(Configuration configuration, WithImpl with, Table<R> table) {
        super(configuration, with, table);
    }

    protected abstract Map<K, V> getValues();

    @Override
    public final void setRecord(R record) {
        for (int i = 0; i < record.size(); ++i) {
            if (!record.changed(i)) continue;
            this.addValue(record.field(i), record.get(i));
        }
    }

    @Override
    public final <T> void addValue(Field<T> field2, T value) {
        this.addValue(field2, -1, value);
    }

    @Override
    public final <T> void addValue(Field<T> field2, Field<T> value) {
        this.addValue(field2, -1, value);
    }

    final <T> void addValue(Field<T> field2, int index, T value) {
        if (field2 == null) {
            if (index >= 0) {
                this.addValue(new UnknownField(index), value);
            } else {
                this.addValue(new UnknownField(this.getValues().size()), value);
            }
        } else {
            this.getValues().put(field2, Tools.field(value, field2));
        }
    }

    final <T> void addValue(Field<T> field2, int index, Field<T> value) {
        if (field2 == null) {
            if (index >= 0) {
                this.addValue((Field<T>)new UnknownField(index), value);
            } else {
                this.addValue((Field<T>)new UnknownField(this.getValues().size()), value);
            }
        } else {
            this.getValues().put(field2, Tools.field(value, field2));
        }
    }

    static class UnknownField<T>
    extends AbstractField<T>
    implements QOM.UTransient {
        private final int index;

        UnknownField(int index) {
            super(DSL.name("unknown field " + index), SQLDataType.OTHER);
            this.index = index;
        }

        @Override
        public void accept(Context<?> ctx) {
            ctx.visit(this.getUnqualifiedName());
        }

        @Override
        public int hashCode() {
            return this.index;
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof UnknownField) {
                UnknownField f = (UnknownField)that;
                return this.index == f.index;
            }
            return false;
        }
    }
}

