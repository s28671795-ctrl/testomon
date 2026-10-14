/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.impl.SingleSignOnServiceImpl;

public class SingleSignOnServiceBuilder
extends AbstractSAMLObjectBuilder<SingleSignOnService> {
    @Override
    public SingleSignOnService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SingleSignOnService", "md");
    }

    @Override
    public SingleSignOnService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SingleSignOnServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

