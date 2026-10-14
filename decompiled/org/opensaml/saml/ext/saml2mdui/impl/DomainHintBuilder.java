/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DomainHint;
import org.opensaml.saml.ext.saml2mdui.impl.DomainHintImpl;

public class DomainHintBuilder
extends AbstractSAMLObjectBuilder<DomainHint> {
    @Override
    public DomainHint buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DomainHint", "mdui");
    }

    @Override
    public DomainHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DomainHintImpl(namespaceURI, localName, namespacePrefix);
    }
}

