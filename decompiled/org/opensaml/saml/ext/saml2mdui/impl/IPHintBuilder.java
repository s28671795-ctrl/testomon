/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.IPHint;
import org.opensaml.saml.ext.saml2mdui.impl.IPHintImpl;

public class IPHintBuilder
extends AbstractSAMLObjectBuilder<IPHint> {
    @Override
    public IPHint buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "IPHint", "mdui");
    }

    @Override
    public IPHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IPHintImpl(namespaceURI, localName, namespacePrefix);
    }
}

