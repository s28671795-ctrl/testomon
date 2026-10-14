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
import org.opensaml.xmlsec.encryption.ReferenceType;

public class ReferenceTypeImpl
extends AbstractXMLObject
implements ReferenceType {
    private String uri;
    private final IndexedXMLObjectChildrenList<XMLObject> xmlChildren = new IndexedXMLObjectChildrenList(this);

    protected ReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
    public List<XMLObject> getUnknownXMLObjects() {
        return this.xmlChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.xmlChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.xmlChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

