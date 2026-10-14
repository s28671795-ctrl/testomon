/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.SubjectLocality;

public class AuthnStatementImpl
extends AbstractSAMLObject
implements AuthnStatement {
    private SubjectLocality subjectLocality;
    private AuthnContext authnContext;
    private DateTime authnInstant;
    private String sessionIndex;
    private DateTime sessionNotOnOrAfter;

    protected AuthnStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public SubjectLocality getSubjectLocality() {
        return this.subjectLocality;
    }

    @Override
    public void setSubjectLocality(SubjectLocality newSubjectLocality) {
        this.subjectLocality = this.prepareForAssignment(this.subjectLocality, newSubjectLocality);
    }

    @Override
    public AuthnContext getAuthnContext() {
        return this.authnContext;
    }

    @Override
    public void setAuthnContext(AuthnContext newAuthnContext) {
        this.authnContext = this.prepareForAssignment(this.authnContext, newAuthnContext);
    }

    @Override
    public DateTime getAuthnInstant() {
        return this.authnInstant;
    }

    @Override
    public void setAuthnInstant(DateTime newAuthnInstant) {
        this.authnInstant = this.prepareForAssignment(this.authnInstant, newAuthnInstant);
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
    public DateTime getSessionNotOnOrAfter() {
        return this.sessionNotOnOrAfter;
    }

    @Override
    public void setSessionNotOnOrAfter(DateTime newSessionNotOnOrAfter) {
        this.sessionNotOnOrAfter = this.prepareForAssignment(this.sessionNotOnOrAfter, newSessionNotOnOrAfter);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.add(this.subjectLocality);
        children.add(this.authnContext);
        return Collections.unmodifiableList(children);
    }
}

