/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SessionIndex;
import org.opensaml.saml.saml2.core.impl.SessionIndexImpl;

public class SessionIndexBuilder
extends AbstractSAMLObjectBuilder<SessionIndex> {
    @Override
    public SessionIndex buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "SessionIndex", "saml2p");
    }

    @Override
    public SessionIndex buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SessionIndexImpl(namespaceURI, localName, namespacePrefix);
    }
}

