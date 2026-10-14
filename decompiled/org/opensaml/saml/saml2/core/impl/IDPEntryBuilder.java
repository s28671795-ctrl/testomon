/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.IDPEntry;
import org.opensaml.saml.saml2.core.impl.IDPEntryImpl;

public class IDPEntryBuilder
extends AbstractSAMLObjectBuilder<IDPEntry> {
    @Override
    public IDPEntry buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntry", "saml2p");
    }

    @Override
    public IDPEntry buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IDPEntryImpl(namespaceURI, localName, namespacePrefix);
    }
}

