/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.impl.DescriptionImpl;

public class DescriptionBuilder
extends AbstractSAMLObjectBuilder<Description> {
    @Override
    public Description buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Description", "mdui");
    }

    @Override
    public Description buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DescriptionImpl(namespaceURI, localName, namespacePrefix);
    }
}

