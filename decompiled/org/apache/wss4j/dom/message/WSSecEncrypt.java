/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.message.Encryptor;
import org.apache.wss4j.dom.message.WSSecEncryptedKey;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.Serializer;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecEncrypt
extends WSSecEncryptedKey {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecEncrypt.class);
    private SecurityTokenReference securityTokenReference;
    private boolean encryptSymmKey = true;
    private String customReferenceValue;
    private boolean encKeyIdDirectId;
    private boolean embedEncryptedKey;
    private List<Element> attachmentEncryptedDataElements;
    private Serializer encryptionSerializer;

    public WSSecEncrypt(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecEncrypt(Document doc) {
        super(doc);
    }

    @Override
    public void prepare(Crypto crypto) throws WSSecurityException {
        this.attachmentEncryptedDataElements = new ArrayList<Element>();
        if (this.symmetricKey == null) {
            if (this.getEphemeralKey() != null) {
                this.symmetricKey = KeyUtils.prepareSecretKey(this.getSymmetricEncAlgorithm(), this.getEphemeralKey());
            } else {
                KeyGenerator keyGen = KeyUtils.getKeyGenerator(this.getSymmetricEncAlgorithm());
                this.symmetricKey = keyGen.generateKey();
            }
        }
        if (this.encryptSymmKey && this.encryptedEphemeralKey == null) {
            if (this.getUseThisPublicKey() != null) {
                this.prepareInternal(this.symmetricKey, this.getUseThisPublicKey(), crypto);
            } else {
                X509Certificate remoteCert = this.getUseThisCert();
                if (remoteCert == null) {
                    if (crypto == null) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "encryptionCryptoFailure");
                    }
                    CryptoType cryptoType = null;
                    if (this.keyIdentifierType == 14) {
                        cryptoType = new CryptoType(CryptoType.TYPE.ENDPOINT);
                        cryptoType.setEndpoint(this.user);
                    } else {
                        cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                        cryptoType.setAlias(this.user);
                    }
                    X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
                    if (certs == null || certs.length <= 0) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUserCertsFound", new Object[]{this.user, "encryption"});
                    }
                    remoteCert = certs[0];
                }
                this.prepareInternal(this.symmetricKey, remoteCert, crypto);
            }
        } else if (this.encryptedEphemeralKey != null) {
            this.prepareInternal(this.symmetricKey);
        } else {
            this.encryptedEphemeralKey = this.symmetricKey.getEncoded();
        }
    }

    public Document build(Crypto crypto) throws WSSecurityException {
        this.prepare(crypto);
        LOG.debug("Beginning Encryption...");
        Element refs = this.encrypt();
        this.addAttachmentEncryptedDataElements();
        if (this.getEncryptedKeyElement() != null) {
            this.addInternalRefElement(refs);
            this.prependToHeader();
        } else {
            this.addExternalRefElement(refs);
        }
        this.prependBSTElementToHeader();
        this.clean();
        LOG.debug("Encryption complete.");
        return this.getDocument();
    }

    public Element encrypt() throws WSSecurityException {
        if (this.getParts().isEmpty()) {
            this.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(this.getDocument()));
        }
        return this.encryptForRef(null, this.getParts());
    }

    public Element encryptForRef(Element dataRef, List<WSEncryptionPart> references) throws WSSecurityException {
        KeyInfo keyInfo = this.createKeyInfo();
        String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(this.getSymmetricEncAlgorithm());
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.symmetricKey.getEncoded(), keyAlgorithm);
        Encryptor encryptor = new Encryptor();
        encryptor.setDoc(this.getDocument());
        encryptor.setSecurityHeader(this.getSecurityHeader());
        encryptor.setIdAllocator(this.getIdAllocator());
        encryptor.setCallbackLookup(this.callbackLookup);
        encryptor.setAttachmentCallbackHandler(this.attachmentCallbackHandler);
        encryptor.setStoreBytesInAttachment(this.storeBytesInAttachment);
        encryptor.setEncryptionSerializer(this.getEncryptionSerializer());
        encryptor.setExpandXopInclude(this.isExpandXopInclude());
        encryptor.setWsDocInfo(this.getWsDocInfo());
        List<String> encDataRefs = encryptor.doEncryption(keyInfo, secretKeySpec, this.getSymmetricEncAlgorithm(), references, this.attachmentEncryptedDataElements);
        if (encDataRefs.isEmpty()) {
            return null;
        }
        if (dataRef == null) {
            dataRef = this.getDocument().createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:ReferenceList");
            if (!this.encryptSymmKey) {
                org.apache.wss4j.common.util.XMLUtils.setNamespace(dataRef, "http://www.w3.org/2001/04/xmlenc#", "xenc");
            }
        }
        return WSSecEncrypt.createDataRefList(this.getDocument(), dataRef, encDataRefs);
    }

    public void addInternalRefElement(Element dataRef) {
        if (dataRef != null) {
            this.getEncryptedKeyElement().appendChild(dataRef);
        }
    }

    public void addExternalRefElement(Element dataRef) {
        if (dataRef != null) {
            Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            WSSecurityUtil.prependChildElement(secHeaderElement, dataRef);
        }
    }

    public void addAttachmentEncryptedDataElements() {
        if (this.attachmentEncryptedDataElements != null) {
            for (int i = 0; i < this.attachmentEncryptedDataElements.size(); ++i) {
                Element encryptedData = this.attachmentEncryptedDataElements.get(i);
                Element secHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
                WSSecurityUtil.prependChildElement(secHeaderElement, encryptedData);
            }
        }
    }

    private KeyInfo createKeyInfo() throws WSSecurityException {
        SecurityTokenReference secToken;
        KeyInfo keyInfo = new KeyInfo(this.getDocument());
        if (this.embedEncryptedKey) {
            keyInfo.addUnknownElement(this.getEncryptedKeyElement());
        } else if (this.keyIdentifierType == 10) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            if (this.customReferenceValue != null) {
                secToken.setKeyIdentifierEncKeySHA1(this.customReferenceValue);
            } else {
                byte[] encodedBytes = KeyUtils.generateDigest(this.encryptedEphemeralKey);
                secToken.setKeyIdentifierEncKeySHA1(XMLUtils.encodeToString(encodedBytes));
            }
            secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            keyInfo.addUnknownElement(secToken.getElement());
        } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(this.customReferenceValue)) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
            secToken.setKeyIdentifier("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID", this.getId());
            keyInfo.addUnknownElement(secToken.getElement());
        } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(this.customReferenceValue)) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
            secToken.setKeyIdentifier("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID", this.getId());
            keyInfo.addUnknownElement(secToken.getElement());
        } else if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(this.customReferenceValue)) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ");
            secToken.setKeyIdentifier(this.customReferenceValue, this.getId(), true);
            keyInfo.addUnknownElement(secToken.getElement());
        } else if (this.securityTokenReference != null) {
            Element tmpE = this.securityTokenReference.getElement();
            tmpE.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + tmpE.getPrefix(), tmpE.getNamespaceURI());
            keyInfo.addUnknownElement(this.securityTokenReference.getElement());
        } else if (this.getId() != null) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            Reference ref = new Reference(this.getDocument());
            if (this.encKeyIdDirectId) {
                ref.setURI(this.getId());
            } else {
                ref.setURI("#" + this.getId());
            }
            if (this.customReferenceValue != null) {
                ref.setValueType(this.customReferenceValue);
            }
            secToken.setReference(ref);
            if (KerberosSecurity.isKerberosToken(this.customReferenceValue)) {
                secToken.addTokenType(this.customReferenceValue);
            } else if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(this.customReferenceValue)) {
                secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            }
            keyInfo.addUnknownElement(secToken.getElement());
        } else if (!this.encryptSymmKey && this.keyIdentifierType == 2) {
            secToken = new SecurityTokenReference(this.getDocument());
            if (this.addWSUNamespace) {
                secToken.addWSUNamespace();
            }
            secToken.addWSSENamespace();
            if (this.customReferenceValue != null) {
                secToken.setKeyIdentifierEncKeySHA1(this.customReferenceValue);
            } else {
                byte[] encodedBytes = KeyUtils.generateDigest(this.encryptedEphemeralKey);
                secToken.setKeyIdentifierEncKeySHA1(XMLUtils.encodeToString(encodedBytes));
            }
            secToken.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            keyInfo.addUnknownElement(secToken.getElement());
        }
        Element keyInfoElement = keyInfo.getElement();
        keyInfoElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
        return keyInfo;
    }

    public static Element createDataRefList(Document doc, Element referenceList, List<String> encDataRefs) {
        for (String dataReferenceUri : encDataRefs) {
            Element dataReference = doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:DataReference");
            dataReference.setAttributeNS(null, "URI", dataReferenceUri);
            referenceList.appendChild(dataReference);
        }
        return referenceList;
    }

    public SecurityTokenReference getSecurityTokenReference() {
        return this.securityTokenReference;
    }

    public void setSecurityTokenReference(SecurityTokenReference reference) {
        this.securityTokenReference = reference;
    }

    public boolean isEncryptSymmKey() {
        return this.encryptSymmKey;
    }

    public void setEncryptSymmKey(boolean encryptSymmKey) {
        this.encryptSymmKey = encryptSymmKey;
    }

    public void setCustomReferenceValue(String customReferenceValue) {
        this.customReferenceValue = customReferenceValue;
    }

    public void setEncKeyIdDirectId(boolean b) {
        this.encKeyIdDirectId = b;
    }

    public void setEmbedEncryptedKey(boolean embedEncryptedKey) {
        this.embedEncryptedKey = embedEncryptedKey;
    }

    public boolean isEmbedEncryptedKey() {
        return this.embedEncryptedKey;
    }

    public List<Element> getAttachmentEncryptedDataElements() {
        return this.attachmentEncryptedDataElements;
    }

    public Serializer getEncryptionSerializer() {
        return this.encryptionSerializer;
    }

    public void setEncryptionSerializer(Serializer encryptionSerializer) {
        this.encryptionSerializer = encryptionSerializer;
    }
}

