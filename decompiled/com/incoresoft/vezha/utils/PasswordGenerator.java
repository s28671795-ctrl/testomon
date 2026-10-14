/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.utils;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    public static String generatePassword(int len, String dic) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            int index = random.nextInt(dic.length());
            result.append(dic.charAt(index));
        }
        return result.toString();
    }

    public static String generateAlphaNumericPassword(int len) {
        return PasswordGenerator.generatePassword(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    public static String generateAlphaNumericSpecPassword(int len) {
        return PasswordGenerator.generatePassword(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/");
    }
}

