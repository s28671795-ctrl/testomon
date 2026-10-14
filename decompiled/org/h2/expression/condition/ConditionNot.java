/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Condition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class ConditionNot
extends Condition {
    private Expression condition;

    public ConditionNot(Expression expression) {
        this.condition = expression;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        return ConditionNot.castToBoolean(sessionLocal, this.condition.optimize(sessionLocal));
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.condition.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return value;
        }
        return value.convertToBoolean().negate();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.condition.mapColumns(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Expression expression = this.condition.getNotIfPossible(sessionLocal);
        if (expression != null) {
            return expression.optimize(sessionLocal);
        }
        Expression expression2 = this.condition.optimize(sessionLocal);
        if (expression2.isConstant()) {
            Value value = expression2.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                return TypedValueExpression.UNKNOWN;
            }
            return ValueExpression.getBoolean(!value.getBoolean());
        }
        this.condition = expression2;
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.condition.setEvaluatable(tableFilter, bl);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.condition.getSQL(stringBuilder.append("NOT "), n, 0);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.condition.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.condition.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.condition.getCost();
    }

    @Override
    public int getSubexpressionCount() {
        return 1;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.condition;
        }
        throw new IndexOutOfBoundsException();
    }
}

