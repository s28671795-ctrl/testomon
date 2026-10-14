/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;

public interface NameIDPolicy
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDPolicy";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicy", "saml2p");
    public static final String TYPE_LOCAL_NAME = "NameIDPolicyType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicyType", "saml2p");
    public static final String FORMAT_ATTRIB_NAME = "Format";
    public static final String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";
    public static final String ALLOW_CREATE_ATTRIB_NAME = "AllowCreate";

    public String getFormat();

    public void setFormat(String var1);

    public String getSPNameQualifier();

    public void setSPNameQualifier(String var1);

    public Boolean getAllowCreate();

    public XSBooleanValue getAllowCreateXSBoolean();

    public void setAllowCreate(Boolean var1);

    public void setAllowCreate(XSBooleanValue var1);
}

