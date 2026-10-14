/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.saml.saml2.core.impl.StatusMessageImpl;

public class StatusMessageBuilder
extends AbstractSAMLObjectBuilder<StatusMessage> {
    @Override
    public StatusMessage buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusMessage", "saml2p");
    }

    @Override
    public StatusMessage buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusMessageImpl(namespaceURI, localName, namespacePrefix);
    }
}

