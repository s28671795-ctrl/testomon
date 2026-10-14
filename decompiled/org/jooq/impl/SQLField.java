/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.SQL;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLImpl;
import org.jooq.impl.TypedReference;

final class SQLField<T>
extends AbstractField<T>
implements QOM.UEmptyField<T>,
TypedReference<T> {
    final SQLImpl delegate;

    SQLField(DataType<T> type, SQL delegate) {
        super(DSL.unquotedName(delegate.toString()), type);
        this.delegate = (SQLImpl)delegate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("field(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }
}

