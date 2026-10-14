/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2aslo.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2aslo.Asynchronous;
import org.opensaml.saml.ext.saml2aslo.impl.AsynchronousImpl;

public class AsynchronousBuilder
extends AbstractSAMLObjectBuilder<Asynchronous> {
    @Override
    public Asynchronous buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "Asynchronous", "aslo");
    }

    @Override
    public Asynchronous buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AsynchronousImpl(namespaceURI, localName, namespacePrefix);
    }
}

