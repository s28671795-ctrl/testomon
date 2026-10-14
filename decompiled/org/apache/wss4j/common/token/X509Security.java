/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class X509Security
extends BinarySecurity {
    public static final String X509_V3_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
    private X509Certificate cachedCert;

    public X509Security(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        super(elem, bspEnforcer);
        String valueType = this.getValueType();
        if (!X509_V3_TYPE.equals(valueType)) {
            bspEnforcer.handleBSPRule(BSPRule.R3033);
        }
    }

    public X509Security(Document doc) {
        super(doc);
        this.setValueType(X509_V3_TYPE);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public X509Certificate getX509Certificate(Crypto crypto) throws WSSecurityException {
        byte[] data;
        if (this.cachedCert != null) {
            return this.cachedCert;
        }
        Crypto certCrypto = crypto;
        if (certCrypto == null) {
            certCrypto = new Merlin();
        }
        if ((data = this.getToken()) == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidCertData", new Object[]{"0"});
        }
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);){
            X509Certificate x509Certificate = this.cachedCert = certCrypto.loadCertificate(in);
            return x509Certificate;
        }
        catch (IOException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "parseError");
        }
    }

    public void setX509Certificate(X509Certificate cert) throws WSSecurityException {
        if (cert == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCert");
        }
        this.cachedCert = cert;
        try {
            this.setToken(cert.getEncoded());
        }
        catch (CertificateEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "encodeError");
        }
    }
}

