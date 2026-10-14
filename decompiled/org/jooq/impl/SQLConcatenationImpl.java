/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

final class SQLConcatenationImpl
extends AbstractQueryPart
implements SQL,
QOM.UEmpty {
    private static final Clause[] CLAUSES = new Clause[]{Clause.TEMPLATE};
    private final QueryPart[] input;

    SQLConcatenationImpl(QueryPart ... input) {
        this.input = input;
    }

    @Override
    public final void accept(Context<?> ctx) {
        for (int i = 0; i < this.input.length; ++i) {
            if (i > 0) {
                ctx.formatSeparator();
            }
            ctx.visit(this.input[i]);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }
}

