/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

public final class Ints {
    private Ints() {
    }

    public static Integer tryParse(String string) {
        return Ints.tryParse(string, 0, string.length());
    }

    public static Integer tryParse(String string, int begin, int end) {
        int digit;
        int index;
        if (begin < 0 || end > string.length() || end - begin < 1) {
            return null;
        }
        int radix = 10;
        char firstChar = string.charAt(begin);
        boolean negative = firstChar == '-';
        int n = index = negative || firstChar == '+' ? begin + 1 : begin;
        if (index == end) {
            return null;
        }
        if ((digit = Character.digit(string.charAt(index++), 10)) < 0 || digit >= radix) {
            return null;
        }
        int accum2 = -digit;
        int cap = Integer.MIN_VALUE / radix;
        while (index < end) {
            if ((digit = Character.digit(string.charAt(index++), 10)) < 0 || digit >= radix || accum2 < cap) {
                return null;
            }
            if ((accum2 *= radix) < Integer.MIN_VALUE + digit) {
                return null;
            }
            accum2 -= digit;
        }
        if (negative) {
            return accum2;
        }
        if (accum2 == Integer.MIN_VALUE) {
            return null;
        }
        return -accum2;
    }
}

