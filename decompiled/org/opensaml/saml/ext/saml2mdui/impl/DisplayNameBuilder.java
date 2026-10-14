/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.impl.DisplayNameImpl;

public class DisplayNameBuilder
extends AbstractSAMLObjectBuilder<DisplayName> {
    @Override
    public DisplayName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DisplayName", "mdui");
    }

    @Override
    public DisplayName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DisplayNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

