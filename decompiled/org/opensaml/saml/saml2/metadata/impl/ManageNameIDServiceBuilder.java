/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml.saml2.metadata.impl.ManageNameIDServiceImpl;

public class ManageNameIDServiceBuilder
extends AbstractSAMLObjectBuilder<ManageNameIDService> {
    @Override
    public ManageNameIDService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ManageNameIDService", "md");
    }

    @Override
    public ManageNameIDService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ManageNameIDServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

