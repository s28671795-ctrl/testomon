/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;
import org.opensaml.saml.ext.saml2mdrpi.impl.RegistrationPolicyImpl;

public class RegistrationPolicyBuilder
extends AbstractSAMLObjectBuilder<RegistrationPolicy> {
    @Override
    public RegistrationPolicy buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationPolicy", "mdrpi");
    }

    @Override
    public RegistrationPolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RegistrationPolicyImpl(namespaceURI, localName, namespacePrefix);
    }
}

