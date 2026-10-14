/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.SimplePredicate;
import org.h2.index.IndexCondition;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class NullPredicate
extends SimplePredicate {
    private boolean optimized;

    public NullPredicate(Expression expression, boolean bl, boolean bl2) {
        super(expression, bl, bl2);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.not ? " IS NOT NULL" : " IS NULL");
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        ExpressionList expressionList;
        if (this.optimized) {
            return this;
        }
        Expression expression = super.optimize(sessionLocal);
        if (expression != this) {
            return expression;
        }
        this.optimized = true;
        if (!this.whenOperand && this.left instanceof ExpressionList && !(expressionList = (ExpressionList)this.left).isArray()) {
            int n = expressionList.getSubexpressionCount();
            for (int i = 0; i < n; ++i) {
                int n2;
                if (!expressionList.getSubexpression(i).isNullConstant()) continue;
                if (this.not) {
                    return ValueExpression.FALSE;
                }
                ArrayList<Expression> arrayList = new ArrayList<Expression>(n - 1);
                for (n2 = 0; n2 < i; ++n2) {
                    arrayList.add(expressionList.getSubexpression(n2));
                }
                for (n2 = i + 1; n2 < n; ++n2) {
                    Expression expression2 = expressionList.getSubexpression(n2);
                    if (expression2.isNullConstant()) continue;
                    arrayList.add(expression2);
                }
                this.left = arrayList.size() == 1 ? (Expression)arrayList.get(0) : new ExpressionList(arrayList.toArray(new Expression[0]), false);
                break;
            }
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return ValueBoolean.get(this.getValue(this.left.getValue(sessionLocal)));
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        return this.getValue(value);
    }

    private boolean getValue(Value value) {
        if (value.getType().getValueType() == 41) {
            for (Value value2 : ((ValueRow)value).getList()) {
                if (!(value2 != ValueNull.INSTANCE ^ this.not)) continue;
                return false;
            }
            return true;
        }
        return value == ValueNull.INSTANCE ^ this.not;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        Expression expression = this.optimize(sessionLocal);
        if (expression != this) {
            return expression.getNotIfPossible(sessionLocal);
        }
        switch (this.left.getType().getValueType()) {
            case -1: 
            case 41: {
                return null;
            }
        }
        return new NullPredicate(this.left, !this.not, false);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        ExpressionList expressionList;
        if (this.not || this.whenOperand || !tableFilter.getTable().isQueryComparable()) {
            return;
        }
        if (this.left instanceof ExpressionColumn) {
            NullPredicate.createNullIndexCondition(tableFilter, (ExpressionColumn)this.left);
        } else if (this.left instanceof ExpressionList && !(expressionList = (ExpressionList)this.left).isArray()) {
            int n = expressionList.getSubexpressionCount();
            for (int i = 0; i < n; ++i) {
                Expression expression = expressionList.getSubexpression(i);
                if (!(expression instanceof ExpressionColumn)) continue;
                NullPredicate.createNullIndexCondition(tableFilter, (ExpressionColumn)expression);
            }
        }
    }

    private static void createNullIndexCondition(TableFilter tableFilter, ExpressionColumn expressionColumn) {
        if (tableFilter == expressionColumn.getTableFilter() && expressionColumn.getType().getValueType() != 41) {
            tableFilter.addIndexCondition(IndexCondition.get(6, expressionColumn, ValueExpression.NULL));
        }
    }
}

