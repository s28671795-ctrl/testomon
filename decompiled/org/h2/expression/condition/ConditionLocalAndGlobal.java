/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.condition.Condition;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class ConditionLocalAndGlobal
extends Condition {
    private Expression local;
    private Expression global;

    public ConditionLocalAndGlobal(Expression expression, Expression expression2) {
        if (expression2 == null) {
            throw DbException.getInternalError();
        }
        this.local = expression;
        this.global = expression2;
    }

    @Override
    public boolean needParentheses() {
        return this.local != null || this.global.needParentheses();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this.local == null) {
            return this.global.getUnenclosedSQL(stringBuilder, n);
        }
        this.local.getSQL(stringBuilder, n, 0);
        stringBuilder.append("\n    _LOCAL_AND_GLOBAL_ ");
        return this.global.getSQL(stringBuilder, n, 0);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.local != null) {
            this.local.createIndexConditions(sessionLocal, tableFilter);
        }
        this.global.createIndexConditions(sessionLocal, tableFilter);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        if (this.local == null) {
            return this.global.getValue(sessionLocal);
        }
        Value value2 = this.local.getValue(sessionLocal);
        if (value2.isFalse() || (value = this.global.getValue(sessionLocal)).isFalse()) {
            return ValueBoolean.FALSE;
        }
        if (value2 == ValueNull.INSTANCE || value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.TRUE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.global = this.global.optimize(sessionLocal);
        if (this.local != null) {
            this.local = this.local.optimize(sessionLocal);
            Expression expression = ConditionAndOr.optimizeIfConstant(sessionLocal, 0, this.local, this.global);
            if (expression != null) {
                return expression;
            }
        }
        return this;
    }

    @Override
    public void addFilterConditions(TableFilter tableFilter) {
        if (this.local != null) {
            this.local.addFilterConditions(tableFilter);
        }
        this.global.addFilterConditions(tableFilter);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.local != null) {
            this.local.mapColumns(columnResolver, n, n2);
        }
        this.global.mapColumns(columnResolver, n, n2);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.local != null) {
            this.local.setEvaluatable(tableFilter, bl);
        }
        this.global.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        if (this.local != null) {
            this.local.updateAggregate(sessionLocal, n);
        }
        this.global.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return (this.local == null || this.local.isEverything(expressionVisitor)) && this.global.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        int n = this.global.getCost();
        if (this.local != null) {
            n += this.local.getCost();
        }
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return this.local == null ? 1 : 2;
    }

    @Override
    public Expression getSubexpression(int n) {
        switch (n) {
            case 0: {
                return this.local != null ? this.local : this.global;
            }
            case 1: {
                if (this.local == null) break;
                return this.global;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}

