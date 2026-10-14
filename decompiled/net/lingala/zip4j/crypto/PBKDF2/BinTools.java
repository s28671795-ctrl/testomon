/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto.PBKDF2;

class BinTools {
    public static final String hex = "0123456789ABCDEF";

    BinTools() {
    }

    public static String bin2hex(byte[] b) {
        if (b == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(2 * b.length);
        for (int i = 0; i < b.length; ++i) {
            int v = (256 + b[i]) % 256;
            sb.append(hex.charAt(v / 16 & 0xF));
            sb.append(hex.charAt(v % 16 & 0xF));
        }
        return sb.toString();
    }

    public static byte[] hex2bin(String s2) {
        String m4 = s2;
        if (s2 == null) {
            m4 = "";
        } else if (s2.length() % 2 != 0) {
            m4 = "0" + s2;
        }
        byte[] r = new byte[m4.length() / 2];
        int i = 0;
        int n = 0;
        while (i < m4.length()) {
            char h2 = m4.charAt(i++);
            char l = m4.charAt(i++);
            r[n] = (byte)(BinTools.hex2bin(h2) * 16 + BinTools.hex2bin(l));
            ++n;
        }
        return r;
    }

    public static int hex2bin(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        throw new IllegalArgumentException("Input string may only contain hex digits, but found '" + c + "'");
    }
}

