/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.derivedKey;

import org.apache.wss4j.common.derivedKey.DerivationAlgorithm;
import org.apache.wss4j.common.derivedKey.P_SHA1;
import org.apache.wss4j.common.ext.WSSecurityException;

public final class AlgoFactory {
    private AlgoFactory() {
    }

    public static DerivationAlgorithm getInstance(String algorithm) throws WSSecurityException {
        if ("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk/p_sha1".equals(algorithm) || "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1".equals(algorithm)) {
            return new P_SHA1();
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unknownAlgorithm", new Object[]{algorithm});
    }
}

