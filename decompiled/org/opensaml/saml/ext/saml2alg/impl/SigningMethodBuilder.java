/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.opensaml.saml.ext.saml2alg.impl.SigningMethodImpl;

public class SigningMethodBuilder
extends AbstractSAMLObjectBuilder<SigningMethod> {
    @Override
    public SigningMethod buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethod", "alg");
    }

    @Override
    public SigningMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SigningMethodImpl(namespaceURI, localName, namespacePrefix);
    }
}

