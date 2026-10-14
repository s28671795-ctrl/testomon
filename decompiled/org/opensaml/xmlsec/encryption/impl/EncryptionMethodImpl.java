/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.OAEPparams;

public class EncryptionMethodImpl
extends AbstractXMLObject
implements EncryptionMethod {
    private String algorithm;
    private KeySize keySize;
    private OAEPparams oaepParams;
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected EncryptionMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public void setAlgorithm(String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }

    @Override
    public KeySize getKeySize() {
        return this.keySize;
    }

    @Override
    public void setKeySize(KeySize newKeySize) {
        this.keySize = this.prepareForAssignment(this.keySize, newKeySize);
    }

    @Override
    public OAEPparams getOAEPparams() {
        return this.oaepParams;
    }

    @Override
    public void setOAEPparams(OAEPparams newOAEPparams) {
        this.oaepParams = this.prepareForAssignment(this.oaepParams, newOAEPparams);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.keySize != null) {
            children.add(this.keySize);
        }
        if (this.oaepParams != null) {
            children.add(this.oaepParams);
        }
        children.addAll(this.unknownChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

