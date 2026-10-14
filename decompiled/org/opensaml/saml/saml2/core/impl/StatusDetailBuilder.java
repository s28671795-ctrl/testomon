/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.impl.StatusDetailImpl;

public class StatusDetailBuilder
extends AbstractSAMLObjectBuilder<StatusDetail> {
    @Override
    public StatusDetail buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusDetail", "saml2p");
    }

    @Override
    public StatusDetail buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusDetailImpl(namespaceURI, localName, namespacePrefix);
    }
}

