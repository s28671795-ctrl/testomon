/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation2;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class Function2
extends Operation2
implements NamedExpression {
    protected Function2(Expression expression, Expression expression2) {
        super(expression, expression2);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        Value value2 = this.right.getValue(sessionLocal);
        if (value2 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        return this.getValue(sessionLocal, value, value2);
    }

    protected Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        throw DbException.getInternalError();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getUnenclosedSQL(stringBuilder.append(this.getName()).append('('), n).append(", ");
        return this.right.getUnenclosedSQL(stringBuilder, n).append(')');
    }
}

