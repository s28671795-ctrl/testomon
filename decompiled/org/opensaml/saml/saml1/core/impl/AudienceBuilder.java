/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Audience;
import org.opensaml.saml.saml1.core.impl.AudienceImpl;

public class AudienceBuilder
extends AbstractSAMLObjectBuilder<Audience> {
    @Override
    public Audience buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Audience", "saml1");
    }

    @Override
    public Audience buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AudienceImpl(namespaceURI, localName, namespacePrefix);
    }
}

