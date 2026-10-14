/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class QuantifiedSelectImpl<R extends Record>
extends AbstractQueryPart
implements QuantifiedSelect<R>,
QOM.QuantifiedSelect<R> {
    final QOM.Quantifier quantifier;
    final Select<R> query;

    QuantifiedSelectImpl(QOM.Quantifier quantifier, Select<R> query) {
        this.quantifier = quantifier;
        this.query = query;
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean extraParentheses = SQLDialect.ORACLE == ctx.family() && Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY));
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.quantifier == QOM.Quantifier.ANY ? "any(" : "all(");
                Tools.visitSubquery(ctx, this.query, 256, false);
                ctx.sql(')');
                break;
            }
            default: {
                ctx.visit(this.quantifier.keyword);
                ctx.sql(extraParentheses ? " ((" : " (");
                Tools.visitSubquery(ctx, this.query, 256, false);
                ctx.sql(extraParentheses ? "))" : ")");
            }
        }
    }

    @Override
    public final Function2<? super QOM.Quantifier, ? super Select<R>, ? extends QOM.QuantifiedSelect<R>> $constructor() {
        return (q, s2) -> new QuantifiedSelectImpl((QOM.Quantifier)((Object)q), s2);
    }

    @Override
    public final QOM.Quantifier $arg1() {
        return this.quantifier;
    }

    @Override
    public final Select<R> $arg2() {
        return this.query;
    }
}

