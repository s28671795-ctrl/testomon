/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.util.Pack;

class Utils {
    Utils() {
    }

    static void resizeArray(long[] lArray, int n, long[] lArray2, int n2, int n3, int n4) {
        long l = Long.MAX_VALUE;
        int n5 = 0;
        if (n < n2) {
            if (n % 64 != 0) {
                n5 = 64 - n % 64;
            }
            System.arraycopy(lArray2, 0, lArray, 0, n3);
            for (int i = 0; i < n5; ++i) {
                int n6 = n4 - 1;
                lArray[n6] = lArray[n6] & l >> i;
            }
        } else {
            System.arraycopy(lArray2, 0, lArray, 0, (n2 + 7) / 8);
        }
    }

    static void fromByte16ArrayToLongArray(long[] lArray, int[] nArray) {
        for (int i = 0; i != nArray.length; i += 4) {
            lArray[i / 4] = (long)nArray[i] & 0xFFFFL;
            int n = i / 4;
            lArray[n] = lArray[n] | (long)nArray[i + 1] << 16;
            int n2 = i / 4;
            lArray[n2] = lArray[n2] | (long)nArray[i + 2] << 32;
            int n3 = i / 4;
            lArray[n3] = lArray[n3] | (long)nArray[i + 3] << 48;
        }
    }

    static void fromByteArrayToByte16Array(int[] nArray, byte[] byArray) {
        byte[] byArray2 = byArray;
        if (byArray.length % 2 != 0) {
            byArray2 = new byte[(byArray.length + 1) / 2 * 2];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        }
        int n = 0;
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = Pack.littleEndianToShort(byArray2, n) & 0xFFFF;
            n += 2;
        }
    }

    static void fromLongArrayToByteArray(byte[] byArray, long[] lArray) {
        int n;
        int n2 = byArray.length / 8;
        for (n = 0; n != n2; ++n) {
            Pack.longToLittleEndian(lArray[n], byArray, n * 8);
        }
        if (byArray.length % 8 != 0) {
            n = n2 * 8;
            int n3 = 0;
            while (n < byArray.length) {
                byArray[n++] = (byte)(lArray[n2] >>> n3++ * 8);
            }
        }
    }

    static long bitMask(long l, long l2) {
        return (1L << (int)(l % l2)) - 1L;
    }

    static void fromByteArrayToLongArray(long[] lArray, byte[] byArray) {
        byte[] byArray2 = byArray;
        if (byArray.length % 8 != 0) {
            byArray2 = new byte[(byArray.length + 7) / 8 * 8];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        }
        int n = 0;
        for (int i = 0; i < lArray.length; ++i) {
            lArray[i] = Pack.littleEndianToLong(byArray2, n);
            n += 8;
        }
    }

    static void fromByte32ArrayToLongArray(long[] lArray, int[] nArray) {
        for (int i = 0; i != nArray.length; i += 2) {
            lArray[i / 2] = (long)nArray[i] & 0xFFFFFFFFL;
            int n = i / 2;
            lArray[n] = lArray[n] | (long)nArray[i + 1] << 32;
        }
    }

    static void fromLongArrayToByte32Array(int[] nArray, long[] lArray) {
        for (int i = 0; i != lArray.length; ++i) {
            nArray[2 * i] = (int)lArray[i];
            nArray[2 * i + 1] = (int)(lArray[i] >> 32);
        }
    }

    static void copyBytes(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        System.arraycopy(nArray, n, nArray2, n2, n3 / 2);
    }

    static int getByteSizeFromBitSize(int n) {
        return (n + 7) / 8;
    }

    static int getByte64SizeFromBitSize(int n) {
        return (n + 63) / 64;
    }

    static int toUnsigned8bits(int n) {
        return n & 0xFF;
    }

    static int toUnsigned16Bits(int n) {
        return n & 0xFFFF;
    }

    static void xorLongToByte16Array(int[] nArray, long l, int n) {
        int n2 = n + 0;
        nArray[n2] = nArray[n2] ^ (int)l & 0xFFFF;
        int n3 = n + 1;
        nArray[n3] = nArray[n3] ^ (int)(l >>> 16) & 0xFFFF;
        int n4 = n + 2;
        nArray[n4] = nArray[n4] ^ (int)(l >>> 32) & 0xFFFF;
        int n5 = n + 3;
        nArray[n5] = nArray[n5] ^ (int)(l >>> 48) & 0xFFFF;
    }
}

