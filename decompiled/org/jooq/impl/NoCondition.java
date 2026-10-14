/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.QOM;
import org.jooq.impl.TrueCondition;

final class NoCondition
extends AbstractCondition
implements QOM.UEmptyCondition {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    static final NoCondition INSTANCE = new NoCondition();

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(TrueCondition.INSTANCE);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private NoCondition() {
    }
}

