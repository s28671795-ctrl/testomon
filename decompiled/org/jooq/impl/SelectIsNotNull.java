/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectIsNull;
import org.jooq.impl.Tools;

final class SelectIsNotNull
extends AbstractCondition
implements QOM.SelectIsNotNull {
    private final Select<?> select;

    SelectIsNotNull(Select<?> select) {
        this.select = select;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (SelectIsNull.EMULATE_NULL_QUERY.contains((Object)ctx.dialect())) {
            if (this.select.getSelect().size() == 1) {
                this.acceptStandard(ctx);
            } else {
                SelectField t2 = new AliasedSelect(this.select, true, true, false).as("t");
                ctx.visit(DSL.inline(1).eq(DSL.selectCount().from((TableLike<?>)((Object)t2)).where(Tools.allNotNull(t2.fields()))));
            }
        } else {
            this.acceptStandard(ctx);
        }
    }

    private final void acceptStandard(Context<?> ctx) {
        Tools.visitSubquery(ctx, this.select, 256);
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
    public final Select<?> $arg1() {
        return this.select;
    }

    @Override
    public final Function1<? super Select<?>, ? extends QOM.SelectIsNotNull> $constructor() {
        return r -> new SelectIsNotNull((Select<?>)r);
    }
}

