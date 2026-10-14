/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.impl.NameIDPolicyImpl;

public class NameIDPolicyBuilder
extends AbstractSAMLObjectBuilder<NameIDPolicy> {
    @Override
    public NameIDPolicy buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicy", "saml2p");
    }

    @Override
    public NameIDPolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDPolicyImpl(namespaceURI, localName, namespacePrefix);
    }
}

