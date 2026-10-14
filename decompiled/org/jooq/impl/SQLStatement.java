/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.SQL;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.QOM;

@Pro
final class SQLStatement
extends AbstractStatement
implements QOM.UEmptyStatement {
    private final SQL delegate;

    SQLStatement(SQL delegate) {
        this.delegate = delegate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("statement(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }
}

