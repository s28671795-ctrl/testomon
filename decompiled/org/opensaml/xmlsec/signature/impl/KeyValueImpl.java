/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public class KeyValueImpl
extends AbstractXMLObject
implements KeyValue {
    private DSAKeyValue dsaKeyValue;
    private RSAKeyValue rsaKeyValue;
    private ECKeyValue ecKeyValue;
    private XMLObject unknownXMLObject;

    protected KeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public DSAKeyValue getDSAKeyValue() {
        return this.dsaKeyValue;
    }

    @Override
    public void setDSAKeyValue(DSAKeyValue newDSAKeyValue) {
        this.dsaKeyValue = this.prepareForAssignment(this.dsaKeyValue, newDSAKeyValue);
    }

    @Override
    public RSAKeyValue getRSAKeyValue() {
        return this.rsaKeyValue;
    }

    @Override
    public void setRSAKeyValue(RSAKeyValue newRSAKeyValue) {
        this.rsaKeyValue = this.prepareForAssignment(this.rsaKeyValue, newRSAKeyValue);
    }

    @Override
    public ECKeyValue getECKeyValue() {
        return this.ecKeyValue;
    }

    @Override
    public void setECKeyValue(ECKeyValue newECKeyValue) {
        this.ecKeyValue = this.prepareForAssignment(this.ecKeyValue, newECKeyValue);
    }

    @Override
    public XMLObject getUnknownXMLObject() {
        return this.unknownXMLObject;
    }

    @Override
    public void setUnknownXMLObject(XMLObject newXMLObject) {
        this.unknownXMLObject = this.prepareForAssignment(this.unknownXMLObject, newXMLObject);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.dsaKeyValue != null) {
            children.add(this.dsaKeyValue);
        }
        if (this.rsaKeyValue != null) {
            children.add(this.rsaKeyValue);
        }
        if (this.ecKeyValue != null) {
            children.add(this.ecKeyValue);
        }
        if (this.unknownXMLObject != null) {
            children.add(this.unknownXMLObject);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

