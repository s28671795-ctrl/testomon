/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.SQL;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.QOM;

final class SQLCondition
extends AbstractCondition
implements QOM.UEmptyCondition {
    private final SQL delegate;

    SQLCondition(SQL delegate) {
        this.delegate = delegate;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("condition(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.sql('(');
                ctx.visit(this.delegate);
                ctx.sql(')');
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

