/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.impl.NewIDImpl;

public class NewIDBuilder
extends AbstractSAMLObjectBuilder<NewID> {
    @Override
    public NewID buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NewID", "saml2p");
    }

    @Override
    public NewID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NewIDImpl(namespaceURI, localName, namespacePrefix);
    }
}

