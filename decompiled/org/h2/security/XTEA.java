/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import org.h2.message.DbException;
import org.h2.security.BlockCipher;
import org.h2.util.Bits;

public class XTEA
implements BlockCipher {
    private static final int DELTA = -1640531527;
    private int k0;
    private int k1;
    private int k2;
    private int k3;
    private int k4;
    private int k5;
    private int k6;
    private int k7;
    private int k8;
    private int k9;
    private int k10;
    private int k11;
    private int k12;
    private int k13;
    private int k14;
    private int k15;
    private int k16;
    private int k17;
    private int k18;
    private int k19;
    private int k20;
    private int k21;
    private int k22;
    private int k23;
    private int k24;
    private int k25;
    private int k26;
    private int k27;
    private int k28;
    private int k29;
    private int k30;
    private int k31;

    @Override
    public void setKey(byte[] byArray) {
        int[] nArray = new int[4];
        for (int i = 0; i < 16; i += 4) {
            nArray[i / 4] = Bits.readInt(byArray, i);
        }
        int[] nArray2 = new int[32];
        int n = 0;
        int n2 = 0;
        while (n < 32) {
            nArray2[n++] = n2 + nArray[n2 & 3];
            nArray2[n++] = (n2 -= 1640531527) + nArray[n2 >>> 11 & 3];
        }
        this.k0 = nArray2[0];
        this.k1 = nArray2[1];
        this.k2 = nArray2[2];
        this.k3 = nArray2[3];
        this.k4 = nArray2[4];
        this.k5 = nArray2[5];
        this.k6 = nArray2[6];
        this.k7 = nArray2[7];
        this.k8 = nArray2[8];
        this.k9 = nArray2[9];
        this.k10 = nArray2[10];
        this.k11 = nArray2[11];
        this.k12 = nArray2[12];
        this.k13 = nArray2[13];
        this.k14 = nArray2[14];
        this.k15 = nArray2[15];
        this.k16 = nArray2[16];
        this.k17 = nArray2[17];
        this.k18 = nArray2[18];
        this.k19 = nArray2[19];
        this.k20 = nArray2[20];
        this.k21 = nArray2[21];
        this.k22 = nArray2[22];
        this.k23 = nArray2[23];
        this.k24 = nArray2[24];
        this.k25 = nArray2[25];
        this.k26 = nArray2[26];
        this.k27 = nArray2[27];
        this.k28 = nArray2[28];
        this.k29 = nArray2[29];
        this.k30 = nArray2[30];
        this.k31 = nArray2[31];
    }

    @Override
    public void encrypt(byte[] byArray, int n, int n2) {
        if (n2 % 16 != 0) {
            throw DbException.getInternalError("unaligned len " + n2);
        }
        for (int i = n; i < n + n2; i += 8) {
            this.encryptBlock(byArray, byArray, i);
        }
    }

    @Override
    public void decrypt(byte[] byArray, int n, int n2) {
        if (n2 % 16 != 0) {
            throw DbException.getInternalError("unaligned len " + n2);
        }
        for (int i = n; i < n + n2; i += 8) {
            this.decryptBlock(byArray, byArray, i);
        }
    }

    private void encryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int n2 = Bits.readInt(byArray, n);
        int n3 = Bits.readInt(byArray, n + 4);
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k0;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k1;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k2;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k3;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k4;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k5;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k6;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k7;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k8;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k9;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k10;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k11;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k12;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k13;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k14;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k15;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k16;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k17;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k18;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k19;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k20;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k21;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k22;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k23;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k24;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k25;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k26;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k27;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k28;
        n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k29;
        n2 += (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k30;
        Bits.writeInt(byArray2, n, n2);
        Bits.writeInt(byArray2, n + 4, n3 += (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k31);
    }

    private void decryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int n2 = Bits.readInt(byArray, n);
        int n3 = Bits.readInt(byArray, n + 4);
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k31;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k30;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k29;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k28;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k27;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k26;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k25;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k24;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k23;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k22;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k21;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k20;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k19;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k18;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k17;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k16;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k15;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k14;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k13;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k12;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k11;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k10;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k9;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k8;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k7;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k6;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k5;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k4;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k3;
        n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k2;
        n3 -= (n2 >>> 5 ^ n2 << 4) + n2 ^ this.k1;
        Bits.writeInt(byArray2, n, n2 -= (n3 << 4 ^ n3 >>> 5) + n3 ^ this.k0);
        Bits.writeInt(byArray2, n + 4, n3);
    }

    @Override
    public int getKeyLength() {
        return 16;
    }
}

