/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Condition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;

public abstract class SimplePredicate
extends Condition {
    Expression left;
    final boolean not;
    final boolean whenOperand;

    SimplePredicate(Expression expression, boolean bl, boolean bl2) {
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (!this.whenOperand && this.left.isConstant()) {
            return ValueExpression.getBoolean(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
    }

    @Override
    public final boolean needParentheses() {
        return true;
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + 1;
    }

    @Override
    public int getSubexpressionCount() {
        return 1;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.left;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final boolean isWhenConditionOperand() {
        return this.whenOperand;
    }
}

