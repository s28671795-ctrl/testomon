/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.crypto;

import com.incoresoft.middleware.analytics.crypto.aes128.AES128Wrapper;
import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsEncryptionException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Cipher {
    @NotNull
    public String encrypt(@NotNull String key, @NotNull String raw) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(key, "value must not be null");
        try {
            return new AES128Wrapper().encode(key, raw);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public String decrypt(@NotNull String key, @NotNull String encrypted) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(encrypted, "encrypted must not be null");
        try {
            return new AES128Wrapper().decode(key, encrypted);
        }
        catch (Exception e) {
            throw new AnalyticsEncryptionException();
        }
    }

    private static char changeChar(int i, byte c) {
        if (i <= 8 || i > 24) {
            return '\u0000';
        }
        if (47 < c && c < 56) {
            c = (byte)(c + i % 2);
        } else if (64 < c && c < 90) {
            c = (byte)(c + i % 2);
        } else if (97 < c && c < 122 && (c = (byte)(c + i % 3)) > 122) {
            c = (byte)(c - i % 3 * 2);
        }
        byte[] ch = new byte[]{c};
        if (i % 2 == 0) {
            return (char)new String(ch).toUpperCase().getBytes(StandardCharsets.UTF_8)[0];
        }
        if (i % 3 == 0) {
            return (char)new String(ch).toLowerCase().getBytes(StandardCharsets.UTF_8)[0];
        }
        return (char)c;
    }

    public static String generateKeyFromSig(String sig) {
        sig = Cipher.reverse(sig);
        Object res = "";
        int count = 0;
        for (byte i : sig.getBytes(StandardCharsets.UTF_8)) {
            char buf = Cipher.changeChar(count, i);
            if (buf != '\u0000') {
                res = (String)res + buf;
            }
            ++count;
        }
        return res;
    }

    public static String reverse(String s2) {
        return new StringBuilder(s2).reverse().toString();
    }
}

