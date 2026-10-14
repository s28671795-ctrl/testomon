/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.impl.ResourceTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

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

