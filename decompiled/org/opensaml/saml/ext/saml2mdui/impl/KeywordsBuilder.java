/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.opensaml.saml.ext.saml2mdui.impl.KeywordsImpl;

public class KeywordsBuilder
extends AbstractSAMLObjectBuilder<Keywords> {
    @Override
    public Keywords buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Keywords", "mdui");
    }

    @Override
    public Keywords buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeywordsImpl(namespaceURI, localName, namespacePrefix);
    }
}

