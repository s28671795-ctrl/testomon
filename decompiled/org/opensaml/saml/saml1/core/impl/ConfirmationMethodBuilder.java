/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.impl.ConfirmationMethodImpl;

public class ConfirmationMethodBuilder
extends AbstractSAMLObjectBuilder<ConfirmationMethod> {
    @Override
    public ConfirmationMethod buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod", "saml1");
    }

    @Override
    public ConfirmationMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ConfirmationMethodImpl(namespaceURI, localName, namespacePrefix);
    }
}

