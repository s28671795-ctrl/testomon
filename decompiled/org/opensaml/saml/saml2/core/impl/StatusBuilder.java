/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.impl.StatusImpl;

public class StatusBuilder
extends AbstractSAMLObjectBuilder<Status> {
    @Override
    public Status buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Status", "saml2p");
    }

    @Override
    public Status buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusImpl(namespaceURI, localName, namespacePrefix);
    }
}

