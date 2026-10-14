/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;

public class AuthnContextClassRefImpl
extends AbstractSAMLObject
implements AuthnContextClassRef {
    private String authnContextClassRef;

    protected AuthnContextClassRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAuthnContextClassRef() {
        return this.authnContextClassRef;
    }

    @Override
    public void setAuthnContextClassRef(String newAuthnContextClassRef) {
        this.authnContextClassRef = this.prepareForAssignment(this.authnContextClassRef, newAuthnContextClassRef);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

