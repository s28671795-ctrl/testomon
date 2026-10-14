/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class EnvironmentTypeImpl
extends AbstractXACMLObject
implements EnvironmentType {
    private XMLObjectChildrenList<AttributeType> attributes = new XMLObjectChildrenList(this);

    protected EnvironmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AttributeType> getAttributes() {
        return this.attributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeType> children = new ArrayList<AttributeType>();
        children.addAll(this.attributes);
        return Collections.unmodifiableList(children);
    }
}

