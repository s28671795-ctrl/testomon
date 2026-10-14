/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Query;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.SubjectQuery;

public interface Request
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Request", "saml1p");
    public static final String TYPE_LOCAL_NAME = "RequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestType", "saml1p");

    public Query getQuery();

    public SubjectQuery getSubjectQuery();

    public AuthenticationQuery getAuthenticationQuery();

    public AttributeQuery getAttributeQuery();

    public AuthorizationDecisionQuery getAuthorizationDecisionQuery();

    public void setQuery(Query var1);

    public List<AssertionIDReference> getAssertionIDReferences();

    public List<AssertionArtifact> getAssertionArtifacts();
}

