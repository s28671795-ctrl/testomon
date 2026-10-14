/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.SubjectStatement;

public class AssertionImpl
extends AbstractSignableSAMLObject
implements Assertion {
    private String id;
    private SAMLVersion version;
    private String issuer;
    private DateTime issueInstant;
    private Conditions conditions;
    private Advice advice;
    private final IndexedXMLObjectChildrenList<Statement> statements = new IndexedXMLObjectChildrenList(this);

    protected AssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.version = SAMLVersion.VERSION_11;
    }

    @Override
    public int getMajorVersion() {
        return this.version.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return this.version.getMinorVersion();
    }

    @Override
    public void setVersion(SAMLVersion newVersion) {
        this.version = this.prepareForAssignment(this.version, newVersion);
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
    public String getIssuer() {
        return this.issuer;
    }

    @Override
    public void setIssuer(String iss) {
        this.issuer = this.prepareForAssignment(this.issuer, iss);
    }

    @Override
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    @Override
    public void setIssueInstant(DateTime instant) {
        this.issueInstant = this.prepareForAssignment(this.issueInstant, instant);
    }

    @Override
    public Conditions getConditions() {
        return this.conditions;
    }

    @Override
    public void setConditions(Conditions c) {
        this.conditions = this.prepareForAssignment(this.conditions, c);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setAdvice(Advice adv) {
        this.advice = this.prepareForAssignment(this.advice, adv);
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
    public List<SubjectStatement> getSubjectStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement");
        return this.statements.subList(statementQName);
    }

    @Override
    public List<AuthenticationStatement> getAuthenticationStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement");
        return this.statements.subList(statementQName);
    }

    @Override
    public List<AuthorizationDecisionStatement> getAuthorizationDecisionStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement");
        return this.statements.subList(statementQName);
    }

    @Override
    public List<AttributeStatement> getAttributeStatements() {
        QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement");
        return this.statements.subList(statementQName);
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.conditions != null) {
            children.add(this.conditions);
        }
        if (this.advice != null) {
            children.add(this.advice);
        }
        children.addAll(this.statements);
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

