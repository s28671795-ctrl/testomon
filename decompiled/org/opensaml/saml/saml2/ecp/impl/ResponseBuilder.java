/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.Response;
import org.opensaml.saml.saml2.ecp.impl.ResponseImpl;

public class ResponseBuilder
extends AbstractSAMLObjectBuilder<Response> {
    @Override
    public Response buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Response", "ecp");
    }

    @Override
    public Response buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

