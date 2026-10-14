/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.opensaml.saml.saml1.core.SubjectStatement;

public interface AuthenticationStatement
extends SAMLObject,
SubjectStatement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthenticationStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement", "saml1");
    public static final String TYPE_LOCAL_NAME = "AuthenticationStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatementType", "saml1");
    public static final String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod";
    public static final String AUTHENTICATIONINSTANT_ATTRIB_NAME = "AuthenticationInstant";
    public static final String KERBEROS_AUTHN_METHOD = "urn:ietf:rfc:1510";
    public static final String HARDWARE_TOKEN_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:HardwareToken";
    public static final String PASSWORD_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:password";
    public static final String X509_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:X509-PKI";
    public static final String PGP_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:PGP";
    public static final String SPKI_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:SPKI";
    public static final String XKMS_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:XKMS";
    public static final String XML_DSIG_AUTHN_METHOD = "urn:ietf:rfc:3075";
    public static final String SRP_AUTHN_METHOD = "urn:ietf:rfc:2945";
    public static final String TLS_CLIENT_AUTHN_METHOD = "urn:ietf:rfc:2246";
    public static final String UNSPECIFIED_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:unspecified";

    public String getAuthenticationMethod();

    public void setAuthenticationMethod(String var1);

    public DateTime getAuthenticationInstant();

    public void setAuthenticationInstant(DateTime var1);

    public SubjectLocality getSubjectLocality();

    public void setSubjectLocality(SubjectLocality var1);

    public List<AuthorityBinding> getAuthorityBindings();
}

