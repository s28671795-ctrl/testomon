/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigDecimalBase;

public final class ValueDecfloat
extends ValueBigDecimalBase {
    public static final ValueDecfloat ZERO = new ValueDecfloat(BigDecimal.ZERO);
    public static final ValueDecfloat ONE = new ValueDecfloat(BigDecimal.ONE);
    public static final ValueDecfloat POSITIVE_INFINITY = new ValueDecfloat(null);
    public static final ValueDecfloat NEGATIVE_INFINITY = new ValueDecfloat(null);
    public static final ValueDecfloat NAN = new ValueDecfloat(null);

    private ValueDecfloat(BigDecimal bigDecimal) {
        super(bigDecimal);
    }

    @Override
    public String getString() {
        if (this.value == null) {
            if (this == POSITIVE_INFINITY) {
                return "Infinity";
            }
            if (this == NEGATIVE_INFINITY) {
                return "-Infinity";
            }
            return "NaN";
        }
        return this.value.toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return this.getSQL(stringBuilder.append("CAST(")).append(" AS DECFLOAT)");
        }
        return this.getSQL(stringBuilder);
    }

    private StringBuilder getSQL(StringBuilder stringBuilder) {
        if (this.value != null) {
            return stringBuilder.append(this.value);
        }
        if (this == POSITIVE_INFINITY) {
            return stringBuilder.append("'Infinity'");
        }
        if (this == NEGATIVE_INFINITY) {
            return stringBuilder.append("'-Infinity'");
        }
        return stringBuilder.append("'NaN'");
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            this.type = typeInfo = new TypeInfo(16, this.value != null ? (long)this.value.precision() : 1L, 0, null);
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 16;
    }

    @Override
    public Value add(Value value) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (this.value != null) {
            if (bigDecimal != null) {
                return ValueDecfloat.get(this.value.add(bigDecimal));
            }
            return value;
        }
        if (bigDecimal != null || this == value) {
            return this;
        }
        return NAN;
    }

    @Override
    public Value subtract(Value value) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (this.value != null) {
            if (bigDecimal != null) {
                return ValueDecfloat.get(this.value.subtract(bigDecimal));
            }
            return value == POSITIVE_INFINITY ? NEGATIVE_INFINITY : (value == NEGATIVE_INFINITY ? POSITIVE_INFINITY : NAN);
        }
        if (bigDecimal != null) {
            return this;
        }
        if (this == POSITIVE_INFINITY) {
            if (value == NEGATIVE_INFINITY) {
                return POSITIVE_INFINITY;
            }
        } else if (this == NEGATIVE_INFINITY && value == POSITIVE_INFINITY) {
            return NEGATIVE_INFINITY;
        }
        return NAN;
    }

    @Override
    public Value negate() {
        if (this.value != null) {
            return ValueDecfloat.get(this.value.negate());
        }
        return this == POSITIVE_INFINITY ? NEGATIVE_INFINITY : (this == NEGATIVE_INFINITY ? POSITIVE_INFINITY : NAN);
    }

    @Override
    public Value multiply(Value value) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (this.value != null) {
            if (bigDecimal != null) {
                return ValueDecfloat.get(this.value.multiply(bigDecimal));
            }
            if (value == POSITIVE_INFINITY) {
                int n = this.value.signum();
                if (n > 0) {
                    return POSITIVE_INFINITY;
                }
                if (n < 0) {
                    return NEGATIVE_INFINITY;
                }
            } else if (value == NEGATIVE_INFINITY) {
                int n = this.value.signum();
                if (n > 0) {
                    return NEGATIVE_INFINITY;
                }
                if (n < 0) {
                    return POSITIVE_INFINITY;
                }
            }
        } else if (bigDecimal != null) {
            if (this == POSITIVE_INFINITY) {
                int n = bigDecimal.signum();
                if (n > 0) {
                    return POSITIVE_INFINITY;
                }
                if (n < 0) {
                    return NEGATIVE_INFINITY;
                }
            } else if (this == NEGATIVE_INFINITY) {
                int n = bigDecimal.signum();
                if (n > 0) {
                    return NEGATIVE_INFINITY;
                }
                if (n < 0) {
                    return POSITIVE_INFINITY;
                }
            }
        } else if (this == POSITIVE_INFINITY) {
            if (value == POSITIVE_INFINITY) {
                return POSITIVE_INFINITY;
            }
            if (value == NEGATIVE_INFINITY) {
                return NEGATIVE_INFINITY;
            }
        } else if (this == NEGATIVE_INFINITY) {
            if (value == POSITIVE_INFINITY) {
                return NEGATIVE_INFINITY;
            }
            if (value == NEGATIVE_INFINITY) {
                return POSITIVE_INFINITY;
            }
        }
        return NAN;
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (bigDecimal != null && bigDecimal.signum() == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        if (this.value != null) {
            if (bigDecimal != null) {
                return ValueDecfloat.divide(this.value, bigDecimal, typeInfo);
            }
            if (value != NAN) {
                return ZERO;
            }
        } else if (bigDecimal != null && this != NAN) {
            return this == POSITIVE_INFINITY == bigDecimal.signum() > 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
        }
        return NAN;
    }

    public static ValueDecfloat divide(BigDecimal bigDecimal, BigDecimal bigDecimal2, TypeInfo typeInfo) {
        int n = (int)typeInfo.getPrecision();
        BigDecimal bigDecimal3 = bigDecimal.divide(bigDecimal2, bigDecimal.scale() - bigDecimal.precision() + bigDecimal2.precision() - bigDecimal2.scale() + n, RoundingMode.HALF_DOWN);
        int n2 = bigDecimal3.precision();
        if (n2 > n) {
            bigDecimal3 = bigDecimal3.setScale(bigDecimal3.scale() - n2 + n, RoundingMode.HALF_UP);
        }
        return ValueDecfloat.get(bigDecimal3);
    }

    @Override
    public Value modulus(Value value) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (bigDecimal != null && bigDecimal.signum() == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        if (this.value != null) {
            if (bigDecimal != null) {
                return ValueDecfloat.get(this.value.remainder(bigDecimal));
            }
            if (value != NAN) {
                return this;
            }
        }
        return NAN;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        BigDecimal bigDecimal = ((ValueDecfloat)value).value;
        if (this.value != null) {
            if (bigDecimal != null) {
                return this.value.compareTo(bigDecimal);
            }
            return value == NEGATIVE_INFINITY ? 1 : -1;
        }
        if (bigDecimal != null) {
            return this == NEGATIVE_INFINITY ? -1 : 1;
        }
        if (this == value) {
            return 0;
        }
        if (this == NEGATIVE_INFINITY) {
            return -1;
        }
        if (value == NEGATIVE_INFINITY) {
            return 1;
        }
        return this == POSITIVE_INFINITY ? -1 : 1;
    }

    @Override
    public int getSignum() {
        if (this.value != null) {
            return this.value.signum();
        }
        return this == POSITIVE_INFINITY ? 1 : (this == NEGATIVE_INFINITY ? -1 : 0);
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (this.value != null) {
            return this.value;
        }
        throw this.getDataConversionError(13);
    }

    @Override
    public float getFloat() {
        if (this.value != null) {
            return this.value.floatValue();
        }
        if (this == POSITIVE_INFINITY) {
            return Float.POSITIVE_INFINITY;
        }
        if (this == NEGATIVE_INFINITY) {
            return Float.NEGATIVE_INFINITY;
        }
        return Float.NaN;
    }

    @Override
    public double getDouble() {
        if (this.value != null) {
            return this.value.doubleValue();
        }
        if (this == POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        if (this == NEGATIVE_INFINITY) {
            return Double.NEGATIVE_INFINITY;
        }
        return Double.NaN;
    }

    @Override
    public int hashCode() {
        return this.value != null ? this.getClass().hashCode() * 31 + this.value.hashCode() : System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ValueDecfloat) {
            BigDecimal bigDecimal = ((ValueDecfloat)object).value;
            if (this.value != null) {
                return this.value.equals(bigDecimal);
            }
            if (bigDecimal == null && this == object) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getMemory() {
        return this.value != null ? this.value.precision() + 120 : 32;
    }

    public boolean isFinite() {
        return this.value != null;
    }

    public static ValueDecfloat get(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.equals(bigDecimal = bigDecimal.stripTrailingZeros())) {
            return ZERO;
        }
        if (BigDecimal.ONE.equals(bigDecimal)) {
            return ONE;
        }
        return (ValueDecfloat)Value.cache(new ValueDecfloat(bigDecimal));
    }
}

