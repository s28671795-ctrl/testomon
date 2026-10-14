/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import org.w3c.dom.Element;

public class KeyInfoBean {
    private X509Certificate cert;
    private CERT_IDENTIFIER certIdentifier = CERT_IDENTIFIER.X509_CERT;
    private PublicKey publicKey;
    private byte[] ephemeralKey;
    private Element keyInfoElement;

    public X509Certificate getCertificate() {
        return this.cert;
    }

    public void setCertificate(X509Certificate cert) {
        this.cert = cert;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public CERT_IDENTIFIER getCertIdentifer() {
        return this.certIdentifier;
    }

    public void setCertIdentifer(CERT_IDENTIFIER certIdentifier) {
        this.certIdentifier = certIdentifier;
    }

    public byte[] getEphemeralKey() {
        return this.ephemeralKey;
    }

    public void setEphemeralKey(byte[] ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public Element getElement() {
        return this.keyInfoElement;
    }

    public void setElement(Element keyInfoElement) {
        this.keyInfoElement = keyInfoElement;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyInfoBean)) {
            return false;
        }
        KeyInfoBean that = (KeyInfoBean)o;
        if (this.certIdentifier != that.certIdentifier) {
            return false;
        }
        if (this.cert == null && that.cert != null) {
            return false;
        }
        if (this.cert != null && !this.cert.equals(that.cert)) {
            return false;
        }
        if (this.publicKey == null && that.publicKey != null) {
            return false;
        }
        if (this.publicKey != null && !this.publicKey.equals(that.publicKey)) {
            return false;
        }
        if (this.keyInfoElement == null && that.keyInfoElement != null) {
            return false;
        }
        return this.keyInfoElement == null || this.keyInfoElement.equals(that.keyInfoElement);
    }

    public int hashCode() {
        int result = this.certIdentifier.hashCode();
        if (this.cert != null) {
            result = 31 * result + this.cert.hashCode();
        }
        if (this.publicKey != null) {
            result = 31 * result + this.publicKey.hashCode();
        }
        if (this.keyInfoElement != null) {
            result = 31 * result + this.keyInfoElement.hashCode();
        }
        return result;
    }

    public static enum CERT_IDENTIFIER {
        X509_CERT,
        X509_ISSUER_SERIAL,
        KEY_VALUE;

    }
}

