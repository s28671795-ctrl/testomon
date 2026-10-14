/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.ext.saml2mdui.impl.InformationURLImpl;

public class InformationURLBuilder
extends AbstractSAMLObjectBuilder<InformationURL> {
    @Override
    public InformationURL buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "InformationURL", "mdui");
    }

    @Override
    public InformationURL buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new InformationURLImpl(namespaceURI, localName, namespacePrefix);
    }
}

