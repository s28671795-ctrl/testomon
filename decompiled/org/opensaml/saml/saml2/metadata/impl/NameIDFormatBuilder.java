/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.impl.NameIDFormatImpl;

public class NameIDFormatBuilder
extends AbstractSAMLObjectBuilder<NameIDFormat> {
    @Override
    public NameIDFormat buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDFormat", "md");
    }

    @Override
    public NameIDFormat buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDFormatImpl(namespaceURI, localName, namespacePrefix);
    }
}

