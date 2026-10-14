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
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.XPath;

public class TransformImpl
extends AbstractXMLObject
implements Transform {
    private String algorithm;
    private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

    protected TransformImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public List<XMLObject> getAllChildren() {
        return this.indexedChildren;
    }

    @Override
    public List<XMLObject> getXMLObjects(QName typeOrName) {
        return this.indexedChildren.subList(typeOrName);
    }

    @Override
    public List<XPath> getXPaths() {
        return this.indexedChildren.subList(XPath.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList children = new ArrayList();
        children.addAll(this.indexedChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

