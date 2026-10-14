/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.impl.RequestedAuthnContextImpl;

public class RequestedAuthnContextBuilder
extends AbstractSAMLObjectBuilder<RequestedAuthnContext> {
    @Override
    public RequestedAuthnContext buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContext", "saml2p");
    }

    @Override
    public RequestedAuthnContext buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestedAuthnContextImpl(namespaceURI, localName, namespacePrefix);
    }
}

