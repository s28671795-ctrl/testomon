/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.CastSpecification;
import org.h2.expression.function.DateTimeFunction;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;

public final class MathFunction
extends Function1_2 {
    public static final int ABS = 0;
    public static final int MOD = 1;
    public static final int FLOOR = 2;
    public static final int CEIL = 3;
    public static final int ROUND = 4;
    public static final int ROUNDMAGIC = 5;
    public static final int SIGN = 6;
    public static final int TRUNC = 7;
    private static final String[] NAMES = new String[]{"ABS", "MOD", "FLOOR", "CEIL", "ROUND", "ROUNDMAGIC", "SIGN", "TRUNC"};
    private final int function;
    private TypeInfo commonType;

    public MathFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        switch (this.function) {
            case 0: {
                if (value.getSignum() >= 0) break;
                value = value.negate();
                break;
            }
            case 1: {
                value = value.convertTo(this.commonType, (CastDataProvider)sessionLocal).modulus(value2.convertTo(this.commonType, (CastDataProvider)sessionLocal)).convertTo(this.type, (CastDataProvider)sessionLocal);
                break;
            }
            case 2: {
                value = this.round(value, value2, RoundingMode.FLOOR);
                break;
            }
            case 3: {
                value = this.round(value, value2, RoundingMode.CEILING);
                break;
            }
            case 4: {
                value = this.round(value, value2, RoundingMode.HALF_UP);
                break;
            }
            case 5: {
                value = ValueDouble.get(MathFunction.roundMagic(value.getDouble()));
                break;
            }
            case 6: {
                value = ValueInteger.get(value.getSignum());
                break;
            }
            case 7: {
                value = this.round(value, value2, RoundingMode.DOWN);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Value round(Value value, Value value2, RoundingMode roundingMode) {
        int n = value2 != null ? value2.getInt() : 0;
        int n2 = this.type.getValueType();
        switch (n2) {
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                long l;
                if (n >= 0) return value;
                long l2 = value.getLong();
                if (l2 == (l = BigDecimal.valueOf(l2).setScale(n, roundingMode).longValue())) return value;
                return ValueBigint.get(l).convertTo(this.type);
            }
            case 13: {
                int n3 = this.type.getScale();
                BigDecimal bigDecimal = value.getBigDecimal();
                if (n >= n3) return ValueNumeric.get(bigDecimal.setScale(n3, roundingMode));
                bigDecimal = bigDecimal.setScale(n, roundingMode);
                return ValueNumeric.get(bigDecimal.setScale(n3, roundingMode));
            }
            case 14: 
            case 15: {
                if (n == 0) {
                    switch (roundingMode) {
                        case DOWN: {
                            double d = value.getDouble();
                            d = d < 0.0 ? Math.ceil(d) : Math.floor(d);
                            return n2 == 14 ? ValueReal.get((float)d) : ValueDouble.get(d);
                        }
                        case CEILING: {
                            double d = Math.ceil(value.getDouble());
                            return n2 == 14 ? ValueReal.get((float)d) : ValueDouble.get(d);
                        }
                        case FLOOR: {
                            double d = Math.floor(value.getDouble());
                            return n2 == 14 ? ValueReal.get((float)d) : ValueDouble.get(d);
                        }
                    }
                }
                BigDecimal bigDecimal = value.getBigDecimal().setScale(n, roundingMode);
                return n2 == 14 ? ValueReal.get(bigDecimal.floatValue()) : ValueDouble.get(bigDecimal.doubleValue());
            }
            case 16: {
                return ValueDecfloat.get(value.getBigDecimal().setScale(n, roundingMode));
            }
        }
        return value;
    }

    private static double roundMagic(double d) {
        if (d < 1.0E-13 && d > -1.0E-13) {
            return 0.0;
        }
        if (d > 1.0E12 || d < -1.0E12) {
            return d;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(d);
        if (stringBuilder.toString().indexOf(69) >= 0) {
            return d;
        }
        int n = stringBuilder.length();
        if (n < 16) {
            return d;
        }
        if (stringBuilder.toString().indexOf(46) > n - 3) {
            return d;
        }
        stringBuilder.delete(n - 2, n);
        char c = stringBuilder.charAt((n -= 2) - 2);
        char c2 = stringBuilder.charAt(n - 3);
        char c3 = stringBuilder.charAt(n - 4);
        if (c == '0' && c2 == '0' && c3 == '0') {
            stringBuilder.setCharAt(n - 1, '0');
        } else if (c == '9' && c2 == '9' && c3 == '9') {
            stringBuilder.setCharAt(n - 1, '9');
            stringBuilder.append('9');
            stringBuilder.append('9');
            stringBuilder.append('9');
        }
        return Double.parseDouble(stringBuilder.toString());
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = this.left.getType();
                if (this.type.getValueType() != 0) break;
                this.type = TypeInfo.TYPE_NUMERIC_FLOATING_POINT;
                break;
            }
            case 2: 
            case 3: {
                Expression expression = this.optimizeRound(0, true, false, true);
                if (expression == null) break;
                return expression;
            }
            case 1: {
                TypeInfo typeInfo = this.right.getType();
                this.commonType = TypeInfo.getHigherType(this.left.getType(), typeInfo);
                int n = this.commonType.getValueType();
                if (n == 0) {
                    this.commonType = TypeInfo.TYPE_BIGINT;
                } else if (!DataType.isNumericType(n)) {
                    throw Store.getInvalidExpressionTypeException("MOD argument", DataType.isNumericType(this.left.getType().getValueType()) ? this.right : this.left);
                }
                this.type = DataType.isNumericType(typeInfo.getValueType()) ? typeInfo : this.commonType;
                break;
            }
            case 4: {
                Expression expression = this.optimizeRoundWithScale(sessionLocal, true);
                if (expression == null) break;
                return expression;
            }
            case 5: {
                this.type = TypeInfo.TYPE_DOUBLE;
                break;
            }
            case 6: {
                this.type = TypeInfo.TYPE_INTEGER;
                break;
            }
            case 7: {
                switch (this.left.getType().getValueType()) {
                    case 2: {
                        this.left = new CastSpecification(this.left, TypeInfo.getTypeInfo(20, -1L, 0, null)).optimize(sessionLocal);
                    }
                    case 20: 
                    case 21: {
                        if (this.right != null) {
                            throw DbException.get(7001, "TRUNC", "1");
                        }
                        return new DateTimeFunction(1, 2, this.left, null).optimize(sessionLocal);
                    }
                    case 17: {
                        if (this.right != null) {
                            throw DbException.get(7001, "TRUNC", "1");
                        }
                        return new CastSpecification(this.left, TypeInfo.getTypeInfo(20, -1L, 0, null)).optimize(sessionLocal);
                    }
                }
                Expression expression = this.optimizeRoundWithScale(sessionLocal, false);
                if (expression == null) break;
                return expression;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    private Expression optimizeRoundWithScale(SessionLocal sessionLocal, boolean bl) {
        int n;
        boolean bl2 = false;
        boolean bl3 = false;
        if (this.right != null) {
            if (this.right.isConstant()) {
                Value value = this.right.getValue(sessionLocal);
                bl2 = true;
                if (value != ValueNull.INSTANCE) {
                    n = value.getInt();
                } else {
                    n = -1;
                    bl3 = true;
                }
            } else {
                n = -1;
            }
        } else {
            n = 0;
            bl2 = true;
        }
        return this.optimizeRound(n, bl2, bl3, bl);
    }

    private Expression optimizeRound(int n, boolean bl, boolean bl2, boolean bl3) {
        TypeInfo typeInfo = this.left.getType();
        switch (typeInfo.getValueType()) {
            case 0: {
                this.type = TypeInfo.TYPE_NUMERIC_SCALE_0;
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                if (bl && n >= 0) {
                    return this.left;
                }
                this.type = typeInfo;
                break;
            }
            case 14: 
            case 15: 
            case 16: {
                this.type = typeInfo;
                break;
            }
            case 13: {
                long l;
                int n2 = typeInfo.getScale();
                if (bl) {
                    if (n2 <= n) {
                        return this.left;
                    }
                    if (n < 0) {
                        n = 0;
                    } else if (n > 100000) {
                        n = 100000;
                    }
                    l = typeInfo.getPrecision() - (long)n2 + (long)n;
                    if (bl3) {
                        ++l;
                    }
                } else {
                    l = typeInfo.getPrecision();
                    if (bl3) {
                        ++l;
                    }
                    n = n2;
                }
                this.type = TypeInfo.getTypeInfo(13, l, n, null);
                break;
            }
            default: {
                throw Store.getInvalidExpressionTypeException(this.getName() + " argument", this.left);
            }
        }
        if (bl2) {
            return TypedValueExpression.get(ValueNull.INSTANCE, this.type);
        }
        return null;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

