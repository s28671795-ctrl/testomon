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

public abstract class Operation2
extends Expression {
    protected Expression left;
    protected Expression right;
    protected TypeInfo type;

    protected Operation2(Expression expression, Expression expression2) {
        this.left = expression;
        this.right = expression2;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
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
        return this.left.getCost() + this.right.getCost() + 1;
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
}

