/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.KeyInfoReference;

public class KeyInfoReferenceImpl
extends AbstractXMLObject
implements KeyInfoReference {
    private String id;
    private String uri;

    protected KeyInfoReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public String getURI() {
        return this.uri;
    }

    @Override
    public void setURI(String newURI) {
        this.uri = this.prepareForAssignment(this.uri, newURI);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

