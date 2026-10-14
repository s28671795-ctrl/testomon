/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.impl.AdviceImpl;

public class AdviceBuilder
extends AbstractSAMLObjectBuilder<Advice> {
    @Override
    public Advice buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Advice", "saml1");
    }

    @Override
    public Advice buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AdviceImpl(namespaceURI, localName, namespacePrefix);
    }
}

