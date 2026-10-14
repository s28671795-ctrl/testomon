/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.MGF1ParameterSpec;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.DOMX509Data;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class WSSecEncryptedKey
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecEncryptedKey.class);
    protected byte[] encryptedEphemeralKey;
    private byte[] ephemeralKey;
    protected SecretKey symmetricKey;
    private String keyEncAlgo = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
    private String symEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
    private String digestAlgo;
    private String mgfAlgo;
    private Element encryptedKeyElement;
    private String encKeyId;
    private BinarySecurity bstToken;
    private X509Certificate useThisCert;
    private PublicKey useThisPublicKey;
    private String customEKTokenValueType;
    private String customEKTokenId;
    private boolean bstAddedToSecurityHeader;
    private boolean includeEncryptionToken;
    private Element customEKKeyInfoElement;
    private Provider provider;

    public WSSecEncryptedKey(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecEncryptedKey(Document doc) {
        this(doc, null);
    }

    public WSSecEncryptedKey(Document doc, Provider provider) {
        super(doc);
        this.provider = provider;
    }

    public void setUserInfo(String user) {
        this.user = user;
    }

    public String getId() {
        return this.encKeyId;
    }

    public void clean() {
        this.ephemeralKey = null;
        this.symmetricKey = null;
        this.encryptedEphemeralKey = null;
    }

    public void prepare(Crypto crypto) throws WSSecurityException {
        if (this.symmetricKey == null) {
            if (this.ephemeralKey != null) {
                this.symmetricKey = KeyUtils.prepareSecretKey(this.symEncAlgo, this.ephemeralKey);
            } else {
                KeyGenerator keyGen = KeyUtils.getKeyGenerator(this.symEncAlgo);
                this.symmetricKey = keyGen.generateKey();
                this.ephemeralKey = this.symmetricKey.getEncoded();
            }
        }
        if (this.encryptedEphemeralKey == null) {
            if (this.useThisPublicKey != null) {
                this.prepareInternal(this.symmetricKey, this.useThisPublicKey, crypto);
            } else {
                X509Certificate remoteCert = this.useThisCert;
                if (remoteCert == null) {
                    CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                    cryptoType.setAlias(this.user);
                    if (crypto == null) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUserCertsFound", new Object[]{this.user, "encryption"});
                    }
                    X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
                    if (certs == null || certs.length <= 0) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUserCertsFound", new Object[]{this.user, "encryption"});
                    }
                    remoteCert = certs[0];
                }
                this.prepareInternal(this.symmetricKey, remoteCert, crypto);
            }
        } else {
            this.prepareInternal(this.symmetricKey);
        }
    }

    private void encryptSymmetricKey(PublicKey encryptingKey, SecretKey keyToBeEncrypted) throws WSSecurityException {
        Cipher cipher = KeyUtils.getCipherInstance(this.keyEncAlgo);
        try {
            OAEPParameterSpec oaepParameterSpec = null;
            if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(this.keyEncAlgo) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(this.keyEncAlgo)) {
                String jceDigestAlgorithm = "SHA-1";
                if (this.digestAlgo != null) {
                    jceDigestAlgorithm = JCEMapper.translateURItoJCEID(this.digestAlgo);
                }
                MGF1ParameterSpec mgf1ParameterSpec = new MGF1ParameterSpec("SHA-1");
                if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(this.keyEncAlgo)) {
                    if ("http://www.w3.org/2009/xmlenc11#mgf1sha224".equals(this.mgfAlgo)) {
                        mgf1ParameterSpec = new MGF1ParameterSpec("SHA-224");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha256".equals(this.mgfAlgo)) {
                        mgf1ParameterSpec = new MGF1ParameterSpec("SHA-256");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha384".equals(this.mgfAlgo)) {
                        mgf1ParameterSpec = new MGF1ParameterSpec("SHA-384");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha512".equals(this.mgfAlgo)) {
                        mgf1ParameterSpec = new MGF1ParameterSpec("SHA-512");
                    }
                }
                oaepParameterSpec = new OAEPParameterSpec(jceDigestAlgorithm, "MGF1", mgf1ParameterSpec, PSource.PSpecified.DEFAULT);
            }
            if (oaepParameterSpec == null) {
                cipher.init(3, encryptingKey);
            } else {
                cipher.init(3, (Key)encryptingKey, oaepParameterSpec);
            }
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, (Exception)e);
        }
        int blockSize = cipher.getBlockSize();
        LOG.debug("cipher blksize: {}", (Object)blockSize);
        try {
            this.encryptedEphemeralKey = cipher.wrap(keyToBeEncrypted);
        }
        catch (IllegalStateException | InvalidKeyException | IllegalBlockSizeException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, ex);
        }
    }

    protected void prepareInternal(SecretKey secretKey, X509Certificate remoteCert, Crypto crypto) throws WSSecurityException {
        this.encryptSymmetricKey(remoteCert.getPublicKey(), secretKey);
        this.encryptedKeyElement = this.createEncryptedKey(this.getDocument(), this.keyEncAlgo);
        if (this.encKeyId == null || "".equals(this.encKeyId)) {
            this.encKeyId = IDGenerator.generateID("EK-");
        }
        this.encryptedKeyElement.setAttributeNS(null, "Id", this.encKeyId);
        if (this.customEKKeyInfoElement != null) {
            this.encryptedKeyElement.appendChild(this.getDocument().adoptNode(this.customEKKeyInfoElement));
        } else {
            SecurityTokenReference secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            switch (this.keyIdentifierType) {
                case 3: {
                    secToken.setKeyIdentifier(remoteCert);
                    break;
                }
                case 4: {
                    secToken.setKeyIdentifierSKI(remoteCert, crypto);
                    if (!this.includeEncryptionToken) break;
                    this.addBST(remoteCert);
                    break;
                }
                case 8: 
                case 10: {
                    secToken.setKeyIdentifierThumb(remoteCert);
                    if (!this.includeEncryptionToken) break;
                    this.addBST(remoteCert);
                    break;
                }
                case 2: {
                    String issuer = remoteCert.getIssuerX500Principal().getName();
                    BigInteger serialNumber = remoteCert.getSerialNumber();
                    DOMX509IssuerSerial domIssuerSerial = new DOMX509IssuerSerial(this.getDocument(), issuer, serialNumber);
                    DOMX509Data domX509Data = new DOMX509Data(this.getDocument(), domIssuerSerial);
                    secToken.setUnknownElement(domX509Data.getElement());
                    if (!this.includeEncryptionToken) break;
                    this.addBST(remoteCert);
                    break;
                }
                case 1: {
                    Reference ref = new Reference(this.getDocument());
                    String certUri = IDGenerator.generateID(null);
                    ref.setURI("#" + certUri);
                    this.bstToken = new X509Security(this.getDocument());
                    ((X509Security)this.bstToken).setX509Certificate(remoteCert);
                    this.bstToken.setID(certUri);
                    ref.setValueType(this.bstToken.getValueType());
                    secToken.setReference(ref);
                    break;
                }
                case 9: {
                    Reference refCust = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else {
                        refCust.setValueType(this.customEKTokenValueType);
                    }
                    refCust.setURI("#" + this.customEKTokenId);
                    secToken.setReference(refCust);
                    break;
                }
                case 11: {
                    Reference refCustd = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else {
                        refCustd.setValueType(this.customEKTokenValueType);
                    }
                    refCustd.setURI(this.customEKTokenId);
                    secToken.setReference(refCustd);
                    break;
                }
                case 12: {
                    secToken.setKeyIdentifier(this.customEKTokenValueType, this.customEKTokenId);
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        break;
                    }
                    if (!"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(this.customEKTokenValueType)) break;
                    secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    break;
                }
                default: {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedKeyId");
                }
            }
            Element keyInfoElement = this.getDocument().createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:KeyInfo");
            keyInfoElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
            keyInfoElement.appendChild(secToken.getElement());
            this.encryptedKeyElement.appendChild(keyInfoElement);
        }
        Element xencCipherValue = this.createCipherValue(this.getDocument(), this.encryptedKeyElement);
        if (this.storeBytesInAttachment) {
            String attachmentId = this.getIdAllocator().createId("", this.getDocument());
            WSSecurityUtil.storeBytesInAttachment(xencCipherValue, this.getDocument(), attachmentId, this.encryptedEphemeralKey, this.attachmentCallbackHandler);
        } else {
            Text keyText = WSSecurityUtil.createBase64EncodedTextNode(this.getDocument(), this.encryptedEphemeralKey);
            xencCipherValue.appendChild(keyText);
        }
    }

    protected void prepareInternal(SecretKey secretKey, PublicKey remoteKey, Crypto crypto) throws WSSecurityException {
        this.encryptSymmetricKey(remoteKey, secretKey);
        this.encryptedKeyElement = this.createEncryptedKey(this.getDocument(), this.keyEncAlgo);
        if (this.encKeyId == null || "".equals(this.encKeyId)) {
            this.encKeyId = IDGenerator.generateID("EK-");
        }
        this.encryptedKeyElement.setAttributeNS(null, "Id", this.encKeyId);
        if (this.customEKKeyInfoElement != null) {
            this.encryptedKeyElement.appendChild(this.getDocument().adoptNode(this.customEKKeyInfoElement));
        } else {
            SecurityTokenReference secToken = null;
            switch (this.keyIdentifierType) {
                case 9: {
                    secToken = new SecurityTokenReference(this.getDocument());
                    Reference refCust = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else {
                        refCust.setValueType(this.customEKTokenValueType);
                    }
                    refCust.setURI("#" + this.customEKTokenId);
                    secToken.setReference(refCust);
                    break;
                }
                case 11: {
                    secToken = new SecurityTokenReference(this.getDocument());
                    Reference refCustd = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else {
                        refCustd.setValueType(this.customEKTokenValueType);
                    }
                    refCustd.setURI(this.customEKTokenId);
                    secToken.setReference(refCustd);
                    break;
                }
                case 12: {
                    secToken = new SecurityTokenReference(this.getDocument());
                    secToken.setKeyIdentifier(this.customEKTokenValueType, this.customEKTokenId);
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        break;
                    }
                    if (!"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(this.customEKTokenValueType)) break;
                    secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    break;
                }
                default: {
                    try {
                        XMLSignatureFactory signatureFactory;
                        if (this.provider == null) {
                            try {
                                signatureFactory = XMLSignatureFactory.getInstance("DOM", "ApacheXMLDSig");
                            }
                            catch (NoSuchProviderException ex) {
                                signatureFactory = XMLSignatureFactory.getInstance("DOM");
                            }
                        } else {
                            signatureFactory = XMLSignatureFactory.getInstance("DOM", this.provider);
                        }
                        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
                        KeyValue keyValue = keyInfoFactory.newKeyValue(remoteKey);
                        String keyInfoUri = this.getIdAllocator().createSecureId("KI-", null);
                        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue), keyInfoUri);
                        keyInfo.marshal(new DOMStructure(this.encryptedKeyElement), null);
                        break;
                    }
                    catch (KeyException | MarshalException ex) {
                        LOG.error("", ex);
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, ex);
                    }
                }
            }
            if (secToken != null) {
                Element keyInfoElement = this.getDocument().createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:KeyInfo");
                keyInfoElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
                keyInfoElement.appendChild(secToken.getElement());
                this.encryptedKeyElement.appendChild(keyInfoElement);
            }
        }
        Element xencCipherValue = this.createCipherValue(this.getDocument(), this.encryptedKeyElement);
        if (this.storeBytesInAttachment) {
            String attachmentId = this.getIdAllocator().createId("", this.getDocument());
            WSSecurityUtil.storeBytesInAttachment(xencCipherValue, this.getDocument(), attachmentId, this.encryptedEphemeralKey, this.attachmentCallbackHandler);
        } else {
            Text keyText = WSSecurityUtil.createBase64EncodedTextNode(this.getDocument(), this.encryptedEphemeralKey);
            xencCipherValue.appendChild(keyText);
        }
    }

    protected void prepareInternal(SecretKey secretKey) throws WSSecurityException {
        this.encryptedKeyElement = this.createEncryptedKey(this.getDocument(), this.keyEncAlgo);
        if (this.encKeyId == null || "".equals(this.encKeyId)) {
            this.encKeyId = IDGenerator.generateID("EK-");
        }
        this.encryptedKeyElement.setAttributeNS(null, "Id", this.encKeyId);
        if (this.customEKKeyInfoElement != null) {
            this.encryptedKeyElement.appendChild(this.getDocument().adoptNode(this.customEKKeyInfoElement));
        } else if (this.keyIdentifierType == 9 || this.keyIdentifierType == 11 || this.keyIdentifierType == 12) {
            SecurityTokenReference secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            switch (this.keyIdentifierType) {
                case 9: {
                    Reference refCust = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCust.setValueType(this.customEKTokenValueType);
                    } else {
                        refCust.setValueType(this.customEKTokenValueType);
                    }
                    refCust.setURI("#" + this.customEKTokenId);
                    secToken.setReference(refCust);
                    break;
                }
                case 11: {
                    Reference refCustd = new Reference(this.getDocument());
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        refCustd.setValueType(this.customEKTokenValueType);
                    } else {
                        refCustd.setValueType(this.customEKTokenValueType);
                    }
                    refCustd.setURI(this.customEKTokenId);
                    secToken.setReference(refCustd);
                    break;
                }
                case 12: {
                    secToken.setKeyIdentifier(this.customEKTokenValueType, this.customEKTokenId);
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                        break;
                    }
                    if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(this.customEKTokenValueType)) {
                        secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                        break;
                    }
                    if (!"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(this.customEKTokenValueType)) break;
                    secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                    break;
                }
                default: {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedKeyId");
                }
            }
            Element keyInfoElement = this.getDocument().createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:KeyInfo");
            keyInfoElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
            keyInfoElement.appendChild(secToken.getElement());
            this.encryptedKeyElement.appendChild(keyInfoElement);
        }
        Element xencCipherValue = this.createCipherValue(this.getDocument(), this.encryptedKeyElement);
        if (this.storeBytesInAttachment) {
            String attachmentId = this.getIdAllocator().createId("", this.getDocument());
            WSSecurityUtil.storeBytesInAttachment(xencCipherValue, this.getDocument(), attachmentId, this.encryptedEphemeralKey, this.attachmentCallbackHandler);
        } else {
            Text keyText = WSSecurityUtil.createBase64EncodedTextNode(this.getDocument(), this.encryptedEphemeralKey);
            xencCipherValue.appendChild(keyText);
        }
    }

    private void addBST(X509Certificate cert) throws WSSecurityException {
        this.bstToken = new X509Security(this.getDocument());
        ((X509Security)this.bstToken).setX509Certificate(cert);
        this.bstAddedToSecurityHeader = false;
        this.bstToken.setID(IDGenerator.generateID(null));
        if (this.addWSUNamespace) {
            this.bstToken.addWSUNamespace();
        }
    }

    protected Element createEncryptedKey(Document doc, String keyTransportAlgo) {
        Element encryptedKey = doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptedKey");
        org.apache.wss4j.common.util.XMLUtils.setNamespace(encryptedKey, "http://www.w3.org/2001/04/xmlenc#", "xenc");
        Element encryptionMethod = doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptionMethod");
        encryptionMethod.setAttributeNS(null, "Algorithm", keyTransportAlgo);
        if (this.digestAlgo != null) {
            Element digestElement = XMLUtils.createElementInSignatureSpace(doc, "DigestMethod");
            digestElement.setAttributeNS(null, "Algorithm", this.digestAlgo);
            encryptionMethod.appendChild(digestElement);
        }
        if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(this.keyEncAlgo) && this.mgfAlgo != null) {
            Element mgfElement = doc.createElementNS("http://www.w3.org/2009/xmlenc11#", "xenc11:MGF");
            mgfElement.setAttributeNS(null, "Algorithm", this.mgfAlgo);
            encryptionMethod.appendChild(mgfElement);
        }
        encryptedKey.appendChild(encryptionMethod);
        return encryptedKey;
    }

    protected Element createCipherValue(Document doc, Element encryptedKey) {
        Element cipherData = doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherData");
        Element cipherValue = doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherValue");
        cipherData.appendChild(cipherValue);
        encryptedKey.appendChild(cipherData);
        return cipherValue;
    }

    public void prependToHeader() {
        Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(secHeaderElement, this.encryptedKeyElement);
    }

    public void appendToHeader() {
        Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        secHeaderElement.appendChild(this.encryptedKeyElement);
    }

    public void prependBSTElementToHeader() {
        if (this.bstToken != null && !this.bstAddedToSecurityHeader) {
            Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            WSSecurityUtil.prependChildElement(secHeaderElement, this.bstToken.getElement());
            this.bstAddedToSecurityHeader = true;
        }
    }

    public void appendBSTElementToHeader() {
        if (this.bstToken != null && !this.bstAddedToSecurityHeader) {
            Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            secHeaderElement.appendChild(this.bstToken.getElement());
            this.bstAddedToSecurityHeader = true;
        }
    }

    public byte[] getEphemeralKey() {
        return this.ephemeralKey;
    }

    public void setUseThisCert(X509Certificate cert) {
        this.useThisCert = cert;
    }

    public X509Certificate getUseThisCert() {
        return this.useThisCert;
    }

    public void setUseThisPublicKey(PublicKey key) {
        this.useThisPublicKey = key;
    }

    public PublicKey getUseThisPublicKey() {
        return this.useThisPublicKey;
    }

    public Element getEncryptedKeyElement() {
        return this.encryptedKeyElement;
    }

    public void setEncryptedKeyElement(Element encryptedKeyElement) {
        this.encryptedKeyElement = encryptedKeyElement;
    }

    public Element getBinarySecurityTokenElement() {
        if (this.bstToken != null) {
            return this.bstToken.getElement();
        }
        return null;
    }

    public void setKeyEncAlgo(String keyEncAlgo) {
        this.keyEncAlgo = keyEncAlgo;
    }

    public String getKeyEncAlgo() {
        return this.keyEncAlgo;
    }

    public void setEphemeralKey(byte[] ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getBSTTokenId() {
        if (this.bstToken == null) {
            return null;
        }
        return this.bstToken.getID();
    }

    public void setEncKeyId(String encKeyId) {
        this.encKeyId = encKeyId;
    }

    public boolean isCertSet() {
        return this.useThisCert != null;
    }

    public byte[] getEncryptedEphemeralKey() {
        return this.encryptedEphemeralKey;
    }

    public void setEncryptedEphemeralKey(byte[] encryptedKey) {
        this.encryptedEphemeralKey = encryptedKey;
    }

    public void setCustomEKTokenValueType(String customEKTokenValueType) {
        this.customEKTokenValueType = customEKTokenValueType;
    }

    public void setCustomEKTokenId(String customEKTokenId) {
        this.customEKTokenId = customEKTokenId;
    }

    public void setSymmetricEncAlgorithm(String algo) {
        this.symEncAlgo = algo;
    }

    public String getSymmetricEncAlgorithm() {
        return this.symEncAlgo;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgo = digestAlgorithm;
    }

    public String getDigestAlgorithm() {
        return this.digestAlgo;
    }

    public void setMGFAlgorithm(String mgfAlgorithm) {
        this.mgfAlgo = mgfAlgorithm;
    }

    public String getMGFAlgorithm() {
        return this.mgfAlgo;
    }

    public SecretKey getSymmetricKey() {
        return this.symmetricKey;
    }

    public void setSymmetricKey(SecretKey key) {
        this.symmetricKey = key;
    }

    public boolean isIncludeEncryptionToken() {
        return this.includeEncryptionToken;
    }

    public void setIncludeEncryptionToken(boolean includeEncryptionToken) {
        this.includeEncryptionToken = includeEncryptionToken;
    }

    public Element getCustomEKKeyInfoElement() {
        return this.customEKKeyInfoElement;
    }

    public void setCustomEKKeyInfoElement(Element customEKKeyInfoElement) {
        this.customEKKeyInfoElement = customEKKeyInfoElement;
    }
}

