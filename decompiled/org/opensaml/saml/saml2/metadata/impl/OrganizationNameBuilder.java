/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.impl.OrganizationNameImpl;

public class OrganizationNameBuilder
extends AbstractSAMLObjectBuilder<OrganizationName> {
    @Override
    public OrganizationName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationName", "md");
    }

    @Override
    public OrganizationName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OrganizationNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

