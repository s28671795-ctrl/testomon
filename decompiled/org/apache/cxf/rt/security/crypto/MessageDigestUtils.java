/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.cxf.common.util.StringUtils;

public final class MessageDigestUtils {
    public static final String ALGO_SHA_1 = "SHA-1";
    public static final String ALGO_SHA_256 = "SHA-256";
    public static final String ALGO_MD5 = "MD5";

    private MessageDigestUtils() {
    }

    public static String generate(byte[] input) {
        return MessageDigestUtils.generate(input, ALGO_SHA_256);
    }

    public static String generate(byte[] input, String algo) {
        try {
            byte[] messageDigest = MessageDigestUtils.createDigest(input, algo);
            return StringUtils.toHexString(messageDigest);
        }
        catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] createDigest(String input, String algo) {
        try {
            return MessageDigestUtils.createDigest(input.getBytes(StandardCharsets.UTF_8), algo);
        }
        catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] createDigest(byte[] input, String algo) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algo);
        return md.digest(input);
    }
}

