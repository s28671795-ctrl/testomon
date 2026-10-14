/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.impl.NewEncryptedIDImpl;

public class NewEncryptedIDBuilder
extends AbstractSAMLObjectBuilder<NewEncryptedID> {
    @Override
    public NewEncryptedID buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NewEncryptedID", "saml2p");
    }

    @Override
    public NewEncryptedID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NewEncryptedIDImpl(namespaceURI, localName, namespacePrefix);
    }
}

