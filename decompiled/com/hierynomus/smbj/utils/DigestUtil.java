/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.utils;

import com.hierynomus.security.MessageDigest;

public class DigestUtil {
    public static byte[] digest(MessageDigest digest, byte[] previous, byte[] extra) {
        digest.reset();
        digest.update(previous);
        digest.update(extra);
        return digest.digest();
    }
}

