/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueRow;

public final class ExpressionList
extends Expression {
    private final Expression[] list;
    private final boolean isArray;
    private TypeInfo type;

    public ExpressionList(Expression[] expressionArray, boolean bl) {
        this.list = expressionArray;
        this.isArray = bl;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value[] valueArray = new Value[this.list.length];
        for (int i = 0; i < this.list.length; ++i) {
            valueArray[i] = this.list[i].getValue(sessionLocal);
        }
        return this.isArray ? ValueArray.get((TypeInfo)this.type.getExtTypeInfo(), valueArray, sessionLocal) : ValueRow.get(this.type, valueArray);
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        for (Expression expression : this.list) {
            expression.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = true;
        int n = this.list.length;
        for (int i = 0; i < n; ++i) {
            Expression expression = this.list[i].optimize(sessionLocal);
            if (!expression.isConstant()) {
                bl = false;
            }
            this.list[i] = expression;
        }
        this.initializeType();
        if (bl) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    void initializeType() {
        this.type = this.isArray ? TypeInfo.getTypeInfo(40, this.list.length, 0, TypeInfo.getHigherType(this.list)) : TypeInfo.getTypeInfo(41, 0L, 0, new ExtTypeInfoRow(this.list));
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.list) {
            expression.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.isArray ? ExpressionList.writeExpressions(stringBuilder.append("ARRAY ["), this.list, n).append(']') : ExpressionList.writeExpressions(stringBuilder.append("ROW ("), this.list, n).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        for (Expression expression : this.list) {
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        for (Expression expression : this.list) {
            if (expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = 1;
        for (Expression expression : this.list) {
            n += expression.getCost();
        }
        return n;
    }

    @Override
    public boolean isConstant() {
        for (Expression expression : this.list) {
            if (expression.isConstant()) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getSubexpressionCount() {
        return this.list.length;
    }

    @Override
    public Expression getSubexpression(int n) {
        return this.list[n];
    }

    public boolean isArray() {
        return this.isArray;
    }
}

