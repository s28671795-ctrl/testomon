/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface BaseID
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "BaseID";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "BaseID", "saml2");
    public static final String TYPE_LOCAL_NAME = "BaseIDAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "BaseIDAbstractType", "saml2");
    public static final String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";
    public static final String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";

    public String getNameQualifier();

    public void setNameQualifier(String var1);

    public String getSPNameQualifier();

    public void setSPNameQualifier(String var1);
}

