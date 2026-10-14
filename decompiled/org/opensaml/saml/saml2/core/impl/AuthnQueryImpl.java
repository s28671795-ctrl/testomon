/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.impl.SubjectQueryImpl;

public class AuthnQueryImpl
extends SubjectQueryImpl
implements AuthnQuery {
    private String sessionIndex;
    private RequestedAuthnContext requestedAuthnContext;

    protected AuthnQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getSessionIndex() {
        return this.sessionIndex;
    }

    @Override
    public void setSessionIndex(String newSessionIndex) {
        this.sessionIndex = this.prepareForAssignment(this.sessionIndex, newSessionIndex);
    }

    @Override
    public RequestedAuthnContext getRequestedAuthnContext() {
        return this.requestedAuthnContext;
    }

    @Override
    public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
        this.requestedAuthnContext = this.prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.requestedAuthnContext != null) {
            children.add(this.requestedAuthnContext);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

