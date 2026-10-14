/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import org.apache.wss4j.dom.str.STRParser;

public class STRParserResult {
    private X509Certificate[] certs;
    private byte[] secretKey;
    private PublicKey publicKey;
    private Principal principal;
    private boolean trustedCredential;
    private STRParser.REFERENCE_TYPE referenceType;

    public X509Certificate[] getCertificates() {
        return this.certs;
    }

    public Principal getPrincipal() {
        if (this.principal == null && this.certs != null && this.certs.length > 0) {
            this.principal = this.certs[0].getSubjectX500Principal();
        }
        return this.principal;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public byte[] getSecretKey() {
        return this.secretKey;
    }

    public boolean isTrustedCredential() {
        return this.trustedCredential;
    }

    public STRParser.REFERENCE_TYPE getCertificatesReferenceType() {
        return this.referenceType;
    }

    public void setCerts(X509Certificate[] certs) {
        this.certs = certs;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void setTrustedCredential(boolean trustedCredential) {
        this.trustedCredential = trustedCredential;
    }

    public void setReferenceType(STRParser.REFERENCE_TYPE referenceType) {
        this.referenceType = referenceType;
    }
}

