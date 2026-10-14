/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Scoping;
import org.opensaml.saml.saml2.core.impl.ScopingImpl;

public class ScopingBuilder
extends AbstractSAMLObjectBuilder<Scoping> {
    @Override
    public Scoping buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Scoping", "saml2p");
    }

    @Override
    public Scoping buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ScopingImpl(namespaceURI, localName, namespacePrefix);
    }
}

