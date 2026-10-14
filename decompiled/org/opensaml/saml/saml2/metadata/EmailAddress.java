/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface EmailAddress
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EmailAddress";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddress", "md");
    public static final String TYPE_LOCAL_NAME = "EmailAddressType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddressType", "md");

    public String getAddress();

    public void setAddress(String var1);
}

