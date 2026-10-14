/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function2;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarchar;

public final class StringFunction2
extends Function2 {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int REPEAT = 2;
    private static final String[] NAMES = new String[]{"LEFT", "RIGHT", "REPEAT"};
    private final int function;

    public StringFunction2(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        String string = value.getString();
        int n = value2.getInt();
        if (n <= 0) {
            return ValueVarchar.get("", sessionLocal);
        }
        int n2 = string.length();
        switch (this.function) {
            case 0: {
                if (n > n2) {
                    n = n2;
                }
                string = string.substring(0, n);
                break;
            }
            case 1: {
                if (n > n2) {
                    n = n2;
                }
                string = string.substring(n2 - n);
                break;
            }
            case 2: {
                StringBuilder stringBuilder = new StringBuilder(n2 * n);
                while (n-- > 0) {
                    stringBuilder.append(string);
                }
                string = stringBuilder.toString();
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return ValueVarchar.get(string, sessionLocal);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        switch (this.function) {
            case 0: 
            case 1: {
                this.type = TypeInfo.getTypeInfo(2, this.left.getType().getPrecision(), 0, null);
                break;
            }
            case 2: {
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        if (this.left.isConstant() && this.right.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

