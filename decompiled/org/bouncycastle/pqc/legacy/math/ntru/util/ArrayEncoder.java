/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.bouncycastle.pqc.legacy.math.ntru.util.Util;
import org.bouncycastle.util.Arrays;

public class ArrayEncoder {
    private static final int[] COEFF1_TABLE = new int[]{0, 0, 0, 1, 1, 1, -1, -1};
    private static final int[] COEFF2_TABLE = new int[]{0, 1, -1, 0, 1, -1, 0, 1};
    private static final int[] BIT1_TABLE = new int[]{1, 1, 1, 0, 0, 0, 1, 0, 1};
    private static final int[] BIT2_TABLE = new int[]{1, 1, 1, 1, 0, 0, 0, 1, 0};
    private static final int[] BIT3_TABLE = new int[]{1, 0, 1, 0, 0, 1, 1, 1, 0};

    public static byte[] encodeModQ(int[] nArray, int n) {
        int n2 = 31 - Integer.numberOfLeadingZeros(n);
        int n3 = nArray.length * n2;
        int n4 = (n3 + 7) / 8;
        byte[] byArray = new byte[n4];
        int n5 = 0;
        int n6 = 0;
        for (int i = 0; i < nArray.length; ++i) {
            for (int j = 0; j < n2; ++j) {
                int n7 = nArray[i] >> j & 1;
                int n8 = n6++;
                byArray[n8] = (byte)(byArray[n8] | n7 << n5);
                if (n5 == 7) {
                    n5 = 0;
                    continue;
                }
                ++n5;
            }
        }
        return byArray;
    }

    public static int[] decodeModQ(byte[] byArray, int n, int n2) {
        int[] nArray = new int[n];
        int n3 = 31 - Integer.numberOfLeadingZeros(n2);
        int n4 = n * n3;
        int n5 = 0;
        for (int i = 0; i < n4; ++i) {
            if (i <= 0 || i % n3 == 0) {
                // empty if block
            }
            int n6 = ArrayEncoder.getBit(byArray, i);
            int n7 = ++n5;
            nArray[n7] = nArray[n7] + (n6 << i % n3);
        }
        return nArray;
    }

    public static int[] decodeModQ(InputStream inputStream2, int n, int n2) throws IOException {
        int n3 = 31 - Integer.numberOfLeadingZeros(n2);
        int n4 = (n * n3 + 7) / 8;
        byte[] byArray = Util.readFullLength(inputStream2, n4);
        return ArrayEncoder.decodeModQ(byArray, n, n2);
    }

    public static int[] decodeMod3Sves(byte[] byArray, int n) {
        int[] nArray = new int[n];
        int n2 = 0;
        int n3 = 0;
        while (n3 < byArray.length * 8) {
            int n4 = ArrayEncoder.getBit(byArray, n3++);
            int n5 = ArrayEncoder.getBit(byArray, n3++);
            int n6 = ArrayEncoder.getBit(byArray, n3++);
            int n7 = n4 * 4 + n5 * 2 + n6;
            nArray[n2++] = COEFF1_TABLE[n7];
            nArray[n2++] = COEFF2_TABLE[n7];
            if (n2 <= n - 2) continue;
            break;
        }
        return nArray;
    }

    public static byte[] encodeMod3Sves(int[] nArray) {
        int n = (nArray.length * 3 + 1) / 2;
        int n2 = (n + 7) / 8;
        byte[] byArray = new byte[n2];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < nArray.length / 2 * 2) {
            int n6 = nArray[n5++] + 1;
            int n7 = nArray[n5++] + 1;
            if (n6 == 0 && n7 == 0) {
                throw new IllegalStateException("Illegal encoding!");
            }
            int n8 = n6 * 3 + n7;
            int[] nArray2 = new int[]{BIT1_TABLE[n8], BIT2_TABLE[n8], BIT3_TABLE[n8]};
            for (int i = 0; i < 3; ++i) {
                int n9 = n4++;
                byArray[n9] = (byte)(byArray[n9] | nArray2[i] << n3);
                if (n3 == 7) {
                    n3 = 0;
                    continue;
                }
                ++n3;
            }
        }
        return byArray;
    }

    public static byte[] encodeMod3Tight(int[] nArray) {
        int n;
        BigInteger bigInteger = BigInteger.ZERO;
        for (n = nArray.length - 1; n >= 0; --n) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(3L));
            bigInteger = bigInteger.add(BigInteger.valueOf(nArray[n] + 1));
        }
        n = (BigInteger.valueOf(3L).pow(nArray.length).bitLength() + 7) / 8;
        byte[] byArray = bigInteger.toByteArray();
        if (byArray.length < n) {
            byte[] byArray2 = new byte[n];
            System.arraycopy(byArray, 0, byArray2, n - byArray.length, byArray.length);
            return byArray2;
        }
        if (byArray.length > n) {
            byArray = Arrays.copyOfRange(byArray, 1, byArray.length);
        }
        return byArray;
    }

    public static int[] decodeMod3Tight(byte[] byArray, int n) {
        BigInteger bigInteger = new BigInteger(1, byArray);
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = bigInteger.mod(BigInteger.valueOf(3L)).intValue() - 1;
            if (nArray[i] > 1) {
                int n2 = i;
                nArray[n2] = nArray[n2] - 3;
            }
            bigInteger = bigInteger.divide(BigInteger.valueOf(3L));
        }
        return nArray;
    }

    public static int[] decodeMod3Tight(InputStream inputStream2, int n) throws IOException {
        int n2 = (int)Math.ceil((double)n * Math.log(3.0) / Math.log(2.0) / 8.0);
        byte[] byArray = Util.readFullLength(inputStream2, n2);
        return ArrayEncoder.decodeMod3Tight(byArray, n);
    }

    private static int getBit(byte[] byArray, int n) {
        int n2 = n / 8;
        int n3 = byArray[n2] & 0xFF;
        return n3 >> n % 8 & 1;
    }
}

