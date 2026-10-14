/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.SubjectQuery;

public interface AuthenticationQuery
extends SubjectQuery {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthenticationQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery", "saml1p");
    public static final String TYPE_LOCAL_NAME = "AuthenticationQueryType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQueryType", "saml1p");
    public static final String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod";

    public String getAuthenticationMethod();

    public void setAuthenticationMethod(String var1);
}

