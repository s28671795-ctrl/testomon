/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ResourceType;
import org.opensaml.xacml.policy.ResourcesType;

public class ResourcesTypeImpl
extends AbstractXACMLObject
implements ResourcesType {
    private XMLObjectChildrenList<ResourceType> resource = new XMLObjectChildrenList(this);

    public ResourcesTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ResourceType> getResources() {
        return this.resource;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ResourceType> children = new ArrayList<ResourceType>();
        children.addAll(this.resource);
        return Collections.unmodifiableList(children);
    }
}

