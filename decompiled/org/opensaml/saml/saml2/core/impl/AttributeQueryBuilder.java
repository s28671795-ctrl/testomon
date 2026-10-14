/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AttributeQuery;
import org.opensaml.saml.saml2.core.impl.AttributeQueryImpl;

public class AttributeQueryBuilder
extends AbstractSAMLObjectBuilder<AttributeQuery> {
    @Override
    public AttributeQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQuery", "saml2p");
    }

    @Override
    public AttributeQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

