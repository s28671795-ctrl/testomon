/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResourceTypeImpl
extends AbstractXACMLObject
implements ResourceType {
    private XMLObjectChildrenList<AttributeType> attributes = new XMLObjectChildrenList(this);
    private ResourceContentType resourceContent;

    protected ResourceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AttributeType> getAttributes() {
        return this.attributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.resourceContent != null) {
            children.add(this.resourceContent);
        }
        children.addAll(this.attributes);
        return Collections.unmodifiableList(children);
    }

    @Override
    public ResourceContentType getResourceContent() {
        return this.resourceContent;
    }

    @Override
    public void setResourceContent(ResourceContentType content) {
        this.resourceContent = this.prepareForAssignment(this.resourceContent, content);
    }
}

