/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPartInternal;
import org.jooq.SQL;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.QOM;

final class SQLQuery
extends AbstractRowCountQuery
implements QOM.UEmptyQuery {
    private final SQL delegate;

    SQLQuery(Configuration configuration, SQL delegate) {
        super(configuration);
        this.delegate = delegate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("query(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        SQL sQL = this.delegate;
        if (sQL instanceof QueryPartInternal) {
            QueryPartInternal q = (QueryPartInternal)((Object)sQL);
            return q.clauses(ctx);
        }
        return null;
    }
}

