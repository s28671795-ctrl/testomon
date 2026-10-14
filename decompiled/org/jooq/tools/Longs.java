/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

public final class Longs {
    private Longs() {
    }

    public static Long tryParse(String string) {
        int digit;
        int length;
        if (string.isEmpty()) {
            return null;
        }
        int radix = 10;
        char firstChar = string.charAt(0);
        boolean negative = firstChar == '-';
        int index = negative || firstChar == '+' ? 1 : 0;
        if (index == (length = string.length())) {
            return null;
        }
        if ((digit = Character.digit(string.charAt(index++), 10)) < 0 || digit >= radix) {
            return null;
        }
        long accum2 = -digit;
        long cap = Long.MIN_VALUE / (long)radix;
        while (index < length) {
            if ((digit = Character.digit(string.charAt(index++), 10)) < 0 || digit >= radix || accum2 < cap) {
                return null;
            }
            if ((accum2 *= (long)radix) < Long.MIN_VALUE + (long)digit) {
                return null;
            }
            accum2 -= (long)digit;
        }
        if (negative) {
            return accum2;
        }
        if (accum2 == Long.MIN_VALUE) {
            return null;
        }
        return -accum2;
    }
}

