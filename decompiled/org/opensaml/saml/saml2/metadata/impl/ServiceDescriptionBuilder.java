/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ServiceDescription;
import org.opensaml.saml.saml2.metadata.impl.ServiceDescriptionImpl;

public class ServiceDescriptionBuilder
extends AbstractSAMLObjectBuilder<ServiceDescription> {
    @Override
    public ServiceDescription buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceDescription", "md");
    }

    @Override
    public ServiceDescription buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ServiceDescriptionImpl(namespaceURI, localName, namespacePrefix);
    }
}

