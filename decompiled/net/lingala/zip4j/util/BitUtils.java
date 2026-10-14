/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

public class BitUtils {
    public static boolean isBitSet(byte b, int pos) {
        return ((long)b & 1L << pos) != 0L;
    }

    public static byte setBit(byte b, int pos) {
        return (byte)(b | 1 << pos);
    }

    public static byte unsetBit(byte b, int pos) {
        return (byte)(b & ~(1 << pos));
    }
}

