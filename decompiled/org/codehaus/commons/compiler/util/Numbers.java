/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import org.codehaus.commons.nullanalysis.Nullable;

public final class Numbers {
    private static final int[] INT_LIMITS = new int[]{0, 0, Integer.MIN_VALUE, 0x55555556, 0x40000000, 0x33333334, 0x2AAAAAAB, 613566757, 0x20000000, 477218589, 0x1999999A, 390451573, 0x15555556, 330382100, 306783379, 0x11111112, 0x10000000, 0xF0F0F10, 238609295, 226050911, 0xCCCCCCD, 204522253, 195225787, 186737709, 0xAAAAAAB, 171798692, 165191050, 159072863, 153391690, 148102321, 0x8888889, 138547333, 0x8000000, 130150525, 0x7878788, 122713352, 119304648};
    private static final long[] LONG_LIMITS = new long[]{0L, 0L, Long.MIN_VALUE, 0x5555555555555556L, 0x4000000000000000L, 0x3333333333333334L, 0x2AAAAAAAAAAAAAABL, 2635249153387078803L, 0x2000000000000000L, 2049638230412172402L, 0x199999999999999AL, 1676976733973595602L, 0x1555555555555556L, 1418980313362273202L, 1317624576693539402L, 0x1111111111111112L, 0x1000000000000000L, 0xF0F0F0F0F0F0F10L, 1024819115206086201L, 970881267037344822L, 0xCCCCCCCCCCCCCCDL, 878416384462359601L, 838488366986797801L, 802032351030850071L, 0xAAAAAAAAAAAAAABL, 737869762948382065L, 0x9D89D89D89D89D9L, 683212743470724134L, 658812288346769701L, 636094623231363849L, 0x888888888888889L, 595056260442243601L, 0x800000000000000L, 558992244657865201L, 0x787878787878788L, 527049830677415761L, 512409557603043101L};

    private Numbers() {
    }

    public static int parseUnsignedInt(@Nullable String s2, int radix) throws NumberFormatException {
        if (s2 == null || s2.isEmpty()) {
            throw new NumberFormatException("null");
        }
        if (radix < 2 || radix > 36) {
            throw new NumberFormatException("Invalid radix " + radix);
        }
        int limit = INT_LIMITS[radix];
        int result = 0;
        for (int i = 0; i < s2.length(); ++i) {
            if (result < 0 || radix != 2 && result >= limit) {
                throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
            }
            int digitValue = Character.digit(s2.charAt(i), radix);
            if (digitValue == -1) {
                throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
            }
            int result2 = result * radix;
            result = result2 + digitValue;
            if (result2 >= 0 || result < 0) continue;
            throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
        }
        return result;
    }

    public static long parseUnsignedLong(@Nullable String s2, int radix) throws NumberFormatException {
        if (s2 == null || s2.isEmpty()) {
            throw new NumberFormatException("null");
        }
        if (radix < 2 || radix > 36) {
            throw new NumberFormatException("Invalid radix " + radix);
        }
        long limit = LONG_LIMITS[radix];
        long result = 0L;
        for (int i = 0; i < s2.length(); ++i) {
            if (result < 0L || radix != 2 && result >= limit) {
                throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
            }
            int digitValue = Character.digit(s2.charAt(i), radix);
            if (digitValue == -1) {
                throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
            }
            long result2 = result * (long)radix;
            result = result2 + (long)digitValue;
            if (result2 >= 0L || result < 0L) continue;
            throw new NumberFormatException("For input string \"" + s2 + "\" and radix " + radix);
        }
        return result;
    }
}

