/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResourceContentTypeImpl
extends AbstractXACMLObject
implements ResourceContentType {
    private IndexedXMLObjectChildrenList<XMLObject> unknownElements;
    private AttributeMap unknownAttributes = new AttributeMap(this);
    private String value;

    protected ResourceContentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownElements = new IndexedXMLObjectChildrenList(this);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.unknownElements);
        return Collections.unmodifiableList(children);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownElements;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownElements.subList(typeOrName);
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
}

