/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.impl.CompanyImpl;

public class CompanyBuilder
extends AbstractSAMLObjectBuilder<Company> {
    @Override
    public Company buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Company", "md");
    }

    @Override
    public Company buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CompanyImpl(namespaceURI, localName, namespacePrefix);
    }
}

