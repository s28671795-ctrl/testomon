/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.security.MessageDigest;

public class MD5 {
    public static final String getMD5(String source2) {
        String res = "";
        try {
            String md5;
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(source2.getBytes());
            res = md5 = MD5.convertToHex(md.digest());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return res;
    }

    private static String convertToHex(byte[] srcData) {
        StringBuilder sbuffer = new StringBuilder();
        for (int i = 0; i < srcData.length; ++i) {
            int flag = srcData[i] >>> 4 & 0xF;
            int twoHalf = 0;
            do {
                if (0 <= flag && flag <= 9) {
                    sbuffer.append((char)(48 + flag));
                } else {
                    sbuffer.append((char)(97 + (flag - 10)));
                }
                flag = srcData[i] & 0xF;
            } while (twoHalf++ < 1);
        }
        return sbuffer.toString();
    }
}

