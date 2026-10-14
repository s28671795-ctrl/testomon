/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.CompatibilityDatePlusTimeOperation;
import org.h2.expression.ConcatenationOperation;
import org.h2.expression.Expression;
import org.h2.expression.IntervalOperation;
import org.h2.expression.Operation2;
import org.h2.expression.UnaryOperation;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.DateTimeFunction;
import org.h2.message.DbException;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;

public class BinaryOperation
extends Operation2 {
    private OpType opType;
    private TypeInfo forcedType;
    private boolean convertRight = true;

    public BinaryOperation(OpType opType, Expression expression, Expression expression2) {
        super(expression, expression2);
        this.opType = opType;
    }

    public void setForcedType(TypeInfo typeInfo) {
        if (this.opType != OpType.MINUS) {
            throw this.getUnexpectedForcedTypeException();
        }
        this.forcedType = typeInfo;
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0).append(' ').append(this.getOperationToken()).append(' ');
        return this.right.getSQL(stringBuilder, n, 0);
    }

    private String getOperationToken() {
        switch (this.opType) {
            case PLUS: {
                return "+";
            }
            case MINUS: {
                return "-";
            }
            case MULTIPLY: {
                return "*";
            }
            case DIVIDE: {
                return "/";
            }
        }
        throw DbException.getInternalError("opType=" + (Object)((Object)this.opType));
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
        Value value2 = this.right.getValue(sessionLocal);
        if (this.convertRight) {
            value2 = value2.convertTo(this.type, (CastDataProvider)sessionLocal);
        }
        switch (this.opType) {
            case PLUS: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return value.add(value2);
            }
            case MINUS: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return value.subtract(value2);
            }
            case MULTIPLY: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return value.multiply(value2);
            }
            case DIVIDE: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                return value.divide(value2, this.type);
            }
        }
        throw DbException.getInternalError("type=" + (Object)((Object)this.opType));
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        TypeInfo typeInfo = this.left.getType();
        TypeInfo typeInfo2 = this.right.getType();
        int n = typeInfo.getValueType();
        int n2 = typeInfo2.getValueType();
        if (n == 0 && n2 == 0 || n == -1 && n2 == -1) {
            if (this.opType == OpType.PLUS && sessionLocal.getDatabase().getMode().allowPlusForStringConcat) {
                return new ConcatenationOperation(this.left, this.right).optimize(sessionLocal);
            }
            this.type = TypeInfo.TYPE_NUMERIC_FLOATING_POINT;
        } else {
            if (DataType.isIntervalType(n) || DataType.isIntervalType(n2)) {
                if (this.forcedType != null) {
                    throw this.getUnexpectedForcedTypeException();
                }
                return this.optimizeInterval(n, n2);
            }
            if (DataType.isDateTimeType(n) || DataType.isDateTimeType(n2)) {
                return this.optimizeDateTime(sessionLocal, n, n2);
            }
            if (this.forcedType != null) {
                throw this.getUnexpectedForcedTypeException();
            }
            int n3 = Value.getHigherOrder(n, n2);
            if (n3 == 13) {
                this.optimizeNumeric(typeInfo, typeInfo2);
            } else if (n3 == 16) {
                this.optimizeDecfloat(typeInfo, typeInfo2);
            } else if (n3 == 36) {
                this.type = TypeInfo.TYPE_INTEGER;
            } else {
                if (DataType.isCharacterStringType(n3) && this.opType == OpType.PLUS && sessionLocal.getDatabase().getMode().allowPlusForStringConcat) {
                    return new ConcatenationOperation(this.left, this.right).optimize(sessionLocal);
                }
                this.type = TypeInfo.getTypeInfo(n3);
            }
        }
        if (this.left.isConstant() && this.right.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    private void optimizeNumeric(TypeInfo typeInfo, TypeInfo typeInfo2) {
        long l;
        int n;
        typeInfo = typeInfo.toNumericType();
        typeInfo2 = typeInfo2.toNumericType();
        long l2 = typeInfo.getPrecision();
        long l3 = typeInfo2.getPrecision();
        int n2 = typeInfo.getScale();
        int n3 = typeInfo2.getScale();
        switch (this.opType) {
            case PLUS: 
            case MINUS: {
                if (n2 < n3) {
                    l2 += (long)(n3 - n2);
                    n = n3;
                } else {
                    l3 += (long)(n2 - n3);
                    n = n2;
                }
                l = Math.max(l2, l3) + 1L;
                break;
            }
            case MULTIPLY: {
                l = l2 + l3;
                n = n2 + n3;
                break;
            }
            case DIVIDE: {
                long l4 = (long)(n2 - n3) + l3 * 2L;
                n = l4 >= 100000L ? 100000 : (l4 <= 0L ? 0 : (int)l4);
                l = l2 + (long)n3 - (long)n2 + (long)n;
                if (l <= 100000L) break;
                long l5 = Math.min(l - 100000L, (long)n);
                l -= l5;
                n = (int)((long)n - l5);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.opType));
            }
        }
        this.type = TypeInfo.getTypeInfo(13, l, n, null);
    }

    private void optimizeDecfloat(TypeInfo typeInfo, TypeInfo typeInfo2) {
        long l;
        typeInfo = typeInfo.toDecfloatType();
        typeInfo2 = typeInfo2.toDecfloatType();
        long l2 = typeInfo.getPrecision();
        long l3 = typeInfo2.getPrecision();
        switch (this.opType) {
            case PLUS: 
            case MINUS: 
            case DIVIDE: {
                l = Math.max(l2, l3) + 1L;
                break;
            }
            case MULTIPLY: {
                l = l2 + l3;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.opType));
            }
        }
        this.type = TypeInfo.getTypeInfo(16, l, 0, null);
    }

    private Expression optimizeInterval(int n, int n2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (DataType.isIntervalType(n)) {
            bl = true;
        } else if (DataType.isNumericType(n)) {
            bl2 = true;
        } else if (DataType.isDateTimeType(n)) {
            bl3 = true;
        } else {
            throw this.getUnsupported(n, n2);
        }
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        if (DataType.isIntervalType(n2)) {
            bl4 = true;
        } else if (DataType.isNumericType(n2)) {
            bl5 = true;
        } else if (DataType.isDateTimeType(n2)) {
            bl6 = true;
        } else {
            throw this.getUnsupported(n, n2);
        }
        switch (this.opType) {
            case PLUS: {
                if (bl && bl4) {
                    if (DataType.isYearMonthIntervalType(n) != DataType.isYearMonthIntervalType(n2)) break;
                    return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_PLUS_INTERVAL, this.left, this.right);
                }
                if (bl && bl6) {
                    if (n2 == 18 && DataType.isYearMonthIntervalType(n)) break;
                    return new IntervalOperation(IntervalOperation.IntervalOpType.DATETIME_PLUS_INTERVAL, this.right, this.left);
                }
                if (!bl3 || !bl4 || n == 18 && DataType.isYearMonthIntervalType(n2)) break;
                return new IntervalOperation(IntervalOperation.IntervalOpType.DATETIME_PLUS_INTERVAL, this.left, this.right);
            }
            case MINUS: {
                if (bl && bl4) {
                    if (DataType.isYearMonthIntervalType(n) != DataType.isYearMonthIntervalType(n2)) break;
                    return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_MINUS_INTERVAL, this.left, this.right);
                }
                if (!bl3 || !bl4 || n == 18 && DataType.isYearMonthIntervalType(n2)) break;
                return new IntervalOperation(IntervalOperation.IntervalOpType.DATETIME_MINUS_INTERVAL, this.left, this.right);
            }
            case MULTIPLY: {
                if (bl && bl5) {
                    return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_MULTIPLY_NUMERIC, this.left, this.right);
                }
                if (!bl2 || !bl4) break;
                return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_MULTIPLY_NUMERIC, this.right, this.left);
            }
            case DIVIDE: {
                if (!bl) break;
                if (bl5) {
                    return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_DIVIDE_NUMERIC, this.left, this.right);
                }
                if (!bl4 || DataType.isYearMonthIntervalType(n) != DataType.isYearMonthIntervalType(n2)) break;
                return new IntervalOperation(IntervalOperation.IntervalOpType.INTERVAL_DIVIDE_INTERVAL, this.left, this.right);
            }
        }
        throw this.getUnsupported(n, n2);
    }

    private Expression optimizeDateTime(SessionLocal sessionLocal, int n, int n2) {
        switch (this.opType) {
            case PLUS: {
                if (DataType.isDateTimeType(n)) {
                    if (DataType.isDateTimeType(n2)) {
                        if (n > n2) {
                            this.swap();
                            int n3 = n;
                            n = n2;
                            n2 = n3;
                        }
                        return new CompatibilityDatePlusTimeOperation(this.right, this.left).optimize(sessionLocal);
                    }
                    this.swap();
                    int n4 = n;
                    n = n2;
                    n2 = n4;
                }
                switch (n) {
                    case 11: {
                        return new DateTimeFunction(2, 2, this.left, this.right).optimize(sessionLocal);
                    }
                    case 13: 
                    case 14: 
                    case 15: 
                    case 16: {
                        return new DateTimeFunction(2, 5, new BinaryOperation(OpType.MULTIPLY, ValueExpression.get(ValueInteger.get(86400)), this.left), this.right).optimize(sessionLocal);
                    }
                }
                break;
            }
            case MINUS: {
                switch (n) {
                    case 17: 
                    case 20: 
                    case 21: {
                        switch (n2) {
                            case 11: {
                                if (this.forcedType != null) {
                                    throw this.getUnexpectedForcedTypeException();
                                }
                                return new DateTimeFunction(2, 2, new UnaryOperation(this.right), this.left).optimize(sessionLocal);
                            }
                            case 13: 
                            case 14: 
                            case 15: 
                            case 16: {
                                if (this.forcedType != null) {
                                    throw this.getUnexpectedForcedTypeException();
                                }
                                return new DateTimeFunction(2, 5, new BinaryOperation(OpType.MULTIPLY, ValueExpression.get(ValueInteger.get(-86400)), this.right), this.left).optimize(sessionLocal);
                            }
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: {
                                return new IntervalOperation(IntervalOperation.IntervalOpType.DATETIME_MINUS_DATETIME, this.left, this.right, this.forcedType);
                            }
                        }
                        break;
                    }
                    case 18: 
                    case 19: {
                        if (!DataType.isDateTimeType(n2)) break;
                        return new IntervalOperation(IntervalOperation.IntervalOpType.DATETIME_MINUS_DATETIME, this.left, this.right, this.forcedType);
                    }
                }
                break;
            }
            case MULTIPLY: {
                if (n == 18) {
                    this.type = TypeInfo.TYPE_TIME;
                    this.convertRight = false;
                    return this;
                }
                if (n2 != 18) break;
                this.swap();
                this.type = TypeInfo.TYPE_TIME;
                this.convertRight = false;
                return this;
            }
            case DIVIDE: {
                if (n != 18) break;
                this.type = TypeInfo.TYPE_TIME;
                this.convertRight = false;
                return this;
            }
        }
        throw this.getUnsupported(n, n2);
    }

    private DbException getUnsupported(int n, int n2) {
        return DbException.getUnsupportedException(Value.getTypeName(n) + ' ' + this.getOperationToken() + ' ' + Value.getTypeName(n2));
    }

    private DbException getUnexpectedForcedTypeException() {
        StringBuilder stringBuilder = this.getUnenclosedSQL(new StringBuilder(), 3);
        int n = stringBuilder.length();
        return DbException.getSyntaxError(IntervalOperation.getForcedTypeSQL(stringBuilder.append(' '), this.forcedType).toString(), n, "");
    }

    private void swap() {
        Expression expression = this.left;
        this.left = this.right;
        this.right = expression;
    }

    public OpType getOperationType() {
        return this.opType;
    }

    public static enum OpType {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE;

    }
}

