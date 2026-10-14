/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.UUID;

public final class Bits {
    public static int compareNotNull(char[] cArray, char[] cArray2) {
        if (cArray == cArray2) {
            return 0;
        }
        int n = Math.min(cArray.length, cArray2.length);
        for (int i = 0; i < n; ++i) {
            char c = cArray[i];
            char c2 = cArray2[i];
            if (c == c2) continue;
            return c > c2 ? 1 : -1;
        }
        return Integer.signum(cArray.length - cArray2.length);
    }

    public static int compareNotNullSigned(byte[] byArray, byte[] byArray2) {
        if (byArray == byArray2) {
            return 0;
        }
        int n = Math.min(byArray.length, byArray2.length);
        for (int i = 0; i < n; ++i) {
            byte by = byArray[i];
            byte by2 = byArray2[i];
            if (by == by2) continue;
            return by > by2 ? 1 : -1;
        }
        return Integer.signum(byArray.length - byArray2.length);
    }

    public static int compareNotNullUnsigned(byte[] byArray, byte[] byArray2) {
        if (byArray == byArray2) {
            return 0;
        }
        int n = Math.min(byArray.length, byArray2.length);
        for (int i = 0; i < n; ++i) {
            int n2 = byArray[i] & 0xFF;
            int n3 = byArray2[i] & 0xFF;
            if (n2 == n3) continue;
            return n2 > n3 ? 1 : -1;
        }
        return Integer.signum(byArray.length - byArray2.length);
    }

    public static int readInt(byte[] byArray, int n) {
        return (byArray[n++] << 24) + ((byArray[n++] & 0xFF) << 16) + ((byArray[n++] & 0xFF) << 8) + (byArray[n] & 0xFF);
    }

    public static int readIntLE(byte[] byArray, int n) {
        return (byArray[n++] & 0xFF) + ((byArray[n++] & 0xFF) << 8) + ((byArray[n++] & 0xFF) << 16) + (byArray[n] << 24);
    }

    public static long readLong(byte[] byArray, int n) {
        return ((long)Bits.readInt(byArray, n) << 32) + ((long)Bits.readInt(byArray, n + 4) & 0xFFFFFFFFL);
    }

    public static long readLongLE(byte[] byArray, int n) {
        return ((long)Bits.readIntLE(byArray, n) & 0xFFFFFFFFL) + ((long)Bits.readIntLE(byArray, n + 4) << 32);
    }

    public static double readDouble(byte[] byArray, int n) {
        return Double.longBitsToDouble(Bits.readLong(byArray, n));
    }

    public static double readDoubleLE(byte[] byArray, int n) {
        return Double.longBitsToDouble(Bits.readLongLE(byArray, n));
    }

    public static byte[] uuidToBytes(long l, long l2) {
        byte[] byArray = new byte[16];
        for (int i = 0; i < 8; ++i) {
            byArray[i] = (byte)(l >> 8 * (7 - i) & 0xFFL);
            byArray[8 + i] = (byte)(l2 >> 8 * (7 - i) & 0xFFL);
        }
        return byArray;
    }

    public static byte[] uuidToBytes(UUID uUID) {
        return Bits.uuidToBytes(uUID.getMostSignificantBits(), uUID.getLeastSignificantBits());
    }

    public static void writeInt(byte[] byArray, int n, int n2) {
        byArray[n++] = (byte)(n2 >> 24);
        byArray[n++] = (byte)(n2 >> 16);
        byArray[n++] = (byte)(n2 >> 8);
        byArray[n] = (byte)n2;
    }

    public static void writeIntLE(byte[] byArray, int n, int n2) {
        byArray[n++] = (byte)n2;
        byArray[n++] = (byte)(n2 >> 8);
        byArray[n++] = (byte)(n2 >> 16);
        byArray[n] = (byte)(n2 >> 24);
    }

    public static void writeLong(byte[] byArray, int n, long l) {
        Bits.writeInt(byArray, n, (int)(l >> 32));
        Bits.writeInt(byArray, n + 4, (int)l);
    }

    public static void writeLongLE(byte[] byArray, int n, long l) {
        Bits.writeIntLE(byArray, n, (int)l);
        Bits.writeIntLE(byArray, n + 4, (int)(l >> 32));
    }

    public static void writeDouble(byte[] byArray, int n, double d) {
        Bits.writeLong(byArray, n, Double.doubleToRawLongBits(d));
    }

    public static void writeDoubleLE(byte[] byArray, int n, double d) {
        Bits.writeLongLE(byArray, n, Double.doubleToRawLongBits(d));
    }

    private Bits() {
    }
}

