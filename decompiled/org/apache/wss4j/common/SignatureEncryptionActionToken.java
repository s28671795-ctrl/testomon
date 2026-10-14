/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class SignatureEncryptionActionToken
implements SecurityActionToken {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureEncryptionActionToken.class);
    private X509Certificate certificate;
    private byte[] key;
    private String user;
    private Element keyInfoElement;
    private Crypto crypto;
    private String keyIdentifier;
    private int keyIdentifierId;
    private String digestAlgorithm;
    private List<WSEncryptionPart> parts = new ArrayList<WSEncryptionPart>();
    private String optionalParts;
    private String cryptoProperties;
    private String tokenType;
    private String tokenId;
    private String sha1Value;
    private String derivedKeyTokenReference;
    private int derivedKeyLength;
    private int derivedKeyIdentifier;
    private boolean includeToken;

    @Override
    public X509Certificate getCertificate() {
        return this.certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public byte[] getKey() {
        return this.key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public Element getKeyInfoElement() {
        return this.keyInfoElement;
    }

    public void setKeyInfoElement(Element keyInfoElement) {
        this.keyInfoElement = keyInfoElement;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public synchronized Crypto getCrypto() throws WSSecurityException {
        if (this.crypto != null) {
            return this.crypto;
        }
        if (this.cryptoProperties != null) {
            ClassLoader classLoader = null;
            try {
                classLoader = Loader.getTCL();
            }
            catch (Exception ex) {
                LOG.debug(ex.getMessage(), ex);
            }
            Properties properties = CryptoFactory.getProperties(this.cryptoProperties, classLoader);
            this.crypto = CryptoFactory.getInstance(properties, classLoader, null);
        }
        return this.crypto;
    }

    public synchronized void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public String getKeyIdentifier() {
        return this.keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }

    public String getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public String getOptionalParts() {
        return this.optionalParts;
    }

    public void setOptionalParts(String optionalParts) {
        this.optionalParts = optionalParts;
    }

    public int getKeyIdentifierId() {
        return this.keyIdentifierId;
    }

    public void setKeyIdentifierId(int keyIdentifierId) {
        this.keyIdentifierId = keyIdentifierId;
    }

    public List<WSEncryptionPart> getParts() {
        return this.parts;
    }

    public void setParts(List<WSEncryptionPart> parts) {
        this.parts = parts;
    }

    public synchronized void setCryptoProperties(String cryptoProperties) {
        this.cryptoProperties = cryptoProperties;
    }

    @Override
    public synchronized String getCryptoProperties() {
        return this.cryptoProperties;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getSha1Value() {
        return this.sha1Value;
    }

    public void setSha1Value(String sha1Value) {
        this.sha1Value = sha1Value;
    }

    public String getDerivedKeyTokenReference() {
        return this.derivedKeyTokenReference;
    }

    public void setDerivedKeyTokenReference(String derivedKeyTokenReference) {
        this.derivedKeyTokenReference = derivedKeyTokenReference;
    }

    public int getDerivedKeyLength() {
        return this.derivedKeyLength;
    }

    public void setDerivedKeyLength(int derivedKeyLength) {
        this.derivedKeyLength = derivedKeyLength;
    }

    public int getDerivedKeyIdentifier() {
        return this.derivedKeyIdentifier;
    }

    public void setDerivedKeyIdentifier(int derivedKeyIdentifier) {
        this.derivedKeyIdentifier = derivedKeyIdentifier;
    }

    public boolean isIncludeToken() {
        return this.includeToken;
    }

    public void setIncludeToken(boolean includeToken) {
        this.includeToken = includeToken;
    }
}

