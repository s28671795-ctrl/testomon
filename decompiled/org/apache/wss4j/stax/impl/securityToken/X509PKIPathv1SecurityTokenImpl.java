/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.X509SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;

public class X509PKIPathv1SecurityTokenImpl
extends X509SecurityTokenImpl {
    private String alias;

    public X509PKIPathv1SecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, byte[] binaryContent, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        super(WSSecurityTokenConstants.X509PkiPathV1Token, wsInboundSecurityContext, crypto, callbackHandler, id, keyIdentifier, securityProperties, true);
        try (UnsyncByteArrayInputStream in = new UnsyncByteArrayInputStream(binaryContent);){
            CertPath certPath = this.getCrypto().getCertificateFactory().generateCertPath(in);
            List<? extends Certificate> l = certPath.getCertificates();
            X509Certificate[] certs = new X509Certificate[l.size()];
            Iterator<? extends Certificate> iterator2 = l.iterator();
            for (int i = 0; i < l.size(); ++i) {
                certs[i] = (X509Certificate)iterator2.next();
            }
            if (certs.length > 0) {
                this.setX509Certificates(certs);
            }
        }
        catch (IOException | CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e, "parseError");
        }
    }

    @Override
    protected String getAlias() throws XMLSecurityException {
        if (this.alias == null && this.getCrypto() != null) {
            this.alias = this.getCrypto().getX509Identifier(this.getX509Certificates()[0]);
        }
        return this.alias;
    }
}

