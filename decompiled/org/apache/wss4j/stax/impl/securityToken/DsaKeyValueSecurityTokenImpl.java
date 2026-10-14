/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import java.security.Principal;
import java.security.PublicKey;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.PublicKeyPrincipalImpl;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.xml.security.binding.xmldsig.DSAKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.DsaKeyValueSecurityToken;

public class DsaKeyValueSecurityTokenImpl
extends DsaKeyValueSecurityToken
implements org.apache.wss4j.stax.securityToken.DsaKeyValueSecurityToken {
    private CallbackHandler callbackHandler;
    private Crypto crypto;
    private WSSSecurityProperties securityProperties;
    private Principal principal;

    public DsaKeyValueSecurityTokenImpl(DSAKeyValueType dsaKeyValueType, WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, WSSSecurityProperties securityProperties) {
        super(dsaKeyValueType, wsInboundSecurityContext);
        this.crypto = crypto;
        this.callbackHandler = callbackHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    public void verify() throws XMLSecurityException {
        this.crypto.verifyTrust(this.getPublicKey());
    }

    @Override
    public Subject getSubject() throws WSSecurityException {
        return null;
    }

    @Override
    public Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        PublicKey publicKey = this.getPublicKey();
        try {
            return this.crypto.getPrivateKey(publicKey, this.callbackHandler);
        }
        catch (WSSecurityException ex) {
            Crypto decCrypto = this.securityProperties.getDecryptionCrypto();
            if (decCrypto != null && decCrypto != this.crypto) {
                return decCrypto.getPrivateKey(publicKey, this.callbackHandler);
            }
            throw ex;
        }
    }

    @Override
    public Principal getPrincipal() throws WSSecurityException {
        if (this.principal == null) {
            try {
                this.principal = new PublicKeyPrincipalImpl(this.getPublicKey());
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
            }
        }
        return this.principal;
    }
}

