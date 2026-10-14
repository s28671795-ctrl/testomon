/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.Request;
import org.opensaml.saml.saml2.ecp.impl.RequestImpl;

public class RequestBuilder
extends AbstractSAMLObjectBuilder<Request> {
    @Override
    public Request buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Request", "ecp");
    }

    @Override
    public Request buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

