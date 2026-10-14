/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.AttributeQueryDescriptorTypeImpl;

public class AttributeQueryDescriptorTypeBuilder
extends AbstractSAMLObjectBuilder<AttributeQueryDescriptorType> {
    @Override
    public AttributeQueryDescriptorType buildObject() {
        return (AttributeQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AttributeQueryDescriptorType.TYPE_NAME);
    }

    @Override
    public AttributeQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

