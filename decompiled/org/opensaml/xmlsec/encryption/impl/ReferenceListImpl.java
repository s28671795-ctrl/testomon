/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.ReferenceType;

public class ReferenceListImpl
extends AbstractXMLObject
implements ReferenceList {
    private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

    protected ReferenceListImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ReferenceType> getReferences() {
        return this.indexedChildren;
    }

    @Override
    public List<DataReference> getDataReferences() {
        return this.indexedChildren.subList(DataReference.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<KeyReference> getKeyReferences() {
        return this.indexedChildren.subList(KeyReference.DEFAULT_ELEMENT_NAME);
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

