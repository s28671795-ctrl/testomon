/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdattr.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.ext.saml2mdattr.impl.EntityAttributesImpl;

public class EntityAttributesBuilder
extends AbstractSAMLObjectBuilder<EntityAttributes> {
    @Override
    public EntityAttributes buildObject() {
        return (EntityAttributes)this.buildObject(EntityAttributes.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public EntityAttributes buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EntityAttributesImpl(namespaceURI, localName, namespacePrefix);
    }
}

