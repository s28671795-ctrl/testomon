/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Company
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Company";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Company", "md");
    public static final String TYPE_LOCAL_NAME = "CompanyType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "CompanyType", "md");

    public String getName();

    public void setName(String var1);
}

