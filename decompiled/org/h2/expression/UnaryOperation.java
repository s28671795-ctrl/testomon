/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation1;
import org.h2.expression.ValueExpression;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class UnaryOperation
extends Operation1 {
    public UnaryOperation(Expression expression) {
        super(expression);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.arg.getSQL(stringBuilder.append("- "), n, 0);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
        return value == ValueNull.INSTANCE ? value : value.negate();
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = this.arg.getType();
        if (this.type.getValueType() == -1) {
            this.type = TypeInfo.TYPE_NUMERIC_FLOATING_POINT;
        } else if (this.type.getValueType() == 36) {
            this.type = TypeInfo.TYPE_INTEGER;
        }
        if (this.arg.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }
}

