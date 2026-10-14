/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.impl.ManageNameIDRequestImpl;

public class ManageNameIDRequestBuilder
extends AbstractSAMLObjectBuilder<ManageNameIDRequest> {
    @Override
    public ManageNameIDRequest buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequest", "saml2p");
    }

    @Override
    public ManageNameIDRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ManageNameIDRequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

