/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.impl.OrganizationImpl;

public class OrganizationBuilder
extends AbstractSAMLObjectBuilder<Organization> {
    @Override
    public Organization buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Organization", "md");
    }

    @Override
    public Organization buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OrganizationImpl(namespaceURI, localName, namespacePrefix);
    }
}

