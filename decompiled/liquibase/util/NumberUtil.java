/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import liquibase.util.ObjectUtil;

public abstract class NumberUtil {
    @Deprecated
    public static Number convertNumberToTargetClass(Number number, Class targetClass) throws IllegalArgumentException {
        return (Number)ObjectUtil.convert(number, targetClass);
    }

    public static Number parseNumber(String text, Class targetClass) {
        String trimmed = text.trim();
        if (targetClass.equals(Byte.class)) {
            return Byte.decode(trimmed);
        }
        if (targetClass.equals(Short.class)) {
            return Short.decode(trimmed);
        }
        if (targetClass.equals(Integer.class)) {
            return Integer.decode(trimmed);
        }
        if (targetClass.equals(Long.class)) {
            return Long.decode(trimmed);
        }
        if (targetClass.equals(BigInteger.class)) {
            return new BigInteger(trimmed);
        }
        if (targetClass.equals(Float.class)) {
            return Float.valueOf(trimmed);
        }
        if (targetClass.equals(Double.class)) {
            return Double.valueOf(trimmed);
        }
        if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return new BigDecimal(trimmed);
        }
        throw new IllegalArgumentException("Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
    }
}

