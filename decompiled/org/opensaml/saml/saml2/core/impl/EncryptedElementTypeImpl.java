/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.EncryptedType;

public class EncryptedElementTypeImpl
extends AbstractSAMLObject
implements EncryptedElementType {
    private EncryptedData encryptedData;
    private final XMLObjectChildrenList<EncryptedKey> encryptedKeys = new XMLObjectChildrenList(this);

    protected EncryptedElementTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public EncryptedData getEncryptedData() {
        return this.encryptedData;
    }

    @Override
    public void setEncryptedData(EncryptedData newEncryptedData) {
        this.encryptedData = this.prepareForAssignment(this.encryptedData, newEncryptedData);
    }

    @Override
    public List<EncryptedKey> getEncryptedKeys() {
        return this.encryptedKeys;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<EncryptedType> children = new ArrayList<EncryptedType>();
        if (this.encryptedData != null) {
            children.add(this.encryptedData);
        }
        children.addAll(this.encryptedKeys);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

