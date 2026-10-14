/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.impl.ConditionsImpl;

public class ConditionsBuilder
extends AbstractSAMLObjectBuilder<Conditions> {
    @Override
    public Conditions buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions", "saml2");
    }

    @Override
    public Conditions buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ConditionsImpl(namespaceURI, localName, namespacePrefix);
    }
}

