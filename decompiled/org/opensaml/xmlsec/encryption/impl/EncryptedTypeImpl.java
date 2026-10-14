/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.signature.KeyInfo;

public abstract class EncryptedTypeImpl
extends AbstractXMLObject
implements EncryptedType {
    private String id;
    private String type;
    private String mimeType;
    private String encoding;
    private EncryptionMethod encryptionMethod;
    private KeyInfo keyInfo;
    private CipherData cipherData;
    private EncryptionProperties encryptionProperties;

    protected EncryptedTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String newID) {
        String oldID = this.id;
        this.id = this.prepareForAssignment(this.id, newID);
        this.registerOwnID(oldID, this.id);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String newType) {
        this.type = this.prepareForAssignment(this.type, newType);
    }

    @Override
    public String getMimeType() {
        return this.mimeType;
    }

    @Override
    public void setMimeType(String newMimeType) {
        this.mimeType = this.prepareForAssignment(this.mimeType, newMimeType);
    }

    @Override
    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public void setEncoding(String newEncoding) {
        this.encoding = this.prepareForAssignment(this.encoding, newEncoding);
    }

    @Override
    public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
    }

    @Override
    public void setEncryptionMethod(EncryptionMethod newEncryptionMethod) {
        this.encryptionMethod = this.prepareForAssignment(this.encryptionMethod, newEncryptionMethod);
    }

    @Override
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }

    @Override
    public void setKeyInfo(KeyInfo newKeyInfo) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, newKeyInfo);
    }

    @Override
    public CipherData getCipherData() {
        return this.cipherData;
    }

    @Override
    public void setCipherData(CipherData newCipherData) {
        this.cipherData = this.prepareForAssignment(this.cipherData, newCipherData);
    }

    @Override
    public EncryptionProperties getEncryptionProperties() {
        return this.encryptionProperties;
    }

    @Override
    public void setEncryptionProperties(EncryptionProperties newEncryptionProperties) {
        this.encryptionProperties = this.prepareForAssignment(this.encryptionProperties, newEncryptionProperties);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.encryptionMethod != null) {
            children.add(this.encryptionMethod);
        }
        if (this.keyInfo != null) {
            children.add(this.keyInfo);
        }
        if (this.cipherData != null) {
            children.add(this.cipherData);
        }
        if (this.encryptionProperties != null) {
            children.add(this.encryptionProperties);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

