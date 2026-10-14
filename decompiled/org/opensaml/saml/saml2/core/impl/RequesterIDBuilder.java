/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.RequesterID;
import org.opensaml.saml.saml2.core.impl.RequesterIDImpl;

public class RequesterIDBuilder
extends AbstractSAMLObjectBuilder<RequesterID> {
    @Override
    public RequesterID buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "RequesterID", "saml2p");
    }

    @Override
    public RequesterID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequesterIDImpl(namespaceURI, localName, namespacePrefix);
    }
}

