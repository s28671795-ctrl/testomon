/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ServiceName;
import org.opensaml.saml.saml2.metadata.impl.ServiceNameImpl;

public class ServiceNameBuilder
extends AbstractSAMLObjectBuilder<ServiceName> {
    @Override
    public ServiceName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceName", "md");
    }

    @Override
    public ServiceName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ServiceNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

