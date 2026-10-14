/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.cert.X509Certificate;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.X509SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;

public class X509SKISecurityTokenImpl
extends X509SecurityTokenImpl {
    private String alias;
    private final byte[] binaryContent;

    X509SKISecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, byte[] binaryContent, String id, WSSSecurityProperties securityProperties) {
        super(WSSecurityTokenConstants.X509V3Token, wsInboundSecurityContext, crypto, callbackHandler, id, WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier, securityProperties, false);
        this.binaryContent = binaryContent;
    }

    @Override
    protected String getAlias() throws XMLSecurityException {
        if (this.alias == null) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.SKI_BYTES);
            cryptoType.setBytes(this.binaryContent);
            X509Certificate[] certs = null;
            if (this.getCrypto() != null) {
                certs = this.getCrypto().getX509Certificates(cryptoType);
            }
            if (certs == null || certs.length == 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE);
            }
            super.setX509Certificates(new X509Certificate[]{certs[0]});
            this.alias = this.getCrypto().getX509Identifier(certs[0]);
        }
        return this.alias;
    }
}

