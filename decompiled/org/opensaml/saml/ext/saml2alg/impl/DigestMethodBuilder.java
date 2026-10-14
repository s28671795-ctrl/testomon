/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.opensaml.saml.ext.saml2alg.impl.DigestMethodImpl;

public class DigestMethodBuilder
extends AbstractSAMLObjectBuilder<DigestMethod> {
    @Override
    public DigestMethod buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethod", "alg");
    }

    @Override
    public DigestMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DigestMethodImpl(namespaceURI, localName, namespacePrefix);
    }
}

