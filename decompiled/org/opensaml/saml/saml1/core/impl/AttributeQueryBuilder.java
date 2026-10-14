/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.impl.AttributeQueryImpl;

public class AttributeQueryBuilder
extends AbstractSAMLObjectBuilder<AttributeQuery> {
    @Override
    public AttributeQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery", "saml1p");
    }

    @Override
    public AttributeQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

