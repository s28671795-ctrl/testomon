/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

public class Hex {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static byte[] decodeHex(char[] data) {
        int length = data.length;
        byte[] decodedData = new byte[length >> 1];
        int i = 0;
        int j = 0;
        while (j < length) {
            int digit = Character.digit(data[j++], 16) << 4;
            decodedData[i] = (byte)(digit |= Character.digit(data[j++], 16));
            ++i;
        }
        return decodedData;
    }

    public static String encodeHexString(byte[] data) {
        int length = data.length;
        char[] hexString = new char[length << 1];
        int j = 0;
        for (int i = 0; i < length; ++i) {
            hexString[j++] = HEX_CHARS[(0xF0 & data[i]) >>> 4];
            hexString[j++] = HEX_CHARS[0xF & data[i]];
        }
        return new String(hexString);
    }
}

