/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

final class ASCIIUtility {
    private ASCIIUtility() {
    }

    public static int parseInt(byte[] b, int start2, int end, int radix) throws NumberFormatException {
        if (b == null) {
            throw new NumberFormatException("null");
        }
        int result = 0;
        boolean negative = false;
        int i = start2;
        if (end > start2) {
            int digit;
            int limit;
            if (b[i] == 45) {
                negative = true;
                limit = Integer.MIN_VALUE;
                ++i;
            } else {
                limit = -2147483647;
            }
            int multmin = limit / radix;
            if (i < end) {
                if ((digit = Character.digit((char)b[i++], radix)) < 0) {
                    throw new NumberFormatException("illegal number: " + ASCIIUtility.toString(b, start2, end));
                }
                result = -digit;
            }
            while (i < end) {
                if ((digit = Character.digit((char)b[i++], radix)) < 0) {
                    throw new NumberFormatException("illegal number");
                }
                if (result < multmin) {
                    throw new NumberFormatException("illegal number");
                }
                if ((result *= radix) < limit + digit) {
                    throw new NumberFormatException("illegal number");
                }
                result -= digit;
            }
        } else {
            throw new NumberFormatException("illegal number");
        }
        if (negative) {
            if (i > start2 + 1) {
                return result;
            }
            throw new NumberFormatException("illegal number");
        }
        return -result;
    }

    public static String toString(byte[] b, int start2, int end) {
        int size = end - start2;
        char[] theChars = new char[size];
        int i = 0;
        int j = start2;
        while (i < size) {
            theChars[i++] = (char)(b[j++] & 0xFF);
        }
        return new String(theChars);
    }
}

