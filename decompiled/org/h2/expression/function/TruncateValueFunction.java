/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.math.BigDecimal;
import java.math.MathContext;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.util.MathUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueNumeric;

public final class TruncateValueFunction
extends FunctionN {
    public TruncateValueFunction(Expression expression, Expression expression2, Expression expression3) {
        super(new Expression[]{expression, expression2, expression3});
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        long l = value2.getLong();
        boolean bl = value3.getBoolean();
        if (l <= 0L) {
            throw DbException.get(90150, Long.toString(l), "1", "2147483647");
        }
        TypeInfo typeInfo = value.getType();
        int n = typeInfo.getValueType();
        if (DataType.getDataType((int)n).supportsPrecision) {
            if (l < typeInfo.getPrecision()) {
                switch (n) {
                    case 13: {
                        BigDecimal bigDecimal = value.getBigDecimal().round(new MathContext(MathUtils.convertLongToInt(l)));
                        if (bigDecimal.scale() < 0) {
                            bigDecimal = bigDecimal.setScale(0);
                        }
                        return ValueNumeric.get(bigDecimal);
                    }
                    case 16: {
                        return ValueDecfloat.get(value.getBigDecimal().round(new MathContext(MathUtils.convertLongToInt(l))));
                    }
                }
                return value.castTo(TypeInfo.getTypeInfo(n, l, typeInfo.getScale(), typeInfo.getExtTypeInfo()), sessionLocal);
            }
        } else if (bl) {
            BigDecimal bigDecimal;
            switch (n) {
                case 9: 
                case 10: 
                case 11: {
                    bigDecimal = BigDecimal.valueOf(value.getInt());
                    break;
                }
                case 12: {
                    bigDecimal = BigDecimal.valueOf(value.getLong());
                    break;
                }
                case 14: 
                case 15: {
                    bigDecimal = value.getBigDecimal();
                    break;
                }
                default: {
                    return value;
                }
            }
            bigDecimal = bigDecimal.round(new MathContext(MathUtils.convertLongToInt(l)));
            if (n == 16) {
                return ValueDecfloat.get(bigDecimal);
            }
            if (bigDecimal.scale() < 0) {
                bigDecimal = bigDecimal.setScale(0);
            }
            return ValueNumeric.get(bigDecimal).convertTo(n);
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        this.type = this.args[0].getType();
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return "TRUNCATE_VALUE";
    }
}

