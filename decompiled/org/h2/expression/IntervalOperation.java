/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation2;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.DateTimeFunction;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.IntervalUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestampTimeZone;

public class IntervalOperation
extends Operation2 {
    private static final int INTERVAL_YEAR_DIGITS = 20;
    private static final int INTERVAL_DAY_DIGITS = 32;
    private static final TypeInfo INTERVAL_DIVIDE_INTERVAL_YEAR_TYPE = TypeInfo.getTypeInfo(13, 60L, 40, null);
    private static final TypeInfo INTERVAL_DIVIDE_INTERVAL_DAY_TYPE = TypeInfo.getTypeInfo(13, 96L, 64, null);
    private final IntervalOpType opType;
    private TypeInfo forcedType;

    private static BigInteger nanosFromValue(SessionLocal sessionLocal, Value value) {
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        return BigInteger.valueOf(DateTimeUtils.absoluteDayFromDateValue(lArray[0])).multiply(IntervalUtils.NANOS_PER_DAY_BI).add(BigInteger.valueOf(lArray[1]));
    }

    public IntervalOperation(IntervalOpType intervalOpType, Expression expression, Expression expression2, TypeInfo typeInfo) {
        this(intervalOpType, expression, expression2);
        this.forcedType = typeInfo;
    }

    public IntervalOperation(IntervalOpType intervalOpType, Expression expression, Expression expression2) {
        super(expression, expression2);
        this.opType = intervalOpType;
        int n = expression.getType().getValueType();
        int n2 = expression2.getType().getValueType();
        switch (intervalOpType) {
            case INTERVAL_PLUS_INTERVAL: 
            case INTERVAL_MINUS_INTERVAL: {
                this.type = TypeInfo.getTypeInfo(Value.getHigherOrder(n, n2));
                break;
            }
            case INTERVAL_DIVIDE_INTERVAL: {
                this.type = DataType.isYearMonthIntervalType(n) ? INTERVAL_DIVIDE_INTERVAL_YEAR_TYPE : INTERVAL_DIVIDE_INTERVAL_DAY_TYPE;
                break;
            }
            case DATETIME_PLUS_INTERVAL: 
            case DATETIME_MINUS_INTERVAL: 
            case INTERVAL_MULTIPLY_NUMERIC: 
            case INTERVAL_DIVIDE_NUMERIC: {
                this.type = expression.getType();
                break;
            }
            case DATETIME_MINUS_DATETIME: {
                this.type = this.forcedType != null ? this.forcedType : (!(n != 18 && n != 19 || n2 != 18 && n2 != 19) ? TypeInfo.TYPE_INTERVAL_HOUR_TO_SECOND : (n == 17 && n2 == 17 ? TypeInfo.TYPE_INTERVAL_DAY : TypeInfo.TYPE_INTERVAL_DAY_TO_SECOND));
            }
        }
    }

    @Override
    public boolean needParentheses() {
        return this.forcedType == null;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this.forcedType != null) {
            this.getInnerSQL2(stringBuilder.append('('), n);
            IntervalOperation.getForcedTypeSQL(stringBuilder.append(") "), this.forcedType);
        } else {
            this.getInnerSQL2(stringBuilder, n);
        }
        return stringBuilder;
    }

    private void getInnerSQL2(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0).append(' ').append(this.getOperationToken()).append(' ');
        this.right.getSQL(stringBuilder, n, 0);
    }

    static StringBuilder getForcedTypeSQL(StringBuilder stringBuilder, TypeInfo typeInfo) {
        int n = (int)typeInfo.getPrecision();
        int n2 = typeInfo.getScale();
        return IntervalQualifier.valueOf(typeInfo.getValueType() - 22).getTypeName(stringBuilder, n == 2 ? -1 : n, n2 == 6 ? -1 : n2, true);
    }

    private char getOperationToken() {
        switch (this.opType) {
            case INTERVAL_PLUS_INTERVAL: 
            case DATETIME_PLUS_INTERVAL: {
                return '+';
            }
            case INTERVAL_MINUS_INTERVAL: 
            case DATETIME_MINUS_INTERVAL: 
            case DATETIME_MINUS_DATETIME: {
                return '-';
            }
            case INTERVAL_MULTIPLY_NUMERIC: {
                return '*';
            }
            case INTERVAL_DIVIDE_INTERVAL: 
            case INTERVAL_DIVIDE_NUMERIC: {
                return '/';
            }
        }
        throw DbException.getInternalError("opType=" + (Object)((Object)this.opType));
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        Value value2 = this.right.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        int n = value.getValueType();
        int n2 = value2.getValueType();
        switch (this.opType) {
            case INTERVAL_PLUS_INTERVAL: 
            case INTERVAL_MINUS_INTERVAL: {
                BigInteger bigInteger = IntervalUtils.intervalToAbsolute((ValueInterval)value);
                BigInteger bigInteger2 = IntervalUtils.intervalToAbsolute((ValueInterval)value2);
                return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(Value.getHigherOrder(n, n2) - 22), this.opType == IntervalOpType.INTERVAL_PLUS_INTERVAL ? bigInteger.add(bigInteger2) : bigInteger.subtract(bigInteger2));
            }
            case INTERVAL_DIVIDE_INTERVAL: {
                return ValueNumeric.get(IntervalUtils.intervalToAbsolute((ValueInterval)value)).divide(ValueNumeric.get(IntervalUtils.intervalToAbsolute((ValueInterval)value2)), this.type);
            }
            case DATETIME_PLUS_INTERVAL: 
            case DATETIME_MINUS_INTERVAL: {
                return this.getDateTimeWithInterval(sessionLocal, value, value2, n, n2);
            }
            case INTERVAL_MULTIPLY_NUMERIC: 
            case INTERVAL_DIVIDE_NUMERIC: {
                BigDecimal bigDecimal = new BigDecimal(IntervalUtils.intervalToAbsolute((ValueInterval)value));
                BigDecimal bigDecimal2 = value2.getBigDecimal();
                return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), (this.opType == IntervalOpType.INTERVAL_MULTIPLY_NUMERIC ? bigDecimal.multiply(bigDecimal2) : bigDecimal.divide(bigDecimal2)).toBigInteger());
            }
            case DATETIME_MINUS_DATETIME: {
                Value value3;
                if (!(n != 18 && n != 19 || n2 != 18 && n2 != 19)) {
                    boolean bl;
                    long l;
                    if (n == 18 && n2 == 18) {
                        l = ((ValueTime)value).getNanos() - ((ValueTime)value2).getNanos();
                    } else {
                        ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value.convertTo(TypeInfo.TYPE_TIME_TZ, (CastDataProvider)sessionLocal);
                        ValueTimeTimeZone valueTimeTimeZone2 = (ValueTimeTimeZone)value2.convertTo(TypeInfo.TYPE_TIME_TZ, (CastDataProvider)sessionLocal);
                        l = valueTimeTimeZone.getNanos() - valueTimeTimeZone2.getNanos() + (long)(valueTimeTimeZone2.getTimeZoneOffsetSeconds() - valueTimeTimeZone.getTimeZoneOffsetSeconds()) * 1000000000L;
                    }
                    boolean bl2 = bl = l < 0L;
                    if (bl) {
                        l = -l;
                    }
                    value3 = ValueInterval.from(IntervalQualifier.HOUR_TO_SECOND, bl, l / 3600000000000L, l % 3600000000000L);
                } else if (this.forcedType != null && DataType.isYearMonthIntervalType(this.forcedType.getValueType())) {
                    boolean bl;
                    long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
                    long[] lArray2 = DateTimeUtils.dateAndTimeFromValue(value2, sessionLocal);
                    long l = n == 18 || n == 19 ? sessionLocal.currentTimestamp().getDateValue() : lArray[0];
                    long l2 = n2 == 18 || n2 == 19 ? sessionLocal.currentTimestamp().getDateValue() : lArray2[0];
                    long l3 = 12L * (long)(DateTimeUtils.yearFromDateValue(l) - DateTimeUtils.yearFromDateValue(l2)) + (long)DateTimeUtils.monthFromDateValue(l) - (long)DateTimeUtils.monthFromDateValue(l2);
                    int n3 = DateTimeUtils.dayFromDateValue(l);
                    int n4 = DateTimeUtils.dayFromDateValue(l2);
                    if (l3 >= 0L) {
                        if (n3 < n4 || n3 == n4 && lArray[1] < lArray2[1]) {
                            --l3;
                        }
                    } else if (n3 > n4 || n3 == n4 && lArray[1] > lArray2[1]) {
                        ++l3;
                    }
                    if (l3 < 0L) {
                        bl = true;
                        l3 = -l3;
                    } else {
                        bl = false;
                    }
                    value3 = ValueInterval.from(IntervalQualifier.MONTH, bl, l3, 0L);
                } else if (n == 17 && n2 == 17) {
                    boolean bl;
                    long l = DateTimeUtils.absoluteDayFromDateValue(((ValueDate)value).getDateValue()) - DateTimeUtils.absoluteDayFromDateValue(((ValueDate)value2).getDateValue());
                    boolean bl3 = bl = l < 0L;
                    if (bl) {
                        l = -l;
                    }
                    value3 = ValueInterval.from(IntervalQualifier.DAY, bl, l, 0L);
                } else {
                    BigInteger bigInteger = IntervalOperation.nanosFromValue(sessionLocal, value).subtract(IntervalOperation.nanosFromValue(sessionLocal, value2));
                    if (n == 21 || n2 == 21) {
                        value = value.convertTo(TypeInfo.TYPE_TIMESTAMP_TZ, (CastDataProvider)sessionLocal);
                        value2 = value2.convertTo(TypeInfo.TYPE_TIMESTAMP_TZ, (CastDataProvider)sessionLocal);
                        bigInteger = bigInteger.add(BigInteger.valueOf((long)(((ValueTimestampTimeZone)value2).getTimeZoneOffsetSeconds() - ((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds()) * 1000000000L));
                    }
                    value3 = IntervalUtils.intervalFromAbsolute(IntervalQualifier.DAY_TO_SECOND, bigInteger);
                }
                if (this.forcedType != null) {
                    value3 = value3.castTo(this.forcedType, sessionLocal);
                }
                return value3;
            }
        }
        throw DbException.getInternalError("type=" + (Object)((Object)this.opType));
    }

    private Value getDateTimeWithInterval(SessionLocal sessionLocal, Value value, Value value2, int n, int n2) {
        switch (n) {
            case 18: {
                if (DataType.isYearMonthIntervalType(n2)) {
                    throw DbException.getInternalError("type=" + n2);
                }
                return ValueTime.fromNanos(this.getTimeWithInterval(value2, ((ValueTime)value).getNanos()));
            }
            case 19: {
                if (DataType.isYearMonthIntervalType(n2)) {
                    throw DbException.getInternalError("type=" + n2);
                }
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                return ValueTimeTimeZone.fromNanos(this.getTimeWithInterval(value2, valueTimeTimeZone.getNanos()), valueTimeTimeZone.getTimeZoneOffsetSeconds());
            }
            case 17: 
            case 20: 
            case 21: {
                if (DataType.isYearMonthIntervalType(n2)) {
                    long l = IntervalUtils.intervalToAbsolute((ValueInterval)value2).longValue();
                    if (this.opType == IntervalOpType.DATETIME_MINUS_INTERVAL) {
                        l = -l;
                    }
                    return DateTimeFunction.dateadd(sessionLocal, 1, l, value);
                }
                BigInteger bigInteger = IntervalUtils.intervalToAbsolute((ValueInterval)value2);
                if (n == 17) {
                    BigInteger bigInteger2 = BigInteger.valueOf(DateTimeUtils.absoluteDayFromDateValue(((ValueDate)value).getDateValue()));
                    bigInteger = bigInteger.divide(IntervalUtils.NANOS_PER_DAY_BI);
                    BigInteger bigInteger3 = this.opType == IntervalOpType.DATETIME_PLUS_INTERVAL ? bigInteger2.add(bigInteger) : bigInteger2.subtract(bigInteger);
                    return ValueDate.fromDateValue(DateTimeUtils.dateValueFromAbsoluteDay(bigInteger3.longValue()));
                }
                long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
                long l = DateTimeUtils.absoluteDayFromDateValue(lArray[0]);
                long l2 = lArray[1];
                BigInteger[] bigIntegerArray = bigInteger.divideAndRemainder(IntervalUtils.NANOS_PER_DAY_BI);
                if (this.opType == IntervalOpType.DATETIME_PLUS_INTERVAL) {
                    l += bigIntegerArray[0].longValue();
                    l2 += bigIntegerArray[1].longValue();
                } else {
                    l -= bigIntegerArray[0].longValue();
                    l2 -= bigIntegerArray[1].longValue();
                }
                if (l2 >= 86400000000000L) {
                    l2 -= 86400000000000L;
                    ++l;
                } else if (l2 < 0L) {
                    l2 += 86400000000000L;
                    --l;
                }
                return DateTimeUtils.dateTimeToValue(value, DateTimeUtils.dateValueFromAbsoluteDay(l), l2);
            }
        }
        throw DbException.getInternalError("type=" + (Object)((Object)this.opType));
    }

    private long getTimeWithInterval(Value value, long l) {
        BigInteger bigInteger;
        BigInteger bigInteger2 = BigInteger.valueOf(l);
        BigInteger bigInteger3 = IntervalUtils.intervalToAbsolute((ValueInterval)value);
        BigInteger bigInteger4 = bigInteger = this.opType == IntervalOpType.DATETIME_PLUS_INTERVAL ? bigInteger2.add(bigInteger3) : bigInteger2.subtract(bigInteger3);
        if (bigInteger.signum() < 0 || bigInteger.compareTo(IntervalUtils.NANOS_PER_DAY_BI) >= 0) {
            throw DbException.get(22003, bigInteger.toString());
        }
        l = bigInteger.longValue();
        return l;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        if (this.left.isConstant() && this.right.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    public static enum IntervalOpType {
        INTERVAL_PLUS_INTERVAL,
        INTERVAL_MINUS_INTERVAL,
        INTERVAL_DIVIDE_INTERVAL,
        DATETIME_PLUS_INTERVAL,
        DATETIME_MINUS_INTERVAL,
        INTERVAL_MULTIPLY_NUMERIC,
        INTERVAL_DIVIDE_NUMERIC,
        DATETIME_MINUS_DATETIME;

    }
}

