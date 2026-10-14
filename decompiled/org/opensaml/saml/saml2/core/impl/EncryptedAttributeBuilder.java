/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.impl.EncryptedAttributeImpl;

public class EncryptedAttributeBuilder
extends AbstractSAMLObjectBuilder<EncryptedAttribute> {
    @Override
    public EncryptedAttribute buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAttribute", "saml2");
    }

    @Override
    public EncryptedAttribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptedAttributeImpl(namespaceURI, localName, namespacePrefix);
    }
}

