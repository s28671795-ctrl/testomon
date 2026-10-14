/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.derivedKey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.wss4j.common.derivedKey.DerivationAlgorithm;
import org.apache.wss4j.common.ext.WSSecurityException;

public class P_SHA1
implements DerivationAlgorithm {
    @Override
    public byte[] createKey(byte[] secret, byte[] seed, int offset, long length) throws WSSecurityException {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            byte[] tempBytes = P_SHA1.pHash(secret, seed, mac, offset + (int)length);
            byte[] key = new byte[(int)length];
            System.arraycopy(tempBytes, offset, key, 0, key.length);
            return key;
        }
        catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "errorInKeyDerivation");
        }
    }

    private static byte[] pHash(byte[] secret, byte[] seed, Mac mac, int required) throws InvalidKeyException {
        int tocpy;
        byte[] out = new byte[required];
        int offset = 0;
        byte[] a = seed;
        SecretKeySpec key = new SecretKeySpec(secret, "HMACSHA1");
        mac.init(key);
        for (int bytesRequired = required; bytesRequired > 0; bytesRequired -= tocpy) {
            mac.update(a);
            a = mac.doFinal();
            mac.update(a);
            mac.update(seed);
            byte[] tmp = mac.doFinal();
            tocpy = Math.min(bytesRequired, tmp.length);
            System.arraycopy(tmp, 0, out, offset, tocpy);
            offset += tocpy;
        }
        return out;
    }
}

