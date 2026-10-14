/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.impl.OrganizationDisplayNameImpl;

public class OrganizationDisplayNameBuilder
extends AbstractSAMLObjectBuilder<OrganizationDisplayName> {
    @Override
    public OrganizationDisplayName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationDisplayName", "md");
    }

    @Override
    public OrganizationDisplayName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OrganizationDisplayNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

