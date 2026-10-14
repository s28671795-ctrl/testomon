/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ManageNameIDResponse;
import org.opensaml.saml.saml2.core.impl.ManageNameIDResponseImpl;

public class ManageNameIDResponseBuilder
extends AbstractSAMLObjectBuilder<ManageNameIDResponse> {
    @Override
    public ManageNameIDResponse buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponse", "saml2p");
    }

    @Override
    public ManageNameIDResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ManageNameIDResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

