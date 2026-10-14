/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourceType;
import org.opensaml.xacml.policy.impl.ResourceTypeImpl;

public class ResourceTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResourceType> {
    @Override
    public ResourceType buildObject() {
        return (ResourceType)this.buildObject(ResourceType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ResourceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResourceTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

