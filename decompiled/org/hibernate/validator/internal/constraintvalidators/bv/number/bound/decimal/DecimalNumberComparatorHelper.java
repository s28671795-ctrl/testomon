/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.OptionalInt;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

final class DecimalNumberComparatorHelper {
    private DecimalNumberComparatorHelper() {
    }

    public static int compare(BigDecimal number, BigDecimal value) {
        return number.compareTo(value);
    }

    public static int compare(BigInteger number, BigDecimal value) {
        return new BigDecimal(number).compareTo(value);
    }

    public static int compare(Long number, BigDecimal value) {
        return BigDecimal.valueOf(number).compareTo(value);
    }

    public static int compare(Number number, BigDecimal value, OptionalInt treatNanAs) {
        if (number instanceof Double) {
            return DecimalNumberComparatorHelper.compare((Double)number, value, treatNanAs);
        }
        if (number instanceof Float) {
            return DecimalNumberComparatorHelper.compare((Float)number, value, treatNanAs);
        }
        if (number instanceof BigDecimal) {
            return DecimalNumberComparatorHelper.compare((BigDecimal)number, value);
        }
        if (number instanceof BigInteger) {
            return DecimalNumberComparatorHelper.compare((BigInteger)number, value);
        }
        if (number instanceof Byte || number instanceof Integer || number instanceof Long || number instanceof Short) {
            return DecimalNumberComparatorHelper.compare(number.longValue(), value);
        }
        return DecimalNumberComparatorHelper.compare(number.doubleValue(), value, treatNanAs);
    }

    public static int compare(Double number, BigDecimal value, OptionalInt treatNanAs) {
        OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
        if (infinity.isPresent()) {
            return infinity.getAsInt();
        }
        return BigDecimal.valueOf(number).compareTo(value);
    }

    public static int compare(Float number, BigDecimal value, OptionalInt treatNanAs) {
        OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
        if (infinity.isPresent()) {
            return infinity.getAsInt();
        }
        return BigDecimal.valueOf(number.floatValue()).compareTo(value);
    }
}

