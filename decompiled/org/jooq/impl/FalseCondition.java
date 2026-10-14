/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.False;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.TrueCondition;

final class FalseCondition
extends AbstractCondition
implements False,
QOM.False,
QOM.UEmpty,
SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    static final FalseCondition INSTANCE = new FalseCondition();

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return !TrueCondition.NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (TrueCondition.NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect())) {
            ctx.sql("1 = 0");
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("falseCondition()");
        } else {
            ctx.visit(Keywords.K_FALSE);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private FalseCondition() {
    }
}

