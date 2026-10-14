/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.encrypt;

import org.h2.security.BlockCipher;

class XTS {
    private static final int GF_128_FEEDBACK = 135;
    private static final int CIPHER_BLOCK_SIZE = 16;
    private final BlockCipher cipher;

    XTS(BlockCipher blockCipher) {
        this.cipher = blockCipher;
    }

    void encrypt(long l, int n, byte[] byArray, int n2) {
        byte[] byArray2 = this.initTweak(l);
        int n3 = 0;
        while (n3 + 16 <= n) {
            if (n3 > 0) {
                XTS.updateTweak(byArray2);
            }
            XTS.xorTweak(byArray, n3 + n2, byArray2);
            this.cipher.encrypt(byArray, n3 + n2, 16);
            XTS.xorTweak(byArray, n3 + n2, byArray2);
            n3 += 16;
        }
        if (n3 < n) {
            XTS.updateTweak(byArray2);
            XTS.swap(byArray, n3 + n2, n3 - 16 + n2, n - n3);
            XTS.xorTweak(byArray, n3 - 16 + n2, byArray2);
            this.cipher.encrypt(byArray, n3 - 16 + n2, 16);
            XTS.xorTweak(byArray, n3 - 16 + n2, byArray2);
        }
    }

    void decrypt(long l, int n, byte[] byArray, int n2) {
        byte[] byArray2;
        byte[] byArray3 = byArray2 = this.initTweak(l);
        int n3 = 0;
        while (n3 + 16 <= n) {
            if (n3 > 0) {
                XTS.updateTweak(byArray2);
                if (n3 + 16 + 16 > n && n3 + 16 < n) {
                    byArray3 = (byte[])byArray2.clone();
                    XTS.updateTweak(byArray2);
                }
            }
            XTS.xorTweak(byArray, n3 + n2, byArray2);
            this.cipher.decrypt(byArray, n3 + n2, 16);
            XTS.xorTweak(byArray, n3 + n2, byArray2);
            n3 += 16;
        }
        if (n3 < n) {
            XTS.swap(byArray, n3, n3 - 16 + n2, n - n3 + n2);
            XTS.xorTweak(byArray, n3 - 16 + n2, byArray3);
            this.cipher.decrypt(byArray, n3 - 16 + n2, 16);
            XTS.xorTweak(byArray, n3 - 16 + n2, byArray3);
        }
    }

    private byte[] initTweak(long l) {
        byte[] byArray = new byte[16];
        int n = 0;
        while (n < 16) {
            byArray[n] = (byte)(l & 0xFFL);
            ++n;
            l >>>= 8;
        }
        this.cipher.encrypt(byArray, 0, 16);
        return byArray;
    }

    private static void xorTweak(byte[] byArray, int n, byte[] byArray2) {
        for (int i = 0; i < 16; ++i) {
            int n2 = n + i;
            byArray[n2] = (byte)(byArray[n2] ^ byArray2[i]);
        }
    }

    private static void updateTweak(byte[] byArray) {
        int n = 0;
        byte by = 0;
        for (int i = 0; i < 16; ++i) {
            by = (byte)(byArray[i] >> 7 & 1);
            byArray[i] = (byte)((byArray[i] << 1) + n & 0xFF);
            n = by;
        }
        if (by != 0) {
            byArray[0] = (byte)(byArray[0] ^ 0x87);
        }
    }

    private static void swap(byte[] byArray, int n, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            byte by = byArray[n + i];
            byArray[n + i] = byArray[n2 + i];
            byArray[n2 + i] = by;
        }
    }
}

