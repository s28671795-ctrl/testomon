/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.And;
import org.jooq.impl.Expression;
import org.jooq.impl.QOM;
import org.jooq.impl.Xor;
import org.jooq.tools.StringUtils;

final class Or
extends AbstractCondition
implements QOM.Or {
    final Condition arg1;
    final Condition arg2;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_OR};

    Or(Condition arg1, Condition arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            boolean indentOp1;
            boolean bl = indentOp1 = !(this.arg1 instanceof And) && !(this.arg2 instanceof Or) && !(this.arg2 instanceof Xor);
            if (indentOp1) {
                ctx.sqlIndentStart().visit(this.arg1);
            } else {
                ctx.visit(this.arg1).sqlIndentStart();
            }
            ctx.formatNewLine().sql(".or(").visit(this.arg2).sql(')');
            ctx.sqlIndentEnd();
        } else {
            ctx.sqlIndentStart('(');
            Expression.acceptAssociative(ctx, this, q -> new Expression.Expr<Condition>(q.arg1, Operator.OR.toKeyword(), q.arg2), Context::formatSeparator);
            ctx.sqlIndentEnd(')');
        }
    }

    @Override
    final boolean isNullable() {
        return ((AbstractCondition)this.arg1).isNullable() || ((AbstractCondition)this.arg2).isNullable();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".or(").visit(this.arg2).sql(')');
    }

    @Override
    public final Condition $arg1() {
        return this.arg1;
    }

    @Override
    public final Condition $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.Or $arg1(Condition newValue) {
        return new Or(newValue, this.$arg2());
    }

    @Override
    public final QOM.Or $arg2(Condition newValue) {
        return new Or(this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Condition, ? super Condition, ? extends QOM.Or> $constructor() {
        return (a1, a2) -> new Or((Condition)a1, (Condition)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Or) {
            QOM.Or o = (QOM.Or)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

