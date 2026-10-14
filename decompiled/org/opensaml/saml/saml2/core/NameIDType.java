/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

public interface NameIDType {
    public static final String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";
    public static final String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";
    public static final String FORMAT_ATTRIB_NAME = "Format";
    public static final String SPPROVIDED_ID_ATTRIB_NAME = "SPProvidedID";
    public static final String UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
    public static final String EMAIL = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
    public static final String X509_SUBJECT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
    public static final String WIN_DOMAIN_QUALIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";
    public static final String KERBEROS = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";
    public static final String ENTITY = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";
    public static final String PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
    public static final String TRANSIENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
    public static final String ENCRYPTED = "urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted";

    public String getValue();

    public void setValue(String var1);

    public String getNameQualifier();

    public void setNameQualifier(String var1);

    public String getSPNameQualifier();

    public void setSPNameQualifier(String var1);

    public String getFormat();

    public void setFormat(String var1);

    public String getSPProvidedID();

    public void setSPProvidedID(String var1);
}

