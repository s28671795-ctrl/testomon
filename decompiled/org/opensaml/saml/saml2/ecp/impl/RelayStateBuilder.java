/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.RelayState;
import org.opensaml.saml.saml2.ecp.impl.RelayStateImpl;

public class RelayStateBuilder
extends AbstractSAMLObjectBuilder<RelayState> {
    @Override
    public RelayState buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayState", "ecp");
    }

    @Override
    public RelayState buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RelayStateImpl(namespaceURI, localName, namespacePrefix);
    }
}

