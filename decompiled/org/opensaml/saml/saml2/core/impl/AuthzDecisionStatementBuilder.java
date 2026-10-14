/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.impl.AuthzDecisionStatementImpl;

public class AuthzDecisionStatementBuilder
extends AbstractSAMLObjectBuilder<AuthzDecisionStatement> {
    @Override
    public AuthzDecisionStatement buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
    }

    @Override
    public AuthzDecisionStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthzDecisionStatementImpl(namespaceURI, localName, namespacePrefix);
    }
}

