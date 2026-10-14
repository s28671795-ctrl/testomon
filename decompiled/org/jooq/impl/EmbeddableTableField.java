/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.EmbeddableRecord;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RecordDataType;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;

final class EmbeddableTableField<R extends Record, E extends EmbeddableRecord<E>>
extends AbstractField<E>
implements TableField<R, E>,
QOM.UNotYetImplemented {
    final Class<E> recordType;
    final boolean replacesFields;
    final Table<R> table;
    @Deprecated
    final TableField<R, ?>[] fields;

    EmbeddableTableField(Name name, Class<E> recordType, boolean replacesFields, Table<R> table, TableField<R, ?>[] fields) {
        super(name, new RecordDataType<E>(Tools.row0(fields), recordType, name.last()));
        this.recordType = recordType;
        this.replacesFields = replacesFields;
        this.table = table;
        this.fields = fields;
        if (replacesFields && table instanceof TableImpl) {
            ((TableImpl)table).fields.replace(fields, this);
        }
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (AbstractRowAsField.forceMultisetContent(ctx, () -> this.getDataType().getRow().size() > 1)) {
            AbstractRowAsField.acceptMultisetContent(ctx, this.getDataType().getRow(), this, this::acceptDefault);
        } else {
            this.acceptDefault(ctx);
        }
    }

    private void acceptDefault(Context<?> ctx) {
        ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c -> c.visit(QueryPartListView.wrap((QueryPart[])this.getDataType().getRow().fields())));
    }

    @Override
    public final Table<R> getTable() {
        return this.table;
    }

    @Override
    int projectionSize() {
        int result = 0;
        for (Field<?> field2 : ((AbstractRow)this.getDataType().getRow()).fields.fields) {
            result += ((AbstractField)field2).projectionSize();
        }
        return result;
    }
}

