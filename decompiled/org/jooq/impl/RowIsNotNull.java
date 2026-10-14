/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Row;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.RowIsNull;
import org.jooq.impl.Tools;

final class RowIsNotNull
extends AbstractCondition
implements QOM.RowIsNotNull {
    final Row row;

    RowIsNotNull(Row row) {
        this.row = row;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (RowIsNull.EMULATE_NULL_ROW.contains((Object)ctx.dialect())) {
            ctx.visit(Tools.allNotNull(this.row.fields()));
        } else {
            this.acceptStandard(ctx);
        }
    }

    private final void acceptStandard(Context<?> ctx) {
        ctx.visit(this.row);
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(".isNotNull()");
                break;
            }
            default: {
                ctx.sql(' ').visit(Keywords.K_IS_NOT_NULL);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Row $arg1() {
        return this.row;
    }

    @Override
    public final Function1<? super Row, ? extends QOM.RowIsNotNull> $constructor() {
        return r -> new RowIsNotNull((Row)r);
    }
}

