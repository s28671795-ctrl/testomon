/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DiscoHints;
import org.opensaml.saml.ext.saml2mdui.impl.DiscoHintsImpl;

public class DiscoHintsBuilder
extends AbstractSAMLObjectBuilder<DiscoHints> {
    @Override
    public DiscoHints buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHints", "mdui");
    }

    @Override
    public DiscoHints buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DiscoHintsImpl(namespaceURI, localName, namespacePrefix);
    }
}

