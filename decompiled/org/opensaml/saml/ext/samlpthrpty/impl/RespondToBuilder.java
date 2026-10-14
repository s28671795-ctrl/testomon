/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlpthrpty.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlpthrpty.RespondTo;
import org.opensaml.saml.ext.samlpthrpty.impl.RespondToImpl;

public class RespondToBuilder
extends AbstractSAMLObjectBuilder<RespondTo> {
    @Override
    public RespondTo buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:protocol:ext:third-party", "RespondTo", "thrpty");
    }

    @Override
    public RespondTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RespondToImpl(namespaceURI, localName, namespacePrefix);
    }
}

