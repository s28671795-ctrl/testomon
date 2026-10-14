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
import org.h2.expression.condition.ConditionAndOrN;
import org.h2.expression.condition.ConditionIn;
import org.h2.expression.condition.ConditionInConstantSet;
import org.h2.expression.condition.ConditionNot;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class ConditionAndOr
extends Condition {
    public static final int AND = 0;
    public static final int OR = 1;
    private final int andOrType;
    private Expression left;
    private Expression right;
    private Expression added;

    public ConditionAndOr(int n, Expression expression, Expression expression2) {
        if (expression == null || expression2 == null) {
            throw DbException.getInternalError(expression + " " + expression2);
        }
        this.andOrType = n;
        this.left = expression;
        this.right = expression2;
    }

    int getAndOrType() {
        return this.andOrType;
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0);
        switch (this.andOrType) {
            case 0: {
                stringBuilder.append("\n    AND ");
                break;
            }
            case 1: {
                stringBuilder.append("\n    OR ");
                break;
            }
            default: {
                throw DbException.getInternalError("andOrType=" + this.andOrType);
            }
        }
        return this.right.getSQL(stringBuilder, n, 0);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.andOrType == 0) {
            this.left.createIndexConditions(sessionLocal, tableFilter);
            this.right.createIndexConditions(sessionLocal, tableFilter);
            if (this.added != null) {
                this.added.createIndexConditions(sessionLocal, tableFilter);
            }
        }
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        Expression expression;
        Expression expression2 = this.left.getNotIfPossible(sessionLocal);
        if (expression2 == null) {
            expression2 = new ConditionNot(this.left);
        }
        if ((expression = this.right.getNotIfPossible(sessionLocal)) == null) {
            expression = new ConditionNot(this.right);
        }
        int n = this.andOrType == 0 ? 1 : 0;
        return new ConditionAndOr(n, expression2, expression);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        switch (this.andOrType) {
            case 0: {
                Value value2;
                if (value.isFalse() || (value2 = this.right.getValue(sessionLocal)).isFalse()) {
                    return ValueBoolean.FALSE;
                }
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return ValueBoolean.TRUE;
            }
            case 1: {
                Value value3;
                if (value.isTrue() || (value3 = this.right.getValue(sessionLocal)).isTrue()) {
                    return ValueBoolean.TRUE;
                }
                if (value == ValueNull.INSTANCE || value3 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return ValueBoolean.FALSE;
            }
        }
        throw DbException.getInternalError("type=" + this.andOrType);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Expression expression;
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        int n = this.left.getCost();
        int n2 = this.right.getCost();
        if (n2 < n) {
            expression = this.left;
            this.left = this.right;
            this.right = expression;
        }
        switch (this.andOrType) {
            case 0: {
                if (!sessionLocal.getDatabase().getSettings().optimizeTwoEquals || !(this.left instanceof Comparison) || !(this.right instanceof Comparison) || (expression = ((Comparison)this.left).getAdditionalAnd(sessionLocal, (Comparison)this.right)) == null) break;
                this.added = expression.optimize(sessionLocal);
                break;
            }
            case 1: {
                if (!sessionLocal.getDatabase().getSettings().optimizeOr) break;
                if (this.left instanceof Comparison && this.right instanceof Comparison) {
                    expression = ((Comparison)this.left).optimizeOr(sessionLocal, (Comparison)this.right);
                } else if (this.left instanceof ConditionIn && this.right instanceof Comparison) {
                    expression = ((ConditionIn)this.left).getAdditional((Comparison)this.right);
                } else if (this.right instanceof ConditionIn && this.left instanceof Comparison) {
                    expression = ((ConditionIn)this.right).getAdditional((Comparison)this.left);
                } else if (this.left instanceof ConditionInConstantSet && this.right instanceof Comparison) {
                    expression = ((ConditionInConstantSet)this.left).getAdditional(sessionLocal, (Comparison)this.right);
                } else if (this.right instanceof ConditionInConstantSet && this.left instanceof Comparison) {
                    expression = ((ConditionInConstantSet)this.right).getAdditional(sessionLocal, (Comparison)this.left);
                } else {
                    if (!(this.left instanceof ConditionAndOr) || !(this.right instanceof ConditionAndOr)) break;
                    expression = ConditionAndOr.optimizeConditionAndOr((ConditionAndOr)this.left, (ConditionAndOr)this.right);
                }
                if (expression == null) break;
                return expression.optimize(sessionLocal);
            }
        }
        expression = ConditionAndOr.optimizeIfConstant(sessionLocal, this.andOrType, this.left, this.right);
        if (expression == null) {
            return ConditionAndOr.optimizeN(this);
        }
        if (expression instanceof ConditionAndOr) {
            return ConditionAndOr.optimizeN((ConditionAndOr)expression);
        }
        return expression;
    }

    private static Expression optimizeN(ConditionAndOr conditionAndOr) {
        Condition condition;
        if (conditionAndOr.right instanceof ConditionAndOr) {
            condition = (ConditionAndOr)conditionAndOr.right;
            if (((ConditionAndOr)condition).andOrType == conditionAndOr.andOrType) {
                return new ConditionAndOrN(conditionAndOr.andOrType, conditionAndOr.left, ((ConditionAndOr)condition).left, ((ConditionAndOr)condition).right);
            }
        }
        if (conditionAndOr.right instanceof ConditionAndOrN && ((ConditionAndOrN)(condition = (ConditionAndOrN)conditionAndOr.right)).getAndOrType() == conditionAndOr.andOrType) {
            ((ConditionAndOrN)condition).addFirst(conditionAndOr.left);
            return condition;
        }
        return conditionAndOr;
    }

    static Expression optimizeIfConstant(SessionLocal sessionLocal, int n, Expression expression, Expression expression2) {
        if (!expression.isConstant()) {
            if (!expression2.isConstant()) {
                return null;
            }
            return ConditionAndOr.optimizeConstant(sessionLocal, n, expression2.getValue(sessionLocal), expression);
        }
        Value value = expression.getValue(sessionLocal);
        if (!expression2.isConstant()) {
            return ConditionAndOr.optimizeConstant(sessionLocal, n, value, expression2);
        }
        Value value2 = expression2.getValue(sessionLocal);
        switch (n) {
            case 0: {
                if (value.isFalse() || value2.isFalse()) {
                    return ValueExpression.FALSE;
                }
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return TypedValueExpression.UNKNOWN;
                }
                return ValueExpression.TRUE;
            }
            case 1: {
                if (value.isTrue() || value2.isTrue()) {
                    return ValueExpression.TRUE;
                }
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return TypedValueExpression.UNKNOWN;
                }
                return ValueExpression.FALSE;
            }
        }
        throw DbException.getInternalError("type=" + n);
    }

    private static Expression optimizeConstant(SessionLocal sessionLocal, int n, Value value, Expression expression) {
        if (value != ValueNull.INSTANCE) {
            switch (n) {
                case 0: {
                    return value.getBoolean() ? ConditionAndOr.castToBoolean(sessionLocal, expression) : ValueExpression.FALSE;
                }
                case 1: {
                    return value.getBoolean() ? ValueExpression.TRUE : ConditionAndOr.castToBoolean(sessionLocal, expression);
                }
            }
            throw DbException.getInternalError("type=" + n);
        }
        return null;
    }

    @Override
    public void addFilterConditions(TableFilter tableFilter) {
        if (this.andOrType == 0) {
            this.left.addFilterConditions(tableFilter);
            this.right.addFilterConditions(tableFilter);
        } else {
            super.addFilterConditions(tableFilter);
        }
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        this.right.mapColumns(columnResolver, n, n2);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        this.right.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        this.right.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.right.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + this.right.getCost();
    }

    @Override
    public int getSubexpressionCount() {
        return 2;
    }

    @Override
    public Expression getSubexpression(int n) {
        switch (n) {
            case 0: {
                return this.left;
            }
            case 1: {
                return this.right;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    static Expression optimizeConditionAndOr(ConditionAndOr conditionAndOr, ConditionAndOr conditionAndOr2) {
        String string;
        if (conditionAndOr.andOrType != 0 || conditionAndOr2.andOrType != 0) {
            return null;
        }
        Expression expression = conditionAndOr.getSubexpression(0);
        Expression expression2 = conditionAndOr.getSubexpression(1);
        Expression expression3 = conditionAndOr2.getSubexpression(0);
        Expression expression4 = conditionAndOr2.getSubexpression(1);
        String string2 = expression3.getSQL(0);
        String string3 = expression4.getSQL(0);
        if (expression.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR)) {
            string = expression.getSQL(0);
            if (string.equals(string2)) {
                return new ConditionAndOr(0, expression, new ConditionAndOr(1, expression2, expression4));
            }
            if (string.equals(string3)) {
                return new ConditionAndOr(0, expression, new ConditionAndOr(1, expression2, expression3));
            }
        }
        if (expression2.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR)) {
            string = expression2.getSQL(0);
            if (string.equals(string2)) {
                return new ConditionAndOr(0, expression2, new ConditionAndOr(1, expression, expression4));
            }
            if (string.equals(string3)) {
                return new ConditionAndOr(0, expression2, new ConditionAndOr(1, expression, expression3));
            }
        }
        return null;
    }
}

