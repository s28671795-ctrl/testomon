/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

public class Base32 {
    private static final int[] a = new int[]{255, 255, 26, 27, 28, 29, 30, 31, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255};

    public static String encode(byte[] bytes) {
        int var1 = 0;
        int var2 = 0;
        int var3 = 0;
        switch (bytes.length) {
            case 1: {
                var3 = 6;
                break;
            }
            case 2: {
                var3 = 4;
                break;
            }
            case 3: {
                var3 = 3;
                break;
            }
            case 4: {
                var3 = 1;
            }
        }
        StringBuffer var5 = new StringBuffer((bytes.length + 7 << 3) / 5 + var3);
        while (var1 < bytes.length) {
            int var6;
            int n = var6 = bytes[var1] >= 0 ? bytes[var1] : bytes[var1] + 256;
            if (var2 > 3) {
                int var4 = var1 + 1 < bytes.length ? (bytes[var1 + 1] >= 0 ? bytes[var1 + 1] : bytes[var1 + 1] + 256) : 0;
                var6 &= 255 >> var2;
                var2 = (var2 + 5) % 8;
                var6 = var6 << var2 | var4 >> 8 - var2;
                ++var1;
            } else {
                var6 = var6 >> 8 - (var2 + 5) & 0x1F;
                if ((var2 = (var2 + 5) % 8) == 0) {
                    ++var1;
                }
            }
            var5.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(var6));
        }
        switch (bytes.length) {
            case 1: {
                var5.append("======");
                break;
            }
            case 2: {
                var5.append("====");
                break;
            }
            case 3: {
                var5.append("===");
                break;
            }
            case 4: {
                var5.append("=");
            }
        }
        return var5.toString();
    }

    public static byte[] decode(String base32) {
        byte[] var5 = new byte[base32.length() * 5 / 8];
        int var2 = 0;
        int var4 = 0;
        for (int var1 = 0; var1 < base32.length(); ++var1) {
            int var3 = base32.charAt(var1) - 48;
            if (var3 < 0 || var3 >= 80 || (var3 = a[var3]) == 255) continue;
            if (var2 <= 3) {
                if ((var2 = (var2 + 5) % 8) == 0) {
                    var5[var4] = (byte)(var5[var4] | var3);
                    if (++var4 < var5.length) continue;
                    break;
                }
                var5[var4] = (byte)(var5[var4] | var3 << 8 - var2);
                continue;
            }
            var2 = (var2 + 5) % 8;
            var5[var4] = (byte)(var5[var4] | var3 >>> var2);
            if (++var4 >= var5.length) break;
            var5[var4] = (byte)(var5[var4] | var3 << 8 - var2);
        }
        return var5;
    }
}

