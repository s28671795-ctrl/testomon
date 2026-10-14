/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.PublicationPath;
import org.opensaml.saml.ext.saml2mdrpi.impl.PublicationPathImpl;

public class PublicationPathBuilder
extends AbstractSAMLObjectBuilder<PublicationPath> {
    @Override
    public PublicationPath buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPath", "mdrpi");
    }

    @Override
    public PublicationPath buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PublicationPathImpl(namespaceURI, localName, namespacePrefix);
    }
}

