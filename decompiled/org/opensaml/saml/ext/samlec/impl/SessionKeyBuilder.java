/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.SessionKey;
import org.opensaml.saml.ext.samlec.impl.SessionKeyImpl;

public class SessionKeyBuilder
extends AbstractSAMLObjectBuilder<SessionKey> {
    @Override
    public SessionKey buildObject() {
        return this.buildObject("urn:ietf:params:xml:ns:samlec", "SessionKey", "samlec");
    }

    @Override
    public SessionKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SessionKeyImpl(namespaceURI, localName, namespacePrefix);
    }
}

