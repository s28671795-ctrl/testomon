/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.SimplePredicate;
import org.h2.index.IndexCondition;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public final class BooleanTest
extends SimplePredicate {
    private final Boolean right;

    public BooleanTest(Expression expression, boolean bl, boolean bl2, Boolean bl3) {
        super(expression, bl, bl2);
        this.right = bl3;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.not ? " IS NOT " : " IS ").append(this.right == null ? "UNKNOWN" : (this.right != false ? "TRUE" : "FALSE"));
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
        return (value == ValueNull.INSTANCE ? this.right == null : this.right != null && this.right.booleanValue() == value.getBoolean()) ^ this.not;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new BooleanTest(this.left, !this.not, false, this.right);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        ExpressionColumn expressionColumn;
        if (this.whenOperand || !tableFilter.getTable().isQueryComparable()) {
            return;
        }
        if (this.left instanceof ExpressionColumn && (expressionColumn = (ExpressionColumn)this.left).getType().getValueType() == 8 && tableFilter == expressionColumn.getTableFilter()) {
            if (this.not) {
                if (this.right == null && expressionColumn.getColumn().isNullable()) {
                    ArrayList<Expression> arrayList = new ArrayList<Expression>(2);
                    arrayList.add(ValueExpression.FALSE);
                    arrayList.add(ValueExpression.TRUE);
                    tableFilter.addIndexCondition(IndexCondition.getInList(expressionColumn, arrayList));
                }
            } else {
                tableFilter.addIndexCondition(IndexCondition.get(6, expressionColumn, this.right == null ? TypedValueExpression.UNKNOWN : ValueExpression.getBoolean(this.right)));
            }
        }
    }
}

