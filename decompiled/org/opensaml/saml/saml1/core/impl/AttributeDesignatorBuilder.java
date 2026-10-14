/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.opensaml.saml.saml1.core.impl.AttributeDesignatorImpl;

public class AttributeDesignatorBuilder
extends AbstractSAMLObjectBuilder<AttributeDesignator> {
    @Override
    public AttributeDesignator buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator", "saml1");
    }

    @Override
    public AttributeDesignator buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeDesignatorImpl(namespaceURI, localName, namespacePrefix);
    }
}

