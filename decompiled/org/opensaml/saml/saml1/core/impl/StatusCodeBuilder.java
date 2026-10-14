/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.StatusCode;
import org.opensaml.saml.saml1.core.impl.StatusCodeImpl;

public class StatusCodeBuilder
extends AbstractSAMLObjectBuilder<StatusCode> {
    @Override
    public StatusCode buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode", "saml1p");
    }

    @Override
    public StatusCode buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusCodeImpl(namespaceURI, localName, namespacePrefix);
    }
}

