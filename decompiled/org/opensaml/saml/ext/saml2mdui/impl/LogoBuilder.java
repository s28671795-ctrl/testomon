/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.impl.LogoImpl;

public class LogoBuilder
extends AbstractSAMLObjectBuilder<Logo> {
    @Override
    public Logo buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Logo", "mdui");
    }

    @Override
    public Logo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new LogoImpl(namespaceURI, localName, namespacePrefix);
    }
}

