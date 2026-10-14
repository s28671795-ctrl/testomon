/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusMessage
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusMessage";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusMessage", "saml1p");

    public String getMessage();

    public void setMessage(String var1);
}

