/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions.api;

import java.security.SecureRandom;

public class TokenGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TOKEN_LENGTH = 32;
    private static final SecureRandom random = new SecureRandom();
    private static final char[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String nextToken() {
        char[] buf = new char[32];
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}

