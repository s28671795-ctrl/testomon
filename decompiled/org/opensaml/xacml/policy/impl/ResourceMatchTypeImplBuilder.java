/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourceMatchType;
import org.opensaml.xacml.policy.impl.ResourceMatchTypeImpl;

public class ResourceMatchTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResourceMatchType> {
    @Override
    public ResourceMatchType buildObject() {
        return (ResourceMatchType)this.buildObject(ResourceMatchType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ResourceMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResourceMatchTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

