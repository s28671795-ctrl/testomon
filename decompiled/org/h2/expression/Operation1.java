/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;

public abstract class Operation1
extends Expression {
    protected Expression arg;
    protected TypeInfo type;

    protected Operation1(Expression expression) {
        this.arg = expression;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.arg.mapColumns(columnResolver, n, n2);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.arg.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.arg.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.arg.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.arg.getCost() + 1;
    }

    @Override
    public int getSubexpressionCount() {
        return 1;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.arg;
        }
        throw new IndexOutOfBoundsException();
    }
}

