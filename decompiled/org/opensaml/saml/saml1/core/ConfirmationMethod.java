/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface ConfirmationMethod
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ConfirmationMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod", "saml1");
    public static final String TYPE_LOCAL_NAME = "ConfirmationMethodType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethodType", "saml1");
    public static final String METHOD_BEARER = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
    public static final String METHOD_ARTIFACT = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
    @Deprecated
    public static final String METHOD_ARTIFACT_DEPRECATED = "urn:oasis:names:tc:SAML:1.0:cm:artifact-01";
    public static final String METHOD_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
    public static final String METHOD_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";

    public String getConfirmationMethod();

    public void setConfirmationMethod(String var1);
}

