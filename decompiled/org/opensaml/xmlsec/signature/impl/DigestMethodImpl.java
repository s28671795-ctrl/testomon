/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.DigestMethod;

public class DigestMethodImpl
extends AbstractXMLObject
implements DigestMethod {
    private String algorithm;
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected DigestMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
        children.addAll(this.unknownChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

