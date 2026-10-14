/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.impl.AttributeProfileImpl;

public class AttributeProfileBuilder
extends AbstractSAMLObjectBuilder<AttributeProfile> {
    @Override
    public AttributeProfile buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfile", "md");
    }

    @Override
    public AttributeProfile buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeProfileImpl(namespaceURI, localName, namespacePrefix);
    }
}

