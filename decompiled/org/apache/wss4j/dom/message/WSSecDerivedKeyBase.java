/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.derivedKey.AlgoFactory;
import org.apache.wss4j.common.derivedKey.DerivationAlgorithm;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignatureBase;
import org.apache.wss4j.dom.message.token.DerivedKeyToken;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class WSSecDerivedKeyBase
extends WSSecSignatureBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecDerivedKeyBase.class);
    private DerivedKeyToken dkt;
    private byte[] ephemeralKey;
    private String clientLabel = "WS-SecureConversation";
    private String serviceLabel = "WS-SecureConversation";
    private String tokenIdentifier;
    private boolean tokenIdDirectId;
    private Element strElem;
    private String dktId;
    private byte[] derivedKeyBytes;
    private int wscVersion = 2;
    private String customValueType;
    private X509Certificate useThisCert;
    private Crypto crypto;

    public WSSecDerivedKeyBase(WSSecHeader securityHeader) {
        super(securityHeader);
        this.setKeyIdentifierType(0);
    }

    public WSSecDerivedKeyBase(Document doc) {
        super(doc);
        this.setKeyIdentifierType(0);
    }

    protected abstract int getDerivedKeyLength() throws WSSecurityException;

    public void setExternalKey(byte[] ephemeralKey, String tokenIdentifier) {
        this.ephemeralKey = ephemeralKey;
        this.tokenIdentifier = tokenIdentifier;
    }

    public void setExternalKey(byte[] ephemeralKey, Element strElem) {
        this.ephemeralKey = ephemeralKey;
        this.strElem = strElem;
    }

    public String getTokenIdentifier() {
        return this.tokenIdentifier;
    }

    public void setX509Certificate(X509Certificate cer) {
        this.useThisCert = cer;
    }

    public String getId() {
        return this.dktId;
    }

    public void setClientLabel(String clientLabel) {
        this.clientLabel = clientLabel;
    }

    public void setServiceLabel(String serviceLabel) {
        this.serviceLabel = serviceLabel;
    }

    public void prepare() throws WSSecurityException {
        int offset = 0;
        int length = this.getDerivedKeyLength();
        String labelText = this.clientLabel + this.serviceLabel;
        byte[] label = labelText.getBytes(StandardCharsets.UTF_8);
        byte[] nonce = WSSecurityUtil.generateNonce(16);
        byte[] seed = new byte[label.length + nonce.length];
        System.arraycopy(label, 0, seed, 0, label.length);
        System.arraycopy(nonce, 0, seed, label.length, nonce.length);
        DerivationAlgorithm algo = AlgoFactory.getInstance("http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1");
        if (this.ephemeralKey == null || this.ephemeralKey.length == 0) {
            LOG.debug("No ephemeral key is supplied for id: " + this.tokenIdentifier);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
        }
        this.derivedKeyBytes = algo.createKey(this.ephemeralKey, seed, offset, length);
        this.dkt = new DerivedKeyToken(this.wscVersion, this.getDocument());
        this.dktId = this.getIdAllocator().createId("DK-", this.dkt);
        this.dkt.setOffset(offset);
        this.dkt.setLength(length);
        this.dkt.setNonce(XMLUtils.encodeToString(nonce));
        this.dkt.setID(this.dktId);
        if (this.addWSUNamespace) {
            this.dkt.addWSUNamespace();
        }
        if (this.strElem == null) {
            SecurityTokenReference secRef = new SecurityTokenReference(this.getDocument());
            String strUri = this.getIdAllocator().createSecureId("STR-", secRef);
            secRef.setID(strUri);
            if (this.addWSUNamespace) {
                secRef.addWSUNamespace();
            }
            X509Certificate[] certs = this.getSigningCerts();
            switch (this.keyIdentifierType) {
                case 3: {
                    secRef.setKeyIdentifier(certs[0]);
                    break;
                }
                case 4: {
                    secRef.setKeyIdentifierSKI(certs[0], this.crypto);
                    break;
                }
                case 8: {
                    secRef.setKeyIdentifierThumb(certs[0]);
                    break;
                }
                case 12: {
                    secRef.setKeyIdentifier(this.customValueType, this.tokenIdentifier);
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                        break;
                    }
                    if (!"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customValueType)) break;
                    secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    break;
                }
                default: {
                    Reference ref = new Reference(this.getDocument());
                    if (this.tokenIdDirectId) {
                        ref.setURI(this.tokenIdentifier);
                    } else {
                        ref.setURI("#" + this.tokenIdentifier);
                    }
                    if (this.customValueType != null && !"".equals(this.customValueType)) {
                        ref.setValueType(this.customValueType);
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        ref.setValueType(this.customValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        ref.setValueType(this.customValueType);
                    } else if (KerberosSecurity.isKerberosToken(this.customValueType)) {
                        secRef.addTokenType(this.customValueType);
                        ref.setValueType(this.customValueType);
                    } else if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(this.customValueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(this.customValueType)) {
                        ref.setValueType(this.customValueType);
                    } else if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(this.customValueType)) {
                        secRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    }
                    secRef.setReference(ref);
                }
            }
            this.dkt.setSecurityTokenReference(secRef);
        } else {
            this.dkt.setSecurityTokenReference(this.strElem);
        }
    }

    public void prependDKElementToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(securityHeaderElement, this.dkt.getElement());
    }

    public void appendDKElementToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        securityHeaderElement.appendChild(this.dkt.getElement());
    }

    public void setWscVersion(int wscVersion) {
        this.wscVersion = wscVersion;
    }

    public int getWscVersion() {
        return this.wscVersion;
    }

    public Element getdktElement() {
        return this.dkt.getElement();
    }

    public void setCustomValueType(String customValueType) {
        this.customValueType = customValueType;
    }

    public void setTokenIdDirectId(boolean b) {
        this.tokenIdDirectId = b;
    }

    private X509Certificate[] getSigningCerts() throws WSSecurityException {
        X509Certificate[] certs = null;
        if (this.keyIdentifierType == 2 || this.keyIdentifierType == 3 || this.keyIdentifierType == 4 || this.keyIdentifierType == 8) {
            if (this.useThisCert == null) {
                CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                cryptoType.setAlias(this.user);
                if (this.crypto == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
                }
                certs = this.crypto.getX509Certificates(cryptoType);
            } else {
                certs = new X509Certificate[]{this.useThisCert};
            }
            if (certs == null || certs.length <= 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUserCertsFound", new Object[]{this.user, "signature"});
            }
        }
        return certs;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    protected SecretKey getDerivedKey(String algorithm) {
        return KeyUtils.prepareSecretKey(algorithm, this.derivedKeyBytes);
    }
}

