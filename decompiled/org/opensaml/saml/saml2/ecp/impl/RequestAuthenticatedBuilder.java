/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.RequestAuthenticated;
import org.opensaml.saml.saml2.ecp.impl.RequestAuthenticatedImpl;

public class RequestAuthenticatedBuilder
extends AbstractSAMLObjectBuilder<RequestAuthenticated> {
    @Override
    public RequestAuthenticated buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticated", "ecp");
    }

    @Override
    public RequestAuthenticated buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestAuthenticatedImpl(namespaceURI, localName, namespacePrefix);
    }
}

