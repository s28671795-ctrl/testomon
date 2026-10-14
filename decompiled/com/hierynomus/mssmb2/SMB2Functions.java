/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.Charsets;

public class SMB2Functions {
    private static final byte[] EMPTY_BYTES = new byte[0];

    public static byte[] unicode(String s2) {
        if (s2 == null) {
            return EMPTY_BYTES;
        }
        return s2.getBytes(Charsets.UTF_16LE);
    }
}

