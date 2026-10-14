/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.LogoutResponse;
import org.opensaml.saml.saml2.core.impl.LogoutResponseImpl;

public class LogoutResponseBuilder
extends AbstractSAMLObjectBuilder<LogoutResponse> {
    @Override
    public LogoutResponse buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponse", "saml2p");
    }

    @Override
    public LogoutResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new LogoutResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

