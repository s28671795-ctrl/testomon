/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.impl.EncryptionMethodImpl;

public class EncryptionMethodBuilder
extends AbstractSAMLObjectBuilder<EncryptionMethod>
implements SAMLObjectBuilder<EncryptionMethod> {
    @Override
    public EncryptionMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptionMethodImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public EncryptionMethod buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EncryptionMethod", "md");
    }
}

