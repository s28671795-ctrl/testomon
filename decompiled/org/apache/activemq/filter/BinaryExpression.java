/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.filter.Expression;

public abstract class BinaryExpression
implements Expression {
    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return this.left;
    }

    public Expression getRight() {
        return this.right;
    }

    public String toString() {
        return "(" + this.left.toString() + " " + this.getExpressionSymbol() + " " + this.right.toString() + ")";
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        return this.toString().equals(o.toString());
    }

    public abstract String getExpressionSymbol();

    public void setRight(Expression expression) {
        this.right = expression;
    }

    public void setLeft(Expression expression) {
        this.left = expression;
    }
}

