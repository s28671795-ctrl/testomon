/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.SubjectQuery;

public interface AuthorizationDecisionQuery
extends SubjectQuery {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorizationDecisionQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQuery", "saml1p");
    public static final String TYPE_LOCAL_NAME = "AuthorizationDecisionQueryType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQueryType", "saml1p");
    public static final String RESOURCE_ATTRIB_NAME = "Resource";

    public String getResource();

    public void setResource(String var1);

    public List<Action> getActions();

    public Evidence getEvidence();

    public void setEvidence(Evidence var1);
}

