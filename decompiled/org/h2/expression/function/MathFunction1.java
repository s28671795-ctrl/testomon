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
import org.h2.value.ValueDouble;
import org.h2.value.ValueNull;

public final class MathFunction1
extends Function1 {
    public static final int SIN = 0;
    public static final int COS = 1;
    public static final int TAN = 2;
    public static final int COT = 3;
    public static final int SINH = 4;
    public static final int COSH = 5;
    public static final int TANH = 6;
    public static final int ASIN = 7;
    public static final int ACOS = 8;
    public static final int ATAN = 9;
    public static final int LOG10 = 10;
    public static final int LN = 11;
    public static final int EXP = 12;
    public static final int SQRT = 13;
    public static final int DEGREES = 14;
    public static final int RADIANS = 15;
    private static final String[] NAMES = new String[]{"SIN", "COS", "TAN", "COT", "SINH", "COSH", "TANH", "ASIN", "ACOS", "ATAN", "LOG10", "LN", "EXP", "SQRT", "DEGREES", "RADIANS"};
    private final int function;

    public MathFunction1(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        double d = value.getDouble();
        switch (this.function) {
            case 0: {
                d = Math.sin(d);
                break;
            }
            case 1: {
                d = Math.cos(d);
                break;
            }
            case 2: {
                d = Math.tan(d);
                break;
            }
            case 3: {
                d = Math.tan(d);
                if (d == 0.0) {
                    throw DbException.get(22012, this.getTraceSQL());
                }
                d = 1.0 / d;
                break;
            }
            case 4: {
                d = Math.sinh(d);
                break;
            }
            case 5: {
                d = Math.cosh(d);
                break;
            }
            case 6: {
                d = Math.tanh(d);
                break;
            }
            case 7: {
                d = Math.asin(d);
                break;
            }
            case 8: {
                d = Math.acos(d);
                break;
            }
            case 9: {
                d = Math.atan(d);
                break;
            }
            case 10: {
                if (d <= 0.0) {
                    throw DbException.getInvalidValueException("LOG10() argument", d);
                }
                d = Math.log10(d);
                break;
            }
            case 11: {
                if (d <= 0.0) {
                    throw DbException.getInvalidValueException("LN() argument", d);
                }
                d = Math.log(d);
                break;
            }
            case 12: {
                d = Math.exp(d);
                break;
            }
            case 13: {
                d = Math.sqrt(d);
                break;
            }
            case 14: {
                d = Math.toDegrees(d);
                break;
            }
            case 15: {
                d = Math.toRadians(d);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return ValueDouble.get(d);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = TypeInfo.TYPE_DOUBLE;
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

