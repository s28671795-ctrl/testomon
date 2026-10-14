/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static SecureRandom random = new SecureRandom();
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    public static String generatePassword(int len, String dic) {
        Object result = "";
        for (int i = 0; i < len; ++i) {
            int index = random.nextInt(dic.length());
            result = (String)result + dic.charAt(index);
        }
        return result;
    }

    public static String generateNumericPassword(int len) {
        return PasswordGenerator.generatePassword(len, NUMERIC);
    }

    public static String generateAlphaNumericUpperCase(int len) {
        return PasswordGenerator.generatePassword(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public static String generateAlphaNumericPassword(int len) {
        return PasswordGenerator.generatePassword(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    public static String generateAlphaNumericSpecPassword(int len) {
        return PasswordGenerator.generatePassword(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/");
    }
}

