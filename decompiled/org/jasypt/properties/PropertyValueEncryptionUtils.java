/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.properties;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.TextEncryptor;

public final class PropertyValueEncryptionUtils {
    private static final String ENCRYPTED_VALUE_PREFIX = "ENC(";
    private static final String ENCRYPTED_VALUE_SUFFIX = ")";

    public static boolean isEncryptedValue(String value) {
        if (value == null) {
            return false;
        }
        String trimmedValue = value.trim();
        return trimmedValue.startsWith(ENCRYPTED_VALUE_PREFIX) && trimmedValue.endsWith(ENCRYPTED_VALUE_SUFFIX);
    }

    private static String getInnerEncryptedValue(String value) {
        return value.substring(ENCRYPTED_VALUE_PREFIX.length(), value.length() - ENCRYPTED_VALUE_SUFFIX.length());
    }

    public static String decrypt(String encodedValue, StringEncryptor encryptor) {
        return encryptor.decrypt(PropertyValueEncryptionUtils.getInnerEncryptedValue(encodedValue.trim()));
    }

    public static String decrypt(String encodedValue, TextEncryptor encryptor) {
        return encryptor.decrypt(PropertyValueEncryptionUtils.getInnerEncryptedValue(encodedValue.trim()));
    }

    public static String encrypt(String decodedValue, StringEncryptor encryptor) {
        return ENCRYPTED_VALUE_PREFIX + encryptor.encrypt(decodedValue) + ENCRYPTED_VALUE_SUFFIX;
    }

    public static String encrypt(String decodedValue, TextEncryptor encryptor) {
        return ENCRYPTED_VALUE_PREFIX + encryptor.encrypt(decodedValue) + ENCRYPTED_VALUE_SUFFIX;
    }

    private PropertyValueEncryptionUtils() {
    }
}

