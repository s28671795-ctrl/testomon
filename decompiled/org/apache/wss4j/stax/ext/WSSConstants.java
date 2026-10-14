/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.ext;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;

public class WSSConstants
extends XMLSecurityConstants {
    public static final String TRANSPORT_SECURITY_ACTIVE = "transportSecurityActive";
    public static final String TIMESTAMP_PROCESSED = "TimestampProcessed";
    public static final String PROP_ALLOW_RSA15_KEYTRANSPORT_ALGORITHM = "secureProcessing.AllowRSA15KeyTransportAlgorithm";
    public static final String PROP_ALLOW_USERNAMETOKEN_NOPASSWORD = "secureProcessing.AllowUsernameTokenNoPassword";
    public static final String NS_WSSE10 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public static final String NS_WSSE11 = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";
    public static final String NS_WSU10 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    public static final String NS_SOAP11 = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String NS_SOAP12 = "http://www.w3.org/2003/05/soap-envelope";
    public static final String NS_WST = "http://schemas.xmlsoap.org/ws/2005/02/trust";
    public static final String NS_WST_05_12 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    public static final String NS_WSC_SCT = "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
    public static final String NS_SAML = "urn:oasis:names:tc:SAML:1.0:assertion";
    public static final String NS_SAML2 = "urn:oasis:names:tc:SAML:2.0:assertion";
    public static final String PREFIX_SOAPENV = "soap";
    public static final String TAG_SOAP_ENVELOPE_LN = "Envelope";
    public static final String TAG_SOAP_HEADER_LN = "Header";
    public static final String TAG_SOAP_BODY_LN = "Body";
    public static final QName TAG_SOAP11_ENVELOPE = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap");
    public static final QName TAG_SOAP11_HEADER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Header", "soap");
    public static final QName TAG_SOAP11_BODY = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body", "soap");
    public static final QName ATT_SOAP11_ACTOR = new QName("http://schemas.xmlsoap.org/soap/envelope/", "actor", "soap");
    public static final QName ATT_SOAP11_MUST_UNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "soap");
    public static final QName TAG_SOAP12_ENVELOPE = new QName("http://www.w3.org/2003/05/soap-envelope", "Envelope", "soap");
    public static final QName TAG_SOAP12_HEADER = new QName("http://www.w3.org/2003/05/soap-envelope", "Header", "soap");
    public static final QName TAG_SOAP12_BODY = new QName("http://www.w3.org/2003/05/soap-envelope", "Body", "soap");
    public static final QName ATT_SOAP12_ROLE = new QName("http://www.w3.org/2003/05/soap-envelope", "role", "soap");
    public static final QName ATT_SOAP12_MUST_UNDERSTAND = new QName("http://www.w3.org/2003/05/soap-envelope", "mustUnderstand", "soap");
    public static final String PREFIX_WSSE = "wsse";
    public static final String PREFIX_WSSE11 = "wsse11";
    public static final QName TAG_WSSE_SECURITY = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse");
    public static final QName TAG_WSSE_SECURITY_TOKEN_REFERENCE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReference", "wsse");
    public static final QName TAG_WSSE_REFERENCE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference", "wsse");
    public static final QName ATT_WSSE_USAGE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Usage", "wsse");
    public static final QName ATT_WSSE11_TOKEN_TYPE = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType", "wsse11");
    public static final QName TAG_WSSE_KEY_IDENTIFIER = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier", "wsse");
    public static final QName ATT_NULL_ENCODING_TYPE = new QName(null, "EncodingType");
    public static final QName ATT_NULL_VALUE_TYPE = new QName(null, "ValueType");
    public static final QName TAG_WSSE_BINARY_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken", "wsse");
    public static final String PREFIX_WSU = "wsu";
    public static final QName ATT_WSU_ID = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");
    public static final QName TAG_WSSE11_ENCRYPTED_HEADER = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader", "wsse11");
    public static final QName TAG_WSSE_TRANSFORMATION_PARAMETERS = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "TransformationParameters", "wsse");
    public static final QName TAG_WSU_TIMESTAMP = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp", "wsu");
    public static final QName TAG_WSU_CREATED = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created", "wsu");
    public static final QName TAG_WSU_EXPIRES = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires", "wsu");
    public static final String NS10_SOAPMESSAGE_SECURITY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0";
    public static final String NS11_SOAPMESSAGE_SECURITY = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1";
    public static final String NS_X509TOKEN_PROFILE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0";
    public static final String NS_X509_V3_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
    public static final String NS_X509_PKIPATH_V1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";
    public static final String NS_X509_SKI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier";
    public static final String NS_THUMBPRINT = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1";
    public static final String NS_ENCRYPTED_KEY_SHA1 = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1";
    public static final String SOAPMESSAGE_NS10_BASE64_ENCODING = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
    public static final QName TAG_WSSE_USERNAME_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken", "wsse");
    public static final QName TAG_WSSE_USERNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username", "wsse");
    public static final QName TAG_WSSE_PASSWORD = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password", "wsse");
    public static final QName TAG_WSSE_NONCE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Nonce", "wsse");
    public static final QName TAG_WSSE11_SALT = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Salt", "wsse11");
    public static final QName TAG_WSSE11_ITERATION = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Iteration", "wsse11");
    public static final String NS_USERNAMETOKEN_PROFILE11 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0";
    public static final String NS_PASSWORD_DIGEST = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
    public static final String NS_PASSWORD_TEXT = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
    public static final String NS_USERNAMETOKEN_PROFILE_USERNAME_TOKEN = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken";
    public static final QName TAG_WSSE11_SIG_CONF = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation", "wsse11");
    public static final QName ATT_NULL_VALUE = new QName(null, "Value");
    public static final String NS_C14N_EXCL = "http://www.w3.org/2001/10/xml-exc-c14n#";
    public static final String PREFIX_C14N_EXCL = "c14nEx";
    public static final QName TAG_WST_BINARY_SECRET = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "BinarySecret");
    public static final QName TAG_WST0512_BINARY_SECRET = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret");
    public static final String SOAPMESSAGE_NS10_STR_TRANSFORM = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform";
    public static final String SWA_ATTACHMENT_CONTENT_SIG_TRANS = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform";
    public static final String SWA_ATTACHMENT_COMPLETE_SIG_TRANS = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform";
    public static final String SWA_ATTACHMENT_CIPHERTEXT_TRANS = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform";
    public static final String SWA_ATTACHMENT_ENCRYPTED_DATA_TYPE_CONTENT_ONLY = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Only";
    public static final String SWA_ATTACHMENT_ENCRYPTED_DATA_TYPE_COMPLETE = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete";
    public static final QName TAG_SAML_ASSERTION = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
    public static final QName TAG_SAML2_ASSERTION = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
    public static final QName TAG_SAML2_ENCRYPTED_ASSERTION = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion");
    public static final String NS_SAML10_TOKEN_PROFILE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0";
    public static final String NS_SAML11_TOKEN_PROFILE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1";
    public static final String NS_SAML10_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
    public static final String NS_SAML20_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
    public static final String NS_SAML11_TOKEN_PROFILE_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1";
    public static final String NS_SAML20_TOKEN_PROFILE_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0";
    public static final String NS_KERBEROS11_TOKEN_PROFILE = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#";
    public static final String NS_GSS_KERBEROS5_AP_REQ = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ";
    public static final String NS_GSS_KERBEROS5_AP_REQ1510 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510";
    public static final String NS_GSS_KERBEROS5_AP_REQ4120 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ4120";
    public static final String NS_KERBEROS5_AP_REQ = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ";
    public static final String NS_KERBEROS5_AP_REQ_SHA1 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1";
    public static final String NS_KERBEROS5_AP_REQ1510 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ1510";
    public static final String NS_KERBEROS5_AP_REQ4120 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ4120";
    public static final QName ATT_NULL_ASSERTION_ID = new QName(null, "AssertionID");
    public static final QName ATT_NULL_ID = new QName(null, "ID");
    public static final String NS_WSC_05_02 = "http://schemas.xmlsoap.org/ws/2005/02/sc";
    public static final String NS_WSC_05_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
    public static final String PREFIX_WSC = "wsc";
    public static final QName TAG_WSC0502_SCT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken", "wsc");
    public static final QName TAG_WSC0512_SCT = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken", "wsc");
    public static final QName TAG_WSC0502_IDENTIFIER = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Identifier", "wsc");
    public static final QName TAG_WSC0512_IDENTIFIER = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Identifier", "wsc");
    public static final QName TAG_WSC0502_DKT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "DerivedKeyToken", "wsc");
    public static final QName TAG_WSC0512_DKT = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "DerivedKeyToken", "wsc");
    public static final QName TAG_WSC0502_PROPERTIES = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Properties", "wsc");
    public static final QName TAG_WSC0512_PROPERTIES = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Properties", "wsc");
    public static final QName TAG_WSC0502_LENGTH = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Length", "wsc");
    public static final QName TAG_WSC0512_LENGTH = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Length", "wsc");
    public static final QName TAG_WSC0502_GENERATION = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Generation", "wsc");
    public static final QName TAG_WSC0512_GENERATION = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Generation", "wsc");
    public static final QName TAG_WSC0502_OFFSET = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Offset", "wsc");
    public static final QName TAG_WSC0512_OFFSET = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Offset", "wsc");
    public static final QName TAG_WSC0502_LABEL = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Label", "wsc");
    public static final QName TAG_WSC0512_LABEL = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Label", "wsc");
    public static final QName TAG_WSC0502_NONCE = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Nonce", "wsc");
    public static final QName TAG_WSC0512_NONCE = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Nonce", "wsc");
    public static final String P_SHA_1 = "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
    public static final String P_SHA_1_2005_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk/p_sha1";
    public static final String WS_SEC_CONV_DEFAULT_LABEL = "WS-SecureConversation";
    public static final String NS_WSS_ENC_KEY_VALUE_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
    public static final String PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS = "PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS";
    public static final String PROP_USE_THIS_TOKEN_ID_FOR_DERIVED_KEY = "PROP_USE_THIS_TOKEN_ID_FOR_DERIVED_KEY";
    public static final String PROP_USE_THIS_TOKEN_ID_FOR_SECURITYCONTEXTTOKEN = "PROP_USE_THIS_TOKEN_ID_FOR_SECURITYCONTEXTTOKEN";
    public static final String PROP_USE_THIS_TOKEN_ID_FOR_CUSTOM_TOKEN = "PROP_USE_THIS_TOKEN_ID_FOR_CUSTOM_TOKEN";
    public static final String PROP_TIMESTAMP_SECURITYEVENT = "PROP_TIMESTAMP";
    public static final String PROP_ENCRYPTED_DATA_REFS = "PROP_ENCRYPTED_DATA_REFS";
    public static final XMLSecurityConstants.Action TIMESTAMP = new XMLSecurityConstants.Action("Timestamp");
    public static final XMLSecurityConstants.Action USERNAMETOKEN = new XMLSecurityConstants.Action("UsernameToken");
    public static final XMLSecurityConstants.Action USERNAMETOKEN_SIGNED = new XMLSecurityConstants.Action("UsernameTokenSignature");
    public static final XMLSecurityConstants.Action SIGNATURE_CONFIRMATION = new XMLSecurityConstants.Action("SignatureConfirmation");
    public static final XMLSecurityConstants.Action SIGNATURE_WITH_DERIVED_KEY = new XMLSecurityConstants.Action("SignatureWithDerivedKey");
    public static final XMLSecurityConstants.Action ENCRYPT_WITH_DERIVED_KEY = new XMLSecurityConstants.Action("EncryptWithDerivedKey");
    public static final XMLSecurityConstants.Action SAML_TOKEN_SIGNED = new XMLSecurityConstants.Action("SAMLTokenSigned");
    public static final XMLSecurityConstants.Action SAML_TOKEN_UNSIGNED = new XMLSecurityConstants.Action("SAMLTokenUnsigned");
    public static final XMLSecurityConstants.Action SIGNATURE_WITH_KERBEROS_TOKEN = new XMLSecurityConstants.Action("SignatureWithKerberosToken");
    public static final XMLSecurityConstants.Action ENCRYPT_WITH_KERBEROS_TOKEN = new XMLSecurityConstants.Action("EncryptWithKerberosToken");
    public static final XMLSecurityConstants.Action KERBEROS_TOKEN = new XMLSecurityConstants.Action("KerberosToken");
    public static final XMLSecurityConstants.Action CUSTOM_TOKEN = new XMLSecurityConstants.Action("CustomToken");
    public static final XMLSecurityConstants.AlgorithmUsage COMP_KEY = new XMLSecurityConstants.AlgorithmUsage("Comp_Key");
    public static final XMLSecurityConstants.AlgorithmUsage ENC_KD = new XMLSecurityConstants.AlgorithmUsage("ENC_KD");
    public static final XMLSecurityConstants.AlgorithmUsage SIG_KD = new XMLSecurityConstants.AlgorithmUsage("SIG_KD");
    public static final XMLSecurityConstants.AlgorithmUsage SOAP_NORM = new XMLSecurityConstants.AlgorithmUsage("Soap_Norm");
    public static final XMLSecurityConstants.AlgorithmUsage STR_TRANS = new XMLSecurityConstants.AlgorithmUsage("STR_Trans");
    public static final XMLSecurityConstants.AlgorithmUsage XPATH = new XMLSecurityConstants.AlgorithmUsage("XPath");
    public static final List<QName> SOAP_11_BODY_PATH = new ArrayList<QName>(2);
    public static final List<QName> SOAP_11_HEADER_PATH = new ArrayList<QName>(2);
    public static final List<QName> WSSE_SECURITY_HEADER_PATH = new ArrayList<QName>(3);

    protected WSSConstants() {
    }

    static {
        SOAP_11_BODY_PATH.add(TAG_SOAP11_ENVELOPE);
        SOAP_11_BODY_PATH.add(TAG_SOAP11_BODY);
        SOAP_11_HEADER_PATH.add(TAG_SOAP11_ENVELOPE);
        SOAP_11_HEADER_PATH.add(TAG_SOAP11_HEADER);
        WSSE_SECURITY_HEADER_PATH.addAll(SOAP_11_HEADER_PATH);
        WSSE_SECURITY_HEADER_PATH.add(TAG_WSSE_SECURITY);
    }

    public static enum UsernameTokenPasswordType {
        PASSWORD_NONE(null),
        PASSWORD_TEXT("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText"),
        PASSWORD_DIGEST("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");

        private final String namespace;
        private static final Map<String, UsernameTokenPasswordType> LOOKUP;

        private UsernameTokenPasswordType(String namespace) {
            this.namespace = namespace;
        }

        public String getNamespace() {
            return this.namespace;
        }

        public static UsernameTokenPasswordType getUsernameTokenPasswordType(String namespace) {
            return LOOKUP.get(namespace);
        }

        static {
            LOOKUP = new HashMap<String, UsernameTokenPasswordType>();
            for (UsernameTokenPasswordType u : EnumSet.allOf(UsernameTokenPasswordType.class)) {
                LOOKUP.put(u.getNamespace(), u);
            }
        }
    }

    public static enum DerivedKeyTokenReference {
        DirectReference,
        EncryptedKey,
        SecurityContextToken;

    }
}

