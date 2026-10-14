/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.impl.SurNameImpl;

public class SurNameBuilder
extends AbstractSAMLObjectBuilder<SurName> {
    @Override
    public SurName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SurName", "md");
    }

    @Override
    public SurName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SurNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

