/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.impl.ExtensionsImpl;

public class ExtensionsBuilder
extends AbstractSAMLObjectBuilder<Extensions> {
    @Override
    public Extensions buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Extensions", "saml2p");
    }

    @Override
    public Extensions buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ExtensionsImpl(namespaceURI, localName, namespacePrefix);
    }
}

