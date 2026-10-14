/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml1.core.impl.RequestImpl;

public class RequestBuilder
extends AbstractSAMLObjectBuilder<Request> {
    @Override
    public Request buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "Request", "saml1");
    }

    @Override
    public Request buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

