/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.Publication;
import org.opensaml.saml.ext.saml2mdrpi.impl.PublicationImpl;

public class PublicationBuilder
extends AbstractSAMLObjectBuilder<Publication> {
    @Override
    public Publication buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "Publication", "mdrpi");
    }

    @Override
    public Publication buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PublicationImpl(namespaceURI, localName, namespacePrefix);
    }
}

