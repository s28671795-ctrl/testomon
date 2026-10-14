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
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.CipherValue;

public class CipherDataImpl
extends AbstractXMLObject
implements CipherData {
    private CipherValue cipherValue;
    private CipherReference cipherReference;

    protected CipherDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public CipherValue getCipherValue() {
        return this.cipherValue;
    }

    @Override
    public void setCipherValue(CipherValue newCipherValue) {
        this.cipherValue = this.prepareForAssignment(this.cipherValue, newCipherValue);
    }

    @Override
    public CipherReference getCipherReference() {
        return this.cipherReference;
    }

    @Override
    public void setCipherReference(CipherReference newCipherReference) {
        this.cipherReference = this.prepareForAssignment(this.cipherReference, newCipherReference);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.cipherValue != null) {
            children.add(this.cipherValue);
        }
        if (this.cipherReference != null) {
            children.add(this.cipherReference);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

