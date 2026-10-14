/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function2;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class NullIfFunction
extends Function2 {
    public NullIfFunction(Expression expression, Expression expression2) {
        super(expression, expression2);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (sessionLocal.compareWithNull(value, this.right.getValue(sessionLocal), true) == 0) {
            value = ValueNull.INSTANCE;
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        this.type = this.left.getType();
        TypeInfo.checkComparable(this.type, this.right.getType());
        if (this.left.isConstant() && this.right.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return "NULLIF";
    }
}

