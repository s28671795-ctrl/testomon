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
import org.apache.xml.security.binding.xmldsig.X509IssuerSerialType;
import org.apache.xml.security.exceptions.XMLSecurityException;

public class X509IssuerSerialTokenImpl
extends X509SecurityTokenImpl {
    private String alias;
    private final X509IssuerSerialType x509IssuerSerialType;

    X509IssuerSerialTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, X509IssuerSerialType x509IssuerSerialType, String id, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        super(WSSecurityTokenConstants.X509V3Token, wsInboundSecurityContext, crypto, callbackHandler, id, WSSecurityTokenConstants.KeyIdentifier_IssuerSerial, securityProperties, false);
        if (x509IssuerSerialType.getX509IssuerName() == null || x509IssuerSerialType.getX509SerialNumber() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noKeyinfo");
        }
        this.x509IssuerSerialType = x509IssuerSerialType;
    }

    @Override
    protected String getAlias() throws XMLSecurityException {
        if (this.alias == null) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
            cryptoType.setIssuerSerial(this.x509IssuerSerialType.getX509IssuerName(), this.x509IssuerSerialType.getX509SerialNumber());
            X509Certificate[] certs = null;
            if (this.getCrypto() != null) {
                certs = this.getCrypto().getX509Certificates(cryptoType);
                this.setX509Certificates(certs);
            }
            if (certs == null || certs.length == 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE);
            }
            super.setX509Certificates(new X509Certificate[]{certs[0]});
            this.alias = this.getCrypto().getX509Identifier(certs[0]);
            return this.alias;
        }
        return this.alias;
    }
}

