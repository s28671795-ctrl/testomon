/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Audience
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Audience";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Audience", "saml1");

    public String getUri();

    public void setUri(String var1);
}

