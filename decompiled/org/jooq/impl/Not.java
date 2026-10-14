/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.tools.StringUtils;

final class Not
extends AbstractCondition
implements QOM.Not {
    final Condition condition;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT};

    Not(Condition condition) {
        this.condition = condition;
    }

    @Override
    boolean isNullable() {
        return !(this.condition instanceof AbstractCondition) || ((AbstractCondition)this.condition).isNullable();
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                if (this.condition instanceof AbstractField && ((AbstractField)((Object)this.condition)).parenthesised(ctx)) {
                    ctx.visit(Keywords.K_NOT).sql(' ').visit(this.condition);
                    break;
                }
                ctx.visit(Keywords.K_NOT).sql(" (").visit(this.condition).sql(')');
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.condition).sql(".not()");
    }

    @Override
    public final Condition $arg1() {
        return this.condition;
    }

    @Override
    public final QOM.Not $arg1(Condition newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Condition, ? extends QOM.Not> $constructor() {
        return a1 -> new Not((Condition)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Not) {
            QOM.Not o = (QOM.Not)that;
            return StringUtils.equals(this.$condition(), o.$condition());
        }
        return super.equals(that);
    }
}

