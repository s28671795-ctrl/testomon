/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.function.CastSpecification;
import org.h2.value.TypeInfo;

abstract class Condition
extends Expression {
    Condition() {
    }

    static Expression castToBoolean(SessionLocal sessionLocal, Expression expression) {
        if (expression.getType().getValueType() == 8) {
            return expression;
        }
        return new CastSpecification(expression, TypeInfo.TYPE_BOOLEAN);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_BOOLEAN;
    }
}

