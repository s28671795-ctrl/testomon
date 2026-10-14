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
import org.h2.value.ValueDouble;

public final class MathFunction2
extends Function2 {
    public static final int ATAN2 = 0;
    public static final int LOG = 1;
    public static final int POWER = 2;
    private static final String[] NAMES = new String[]{"ATAN2", "LOG", "POWER"};
    private final int function;

    public MathFunction2(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        double d = value.getDouble();
        double d2 = value2.getDouble();
        switch (this.function) {
            case 0: {
                d = Math.atan2(d, d2);
                break;
            }
            case 1: {
                if (sessionLocal.getMode().swapLogFunctionParameters) {
                    double d3 = d2;
                    d2 = d;
                    d = d3;
                }
                if (d2 <= 0.0) {
                    throw DbException.getInvalidValueException("LOG() argument", d2);
                }
                if (d <= 0.0 || d == 1.0) {
                    throw DbException.getInvalidValueException("LOG() base", d);
                }
                if (d == Math.E) {
                    d = Math.log(d2);
                    break;
                }
                if (d == 10.0) {
                    d = Math.log10(d2);
                    break;
                }
                d = Math.log(d2) / Math.log(d);
                break;
            }
            case 2: {
                d = Math.pow(d, d2);
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
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        this.type = TypeInfo.TYPE_DOUBLE;
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

