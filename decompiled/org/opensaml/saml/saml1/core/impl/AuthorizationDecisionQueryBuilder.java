/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.impl.AuthorizationDecisionQueryImpl;

public class AuthorizationDecisionQueryBuilder
extends AbstractSAMLObjectBuilder<AuthorizationDecisionQuery> {
    @Override
    public AuthorizationDecisionQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQuery", "saml1p");
    }

    @Override
    public AuthorizationDecisionQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthorizationDecisionQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

