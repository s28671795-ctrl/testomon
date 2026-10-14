/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourcesType;
import org.opensaml.xacml.policy.impl.ResourcesTypeImpl;

public class ResourcesTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResourcesType> {
    @Override
    public ResourcesType buildObject() {
        return (ResourcesType)this.buildObject(ResourcesType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ResourcesType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResourcesTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

