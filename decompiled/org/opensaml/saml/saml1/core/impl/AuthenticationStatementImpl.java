/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.opensaml.saml.saml1.core.impl.SubjectStatementImpl;

public class AuthenticationStatementImpl
extends SubjectStatementImpl
implements AuthenticationStatement {
    private String authenticationMethod;
    private DateTime authenticationInstant;
    private SubjectLocality subjectLocality;
    private final XMLObjectChildrenList<AuthorityBinding> authorityBindings = new XMLObjectChildrenList(this);

    protected AuthenticationStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override
    public void setAuthenticationMethod(String method2) {
        this.authenticationMethod = this.prepareForAssignment(this.authenticationMethod, method2);
    }

    @Override
    public DateTime getAuthenticationInstant() {
        return this.authenticationInstant;
    }

    @Override
    public void setAuthenticationInstant(DateTime instant) {
        this.authenticationInstant = this.prepareForAssignment(this.authenticationInstant, instant);
    }

    @Override
    public SubjectLocality getSubjectLocality() {
        return this.subjectLocality;
    }

    @Override
    public void setSubjectLocality(SubjectLocality locality) {
        this.subjectLocality = this.prepareForAssignment(this.subjectLocality, locality);
    }

    @Override
    public List<AuthorityBinding> getAuthorityBindings() {
        return this.authorityBindings;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(this.authorityBindings.size() + 2);
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        if (this.subjectLocality != null) {
            list.add(this.subjectLocality);
        }
        list.addAll(this.authorityBindings);
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

