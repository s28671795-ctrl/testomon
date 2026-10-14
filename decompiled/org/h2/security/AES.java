/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import org.h2.security.BlockCipher;
import org.h2.util.Bits;

public class AES
implements BlockCipher {
    private static final int[] RCON;
    private static final int[] FS;
    private static final int[] FT0;
    private static final int[] FT1;
    private static final int[] FT2;
    private static final int[] FT3;
    private static final int[] RS;
    private static final int[] RT0;
    private static final int[] RT1;
    private static final int[] RT2;
    private static final int[] RT3;
    private final int[] encKey = new int[44];
    private final int[] decKey = new int[44];

    private static int rot8(int n) {
        return n >>> 8 | n << 24;
    }

    private static int xtime(int n) {
        return (n << 1 ^ ((n & 0x80) != 0 ? 27 : 0)) & 0xFF;
    }

    private static int mul(int[] nArray, int[] nArray2, int n, int n2) {
        return n != 0 && n2 != 0 ? nArray[(nArray2[n] + nArray2[n2]) % 255] : 0;
    }

    private static int getDec(int n) {
        return RT0[FS[n >> 24 & 0xFF]] ^ RT1[FS[n >> 16 & 0xFF]] ^ RT2[FS[n >> 8 & 0xFF]] ^ RT3[FS[n & 0xFF]];
    }

    @Override
    public void setKey(byte[] byArray) {
        int n;
        int n2 = 0;
        for (n = 0; n < 4; ++n) {
            this.encKey[n] = this.decKey[n] = (byArray[n2++] & 0xFF) << 24 | (byArray[n2++] & 0xFF) << 16 | (byArray[n2++] & 0xFF) << 8 | byArray[n2++] & 0xFF;
        }
        n = 0;
        n2 = 0;
        while (n2 < 10) {
            this.encKey[n + 4] = this.encKey[n] ^ RCON[n2] ^ FS[this.encKey[n + 3] >> 16 & 0xFF] << 24 ^ FS[this.encKey[n + 3] >> 8 & 0xFF] << 16 ^ FS[this.encKey[n + 3] & 0xFF] << 8 ^ FS[this.encKey[n + 3] >> 24 & 0xFF];
            this.encKey[n + 5] = this.encKey[n + 1] ^ this.encKey[n + 4];
            this.encKey[n + 6] = this.encKey[n + 2] ^ this.encKey[n + 5];
            this.encKey[n + 7] = this.encKey[n + 3] ^ this.encKey[n + 6];
            ++n2;
            n += 4;
        }
        n2 = 0;
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2++] = this.encKey[n++];
        for (int i = 1; i < 10; ++i) {
            n -= 8;
            this.decKey[n2++] = AES.getDec(this.encKey[n++]);
            this.decKey[n2++] = AES.getDec(this.encKey[n++]);
            this.decKey[n2++] = AES.getDec(this.encKey[n++]);
            this.decKey[n2++] = AES.getDec(this.encKey[n++]);
        }
        n -= 8;
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2++] = this.encKey[n++];
        this.decKey[n2] = this.encKey[n];
    }

    @Override
    public void encrypt(byte[] byArray, int n, int n2) {
        for (int i = n; i < n + n2; i += 16) {
            this.encryptBlock(byArray, byArray, i);
        }
    }

    @Override
    public void decrypt(byte[] byArray, int n, int n2) {
        for (int i = n; i < n + n2; i += 16) {
            this.decryptBlock(byArray, byArray, i);
        }
    }

    private void encryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int[] nArray = this.encKey;
        int n2 = Bits.readInt(byArray, n) ^ nArray[0];
        int n3 = Bits.readInt(byArray, n + 4) ^ nArray[1];
        int n4 = Bits.readInt(byArray, n + 8) ^ nArray[2];
        int n5 = Bits.readInt(byArray, n + 12) ^ nArray[3];
        int n6 = FT0[n2 >> 24 & 0xFF] ^ FT1[n3 >> 16 & 0xFF] ^ FT2[n4 >> 8 & 0xFF] ^ FT3[n5 & 0xFF] ^ nArray[4];
        int n7 = FT0[n3 >> 24 & 0xFF] ^ FT1[n4 >> 16 & 0xFF] ^ FT2[n5 >> 8 & 0xFF] ^ FT3[n2 & 0xFF] ^ nArray[5];
        int n8 = FT0[n4 >> 24 & 0xFF] ^ FT1[n5 >> 16 & 0xFF] ^ FT2[n2 >> 8 & 0xFF] ^ FT3[n3 & 0xFF] ^ nArray[6];
        int n9 = FT0[n5 >> 24 & 0xFF] ^ FT1[n2 >> 16 & 0xFF] ^ FT2[n3 >> 8 & 0xFF] ^ FT3[n4 & 0xFF] ^ nArray[7];
        n2 = FT0[n6 >> 24 & 0xFF] ^ FT1[n7 >> 16 & 0xFF] ^ FT2[n8 >> 8 & 0xFF] ^ FT3[n9 & 0xFF] ^ nArray[8];
        n3 = FT0[n7 >> 24 & 0xFF] ^ FT1[n8 >> 16 & 0xFF] ^ FT2[n9 >> 8 & 0xFF] ^ FT3[n6 & 0xFF] ^ nArray[9];
        n4 = FT0[n8 >> 24 & 0xFF] ^ FT1[n9 >> 16 & 0xFF] ^ FT2[n6 >> 8 & 0xFF] ^ FT3[n7 & 0xFF] ^ nArray[10];
        n5 = FT0[n9 >> 24 & 0xFF] ^ FT1[n6 >> 16 & 0xFF] ^ FT2[n7 >> 8 & 0xFF] ^ FT3[n8 & 0xFF] ^ nArray[11];
        n6 = FT0[n2 >> 24 & 0xFF] ^ FT1[n3 >> 16 & 0xFF] ^ FT2[n4 >> 8 & 0xFF] ^ FT3[n5 & 0xFF] ^ nArray[12];
        n7 = FT0[n3 >> 24 & 0xFF] ^ FT1[n4 >> 16 & 0xFF] ^ FT2[n5 >> 8 & 0xFF] ^ FT3[n2 & 0xFF] ^ nArray[13];
        n8 = FT0[n4 >> 24 & 0xFF] ^ FT1[n5 >> 16 & 0xFF] ^ FT2[n2 >> 8 & 0xFF] ^ FT3[n3 & 0xFF] ^ nArray[14];
        n9 = FT0[n5 >> 24 & 0xFF] ^ FT1[n2 >> 16 & 0xFF] ^ FT2[n3 >> 8 & 0xFF] ^ FT3[n4 & 0xFF] ^ nArray[15];
        n2 = FT0[n6 >> 24 & 0xFF] ^ FT1[n7 >> 16 & 0xFF] ^ FT2[n8 >> 8 & 0xFF] ^ FT3[n9 & 0xFF] ^ nArray[16];
        n3 = FT0[n7 >> 24 & 0xFF] ^ FT1[n8 >> 16 & 0xFF] ^ FT2[n9 >> 8 & 0xFF] ^ FT3[n6 & 0xFF] ^ nArray[17];
        n4 = FT0[n8 >> 24 & 0xFF] ^ FT1[n9 >> 16 & 0xFF] ^ FT2[n6 >> 8 & 0xFF] ^ FT3[n7 & 0xFF] ^ nArray[18];
        n5 = FT0[n9 >> 24 & 0xFF] ^ FT1[n6 >> 16 & 0xFF] ^ FT2[n7 >> 8 & 0xFF] ^ FT3[n8 & 0xFF] ^ nArray[19];
        n6 = FT0[n2 >> 24 & 0xFF] ^ FT1[n3 >> 16 & 0xFF] ^ FT2[n4 >> 8 & 0xFF] ^ FT3[n5 & 0xFF] ^ nArray[20];
        n7 = FT0[n3 >> 24 & 0xFF] ^ FT1[n4 >> 16 & 0xFF] ^ FT2[n5 >> 8 & 0xFF] ^ FT3[n2 & 0xFF] ^ nArray[21];
        n8 = FT0[n4 >> 24 & 0xFF] ^ FT1[n5 >> 16 & 0xFF] ^ FT2[n2 >> 8 & 0xFF] ^ FT3[n3 & 0xFF] ^ nArray[22];
        n9 = FT0[n5 >> 24 & 0xFF] ^ FT1[n2 >> 16 & 0xFF] ^ FT2[n3 >> 8 & 0xFF] ^ FT3[n4 & 0xFF] ^ nArray[23];
        n2 = FT0[n6 >> 24 & 0xFF] ^ FT1[n7 >> 16 & 0xFF] ^ FT2[n8 >> 8 & 0xFF] ^ FT3[n9 & 0xFF] ^ nArray[24];
        n3 = FT0[n7 >> 24 & 0xFF] ^ FT1[n8 >> 16 & 0xFF] ^ FT2[n9 >> 8 & 0xFF] ^ FT3[n6 & 0xFF] ^ nArray[25];
        n4 = FT0[n8 >> 24 & 0xFF] ^ FT1[n9 >> 16 & 0xFF] ^ FT2[n6 >> 8 & 0xFF] ^ FT3[n7 & 0xFF] ^ nArray[26];
        n5 = FT0[n9 >> 24 & 0xFF] ^ FT1[n6 >> 16 & 0xFF] ^ FT2[n7 >> 8 & 0xFF] ^ FT3[n8 & 0xFF] ^ nArray[27];
        n6 = FT0[n2 >> 24 & 0xFF] ^ FT1[n3 >> 16 & 0xFF] ^ FT2[n4 >> 8 & 0xFF] ^ FT3[n5 & 0xFF] ^ nArray[28];
        n7 = FT0[n3 >> 24 & 0xFF] ^ FT1[n4 >> 16 & 0xFF] ^ FT2[n5 >> 8 & 0xFF] ^ FT3[n2 & 0xFF] ^ nArray[29];
        n8 = FT0[n4 >> 24 & 0xFF] ^ FT1[n5 >> 16 & 0xFF] ^ FT2[n2 >> 8 & 0xFF] ^ FT3[n3 & 0xFF] ^ nArray[30];
        n9 = FT0[n5 >> 24 & 0xFF] ^ FT1[n2 >> 16 & 0xFF] ^ FT2[n3 >> 8 & 0xFF] ^ FT3[n4 & 0xFF] ^ nArray[31];
        n2 = FT0[n6 >> 24 & 0xFF] ^ FT1[n7 >> 16 & 0xFF] ^ FT2[n8 >> 8 & 0xFF] ^ FT3[n9 & 0xFF] ^ nArray[32];
        n3 = FT0[n7 >> 24 & 0xFF] ^ FT1[n8 >> 16 & 0xFF] ^ FT2[n9 >> 8 & 0xFF] ^ FT3[n6 & 0xFF] ^ nArray[33];
        n4 = FT0[n8 >> 24 & 0xFF] ^ FT1[n9 >> 16 & 0xFF] ^ FT2[n6 >> 8 & 0xFF] ^ FT3[n7 & 0xFF] ^ nArray[34];
        n5 = FT0[n9 >> 24 & 0xFF] ^ FT1[n6 >> 16 & 0xFF] ^ FT2[n7 >> 8 & 0xFF] ^ FT3[n8 & 0xFF] ^ nArray[35];
        n6 = FT0[n2 >> 24 & 0xFF] ^ FT1[n3 >> 16 & 0xFF] ^ FT2[n4 >> 8 & 0xFF] ^ FT3[n5 & 0xFF] ^ nArray[36];
        n7 = FT0[n3 >> 24 & 0xFF] ^ FT1[n4 >> 16 & 0xFF] ^ FT2[n5 >> 8 & 0xFF] ^ FT3[n2 & 0xFF] ^ nArray[37];
        n8 = FT0[n4 >> 24 & 0xFF] ^ FT1[n5 >> 16 & 0xFF] ^ FT2[n2 >> 8 & 0xFF] ^ FT3[n3 & 0xFF] ^ nArray[38];
        n9 = FT0[n5 >> 24 & 0xFF] ^ FT1[n2 >> 16 & 0xFF] ^ FT2[n3 >> 8 & 0xFF] ^ FT3[n4 & 0xFF] ^ nArray[39];
        n2 = (FS[n6 >> 24 & 0xFF] << 24 | FS[n7 >> 16 & 0xFF] << 16 | FS[n8 >> 8 & 0xFF] << 8 | FS[n9 & 0xFF]) ^ nArray[40];
        n3 = (FS[n7 >> 24 & 0xFF] << 24 | FS[n8 >> 16 & 0xFF] << 16 | FS[n9 >> 8 & 0xFF] << 8 | FS[n6 & 0xFF]) ^ nArray[41];
        n4 = (FS[n8 >> 24 & 0xFF] << 24 | FS[n9 >> 16 & 0xFF] << 16 | FS[n6 >> 8 & 0xFF] << 8 | FS[n7 & 0xFF]) ^ nArray[42];
        n5 = (FS[n9 >> 24 & 0xFF] << 24 | FS[n6 >> 16 & 0xFF] << 16 | FS[n7 >> 8 & 0xFF] << 8 | FS[n8 & 0xFF]) ^ nArray[43];
        Bits.writeInt(byArray2, n, n2);
        Bits.writeInt(byArray2, n + 4, n3);
        Bits.writeInt(byArray2, n + 8, n4);
        Bits.writeInt(byArray2, n + 12, n5);
    }

    private void decryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int[] nArray = this.decKey;
        int n2 = Bits.readInt(byArray, n) ^ nArray[0];
        int n3 = Bits.readInt(byArray, n + 4) ^ nArray[1];
        int n4 = Bits.readInt(byArray, n + 8) ^ nArray[2];
        int n5 = Bits.readInt(byArray, n + 12) ^ nArray[3];
        int n6 = RT0[n2 >> 24 & 0xFF] ^ RT1[n5 >> 16 & 0xFF] ^ RT2[n4 >> 8 & 0xFF] ^ RT3[n3 & 0xFF] ^ nArray[4];
        int n7 = RT0[n3 >> 24 & 0xFF] ^ RT1[n2 >> 16 & 0xFF] ^ RT2[n5 >> 8 & 0xFF] ^ RT3[n4 & 0xFF] ^ nArray[5];
        int n8 = RT0[n4 >> 24 & 0xFF] ^ RT1[n3 >> 16 & 0xFF] ^ RT2[n2 >> 8 & 0xFF] ^ RT3[n5 & 0xFF] ^ nArray[6];
        int n9 = RT0[n5 >> 24 & 0xFF] ^ RT1[n4 >> 16 & 0xFF] ^ RT2[n3 >> 8 & 0xFF] ^ RT3[n2 & 0xFF] ^ nArray[7];
        n2 = RT0[n6 >> 24 & 0xFF] ^ RT1[n9 >> 16 & 0xFF] ^ RT2[n8 >> 8 & 0xFF] ^ RT3[n7 & 0xFF] ^ nArray[8];
        n3 = RT0[n7 >> 24 & 0xFF] ^ RT1[n6 >> 16 & 0xFF] ^ RT2[n9 >> 8 & 0xFF] ^ RT3[n8 & 0xFF] ^ nArray[9];
        n4 = RT0[n8 >> 24 & 0xFF] ^ RT1[n7 >> 16 & 0xFF] ^ RT2[n6 >> 8 & 0xFF] ^ RT3[n9 & 0xFF] ^ nArray[10];
        n5 = RT0[n9 >> 24 & 0xFF] ^ RT1[n8 >> 16 & 0xFF] ^ RT2[n7 >> 8 & 0xFF] ^ RT3[n6 & 0xFF] ^ nArray[11];
        n6 = RT0[n2 >> 24 & 0xFF] ^ RT1[n5 >> 16 & 0xFF] ^ RT2[n4 >> 8 & 0xFF] ^ RT3[n3 & 0xFF] ^ nArray[12];
        n7 = RT0[n3 >> 24 & 0xFF] ^ RT1[n2 >> 16 & 0xFF] ^ RT2[n5 >> 8 & 0xFF] ^ RT3[n4 & 0xFF] ^ nArray[13];
        n8 = RT0[n4 >> 24 & 0xFF] ^ RT1[n3 >> 16 & 0xFF] ^ RT2[n2 >> 8 & 0xFF] ^ RT3[n5 & 0xFF] ^ nArray[14];
        n9 = RT0[n5 >> 24 & 0xFF] ^ RT1[n4 >> 16 & 0xFF] ^ RT2[n3 >> 8 & 0xFF] ^ RT3[n2 & 0xFF] ^ nArray[15];
        n2 = RT0[n6 >> 24 & 0xFF] ^ RT1[n9 >> 16 & 0xFF] ^ RT2[n8 >> 8 & 0xFF] ^ RT3[n7 & 0xFF] ^ nArray[16];
        n3 = RT0[n7 >> 24 & 0xFF] ^ RT1[n6 >> 16 & 0xFF] ^ RT2[n9 >> 8 & 0xFF] ^ RT3[n8 & 0xFF] ^ nArray[17];
        n4 = RT0[n8 >> 24 & 0xFF] ^ RT1[n7 >> 16 & 0xFF] ^ RT2[n6 >> 8 & 0xFF] ^ RT3[n9 & 0xFF] ^ nArray[18];
        n5 = RT0[n9 >> 24 & 0xFF] ^ RT1[n8 >> 16 & 0xFF] ^ RT2[n7 >> 8 & 0xFF] ^ RT3[n6 & 0xFF] ^ nArray[19];
        n6 = RT0[n2 >> 24 & 0xFF] ^ RT1[n5 >> 16 & 0xFF] ^ RT2[n4 >> 8 & 0xFF] ^ RT3[n3 & 0xFF] ^ nArray[20];
        n7 = RT0[n3 >> 24 & 0xFF] ^ RT1[n2 >> 16 & 0xFF] ^ RT2[n5 >> 8 & 0xFF] ^ RT3[n4 & 0xFF] ^ nArray[21];
        n8 = RT0[n4 >> 24 & 0xFF] ^ RT1[n3 >> 16 & 0xFF] ^ RT2[n2 >> 8 & 0xFF] ^ RT3[n5 & 0xFF] ^ nArray[22];
        n9 = RT0[n5 >> 24 & 0xFF] ^ RT1[n4 >> 16 & 0xFF] ^ RT2[n3 >> 8 & 0xFF] ^ RT3[n2 & 0xFF] ^ nArray[23];
        n2 = RT0[n6 >> 24 & 0xFF] ^ RT1[n9 >> 16 & 0xFF] ^ RT2[n8 >> 8 & 0xFF] ^ RT3[n7 & 0xFF] ^ nArray[24];
        n3 = RT0[n7 >> 24 & 0xFF] ^ RT1[n6 >> 16 & 0xFF] ^ RT2[n9 >> 8 & 0xFF] ^ RT3[n8 & 0xFF] ^ nArray[25];
        n4 = RT0[n8 >> 24 & 0xFF] ^ RT1[n7 >> 16 & 0xFF] ^ RT2[n6 >> 8 & 0xFF] ^ RT3[n9 & 0xFF] ^ nArray[26];
        n5 = RT0[n9 >> 24 & 0xFF] ^ RT1[n8 >> 16 & 0xFF] ^ RT2[n7 >> 8 & 0xFF] ^ RT3[n6 & 0xFF] ^ nArray[27];
        n6 = RT0[n2 >> 24 & 0xFF] ^ RT1[n5 >> 16 & 0xFF] ^ RT2[n4 >> 8 & 0xFF] ^ RT3[n3 & 0xFF] ^ nArray[28];
        n7 = RT0[n3 >> 24 & 0xFF] ^ RT1[n2 >> 16 & 0xFF] ^ RT2[n5 >> 8 & 0xFF] ^ RT3[n4 & 0xFF] ^ nArray[29];
        n8 = RT0[n4 >> 24 & 0xFF] ^ RT1[n3 >> 16 & 0xFF] ^ RT2[n2 >> 8 & 0xFF] ^ RT3[n5 & 0xFF] ^ nArray[30];
        n9 = RT0[n5 >> 24 & 0xFF] ^ RT1[n4 >> 16 & 0xFF] ^ RT2[n3 >> 8 & 0xFF] ^ RT3[n2 & 0xFF] ^ nArray[31];
        n2 = RT0[n6 >> 24 & 0xFF] ^ RT1[n9 >> 16 & 0xFF] ^ RT2[n8 >> 8 & 0xFF] ^ RT3[n7 & 0xFF] ^ nArray[32];
        n3 = RT0[n7 >> 24 & 0xFF] ^ RT1[n6 >> 16 & 0xFF] ^ RT2[n9 >> 8 & 0xFF] ^ RT3[n8 & 0xFF] ^ nArray[33];
        n4 = RT0[n8 >> 24 & 0xFF] ^ RT1[n7 >> 16 & 0xFF] ^ RT2[n6 >> 8 & 0xFF] ^ RT3[n9 & 0xFF] ^ nArray[34];
        n5 = RT0[n9 >> 24 & 0xFF] ^ RT1[n8 >> 16 & 0xFF] ^ RT2[n7 >> 8 & 0xFF] ^ RT3[n6 & 0xFF] ^ nArray[35];
        n6 = RT0[n2 >> 24 & 0xFF] ^ RT1[n5 >> 16 & 0xFF] ^ RT2[n4 >> 8 & 0xFF] ^ RT3[n3 & 0xFF] ^ nArray[36];
        n7 = RT0[n3 >> 24 & 0xFF] ^ RT1[n2 >> 16 & 0xFF] ^ RT2[n5 >> 8 & 0xFF] ^ RT3[n4 & 0xFF] ^ nArray[37];
        n8 = RT0[n4 >> 24 & 0xFF] ^ RT1[n3 >> 16 & 0xFF] ^ RT2[n2 >> 8 & 0xFF] ^ RT3[n5 & 0xFF] ^ nArray[38];
        n9 = RT0[n5 >> 24 & 0xFF] ^ RT1[n4 >> 16 & 0xFF] ^ RT2[n3 >> 8 & 0xFF] ^ RT3[n2 & 0xFF] ^ nArray[39];
        n2 = (RS[n6 >> 24 & 0xFF] << 24 | RS[n9 >> 16 & 0xFF] << 16 | RS[n8 >> 8 & 0xFF] << 8 | RS[n7 & 0xFF]) ^ nArray[40];
        n3 = (RS[n7 >> 24 & 0xFF] << 24 | RS[n6 >> 16 & 0xFF] << 16 | RS[n9 >> 8 & 0xFF] << 8 | RS[n8 & 0xFF]) ^ nArray[41];
        n4 = (RS[n8 >> 24 & 0xFF] << 24 | RS[n7 >> 16 & 0xFF] << 16 | RS[n6 >> 8 & 0xFF] << 8 | RS[n9 & 0xFF]) ^ nArray[42];
        n5 = (RS[n9 >> 24 & 0xFF] << 24 | RS[n8 >> 16 & 0xFF] << 16 | RS[n7 >> 8 & 0xFF] << 8 | RS[n6 & 0xFF]) ^ nArray[43];
        Bits.writeInt(byArray2, n, n2);
        Bits.writeInt(byArray2, n + 4, n3);
        Bits.writeInt(byArray2, n + 8, n4);
        Bits.writeInt(byArray2, n + 12, n5);
    }

    @Override
    public int getKeyLength() {
        return 16;
    }

    static {
        int n;
        RCON = new int[10];
        FS = new int[256];
        FT0 = new int[256];
        FT1 = new int[256];
        FT2 = new int[256];
        FT3 = new int[256];
        RS = new int[256];
        RT0 = new int[256];
        RT1 = new int[256];
        RT2 = new int[256];
        RT3 = new int[256];
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        int n2 = 0;
        int n3 = 1;
        while (n2 < 256) {
            nArray[n2] = n3;
            nArray2[n3] = n2++;
            n3 ^= AES.xtime(n3);
        }
        n3 = 1;
        for (n2 = 0; n2 < 10; ++n2) {
            AES.RCON[n2] = n3 << 24;
            n3 = AES.xtime(n3);
        }
        AES.FS[0] = 99;
        AES.RS[99] = 0;
        for (n2 = 1; n2 < 256; ++n2) {
            n = n3 = nArray[255 - nArray2[n2]];
            n = (n << 1 | n >> 7) & 0xFF;
            n3 ^= n;
            n = (n << 1 | n >> 7) & 0xFF;
            n3 ^= n;
            n = (n << 1 | n >> 7) & 0xFF;
            n3 ^= n;
            n = (n << 1 | n >> 7) & 0xFF;
            AES.FS[n2] = (n3 ^= n ^ 0x63) & 0xFF;
            AES.RS[n3] = n2 & 0xFF;
        }
        for (n2 = 0; n2 < 256; ++n2) {
            n3 = FS[n2];
            n = AES.xtime(n3);
            AES.FT0[n2] = n3 ^ n ^ n3 << 8 ^ n3 << 16 ^ n << 24;
            AES.FT1[n2] = AES.rot8(FT0[n2]);
            AES.FT2[n2] = AES.rot8(FT1[n2]);
            AES.FT3[n2] = AES.rot8(FT2[n2]);
            n = RS[n2];
            AES.RT0[n2] = AES.mul(nArray, nArray2, 11, n) ^ AES.mul(nArray, nArray2, 13, n) << 8 ^ AES.mul(nArray, nArray2, 9, n) << 16 ^ AES.mul(nArray, nArray2, 14, n) << 24;
            AES.RT1[n2] = AES.rot8(RT0[n2]);
            AES.RT2[n2] = AES.rot8(RT1[n2]);
            AES.RT3[n2] = AES.rot8(RT2[n2]);
        }
    }
}

