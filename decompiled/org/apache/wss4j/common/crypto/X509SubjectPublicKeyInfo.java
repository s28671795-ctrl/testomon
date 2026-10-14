/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.security.PublicKey;
import org.apache.wss4j.common.crypto.DERDecoder;
import org.apache.wss4j.common.ext.WSSecurityException;

public class X509SubjectPublicKeyInfo
extends DERDecoder {
    public X509SubjectPublicKeyInfo(PublicKey key) throws WSSecurityException {
        super(key.getEncoded());
        if (!"X.509".equalsIgnoreCase(key.getFormat()) && !"X509".equalsIgnoreCase(key.getFormat())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Support for X.509-encoded public keys only"});
        }
    }

    public X509SubjectPublicKeyInfo(byte[] x509EncodedPublicKey) throws WSSecurityException {
        super(x509EncodedPublicKey);
    }

    public byte[] getSubjectPublicKey() throws WSSecurityException {
        this.reset();
        this.expect((byte)48);
        this.getLength();
        this.expect((byte)48);
        int algIDlen = this.getLength();
        if (algIDlen < 0) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Unsupported X.509 public key format"});
        }
        this.skip(algIDlen);
        this.expect((byte)3);
        int keyLen = this.getLength() - 1;
        if (keyLen < 0) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid X.509 public key format"});
        }
        this.skip(1);
        return this.getBytes(keyLen);
    }
}

