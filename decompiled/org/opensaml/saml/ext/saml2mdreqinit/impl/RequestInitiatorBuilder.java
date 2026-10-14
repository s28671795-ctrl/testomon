/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdreqinit.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdreqinit.RequestInitiator;
import org.opensaml.saml.ext.saml2mdreqinit.impl.RequestInitiatorImpl;

public class RequestInitiatorBuilder
extends AbstractSAMLObjectBuilder<RequestInitiator> {
    @Override
    public RequestInitiator buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:profiles:SSO:request-init", "RequestInitiator", "init");
    }

    @Override
    public RequestInitiator buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestInitiatorImpl(namespaceURI, localName, namespacePrefix);
    }
}

