/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public final class LengthFunction
extends Function1 {
    public static final int CHAR_LENGTH = 0;
    public static final int OCTET_LENGTH = 1;
    public static final int BIT_LENGTH = 2;
    private static final String[] NAMES = new String[]{"CHAR_LENGTH", "OCTET_LENGTH", "BIT_LENGTH"};
    private final int function;

    public LengthFunction(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        long l;
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        switch (this.function) {
            case 0: {
                l = value.charLength();
                break;
            }
            case 1: {
                l = value.octetLength();
                break;
            }
            case 2: {
                l = value.octetLength() * 8L;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return ValueBigint.get(l);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = TypeInfo.TYPE_BIGINT;
        if (this.arg.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

