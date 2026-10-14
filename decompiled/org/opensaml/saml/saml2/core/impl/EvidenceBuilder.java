/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Evidence;
import org.opensaml.saml.saml2.core.impl.EvidenceImpl;

public class EvidenceBuilder
extends AbstractSAMLObjectBuilder<Evidence> {
    @Override
    public Evidence buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Evidence", "saml2");
    }

    @Override
    public Evidence buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EvidenceImpl(namespaceURI, localName, namespacePrefix);
    }
}

