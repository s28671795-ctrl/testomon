/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.message.Encryptor;
import org.apache.wss4j.dom.message.WSSecDerivedKeyBase;
import org.apache.wss4j.dom.message.WSSecEncrypt;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.encryption.Serializer;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecDKEncrypt
extends WSSecDerivedKeyBase {
    private String symEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
    private int derivedKeyLength = -1;
    private List<Element> attachmentEncryptedDataElements;
    private Serializer encryptionSerializer;

    public WSSecDKEncrypt(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecDKEncrypt(Document doc) {
        super(doc);
    }

    @Override
    public void prepare() throws WSSecurityException {
        super.prepare();
        this.attachmentEncryptedDataElements = new ArrayList<Element>();
    }

    public Document build() throws WSSecurityException {
        this.prepare();
        this.prependDKElementToHeader();
        Element externRefList = this.encrypt();
        this.addAttachmentEncryptedDataElements();
        this.addExternalRefElement(externRefList);
        return this.getDocument();
    }

    public void addAttachmentEncryptedDataElements() {
        if (this.attachmentEncryptedDataElements != null) {
            for (int i = 0; i < this.attachmentEncryptedDataElements.size(); ++i) {
                Element encryptedData = this.attachmentEncryptedDataElements.get(i);
                Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
                WSSecurityUtil.prependChildElement(securityHeaderElement, encryptedData);
            }
        }
    }

    public Element encrypt() throws WSSecurityException {
        if (this.getParts().isEmpty()) {
            this.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(this.getDocument()));
        }
        return this.encryptForExternalRef(null, this.getParts());
    }

    public Element encryptForExternalRef(Element dataRef, List<WSEncryptionPart> references) throws WSSecurityException {
        KeyInfo keyInfo = this.createKeyInfo();
        SecretKey key = this.getDerivedKey(this.symEncAlgo);
        Encryptor encryptor = new Encryptor();
        encryptor.setDoc(this.getDocument());
        encryptor.setSecurityHeader(this.getSecurityHeader());
        encryptor.setIdAllocator(this.getIdAllocator());
        encryptor.setCallbackLookup(this.callbackLookup);
        encryptor.setAttachmentCallbackHandler(this.attachmentCallbackHandler);
        encryptor.setStoreBytesInAttachment(this.storeBytesInAttachment);
        encryptor.setEncryptionSerializer(this.encryptionSerializer);
        encryptor.setWsDocInfo(this.getWsDocInfo());
        List<String> encDataRefs = encryptor.doEncryption(keyInfo, key, this.symEncAlgo, references, this.attachmentEncryptedDataElements);
        if (dataRef == null) {
            dataRef = this.getDocument().createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:ReferenceList");
        }
        return WSSecEncrypt.createDataRefList(this.getDocument(), dataRef, encDataRefs);
    }

    private KeyInfo createKeyInfo() throws WSSecurityException {
        KeyInfo keyInfo = new KeyInfo(this.getDocument());
        SecurityTokenReference secToken = new SecurityTokenReference(this.getDocument());
        secToken.addWSSENamespace();
        if (this.addWSUNamespace) {
            secToken.addWSUNamespace();
        }
        Reference ref = new Reference(this.getDocument());
        ref.setURI("#" + this.getId());
        String ns = ConversationConstants.getWSCNs(this.getWscVersion()) + "/dk";
        ref.setValueType(ns);
        secToken.setReference(ref);
        keyInfo.addUnknownElement(secToken.getElement());
        Element keyInfoElement = keyInfo.getElement();
        keyInfoElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
        return keyInfo;
    }

    public void addExternalRefElement(Element referenceList) {
        if (referenceList != null) {
            Node node = this.getdktElement().getNextSibling();
            Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
            if (node != null && 1 == node.getNodeType()) {
                securityHeaderElement.insertBefore(referenceList, node);
            } else {
                securityHeaderElement.appendChild(referenceList);
            }
        }
    }

    public void setSymmetricEncAlgorithm(String algo) {
        this.symEncAlgo = algo;
    }

    @Override
    protected int getDerivedKeyLength() throws WSSecurityException {
        return this.derivedKeyLength > 0 ? this.derivedKeyLength : KeyUtils.getKeyLength(this.symEncAlgo);
    }

    public void setDerivedKeyLength(int keyLength) {
        this.derivedKeyLength = keyLength;
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

