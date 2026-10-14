/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

import com.license4j.util.Base32;
import com.license4j.util.Hex;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Crypto {
    private static String a = null;

    public static String encrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        return Crypto.a(data, "SHA512withRSA", true);
    }

    public static String encrypt(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        a = key + key.substring(key.length() - 4, key.length()) + key.substring(0, 5);
        return Crypto.a(data, a, true);
    }

    public static String encrypt2(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        a = key + key.substring(key.length() - 4, key.length()) + key.substring(0, 5);
        return Crypto.a(data, a, false);
    }

    private static String a(String var0, String var1, boolean var2) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        byte[] var3 = new byte[]{-57, 115, 33, -116, 126, -56, -18, -103};
        PBEParameterSpec var8 = new PBEParameterSpec(var3, 10);
        PBEKeySpec var5 = new PBEKeySpec(((String)var1).toCharArray());
        SecretKey var6 = SecretKeyFactory.getInstance(new String(Base32.decode("KBBEKV3JORUE2RBVIFXGIRCFKM"))).generateSecret(var5);
        Cipher var4 = Cipher.getInstance(new String(Base32.decode("KBBEKV3JORUE2RBVIFXGIRCFKM")));
        var4.init(1, (Key)var6, var8);
        var0 = Hex.encodeHexString(var4.doFinal(var0.getBytes(StandardCharsets.UTF_8)));
        var1 = "";
        if (var0.length() > 60 && var2) {
            int var7;
            for (var7 = 0; var7 <= var0.length() - 60; var7 += 60) {
                var1 = (String)var1 + var0.substring(var7, var7 + 60) + "\n";
            }
            if (var0.length() > var7 - 60) {
                var1 = (String)var1 + var0.substring(var7, var0.length()) + "\n";
            }
        } else {
            var1 = var0;
        }
        return var1;
    }

    public static String decrypt(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        a = key + key.substring(key.length() - 4, key.length()) + key.substring(0, 5);
        return Crypto.a(data, a);
    }

    public static String decrypt(String data) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        return Crypto.a(data, "SHA512withRSA");
    }

    private static String a(String var0, String var1) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        String var2;
        block3: {
            String var10000 = var0 = var0.replace("\r\n", "").replace("\n", "");
            var0 = var10000.substring(var10000.indexOf(")") + 1, var0.length());
            var2 = "";
            byte[] var3 = new byte[]{-57, 115, 33, -116, 126, -56, -18, -103};
            PBEParameterSpec var9 = new PBEParameterSpec(var3, 10);
            PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray());
            SecretKey var7 = SecretKeyFactory.getInstance(new String(Base32.decode("KBBEKV3JORUE2RBVIFXGIRCFKM"))).generateSecret(var6);
            Cipher var4 = Cipher.getInstance(new String(Base32.decode("KBBEKV3JORUE2RBVIFXGIRCFKM")));
            var4.init(2, (Key)var7, var9);
            try {
                var2 = new String(var4.doFinal(Hex.decodeHex(var0.toCharArray())), StandardCharsets.UTF_8);
            }
            catch (Exception var5) {
                var0 = "EX6101";
                String var10 = System.getProperty("LicensingDebug");
                if (var10 == null) {
                    var10 = System.getProperty("licensingdebug");
                }
                if (var10 == null || var10.compareToIgnoreCase("true") != 0) break block3;
                StringWriter var12 = new StringWriter();
                PrintWriter var11 = new PrintWriter((Writer)var12, true);
                var5.printStackTrace(var11);
                var12.getBuffer();
                StringBuilder var8 = new StringBuilder();
                var8.append(var0);
                var8.append(": ");
                var8.append(var12.getBuffer().toString());
                System.err.println(var8.toString());
            }
        }
        return var2;
    }
}

