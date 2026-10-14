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

public abstract class Operation1_2
extends Expression {
    protected Expression left;
    protected Expression right;
    protected TypeInfo type;

    protected Operation1_2(Expression expression, Expression expression2) {
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
        if (this.right != null) {
            this.right.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        if (this.right != null) {
            this.right.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        if (this.right != null) {
            this.right.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && (this.right == null || this.right.isEverything(expressionVisitor));
    }

    @Override
    public int getCost() {
        int n = this.left.getCost() + 1;
        if (this.right != null) {
            n += this.right.getCost();
        }
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return this.right != null ? 2 : 1;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.left;
        }
        if (n == 1 && this.right != null) {
            return this.right;
        }
        throw new IndexOutOfBoundsException();
    }
}

