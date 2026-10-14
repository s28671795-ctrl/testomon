/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.impl.AdviceImpl;

public class AdviceBuilder
extends AbstractSAMLObjectBuilder<Advice> {
    @Override
    public Advice buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Advice", "saml2");
    }

    @Override
    public Advice buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AdviceImpl(namespaceURI, localName, namespacePrefix);
    }
}

