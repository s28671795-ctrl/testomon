/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.impl.NameIDImpl;

public class NameIDBuilder
extends AbstractSAMLObjectBuilder<NameID> {
    @Override
    public NameID buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "NameID", "saml2");
    }

    @Override
    public NameID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDImpl(namespaceURI, localName, namespacePrefix);
    }
}

