/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public interface AuthnContext
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContext";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContext", "saml2");
    public static final String TYPE_LOCAL_NAME = "AuthnContextType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextType", "saml2");
    public static final String IP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocol";
    public static final String IP_PASSWORD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocolPassword";
    public static final String KERBEROS_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos";
    public static final String MOFU_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorUnregistered";
    public static final String MTFU_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorUnregistered";
    public static final String MOFC_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract";
    public static final String MTFC_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorContract";
    public static final String PASSWORD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
    public static final String PPT_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
    public static final String PREVIOUS_SESSION_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession";
    public static final String X509_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:X509";
    public static final String PGP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PGP";
    public static final String SPKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI";
    public static final String XML_DSIG_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:XMLDSig";
    public static final String SMARTCARD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard";
    public static final String SMARTCARD_PKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SmartcardPKI";
    public static final String SOFTWARE_PKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SoftwarePKI";
    public static final String TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Telephony";
    public static final String NOMAD_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:NomadTelephony";
    public static final String PERSONAL_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PersonalTelephony";
    public static final String AUTHENTICATED_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:AuthenticatedTelephony";
    public static final String SRP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SecureRemotePassword";
    public static final String TLS_CLIENT_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient";
    public static final String TIME_SYNC_TOKEN_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken";
    public static final String UNSPECIFIED_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";

    public AuthnContextClassRef getAuthnContextClassRef();

    public void setAuthnContextClassRef(AuthnContextClassRef var1);

    public AuthnContextDecl getAuthContextDecl();

    public void setAuthnContextDecl(AuthnContextDecl var1);

    public AuthnContextDeclRef getAuthnContextDeclRef();

    public void setAuthnContextDeclRef(AuthnContextDeclRef var1);

    public List<AuthenticatingAuthority> getAuthenticatingAuthorities();
}

