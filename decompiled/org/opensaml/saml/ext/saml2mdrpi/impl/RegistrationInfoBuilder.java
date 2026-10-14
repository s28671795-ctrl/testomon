/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.ext.saml2mdrpi.impl.RegistrationInfoImpl;

public class RegistrationInfoBuilder
extends AbstractSAMLObjectBuilder<RegistrationInfo> {
    @Override
    public RegistrationInfo buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfo", "mdrpi");
    }

    @Override
    public RegistrationInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RegistrationInfoImpl(namespaceURI, localName, namespacePrefix);
    }
}

