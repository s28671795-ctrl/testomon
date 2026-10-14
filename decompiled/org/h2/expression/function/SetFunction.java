/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Variable;
import org.h2.expression.function.Function2;
import org.h2.message.DbException;
import org.h2.value.Value;

public final class SetFunction
extends Function2 {
    public SetFunction(Expression expression, Expression expression2) {
        super(expression, expression2);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Variable variable = (Variable)this.left;
        Value value = this.right.getValue(sessionLocal);
        sessionLocal.setVariable(variable.getName(), value);
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        this.type = this.right.getType();
        if (!(this.left instanceof Variable)) {
            throw DbException.get(90137, this.left.getTraceSQL());
        }
        return this;
    }

    @Override
    public String getName() {
        return "SET";
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!super.isEverything(expressionVisitor)) {
            return false;
        }
        switch (expressionVisitor.getType()) {
            case 2: 
            case 5: 
            case 8: {
                return false;
            }
        }
        return true;
    }
}

