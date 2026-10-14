/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.impl.AttributeImpl;

public class AttributeBuilder
extends AbstractSAMLObjectBuilder<Attribute> {
    @Override
    public Attribute buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml2");
    }

    @Override
    public Attribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeImpl(namespaceURI, localName, namespacePrefix);
    }
}

