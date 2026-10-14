/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.util.Arrays;

public final class Hex {
    private static final char[] HEX_CHARS;
    private static final byte[] DECODE;

    private Hex() {
    }

    public static char[] encode(byte ... value) {
        if (value == null) {
            return null;
        }
        int l = value.length;
        char[] encoded = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; ++i) {
            encoded[j++] = HEX_CHARS[(0xF0 & value[i]) >>> 4];
            encoded[j++] = HEX_CHARS[0xF & value[i]];
        }
        return encoded;
    }

    public static byte[] decode(char ... value) {
        if (value == null) {
            return null;
        }
        int l = value.length;
        if ((l & 1) != 0) {
            throw new IllegalArgumentException(String.format("Cannot decode odd number of characters for %s", String.valueOf(value)));
        }
        byte[] decoded = new byte[l >> 1];
        int i = 0;
        for (int j = 0; j < l; j += 2) {
            int high = Hex.decode(value, j) << 4;
            int low = Hex.decode(value, j + 1);
            decoded[i] = (byte)((high | low) & 0xFF);
            ++i;
        }
        return decoded;
    }

    protected static int decode(char[] hex, int i) {
        char c = hex[i];
        if (c > 'f') {
            throw new IllegalArgumentException(String.format("Invalid hex character '%s' at position %s in %s", Character.valueOf(c), i, Arrays.toString(hex)));
        }
        byte b = DECODE[c];
        if (b < 0) {
            throw new IllegalArgumentException(String.format("Invalid hex character '%s' at position %s in %s", Character.valueOf(c), i, Arrays.toString(hex)));
        }
        return b;
    }

    static {
        int i;
        HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        DECODE = new byte[103];
        Arrays.fill(DECODE, (byte)-1);
        for (i = 48; i <= 57; ++i) {
            Hex.DECODE[i] = (byte)(i - 48);
        }
        for (i = 65; i <= 70; ++i) {
            Hex.DECODE[i] = (byte)(i - 65 + 10);
        }
        for (i = 97; i <= 102; ++i) {
            Hex.DECODE[i] = (byte)(i - 97 + 10);
        }
    }
}

