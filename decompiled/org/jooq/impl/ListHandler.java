/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.sql.Struct;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.Tools;

@Pro
final class ListHandler<R extends Record> {
    private final DSLContext ctx;
    private final AbstractRow<R> row;
    private final Class<? extends R> recordType;

    ListHandler(DSLContext ctx, AbstractRow<R> row, Class<? extends R> recordType) {
        this.ctx = ctx;
        this.row = row;
        this.recordType = recordType;
    }

    final Result<R> read(List<Struct> list) throws SQLException {
        ResultImpl result = new ResultImpl(this.ctx.configuration(), (AbstractRow)this.row);
        for (Struct s2 : list) {
            result.add(Tools.newRecord(true, this.recordType, this.row, this.ctx.configuration()).operate(r -> {
                Object[] attributes = s2.getAttributes();
                for (int i = 0; i < attributes.length && i < this.row.size(); ++i) {
                    DataType t2 = this.row.field(i).getDataType();
                    if (t2.isMultiset() && attributes[i] instanceof List) {
                        attributes[i] = new ListHandler<Record>(this.ctx, (AbstractRow)t2.getRow(), t2.getRecordType()).read((List)attributes[i]);
                        continue;
                    }
                    if (!t2.isRecord() || !(attributes[i] instanceof Struct)) continue;
                    attributes[i] = new ListHandler<Record>(this.ctx, (AbstractRow)t2.getRow(), t2.getRecordType()).read(Arrays.asList((Struct)attributes[i])).get(0);
                }
                r.fromArray(attributes);
                r.changed(false);
                return r;
            }));
        }
        return result;
    }
}

