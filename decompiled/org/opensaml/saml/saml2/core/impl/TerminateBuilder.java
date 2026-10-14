/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Terminate;
import org.opensaml.saml.saml2.core.impl.TerminateImpl;

public class TerminateBuilder
extends AbstractSAMLObjectBuilder<Terminate> {
    @Override
    public Terminate buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Terminate", "saml2p");
    }

    @Override
    public Terminate buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TerminateImpl(namespaceURI, localName, namespacePrefix);
    }
}

