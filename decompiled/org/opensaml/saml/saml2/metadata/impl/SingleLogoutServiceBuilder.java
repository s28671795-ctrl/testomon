/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.impl.SingleLogoutServiceImpl;

public class SingleLogoutServiceBuilder
extends AbstractSAMLObjectBuilder<SingleLogoutService> {
    @Override
    public SingleLogoutService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SingleLogoutService", "md");
    }

    @Override
    public SingleLogoutService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SingleLogoutServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

