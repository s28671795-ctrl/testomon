/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.impl.AuthorizationDecisionStatementImpl;

public class AuthorizationDecisionStatementBuilder
extends AbstractSAMLObjectBuilder<AuthorizationDecisionStatement> {
    @Override
    public AuthorizationDecisionStatement buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement", "saml1");
    }

    @Override
    public AuthorizationDecisionStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthorizationDecisionStatementImpl(namespaceURI, localName, namespacePrefix);
    }
}

