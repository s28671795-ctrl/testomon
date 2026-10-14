/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextDeclRefImpl
extends AbstractSAMLObject
implements AuthnContextDeclRef {
    private String authnContextDeclRef;

    protected AuthnContextDeclRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAuthnContextDeclRef() {
        return this.authnContextDeclRef;
    }

    @Override
    public void setAuthnContextDeclRef(String newAuthnContextDeclRef) {
        this.authnContextDeclRef = this.prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

