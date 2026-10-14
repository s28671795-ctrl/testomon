/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Evidentiary;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.Subject;

public interface Assertion
extends SignableSAMLObject,
Evidentiary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Assertion";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion", "saml2");
    public static final String TYPE_LOCAL_NAME = "AssertionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionType", "saml2");
    public static final String VERSION_ATTRIB_NAME = "Version";
    public static final String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
    public static final String ID_ATTRIB_NAME = "ID";

    public SAMLVersion getVersion();

    public void setVersion(SAMLVersion var1);

    public DateTime getIssueInstant();

    public void setIssueInstant(DateTime var1);

    public String getID();

    public void setID(String var1);

    public Issuer getIssuer();

    public void setIssuer(Issuer var1);

    public Subject getSubject();

    public void setSubject(Subject var1);

    public Conditions getConditions();

    public void setConditions(Conditions var1);

    public Advice getAdvice();

    public void setAdvice(Advice var1);

    public List<Statement> getStatements();

    public List<Statement> getStatements(QName var1);

    public List<AuthnStatement> getAuthnStatements();

    public List<AuthzDecisionStatement> getAuthzDecisionStatements();

    public List<AttributeStatement> getAttributeStatements();
}

