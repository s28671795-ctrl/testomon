/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;

public class ExtensionsImpl
extends AbstractSAMLObject
implements Extensions {
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected ExtensionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
        return Collections.unmodifiableList(this.unknownChildren);
    }
}

