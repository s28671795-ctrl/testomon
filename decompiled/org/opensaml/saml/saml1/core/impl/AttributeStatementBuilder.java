/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.impl.AttributeStatementImpl;

public class AttributeStatementBuilder
extends AbstractSAMLObjectBuilder<AttributeStatement> {
    @Override
    public AttributeStatement buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement", "saml1");
    }

    @Override
    public AttributeStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeStatementImpl(namespaceURI, localName, namespacePrefix);
    }
}

