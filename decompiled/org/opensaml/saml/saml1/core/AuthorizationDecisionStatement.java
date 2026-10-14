/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.SubjectStatement;

public interface AuthorizationDecisionStatement
extends SAMLObject,
SubjectStatement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorizationDecisionStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement", "saml1");
    public static final String TYPE_LOCAL_NAME = "AuthorizationDecisionStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatementType", "saml1");
    public static final String RESOURCE_ATTRIB_NAME = "Resource";
    public static final String DECISION_ATTRIB_NAME = "Decision";

    public String getResource();

    public void setResource(String var1);

    public DecisionTypeEnumeration getDecision();

    public void setDecision(DecisionTypeEnumeration var1);

    public List<Action> getActions();

    public Evidence getEvidence();

    public void setEvidence(Evidence var1) throws IllegalArgumentException;
}

