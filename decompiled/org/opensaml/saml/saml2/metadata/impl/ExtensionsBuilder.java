/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.impl.ExtensionsImpl;

public class ExtensionsBuilder
extends AbstractSAMLObjectBuilder<Extensions> {
    @Override
    public Extensions buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Extensions", "md");
    }

    @Override
    public Extensions buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ExtensionsImpl(namespaceURI, localName, namespacePrefix);
    }
}

