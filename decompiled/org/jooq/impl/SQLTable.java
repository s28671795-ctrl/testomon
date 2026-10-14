/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SelectField;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;

final class SQLTable
extends AbstractTable<Record>
implements QOM.UEmptyTable<Record> {
    private final SQL delegate;

    SQLTable(SQL delegate) {
        super(TableOptions.expression(), DSL.name(delegate.toString()));
        this.delegate = delegate;
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("table(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }

    @Override
    final FieldsImpl<Record> fields0() {
        return new FieldsImpl<Record>(new SelectField[0]);
    }
}

