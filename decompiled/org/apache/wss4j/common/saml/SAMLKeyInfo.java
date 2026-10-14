/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class SAMLKeyInfo {
    private X509Certificate[] certs;
    private byte[] secret;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public SAMLKeyInfo() {
    }

    public SAMLKeyInfo(X509Certificate[] certs) {
        this.certs = certs;
    }

    public SAMLKeyInfo(byte[] secret) {
        this.secret = secret;
    }

    public SAMLKeyInfo(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public X509Certificate[] getCerts() {
        return this.certs;
    }

    public void setCerts(X509Certificate[] certs) {
        this.certs = certs;
    }

    public byte[] getSecret() {
        return this.secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}

