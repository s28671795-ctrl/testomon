/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.PublicKey;

public class ECKeyValueImpl
extends AbstractXMLObject
implements ECKeyValue {
    private String id;
    private XMLObject ecParams;
    private NamedCurve namedCurve;
    private PublicKey publicKey;

    protected ECKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public XMLObject getECParameters() {
        return this.ecParams;
    }

    @Override
    public void setECParameters(XMLObject newParams) {
        this.ecParams = this.prepareForAssignment(this.ecParams, newParams);
    }

    @Override
    public NamedCurve getNamedCurve() {
        return this.namedCurve;
    }

    @Override
    public void setNamedCurve(NamedCurve newCurve) {
        this.namedCurve = this.prepareForAssignment(this.namedCurve, newCurve);
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public void setPublicKey(PublicKey newKey) {
        this.publicKey = this.prepareForAssignment(this.publicKey, newKey);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.ecParams != null) {
            children.add(this.ecParams);
        }
        if (this.namedCurve != null) {
            children.add(this.namedCurve);
        }
        if (this.publicKey != null) {
            children.add(this.publicKey);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

