/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.impl.LogoutRequestImpl;

public class LogoutRequestBuilder
extends AbstractSAMLObjectBuilder<LogoutRequest> {
    @Override
    public LogoutRequest buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequest", "saml2p");
    }

    @Override
    public LogoutRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new LogoutRequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

