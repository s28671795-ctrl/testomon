/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.cert.X509Certificate;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConfigurationException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.X509SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;

public class X509V3SecurityTokenImpl
extends X509SecurityTokenImpl {
    private String alias;

    public X509V3SecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, byte[] binaryContent, String id, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        super(WSSecurityTokenConstants.X509V3Token, wsInboundSecurityContext, crypto, callbackHandler, id, WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier, securityProperties, true);
        try (UnsyncByteArrayInputStream inputStream2 = new UnsyncByteArrayInputStream(binaryContent);){
            X509Certificate x509Certificate = this.getCrypto().loadCertificate(inputStream2);
            this.setX509Certificates(new X509Certificate[]{x509Certificate});
        }
        catch (IOException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e, "parseError");
        }
        if (this.getCrypto().getX509Identifier(this.getX509Certificates()[0]) == null) {
            try {
                Crypto decCrypto = securityProperties.getDecryptionCrypto();
                if (decCrypto != null && decCrypto != this.getCrypto() && decCrypto.getX509Identifier(this.getX509Certificates()[0]) != null) {
                    this.setCrypto(decCrypto);
                }
            }
            catch (WSSConfigurationException wSSConfigurationException) {
                // empty catch block
            }
        }
    }

    @Override
    protected String getAlias() throws XMLSecurityException {
        if (this.alias == null) {
            this.alias = this.getCrypto().getX509Identifier(this.getX509Certificates()[0]);
        }
        return this.alias;
    }
}

