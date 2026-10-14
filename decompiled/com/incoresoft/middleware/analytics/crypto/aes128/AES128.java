/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.crypto.aes128;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES128 {
    private static final byte[] STab = new byte[]{99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] ITab = new byte[]{82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] MixM = new int[]{2, 3, 1, 1, 1, 2, 3, 1, 1, 1, 2, 3, 3, 1, 1, 2};
    private static final int[] MixMI = new int[]{14, 11, 13, 9, 9, 14, 11, 13, 13, 9, 14, 11, 11, 13, 9, 14};
    private static final byte[] RCcon = new byte[]{1, 2, 4, 8, 16, 32, 64, -128, 27, 54};
    private static byte[][] MulTab = null;
    private final byte[] RKey;
    private final byte[] Stat;
    private final SecureRandom Rnd;
    private final Base64.Encoder B64E;
    private final Base64.Decoder B64D;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AES128() {
        Class<AES128> clazz = AES128.class;
        synchronized (AES128.class) {
            if (MulTab == null) {
                MulTab = new byte[16][];
                for (int i = 0; i < 16; ++i) {
                    AES128.MulTab[i] = new byte[256];
                    for (int j = 0; j < 256; ++j) {
                        int p = 0;
                        int u = i;
                        int v = j;
                        for (int k = 0; k < 4; ++k) {
                            if ((u & 1) != 0) {
                                p ^= v;
                            }
                            u >>= 1;
                            if (((v <<= 1) & 0x100) == 0) continue;
                            v ^= 0x11B;
                        }
                        AES128.MulTab[i][j] = (byte)(p & 0xFF);
                    }
                }
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            this.Stat = new byte[16];
            this.RKey = new byte[176];
            this.Rnd = new SecureRandom();
            this.B64E = Base64.getEncoder().withoutPadding();
            this.B64D = Base64.getDecoder();
            return;
        }
    }

    private int indx(byte b) {
        return b & 0xFF;
    }

    private void SubBytes(byte[] Tab) {
        for (int i = 0; i < 16; ++i) {
            this.Stat[i] = Tab[this.indx(this.Stat[i])];
        }
    }

    private void RollState() {
        byte t2 = this.Stat[1];
        this.Stat[1] = this.Stat[5];
        this.Stat[5] = this.Stat[9];
        this.Stat[9] = this.Stat[13];
        this.Stat[13] = t2;
        t2 = this.Stat[2];
        this.Stat[2] = this.Stat[10];
        this.Stat[10] = t2;
        t2 = this.Stat[6];
        this.Stat[6] = this.Stat[14];
        this.Stat[14] = t2;
        t2 = this.Stat[15];
        this.Stat[15] = this.Stat[11];
        this.Stat[11] = this.Stat[7];
        this.Stat[7] = this.Stat[3];
        this.Stat[3] = t2;
    }

    private void RollStateI() {
        byte t2 = this.Stat[13];
        this.Stat[13] = this.Stat[9];
        this.Stat[9] = this.Stat[5];
        this.Stat[5] = this.Stat[1];
        this.Stat[1] = t2;
        t2 = this.Stat[14];
        this.Stat[14] = this.Stat[6];
        this.Stat[6] = t2;
        t2 = this.Stat[10];
        this.Stat[10] = this.Stat[2];
        this.Stat[2] = t2;
        t2 = this.Stat[3];
        this.Stat[3] = this.Stat[7];
        this.Stat[7] = this.Stat[11];
        this.Stat[11] = this.Stat[15];
        this.Stat[15] = t2;
    }

    private void MixColls(int[] M) {
        byte[] tmp = new byte[16];
        System.arraycopy(this.Stat, 0, tmp, 0, 16);
        for (int i = 0; i < 16; ++i) {
            int k = i & 0xC;
            int m4 = (i & 3) << 2;
            this.Stat[i] = (byte)(MulTab[M[m4]][this.indx(tmp[k])] ^ MulTab[M[m4 + 1]][this.indx(tmp[k + 1])] ^ MulTab[M[m4 + 2]][this.indx(tmp[k + 2])] ^ MulTab[M[m4 + 3]][this.indx(tmp[k + 3])]);
        }
        Arrays.fill(tmp, (byte)-1);
    }

    private void XORblock(byte[] A2, byte[] B, int a0, int b0) {
        for (int i = 0; i < 16; ++i) {
            int n = a0++;
            A2[n] = (byte)(A2[n] ^ B[b0++]);
        }
    }

    private void AddRound(int round) {
        this.XORblock(this.Stat, this.RKey, 0, round << 4);
    }

    private void CheckParms(byte[] data, int b0, int len, byte[] iv) throws Exception {
        if (data.length < b0 + len) {
            throw new Exception("Insufficient length of the array data");
        }
        if ((len & 0xF) != 0) {
            throw new Exception("len must be a multiple of 16 bytes");
        }
        if (iv != null && iv.length < 16) {
            throw new Exception("iv length must be at least 16 bytes");
        }
    }

    private void CryptStep(byte[] data, int b0) {
        System.arraycopy(data, b0, this.Stat, 0, 16);
        this.AddRound(0);
        for (int i = 1; i < 10; ++i) {
            this.SubBytes(STab);
            this.RollState();
            this.MixColls(MixM);
            this.AddRound(i);
        }
        this.SubBytes(STab);
        this.RollState();
        this.AddRound(10);
        System.arraycopy(this.Stat, 0, data, b0, 16);
    }

    private void DecryptStep(byte[] data, int b0) {
        System.arraycopy(data, b0, this.Stat, 0, 16);
        this.AddRound(10);
        for (int i = 9; i > 0; --i) {
            this.RollStateI();
            this.SubBytes(ITab);
            this.AddRound(i);
            this.MixColls(MixMI);
        }
        this.RollStateI();
        this.SubBytes(ITab);
        this.AddRound(0);
        System.arraycopy(this.Stat, 0, data, b0, 16);
    }

    public void SetKey(byte[] K2) {
        System.arraycopy(K2, 0, this.RKey, 0, 16);
        for (int i = 0; i < 10; ++i) {
            int j;
            int k = j = i << 4;
            this.RKey[j += 16] = (byte)(this.RKey[k] ^ STab[this.indx(this.RKey[k + 13])] ^ RCcon[i]);
            this.RKey[j + 1] = (byte)(this.RKey[k + 1] ^ STab[this.indx(this.RKey[k + 14])]);
            this.RKey[j + 2] = (byte)(this.RKey[k + 2] ^ STab[this.indx(this.RKey[k + 15])]);
            this.RKey[j + 3] = (byte)(this.RKey[k + 3] ^ STab[this.indx(this.RKey[k + 12])]);
            j += 4;
            k = 4;
            while (k < 16) {
                this.RKey[j] = (byte)(this.RKey[j - 16] ^ this.RKey[j - 4]);
                ++k;
                ++j;
            }
        }
    }

    public void CryptECB(byte[] data, int b0, int len) throws Exception {
        this.CheckParms(data, b0, len, null);
        int i = 0;
        while (i < len) {
            this.CryptStep(data, b0);
            i += 16;
            b0 += 16;
        }
    }

    public void DecryptECB(byte[] data, int b0, int len) throws Exception {
        this.CheckParms(data, b0, len, null);
        int i = 0;
        while (i < len) {
            this.DecryptStep(data, b0);
            i += 16;
            b0 += 16;
        }
    }

    public void CryptCBC(byte[] data, int b0, int len, byte[] iv) throws Exception {
        this.CheckParms(data, b0, len, iv);
        int i = 0;
        while (i < len) {
            this.XORblock(data, iv, b0, 0);
            this.CryptStep(data, b0);
            System.arraycopy(data, b0, iv, 0, 16);
            i += 16;
            b0 += 16;
        }
    }

    public void DecryptCBC(byte[] data, int b0, int len, byte[] iv) throws Exception {
        this.CheckParms(data, b0, len, iv);
        byte[] temp = new byte[16];
        int i = 0;
        while (i < len) {
            System.arraycopy(data, b0, temp, 0, 16);
            this.DecryptStep(data, b0);
            this.XORblock(data, iv, b0, 0);
            System.arraycopy(temp, 0, iv, 0, 16);
            i += 16;
            b0 += 16;
        }
    }

    public void Clean() {
        Arrays.fill(this.Stat, (byte)-1);
        Arrays.fill(this.RKey, (byte)-1);
    }

    public String CryptPacket(byte[] data) throws Exception {
        return this.CryptPacket(data, "PKCS5Padding");
    }

    public String CryptPacket(byte[] data, String padding) throws Exception {
        int l = data.length;
        int ln = l + 16 & 0xFFFFFFF0;
        byte[] pad = new byte[16];
        byte[] resdata = new byte[ln + 16];
        if (padding.equals("ISO7816Padding")) {
            Arrays.fill(pad, (byte)0);
            pad[0] = -128;
        } else if (padding.equals("PKCS5Padding")) {
            Arrays.fill(pad, (byte)(ln - l));
        } else {
            throw new Exception("CryptPacket: wrong padding type");
        }
        byte[] IV = new byte[16];
        this.Rnd.nextBytes(IV);
        System.arraycopy(IV, 0, resdata, 0, 16);
        System.arraycopy(data, 0, resdata, 16, l);
        System.arraycopy(pad, 0, resdata, 16 + l, ln - l);
        this.CryptCBC(resdata, 16, ln, IV);
        return this.B64E.encodeToString(resdata);
    }

    public byte[] DecryptPacket(String P2) throws Exception {
        return this.DecryptPacket(P2, "PKCS5Padding");
    }

    public byte[] DecryptPacket(String P2, String padding) throws Exception {
        byte[] data = this.B64D.decode(P2);
        int l = data.length;
        if (l < 32) {
            throw new Exception("DecryptPacket: data.lenght must be >=32");
        }
        if ((l & 0xF) != 0) {
            throw new Exception("DecryptPacket: data.lenght must be a multiple of 16 bytes");
        }
        this.DecryptCBC(data, 16, l - 16, data);
        if (padding.equals("ISO7816Padding")) {
            for (k = 0; k < 16 && data[--l] == 0; ++k) {
            }
            if (k == 16 || data[l] != -128) {
                throw new Exception("Remove ISO padding: wrong padding");
            }
        } else if (padding.equals("PKCS5Padding")) {
            int v;
            if ((v = data[--l]) < 1 || v > 16) {
                throw new Exception("Remove PKCS padding: wrong padding");
            }
            for (k = 1; k < v && data[--l] == (byte)v; ++k) {
            }
            if (k != v) {
                throw new Exception("Remove PKCS padding: wrong padding");
            }
        } else {
            throw new Exception("DecryptPacket: wrong padding type");
        }
        byte[] resdata = new byte[l -= 16];
        System.arraycopy(data, 16, resdata, 0, l);
        return resdata;
    }
}

