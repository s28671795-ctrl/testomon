/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.OrganizationURL;
import org.opensaml.saml.saml2.metadata.impl.OrganizationURLImpl;

public class OrganizationURLBuilder
extends AbstractSAMLObjectBuilder<OrganizationURL> {
    @Override
    public OrganizationURL buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationURL", "md");
    }

    @Override
    public OrganizationURL buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OrganizationURLImpl(namespaceURI, localName, namespacePrefix);
    }
}

