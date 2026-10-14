/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.OperationN;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class FunctionN
extends OperationN
implements NamedExpression {
    protected FunctionN(Expression[] expressionArray) {
        super(expressionArray);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        Value value2;
        Value value3;
        int n = this.args.length;
        if (n >= 1) {
            value3 = this.args[0].getValue(sessionLocal);
            if (value3 == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            if (n >= 2) {
                value2 = this.args[1].getValue(sessionLocal);
                if (value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                if (n >= 3) {
                    value = this.args[2].getValue(sessionLocal);
                    if (value == ValueNull.INSTANCE) {
                        return ValueNull.INSTANCE;
                    }
                } else {
                    value = null;
                }
            } else {
                value2 = null;
                value = null;
            }
        } else {
            value3 = null;
            value2 = null;
            value = null;
        }
        return this.getValue(sessionLocal, value3, value2, value);
    }

    protected Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        throw DbException.getInternalError();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return FunctionN.writeExpressions(stringBuilder.append(this.getName()).append('('), this.args, n).append(')');
    }
}

