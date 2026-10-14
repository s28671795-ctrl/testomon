/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ldap;

public class SidDecoder {
    public static String decode(byte[] bytes) {
        StringBuilder strSid = new StringBuilder("S-");
        byte revision = bytes[0];
        strSid.append(revision);
        int countSubAuths = bytes[1] & 0xFF;
        long authority = 0L;
        for (int i = 2; i <= 7; ++i) {
            authority |= (long)bytes[i] << 8 * (5 - (i - 2));
        }
        strSid.append("-");
        strSid.append(Long.toHexString(authority));
        int offset = 8;
        int size = 4;
        for (int j = 0; j < countSubAuths; ++j) {
            long subAuthority = 0L;
            for (int k = 0; k < size; ++k) {
                subAuthority |= (long)(bytes[offset + k] & 0xFF) << 8 * k;
            }
            strSid.append("-");
            strSid.append(subAuthority);
            offset += size;
        }
        return strSid.toString();
    }
}

