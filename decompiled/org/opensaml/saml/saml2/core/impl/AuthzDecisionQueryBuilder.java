/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;
import org.opensaml.saml.saml2.core.impl.AuthzDecisionQueryImpl;

public class AuthzDecisionQueryBuilder
extends AbstractSAMLObjectBuilder<AuthzDecisionQuery> {
    @Override
    public AuthzDecisionQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthzDecisionQuery", "saml2p");
    }

    @Override
    public AuthzDecisionQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthzDecisionQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

