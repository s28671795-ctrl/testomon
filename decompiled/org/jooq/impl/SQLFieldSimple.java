/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;

final class SQLFieldSimple<T>
extends AbstractField<T>
implements SimpleQueryPart,
QOM.UEmptyField<T> {
    private final QueryPart delegate;

    SQLFieldSimple(DataType<T> type, SQL delegate) {
        super(DSL.unquotedName(delegate.toString()), type);
        this.delegate = delegate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.delegate.toString());
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }
}

