/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.RespondWith;
import org.opensaml.saml.saml1.core.impl.RespondWithImpl;

public class RespondWithBuilder
extends AbstractSAMLObjectBuilder<RespondWith> {
    @Override
    public RespondWith buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith", "saml1p");
    }

    @Override
    public RespondWith buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RespondWithImpl(namespaceURI, localName, namespacePrefix);
    }
}

