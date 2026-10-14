/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.ctx.impl.ResourceContentTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResourceContentTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResourceContentType> {
    @Override
    public ResourceContentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResourceContentTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public ResourceContentType buildObject() {
        return (ResourceContentType)this.buildObject(ResourceContentType.DEFAULT_ELEMENT_NAME);
    }
}

