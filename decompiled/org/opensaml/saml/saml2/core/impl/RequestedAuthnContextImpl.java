/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;

public class RequestedAuthnContextImpl
extends AbstractSAMLObject
implements RequestedAuthnContext {
    private final XMLObjectChildrenList<AuthnContextClassRef> authnContextClassRefs = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AuthnContextDeclRef> authnContextDeclRefs = new XMLObjectChildrenList(this);
    private AuthnContextComparisonTypeEnumeration comparison;

    protected RequestedAuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public AuthnContextComparisonTypeEnumeration getComparison() {
        return this.comparison;
    }

    @Override
    public void setComparison(AuthnContextComparisonTypeEnumeration newComparison) {
        this.comparison = this.prepareForAssignment(this.comparison, newComparison);
    }

    @Override
    public List<AuthnContextClassRef> getAuthnContextClassRefs() {
        return this.authnContextClassRefs;
    }

    @Override
    public List<AuthnContextDeclRef> getAuthnContextDeclRefs() {
        return this.authnContextDeclRefs;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.addAll(this.authnContextClassRefs);
        children.addAll(this.authnContextDeclRefs);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

