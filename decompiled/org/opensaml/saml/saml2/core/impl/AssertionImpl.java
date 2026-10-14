/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.Subject;

public class AssertionImpl
extends AbstractSignableSAMLObject
implements Assertion {
    private SAMLVersion version = SAMLVersion.VERSION_20;
    private DateTime issueInstant;
    private String id;
    private Issuer issuer;
    private Subject subject;
    private Conditions conditions;
    private Advice advice;
    private final IndexedXMLObjectChildrenList<Statement> statements = new IndexedXMLObjectChildrenList(this);

    protected AssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public SAMLVersion getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(SAMLVersion newVersion) {
        this.version = this.prepareForAssignment(this.version, newVersion);
    }

    @Override
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    @Override
    public void setIssueInstant(DateTime newIssueInstance) {
        this.issueInstant = this.prepareForAssignment(this.issueInstant, newIssueInstance);
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String newID) {
        String oldID = this.id;
        this.id = this.prepareForAssignment(this.id, newID);
        this.registerOwnID(oldID, this.id);
    }

    @Override
    public Issuer getIssuer() {
        return this.issuer;
    }

    @Override
    public void setIssuer(Issuer newIssuer) {
        this.issuer = this.prepareForAssignment(this.issuer, newIssuer);
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(Subject newSubject) {
        this.subject = this.prepareForAssignment(this.subject, newSubject);
    }

    @Override
    public Conditions getConditions() {
        return this.conditions;
    }

    @Override
    public void setConditions(Conditions newConditions) {
        this.conditions = this.prepareForAssignment(this.conditions, newConditions);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setAdvice(Advice newAdvice) {
        this.advice = this.prepareForAssignment(this.advice, newAdvice);
    }

    @Override
    public List<Statement> getStatements() {
        return this.statements;
    }

    @Override
    public List<Statement> getStatements(QName typeOrName) {
        return this.statements.subList(typeOrName);
    }

    @Override
    public List<AuthnStatement> getAuthnStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
        return this.statements.subList(statementQName);
    }

    @Override
    public List<AuthzDecisionStatement> getAuthzDecisionStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
        return this.statements.subList(statementQName);
    }

    @Override
    public List<AttributeStatement> getAttributeStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml2");
        return this.statements.subList(statementQName);
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.add(this.issuer);
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        children.add(this.subject);
        children.add(this.conditions);
        children.add(this.advice);
        children.addAll(this.statements);
        return Collections.unmodifiableList(children);
    }
}

