/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.impl.ResponseImpl;

public class ResponseBuilder
extends AbstractSAMLObjectBuilder<Response> {
    @Override
    public Response buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "Response", "saml1p");
    }

    @Override
    public Response buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

