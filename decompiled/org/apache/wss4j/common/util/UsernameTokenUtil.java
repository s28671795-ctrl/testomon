/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UsernameTokenUtil {
    public static final int DEFAULT_ITERATION = 1000;
    private static final Logger LOG = LoggerFactory.getLogger(UsernameTokenUtil.class);

    private UsernameTokenUtil() {
    }

    public static byte[] generateDerivedKey(byte[] password, byte[] salt, int iteration) throws WSSecurityException {
        byte[] pwSalt = new byte[salt.length + password.length];
        System.arraycopy(password, 0, pwSalt, 0, password.length);
        System.arraycopy(salt, 0, pwSalt, password.length, salt.length);
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException e) {
            LOG.debug(e.getMessage(), e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "decoding.general");
        }
        byte[] k = sha.digest(pwSalt);
        int iter = iteration;
        if (iter <= 0) {
            iter = 1000;
        }
        for (int i = 1; i < iter; ++i) {
            k = sha.digest(k);
        }
        return k;
    }

    public static byte[] generateDerivedKey(String password, byte[] salt, int iteration) throws WSSecurityException {
        return UsernameTokenUtil.generateDerivedKey(password.getBytes(StandardCharsets.UTF_8), salt, iteration);
    }

    public static byte[] generateSalt(boolean useForMac) {
        byte[] saltValue = null;
        try {
            saltValue = UsernameTokenUtil.generateNonce(16);
        }
        catch (WSSecurityException ex) {
            LOG.debug(ex.getMessage(), ex);
            return null;
        }
        saltValue[0] = useForMac ? 1 : 2;
        return saltValue;
    }

    private static byte[] generateNonce(int length) throws WSSecurityException {
        try {
            return XMLSecurityConstants.generateBytes(length);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error in generating nonce of length " + length});
        }
    }
}

