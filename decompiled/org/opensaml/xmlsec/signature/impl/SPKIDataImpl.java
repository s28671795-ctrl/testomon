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
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.SPKISexp;

public class SPKIDataImpl
extends AbstractXMLObject
implements SPKIData {
    private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

    protected SPKIDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getXMLObjects() {
        return this.indexedChildren;
    }

    @Override
    public List<XMLObject> getXMLObjects(QName typeOrName) {
        return this.indexedChildren.subList(typeOrName);
    }

    @Override
    public List<SPKISexp> getSPKISexps() {
        return this.indexedChildren.subList(SPKISexp.DEFAULT_ELEMENT_NAME);
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

