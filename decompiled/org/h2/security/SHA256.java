/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.h2.util.Bits;

public class SHA256 {
    private SHA256() {
    }

    public static byte[] getHashWithSalt(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return SHA256.getHash(byArray3, true);
    }

    public static byte[] getKeyPasswordHash(String string, char[] cArray) {
        String string2 = string + "@";
        byte[] byArray = new byte[2 * (string2.length() + cArray.length)];
        int n = 0;
        int n2 = string2.length();
        for (int i = 0; i < n2; ++i) {
            int n3 = string2.charAt(i);
            byArray[n++] = (byte)(n3 >> 8);
            byArray[n++] = (byte)n3;
        }
        for (char c : cArray) {
            byArray[n++] = (byte)(c >> 8);
            byArray[n++] = (byte)c;
        }
        Arrays.fill(cArray, '\u0000');
        return SHA256.getHash(byArray, true);
    }

    public static byte[] getHMAC(byte[] byArray, byte[] byArray2) {
        return SHA256.initMac(byArray).doFinal(byArray2);
    }

    private static Mac initMac(byte[] byArray) {
        if (byArray.length == 0) {
            byArray = new byte[1];
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(byArray, "HmacSHA256"));
            return mac;
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException(generalSecurityException);
        }
    }

    public static byte[] getPBKDF2(byte[] byArray, byte[] byArray2, int n, int n2) {
        byte[] byArray3 = new byte[n2];
        Mac mac = SHA256.initMac(byArray);
        int n3 = 64 + Math.max(32, byArray2.length + 4);
        byte[] byArray4 = new byte[n3];
        byte[] byArray5 = null;
        int n4 = 1;
        for (int i = 0; i < n2; i += 32) {
            for (int j = 0; j < n; ++j) {
                if (j == 0) {
                    System.arraycopy(byArray2, 0, byArray4, 0, byArray2.length);
                    Bits.writeInt(byArray4, byArray2.length, n4);
                    n3 = byArray2.length + 4;
                } else {
                    System.arraycopy(byArray5, 0, byArray4, 0, 32);
                    n3 = 32;
                }
                mac.update(byArray4, 0, n3);
                byArray5 = mac.doFinal();
                for (int k = 0; k < 32 && k + i < n2; ++k) {
                    int n5 = k + i;
                    byArray3[n5] = (byte)(byArray3[n5] ^ byArray5[k]);
                }
            }
            ++n4;
        }
        Arrays.fill(byArray, (byte)0);
        return byArray3;
    }

    public static byte[] getHash(byte[] byArray, boolean bl) {
        byte[] byArray2;
        try {
            byArray2 = MessageDigest.getInstance("SHA-256").digest(byArray);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        if (bl) {
            Arrays.fill(byArray, (byte)0);
        }
        return byArray2;
    }
}

