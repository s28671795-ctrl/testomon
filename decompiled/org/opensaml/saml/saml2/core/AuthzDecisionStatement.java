/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml2.core.Evidence;
import org.opensaml.saml.saml2.core.Statement;

public interface AuthzDecisionStatement
extends Statement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthzDecisionStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
    public static final String TYPE_LOCAL_NAME = "AuthzDecisionStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatementType", "saml2");
    public static final String RESOURCE_ATTRIB_NAME = "Resource";
    public static final String DECISION_ATTRIB_NAME = "Decision";

    public String getResource();

    public void setResource(String var1);

    public DecisionTypeEnumeration getDecision();

    public void setDecision(DecisionTypeEnumeration var1);

    public List<Action> getActions();

    public Evidence getEvidence();

    public void setEvidence(Evidence var1);
}

