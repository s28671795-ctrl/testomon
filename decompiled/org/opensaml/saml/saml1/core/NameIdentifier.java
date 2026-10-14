/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NameIdentifier
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NameIdentifier";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier", "saml1");
    public static final String TYPE_LOCAL_NAME = "NameIdentifierType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifierType", "saml1");
    public static final String NAMEQUALIFIER_ATTRIB_NAME = "NameQualifier";
    public static final String FORMAT_ATTRIB_NAME = "Format";
    public static final String UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
    public static final String EMAIL = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
    public static final String X509_SUBJECT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
    public static final String WIN_DOMAIN_QUALIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";

    public String getNameQualifier();

    public void setNameQualifier(String var1);

    public String getFormat();

    public void setFormat(String var1);

    @Deprecated
    public String getNameIdentifier();

    @Deprecated
    public void setNameIdentifier(String var1);

    public String getValue();

    public void setValue(String var1);
}

