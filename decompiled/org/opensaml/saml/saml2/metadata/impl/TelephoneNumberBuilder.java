/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;
import org.opensaml.saml.saml2.metadata.impl.TelephoneNumberImpl;

public class TelephoneNumberBuilder
extends AbstractSAMLObjectBuilder<TelephoneNumber> {
    @Override
    public TelephoneNumber buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "TelephoneNumber", "md");
    }

    @Override
    public TelephoneNumber buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TelephoneNumberImpl(namespaceURI, localName, namespacePrefix);
    }
}

