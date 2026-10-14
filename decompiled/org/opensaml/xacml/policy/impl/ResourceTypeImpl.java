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
import org.opensaml.xacml.policy.ResourceMatchType;
import org.opensaml.xacml.policy.ResourceType;

public class ResourceTypeImpl
extends AbstractXACMLObject
implements ResourceType {
    private XMLObjectChildrenList<ResourceMatchType> resourceMatch = new XMLObjectChildrenList(this);

    protected ResourceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ResourceMatchType> getResourceMatches() {
        return this.resourceMatch;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ResourceMatchType> children = new ArrayList<ResourceMatchType>();
        children.addAll(this.resourceMatch);
        return Collections.unmodifiableList(children);
    }
}

