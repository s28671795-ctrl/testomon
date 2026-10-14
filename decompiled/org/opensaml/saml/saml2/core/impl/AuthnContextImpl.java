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
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextImpl
extends AbstractSAMLObject
implements AuthnContext {
    private AuthnContextClassRef authnContextClassRef;
    private AuthnContextDecl authnContextDecl;
    private AuthnContextDeclRef authnContextDeclRef;
    private final XMLObjectChildrenList<AuthenticatingAuthority> authenticatingAuthority = new XMLObjectChildrenList(this);

    protected AuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public AuthnContextClassRef getAuthnContextClassRef() {
        return this.authnContextClassRef;
    }

    @Override
    public void setAuthnContextClassRef(AuthnContextClassRef newAuthnContextClassRef) {
        this.authnContextClassRef = this.prepareForAssignment(this.authnContextClassRef, newAuthnContextClassRef);
    }

    @Override
    public AuthnContextDecl getAuthContextDecl() {
        return this.authnContextDecl;
    }

    @Override
    public void setAuthnContextDecl(AuthnContextDecl newAuthnContextDecl) {
        this.authnContextDecl = this.prepareForAssignment(this.authnContextDecl, newAuthnContextDecl);
    }

    @Override
    public AuthnContextDeclRef getAuthnContextDeclRef() {
        return this.authnContextDeclRef;
    }

    @Override
    public void setAuthnContextDeclRef(AuthnContextDeclRef newAuthnContextDeclRef) {
        this.authnContextDeclRef = this.prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
    }

    @Override
    public List<AuthenticatingAuthority> getAuthenticatingAuthorities() {
        return this.authenticatingAuthority;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.add(this.authnContextClassRef);
        children.add(this.authnContextDecl);
        children.add(this.authnContextDeclRef);
        children.addAll(this.authenticatingAuthority);
        return Collections.unmodifiableList(children);
    }
}

