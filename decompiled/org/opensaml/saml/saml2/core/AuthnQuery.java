/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.SubjectQuery;

public interface AuthnQuery
extends SubjectQuery {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQuery", "saml2p");
    public static final String TYPE_LOCAL_NAME = "AuthnQueryType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQueryType", "saml2p");
    public static final String SESSION_INDEX_ATTRIB_NAME = "SessionIndex";

    public String getSessionIndex();

    public void setSessionIndex(String var1);

    public RequestedAuthnContext getRequestedAuthnContext();

    public void setRequestedAuthnContext(RequestedAuthnContext var1);
}

