/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.derivedKey;

import java.nio.charset.StandardCharsets;
import org.apache.wss4j.common.derivedKey.AlgoFactory;
import org.apache.wss4j.common.derivedKey.DerivationAlgorithm;
import org.apache.wss4j.common.ext.WSSecurityException;

public final class DerivedKeyUtils {
    private DerivedKeyUtils() {
    }

    public static byte[] deriveKey(String algorithm, String label, int length, byte[] secret, byte[] nonce, int offset) throws WSSecurityException {
        byte[] labelBytes;
        DerivationAlgorithm algo = AlgoFactory.getInstance(algorithm);
        if (label == null || label.length() == 0) {
            String defaultLabel = "WS-SecureConversationWS-SecureConversation";
            labelBytes = defaultLabel.getBytes(StandardCharsets.UTF_8);
        } else {
            labelBytes = label.getBytes(StandardCharsets.UTF_8);
        }
        byte[] seed = new byte[labelBytes.length + nonce.length];
        System.arraycopy(labelBytes, 0, seed, 0, labelBytes.length);
        System.arraycopy(nonce, 0, seed, labelBytes.length, nonce.length);
        long keyLength = length;
        if (keyLength <= 0L) {
            keyLength = 32L;
        }
        return algo.createKey(secret, seed, offset, keyLength);
    }
}

