/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public final class BetweenPredicate
extends Condition {
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private boolean symmetric;
    private Expression a;
    private Expression b;

    public BetweenPredicate(Expression expression, boolean bl, boolean bl2, boolean bl3, Expression expression2, Expression expression3) {
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
        this.symmetric = bl3;
        this.a = expression2;
        this.b = expression3;
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        if (this.not) {
            stringBuilder.append(" NOT");
        }
        stringBuilder.append(" BETWEEN ");
        if (this.symmetric) {
            stringBuilder.append("SYMMETRIC ");
        }
        this.a.getSQL(stringBuilder, n, 0).append(" AND ");
        return this.b.getSQL(stringBuilder, n, 0);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Value value;
        this.left = this.left.optimize(sessionLocal);
        this.a = this.a.optimize(sessionLocal);
        this.b = this.b.optimize(sessionLocal);
        TypeInfo typeInfo = this.left.getType();
        TypeInfo.checkComparable(typeInfo, this.a.getType());
        TypeInfo.checkComparable(typeInfo, this.b.getType());
        if (this.whenOperand) {
            return this;
        }
        Value value2 = this.left.isConstant() ? this.left.getValue(sessionLocal) : null;
        Value value3 = this.a.isConstant() ? this.a.getValue(sessionLocal) : null;
        Value value4 = value = this.b.isConstant() ? this.b.getValue(sessionLocal) : null;
        if (value2 != null) {
            if (value2 == ValueNull.INSTANCE) {
                return TypedValueExpression.UNKNOWN;
            }
            if (value3 != null && value != null) {
                return ValueExpression.getBoolean(this.getValue(sessionLocal, value2, value3, value));
            }
        }
        if (this.symmetric ? value3 == ValueNull.INSTANCE || value == ValueNull.INSTANCE : value3 == ValueNull.INSTANCE && value == ValueNull.INSTANCE) {
            return TypedValueExpression.UNKNOWN;
        }
        if (value3 != null && value != null && sessionLocal.compareWithNull(value3, value, false) == 0) {
            return new Comparison(this.not ? 1 : 0, this.left, this.a, false).optimize(sessionLocal);
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        return this.getValue(sessionLocal, value, this.a.getValue(sessionLocal), this.b.getValue(sessionLocal));
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        if (value == ValueNull.INSTANCE) {
            return false;
        }
        return this.getValue(sessionLocal, value, this.a.getValue(sessionLocal), this.b.getValue(sessionLocal)).isTrue();
    }

    private Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        int n = sessionLocal.compareWithNull(value2, value, false);
        int n2 = sessionLocal.compareWithNull(value, value3, false);
        if (n == Integer.MIN_VALUE) {
            return this.symmetric || n2 <= 0 ? ValueNull.INSTANCE : ValueBoolean.get(this.not);
        }
        if (n2 == Integer.MIN_VALUE) {
            return this.symmetric || n <= 0 ? ValueNull.INSTANCE : ValueBoolean.get(this.not);
        }
        return ValueBoolean.get(this.not ^ (this.symmetric ? n <= 0 && n2 <= 0 || n >= 0 && n2 >= 0 : n <= 0 && n2 <= 0));
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new BetweenPredicate(this.left, !this.not, false, this.symmetric, this.a, this.b);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (!(this.not || this.whenOperand || this.symmetric)) {
            Comparison.createIndexConditions(tableFilter, this.a, this.left, 4);
            Comparison.createIndexConditions(tableFilter, this.left, this.b, 4);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        this.a.setEvaluatable(tableFilter, bl);
        this.b.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        this.a.updateAggregate(sessionLocal, n);
        this.b.updateAggregate(sessionLocal, n);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        this.a.mapColumns(columnResolver, n, n2);
        this.b.mapColumns(columnResolver, n, n2);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.a.isEverything(expressionVisitor) && this.b.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + this.a.getCost() + this.b.getCost() + 1;
    }

    @Override
    public int getSubexpressionCount() {
        return 3;
    }

    @Override
    public Expression getSubexpression(int n) {
        switch (n) {
            case 0: {
                return this.left;
            }
            case 1: {
                return this.a;
            }
            case 2: {
                return this.b;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}

