/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.PublicationInfo;
import org.opensaml.saml.ext.saml2mdrpi.impl.PublicationInfoImpl;

public class PublicationInfoBuilder
extends AbstractSAMLObjectBuilder<PublicationInfo> {
    @Override
    public PublicationInfo buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationInfo", "mdrpi");
    }

    @Override
    public PublicationInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PublicationInfoImpl(namespaceURI, localName, namespacePrefix);
    }
}

