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
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class AttributeValueTypeImpl
extends AbstractXACMLObject
implements AttributeValueType {
    private IndexedXMLObjectChildrenList<XMLObject> unknownElements;
    private AttributeMap unknownAttributes = new AttributeMap(this);
    private String textContent;

    protected AttributeValueTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownElements = new IndexedXMLObjectChildrenList(this);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.textContent == null) {
            children.addAll(this.unknownElements);
        }
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
        return this.textContent;
    }

    @Override
    public void setValue(String value) {
        this.textContent = this.prepareForAssignment(this.textContent, value);
    }
}

