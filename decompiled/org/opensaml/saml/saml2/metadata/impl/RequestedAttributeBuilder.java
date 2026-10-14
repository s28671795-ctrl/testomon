/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml.saml2.metadata.impl.RequestedAttributeImpl;

public class RequestedAttributeBuilder
extends AbstractSAMLObjectBuilder<RequestedAttribute> {
    @Override
    public RequestedAttribute buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttribute", "md");
    }

    @Override
    public RequestedAttribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestedAttributeImpl(namespaceURI, localName, namespacePrefix);
    }
}

