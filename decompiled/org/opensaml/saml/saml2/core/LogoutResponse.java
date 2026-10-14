/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface LogoutResponse
extends StatusResponseType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "LogoutResponse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponse", "saml2p");
    public static final String TYPE_LOCAL_NAME = "LogoutResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponseType", "saml2p");
    public static final String USER_LOGOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:user";
    public static final String ADMIN_LOGOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:admin";
    public static final String GLOBAL_TIMEOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:global-timeout";
    public static final String SP_TIMEOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:sp-timeout";
}

