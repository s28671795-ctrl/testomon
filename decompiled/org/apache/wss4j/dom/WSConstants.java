/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSS4JConstants;

public final class WSConstants
extends WSS4JConstants {
    public static final QName BINARY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
    public static final QName USERNAME_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
    public static final QName TIMESTAMP = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp");
    public static final QName SIGNATURE_CONFIRMATION = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation");
    public static final QName SIGNATURE = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
    public static final QName ENCRYPTED_KEY = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
    public static final QName ENCRYPTED_DATA = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
    public static final QName REFERENCE_LIST = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceList");
    public static final QName SAML_TOKEN = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
    public static final QName SAML2_TOKEN = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
    public static final QName ENCRYPTED_ASSERTION = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion");
    public static final QName DERIVED_KEY_TOKEN_05_02 = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "DerivedKeyToken");
    public static final QName SECURITY_CONTEXT_TOKEN_05_02 = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken");
    public static final QName DERIVED_KEY_TOKEN_05_12 = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "DerivedKeyToken");
    public static final QName SECURITY_CONTEXT_TOKEN_05_12 = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken");
    public static final QName UNSUPPORTED_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedSecurityToken");
    public static final QName UNSUPPORTED_ALGORITHM = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedAlgorithm");
    public static final QName INVALID_SECURITY = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurity");
    public static final QName INVALID_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurityToken");
    public static final QName FAILED_AUTHENTICATION = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedAuthentication");
    public static final QName FAILED_CHECK = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedCheck");
    public static final QName SECURITY_TOKEN_UNAVAILABLE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenUnavailable");
    public static final QName MESSAGE_EXPIRED = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "MessageExpired");
    public static final int BST_DIRECT_REFERENCE = 1;
    public static final int ISSUER_SERIAL = 2;
    public static final int X509_KEY_IDENTIFIER = 3;
    public static final int SKI_KEY_IDENTIFIER = 4;
    @Deprecated
    public static final int EMBEDDED_KEYNAME = 5;
    @Deprecated
    public static final int EMBED_SECURITY_TOKEN_REF = 6;
    public static final int UT_SIGNING = 7;
    public static final int THUMBPRINT_IDENTIFIER = 8;
    public static final int CUSTOM_SYMM_SIGNING = 9;
    public static final int ENCRYPTED_KEY_SHA1_IDENTIFIER = 10;
    public static final int CUSTOM_SYMM_SIGNING_DIRECT = 11;
    public static final int CUSTOM_KEY_IDENTIFIER = 12;
    public static final int KEY_VALUE = 13;
    public static final int ENDPOINT_KEY_IDENTIFIER = 14;
    public static final int NO_SECURITY = 0;
    public static final int UT = 1;
    public static final int SIGN = 2;
    public static final int ENCR = 4;
    public static final int ST_UNSIGNED = 8;
    public static final int ST_SIGNED = 16;
    public static final int TS = 32;
    public static final int UT_SIGN = 64;
    public static final int SC = 128;
    public static final int NO_SERIALIZE = 256;
    public static final int SERIALIZE = 512;
    public static final int SCT = 1024;
    public static final int DKT = 2048;
    public static final int BST = 4096;
    public static final int UT_NOPASSWORD = 8192;
    public static final int CUSTOM_TOKEN = 16384;
    public static final int DKT_SIGN = 32768;
    public static final int DKT_ENCR = 65536;

    private WSConstants() {
    }
}

