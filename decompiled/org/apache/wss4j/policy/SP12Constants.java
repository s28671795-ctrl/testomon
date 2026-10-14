/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy;

import javax.xml.namespace.QName;
import org.apache.wss4j.policy.SPConstants;

public class SP12Constants
extends SPConstants {
    private static SP12Constants sp12Constants;
    public static final String SP_NS = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702";
    public static final String SP_PREFIX = "sp";
    public static final String WST_NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    public static final String WST_PREFIX = "wst";
    public static final QName INCLUDE_TOKEN;
    public static final String INCLUDE_NEVER = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Never";
    public static final String INCLUDE_ONCE = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Once";
    public static final String INCLUDE_ALWAYS_TO_RECIPIENT = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient";
    public static final String INCLUDE_ALWAYS_TO_INITIATOR = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToInitiator";
    public static final String INCLUDE_ALWAYS = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Always";
    public static final QName TRUST_13;
    public static final QName REQUIRE_CLIENT_CERTIFICATE;
    public static final QName HTTP_BASIC_AUTHENTICATION;
    public static final QName HTTP_DIGEST_AUTHENTICATION;
    public static final QName TRANSPORT_BINDING;
    public static final QName ALGORITHM_SUITE;
    public static final QName LAYOUT;
    public static final QName STRICT;
    public static final QName LAX;
    public static final QName LAXTSFIRST;
    public static final QName LAXTSLAST;
    public static final QName INCLUDE_TIMESTAMP;
    public static final QName ENCRYPT_BEFORE_SIGNING;
    public static final QName SIGN_BEFORE_ENCRYPTING;
    public static final QName ONLY_SIGN_ENTIRE_HEADERS_AND_BODY;
    public static final QName TRANSPORT_TOKEN;
    public static final QName HTTPS_TOKEN;
    public static final QName KERBEROS_TOKEN;
    public static final QName SPNEGO_CONTEXT_TOKEN;
    public static final QName SECURITY_CONTEXT_TOKEN;
    public static final QName SECURE_CONVERSATION_TOKEN;
    public static final QName MUST_NOT_SEND_CANCEL;
    public static final QName MUST_NOT_SEND_AMEND;
    public static final QName MUST_NOT_SEND_RENEW;
    public static final QName SAML_TOKEN;
    public static final QName REL_TOKEN;
    public static final QName KEY_VALUE_TOKEN;
    public static final QName RSA_KEY_VALUE;
    public static final QName SIGNATURE_TOKEN;
    public static final QName SIGNED_PARTS;
    public static final QName ENCRYPTED_PARTS;
    public static final QName SIGNED_ELEMENTS;
    public static final QName XPATH_EXPR;
    public static final QName ENCRYPTED_ELEMENTS;
    public static final QName REQUIRED_ELEMENTS;
    public static final QName REQUIRED_PARTS;
    public static final QName CONTENT_ENCRYPTED_ELEMENTS;
    public static final QName USERNAME_TOKEN;
    public static final QName WSS_USERNAME_TOKEN10;
    public static final QName WSS_USERNAME_TOKEN11;
    public static final QName ENCRYPTION_TOKEN;
    public static final QName X509_TOKEN;
    public static final QName WSS_X509_V1_TOKEN_10;
    public static final QName WSS_X509_V3_TOKEN_10;
    public static final QName WSS_X509_PKCS7_TOKEN_10;
    public static final QName WSS_X509_PKI_PATH_V1_TOKEN_10;
    public static final QName WSS_X509_V1_TOKEN_11;
    public static final QName WSS_X509_V3_TOKEN_11;
    public static final QName WSS_X509_PKCS7_TOKEN_11;
    public static final QName WSS_X509_PKI_PATH_V1_TOKEN_11;
    public static final QName ISSUED_TOKEN;
    public static final QName SUPPORTING_TOKENS;
    public static final QName SIGNED_SUPPORTING_TOKENS;
    public static final QName ENDORSING_SUPPORTING_TOKENS;
    public static final QName SIGNED_ENDORSING_SUPPORTING_TOKENS;
    public static final QName ENCRYPTED_SUPPORTING_TOKENS;
    public static final QName SIGNED_ENCRYPTED_SUPPORTING_TOKENS;
    public static final QName ENDORSING_ENCRYPTED_SUPPORTING_TOKENS;
    public static final QName SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS;
    public static final QName PROTECTION_TOKEN;
    public static final QName ASYMMETRIC_BINDING;
    public static final QName SYMMETRIC_BINDING;
    public static final QName INITIATOR_TOKEN;
    public static final QName INITIATOR_SIGNATURE_TOKEN;
    public static final QName INITIATOR_ENCRYPTION_TOKEN;
    public static final QName RECIPIENT_TOKEN;
    public static final QName RECIPIENT_SIGNATURE_TOKEN;
    public static final QName RECIPIENT_ENCRYPTION_TOKEN;
    public static final QName ENCRYPT_SIGNATURE;
    public static final QName PROTECT_TOKENS;
    public static final QName REQUIRE_KEY_IDENTIFIER_REFERENCE;
    public static final QName REQUIRE_ISSUER_SERIAL_REFERENCE;
    public static final QName REQUIRE_EMBEDDED_TOKEN_REFERENCE;
    public static final QName REQUIRE_THUMBPRINT_REFERENCE;
    public static final QName MUST_SUPPORT_REF_KEY_IDENTIFIER;
    public static final QName MUST_SUPPORT_REF_ISSUER_SERIAL;
    public static final QName MUST_SUPPORT_REF_EXTERNAL_URI;
    public static final QName MUST_SUPPORT_REF_EMBEDDED_TOKEN;
    public static final QName MUST_SUPPORT_REF_THUMBPRINT;
    public static final QName MUST_SUPPORT_REF_ENCRYPTED_KEY;
    public static final QName WSS10;
    public static final QName WSS11;
    public static final QName REQUIRE_SIGNATURE_CONFIRMATION;
    public static final QName MUST_SUPPORT_CLIENT_CHALLENGE;
    public static final QName MUST_SUPPORT_SERVER_CHALLENGE;
    public static final QName REQUIRE_CLIENT_ENTROPY;
    public static final QName REQUIRE_SERVER_ENTROPY;
    public static final QName MUST_SUPPORT_ISSUED_TOKENS;
    public static final QName REQUIRE_REQUEST_SECURITY_TOKEN_COLLECTION;
    public static final QName REQUIRE_APPLIES_TO;
    public static final QName ISSUER;
    public static final QName ISSUER_NAME;
    public static final QName CLAIMS;
    public static final QName REQUIRE_DERIVED_KEYS;
    public static final QName REQUIRE_IMPLIED_DERIVED_KEYS;
    public static final QName REQUIRE_EXPLICIT_DERIVED_KEYS;
    public static final QName REQUIRE_EXTERNAL_URI_REFERENCE;
    public static final QName SC13_SECURITY_CONTEXT_TOKEN;
    public static final QName REQUIRE_EXTERNAL_REFERNCE;
    public static final QName REQUIRE_INTERNAL_REFERENCE;
    public static final QName REQUEST_SECURITY_TOKEN_TEMPLATE;
    public static final QName BOOTSTRAP_POLICY;
    public static final QName XPATH;
    public static final QName NO_PASSWORD;
    public static final QName HASH_PASSWORD;
    public static final QName HEADER;
    public static final QName BODY;
    public static final QName ATTACHMENTS;

    protected SP12Constants() {
    }

    public static synchronized SP12Constants getInstance() {
        if (sp12Constants == null) {
            sp12Constants = new SP12Constants();
        }
        return sp12Constants;
    }

    @Override
    public SPConstants.IncludeTokenType getInclusionFromAttributeValue(String value) {
        if (value == null || "".equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS;
        }
        if (INCLUDE_ALWAYS.equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS;
        }
        if (INCLUDE_ALWAYS_TO_RECIPIENT.equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT;
        }
        if (INCLUDE_ALWAYS_TO_INITIATOR.equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR;
        }
        if (INCLUDE_NEVER.equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER;
        }
        if (INCLUDE_ONCE.equals(value)) {
            return SPConstants.IncludeTokenType.INCLUDE_TOKEN_ONCE;
        }
        throw new IllegalArgumentException("Invalid Policy");
    }

    @Override
    public String getAttributeValueFromInclusion(SPConstants.IncludeTokenType value) {
        switch (value) {
            case INCLUDE_TOKEN_ALWAYS: {
                return INCLUDE_ALWAYS;
            }
            case INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT: {
                return INCLUDE_ALWAYS_TO_RECIPIENT;
            }
            case INCLUDE_TOKEN_ALWAYS_TO_INITIATOR: {
                return INCLUDE_ALWAYS_TO_INITIATOR;
            }
            case INCLUDE_TOKEN_NEVER: {
                return INCLUDE_NEVER;
            }
            case INCLUDE_TOKEN_ONCE: {
                return INCLUDE_ONCE;
            }
        }
        throw new IllegalArgumentException("Invalid Policy");
    }

    @Override
    public QName getIncludeToken() {
        return INCLUDE_TOKEN;
    }

    @Override
    public QName getIssuer() {
        return ISSUER;
    }

    @Override
    public QName getIssuerName() {
        return ISSUER_NAME;
    }

    @Override
    public QName getClaims() {
        return CLAIMS;
    }

    @Override
    public QName getX509Token() {
        return X509_TOKEN;
    }

    @Override
    public QName getRequireIssuerSerialReference() {
        return REQUIRE_ISSUER_SERIAL_REFERENCE;
    }

    @Override
    public QName getRequireEmbeddedTokenReference() {
        return REQUIRE_EMBEDDED_TOKEN_REFERENCE;
    }

    @Override
    public QName getRequireThumbprintReference() {
        return REQUIRE_THUMBPRINT_REFERENCE;
    }

    @Override
    public QName getHttpsToken() {
        return HTTPS_TOKEN;
    }

    @Override
    public QName getUsernameToken() {
        return USERNAME_TOKEN;
    }

    @Override
    public QName getCreated() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getNonce() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getIssuedToken() {
        return ISSUED_TOKEN;
    }

    @Override
    public QName getRequireExternalReference() {
        return REQUIRE_EXTERNAL_REFERNCE;
    }

    @Override
    public QName getRequireInternalReference() {
        return REQUIRE_INTERNAL_REFERENCE;
    }

    @Override
    public QName getRequestSecurityTokenTemplate() {
        return REQUEST_SECURITY_TOKEN_TEMPLATE;
    }

    @Override
    public QName getKerberosToken() {
        return KERBEROS_TOKEN;
    }

    @Override
    public QName getSpnegoContextToken() {
        return SPNEGO_CONTEXT_TOKEN;
    }

    @Override
    public QName getSecurityContextToken() {
        return SECURITY_CONTEXT_TOKEN;
    }

    @Override
    public QName getRequireExternalUriReference() {
        return REQUIRE_EXTERNAL_URI_REFERENCE;
    }

    @Override
    public QName getSc13SecurityContextToken() {
        return SC13_SECURITY_CONTEXT_TOKEN;
    }

    @Override
    public QName getSc10SecurityContextToken() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getSecureConversationToken() {
        return SECURE_CONVERSATION_TOKEN;
    }

    @Override
    public QName getMustNotSendCancel() {
        return MUST_NOT_SEND_CANCEL;
    }

    @Override
    public QName getMustNotSendAmend() {
        return MUST_NOT_SEND_AMEND;
    }

    @Override
    public QName getMustNotSendRenew() {
        return MUST_NOT_SEND_RENEW;
    }

    @Override
    public QName getBootstrapPolicy() {
        return BOOTSTRAP_POLICY;
    }

    @Override
    public QName getSamlToken() {
        return SAML_TOKEN;
    }

    @Override
    public QName getRelToken() {
        return REL_TOKEN;
    }

    @Override
    public QName getRequireKeyIdentifierReference() {
        return REQUIRE_KEY_IDENTIFIER_REFERENCE;
    }

    @Override
    public QName getKeyValueToken() {
        return KEY_VALUE_TOKEN;
    }

    @Override
    public QName getRsaKeyValue() {
        return RSA_KEY_VALUE;
    }

    @Override
    public QName getSignedParts() {
        return SIGNED_PARTS;
    }

    @Override
    public QName getSignedElements() {
        return SIGNED_ELEMENTS;
    }

    @Override
    public QName getXPathExpression() {
        return XPATH_EXPR;
    }

    @Override
    public QName getXPath2Expression() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getEncryptedParts() {
        return ENCRYPTED_PARTS;
    }

    @Override
    public QName getEncryptedElements() {
        return ENCRYPTED_ELEMENTS;
    }

    @Override
    public QName getContentEncryptedElements() {
        return CONTENT_ENCRYPTED_ELEMENTS;
    }

    @Override
    public QName getRequiredElements() {
        return REQUIRED_ELEMENTS;
    }

    @Override
    public QName getRequiredParts() {
        return REQUIRED_PARTS;
    }

    @Override
    public QName getAlgorithmSuite() {
        return ALGORITHM_SUITE;
    }

    @Override
    public QName getLayout() {
        return LAYOUT;
    }

    @Override
    public QName getBody() {
        return BODY;
    }

    @Override
    public QName getAttachments() {
        return ATTACHMENTS;
    }

    @Override
    public QName getContentSignatureTransform() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getAttachmentCompleteSignatureTransform() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getHeader() {
        return HEADER;
    }

    @Override
    public QName getEncryptSignature() {
        return ENCRYPT_SIGNATURE;
    }

    @Override
    public QName getProtectTokens() {
        return PROTECT_TOKENS;
    }

    @Override
    public QName getOnlySignEntireHeadersAndBody() {
        return ONLY_SIGN_ENTIRE_HEADERS_AND_BODY;
    }

    @Override
    public QName getTransportBinding() {
        return TRANSPORT_BINDING;
    }

    @Override
    public QName getSymmetricBinding() {
        return SYMMETRIC_BINDING;
    }

    @Override
    public QName getAsymmetricBinding() {
        return ASYMMETRIC_BINDING;
    }

    @Override
    public QName getEncryptionToken() {
        return ENCRYPTION_TOKEN;
    }

    @Override
    public QName getSignatureToken() {
        return SIGNATURE_TOKEN;
    }

    @Override
    public QName getProtectionToken() {
        return PROTECTION_TOKEN;
    }

    @Override
    public QName getTransportToken() {
        return TRANSPORT_TOKEN;
    }

    @Override
    public QName getInitiatorToken() {
        return INITIATOR_TOKEN;
    }

    @Override
    public QName getInitiatorSignatureToken() {
        return INITIATOR_SIGNATURE_TOKEN;
    }

    @Override
    public QName getInitiatorEncryptionToken() {
        return INITIATOR_ENCRYPTION_TOKEN;
    }

    @Override
    public QName getRecipientToken() {
        return RECIPIENT_TOKEN;
    }

    @Override
    public QName getRecipientSignatureToken() {
        return RECIPIENT_SIGNATURE_TOKEN;
    }

    @Override
    public QName getRecipientEncryptionToken() {
        return RECIPIENT_ENCRYPTION_TOKEN;
    }

    @Override
    public QName getTrust10() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getTrust13() {
        return TRUST_13;
    }

    @Override
    public QName getScopePolicy15() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getMustSupportClientChallenge() {
        return MUST_SUPPORT_CLIENT_CHALLENGE;
    }

    @Override
    public QName getMustSupportServerChallenge() {
        return MUST_SUPPORT_SERVER_CHALLENGE;
    }

    @Override
    public QName getRequireClientEntropy() {
        return REQUIRE_CLIENT_ENTROPY;
    }

    @Override
    public QName getRequireServerEntropy() {
        return REQUIRE_SERVER_ENTROPY;
    }

    @Override
    public QName getMustSupportIssuedTokens() {
        return MUST_SUPPORT_ISSUED_TOKENS;
    }

    @Override
    public QName getRequireRequestSecurityTokenCollection() {
        return REQUIRE_REQUEST_SECURITY_TOKEN_COLLECTION;
    }

    @Override
    public QName getRequireAppliesTo() {
        return REQUIRE_APPLIES_TO;
    }

    @Override
    public QName getMustSupportInteractiveChallenge() {
        return EMPTY_QNAME;
    }

    @Override
    public QName getWss10() {
        return WSS10;
    }

    @Override
    public QName getMustSupportRefKeyIdentifier() {
        return MUST_SUPPORT_REF_KEY_IDENTIFIER;
    }

    @Override
    public QName getMustSupportRefIssuerSerial() {
        return MUST_SUPPORT_REF_ISSUER_SERIAL;
    }

    @Override
    public QName getMustSupportRefExternalUri() {
        return MUST_SUPPORT_REF_EXTERNAL_URI;
    }

    @Override
    public QName getMustSupportRefEmbeddedToken() {
        return MUST_SUPPORT_REF_EMBEDDED_TOKEN;
    }

    @Override
    public QName getWss11() {
        return WSS11;
    }

    @Override
    public QName getMustSupportRefThumbprint() {
        return MUST_SUPPORT_REF_THUMBPRINT;
    }

    @Override
    public QName getMustSupportRefEncryptedKey() {
        return MUST_SUPPORT_REF_ENCRYPTED_KEY;
    }

    @Override
    public QName getRequireSignatureConfirmation() {
        return REQUIRE_SIGNATURE_CONFIRMATION;
    }

    static {
        INCLUDE_TOKEN = new QName(SP_NS, "IncludeToken", SP_PREFIX);
        TRUST_13 = new QName(SP_NS, "Trust13", SP_PREFIX);
        REQUIRE_CLIENT_CERTIFICATE = new QName(SP_NS, "RequireClientCertificate", SP_PREFIX);
        HTTP_BASIC_AUTHENTICATION = new QName(SP_NS, "HttpBasicAuthentication", SP_PREFIX);
        HTTP_DIGEST_AUTHENTICATION = new QName(SP_NS, "HttpDigestAuthentication", SP_PREFIX);
        TRANSPORT_BINDING = new QName(SP_NS, "TransportBinding", SP_PREFIX);
        ALGORITHM_SUITE = new QName(SP_NS, "AlgorithmSuite", SP_PREFIX);
        LAYOUT = new QName(SP_NS, "Layout", SP_PREFIX);
        STRICT = new QName(SP_NS, "Strict", SP_PREFIX);
        LAX = new QName(SP_NS, "Lax", SP_PREFIX);
        LAXTSFIRST = new QName(SP_NS, "LaxTsFirst", SP_PREFIX);
        LAXTSLAST = new QName(SP_NS, "LaxTsLast", SP_PREFIX);
        INCLUDE_TIMESTAMP = new QName(SP_NS, "IncludeTimestamp", SP_PREFIX);
        ENCRYPT_BEFORE_SIGNING = new QName(SP_NS, "EncryptBeforeSigning", SP_PREFIX);
        SIGN_BEFORE_ENCRYPTING = new QName(SP_NS, "SignBeforeEncrypting", SP_PREFIX);
        ONLY_SIGN_ENTIRE_HEADERS_AND_BODY = new QName(SP_NS, "OnlySignEntireHeadersAndBody", SP_PREFIX);
        TRANSPORT_TOKEN = new QName(SP_NS, "TransportToken", SP_PREFIX);
        HTTPS_TOKEN = new QName(SP_NS, "HttpsToken", SP_PREFIX);
        KERBEROS_TOKEN = new QName(SP_NS, "KerberosToken", SP_PREFIX);
        SPNEGO_CONTEXT_TOKEN = new QName(SP_NS, "SpnegoContextToken", SP_PREFIX);
        SECURITY_CONTEXT_TOKEN = new QName(SP_NS, "SecurityContextToken", SP_PREFIX);
        SECURE_CONVERSATION_TOKEN = new QName(SP_NS, "SecureConversationToken", SP_PREFIX);
        MUST_NOT_SEND_CANCEL = new QName(SP_NS, "MustNotSendCancel", SP_PREFIX);
        MUST_NOT_SEND_AMEND = new QName(SP_NS, "MustNotSendAmend", SP_PREFIX);
        MUST_NOT_SEND_RENEW = new QName(SP_NS, "MustNotSendRenew", SP_PREFIX);
        SAML_TOKEN = new QName(SP_NS, "SamlToken", SP_PREFIX);
        REL_TOKEN = new QName(SP_NS, "RelToken", SP_PREFIX);
        KEY_VALUE_TOKEN = new QName(SP_NS, "KeyValueToken", SP_PREFIX);
        RSA_KEY_VALUE = new QName(SP_NS, "RsaKeyValue", SP_PREFIX);
        SIGNATURE_TOKEN = new QName(SP_NS, "SignatureToken", SP_PREFIX);
        SIGNED_PARTS = new QName(SP_NS, "SignedParts", SP_PREFIX);
        ENCRYPTED_PARTS = new QName(SP_NS, "EncryptedParts", SP_PREFIX);
        SIGNED_ELEMENTS = new QName(SP_NS, "SignedElements", SP_PREFIX);
        XPATH_EXPR = new QName(SP_NS, "XPath", SP_PREFIX);
        ENCRYPTED_ELEMENTS = new QName(SP_NS, "EncryptedElements", SP_PREFIX);
        REQUIRED_ELEMENTS = new QName(SP_NS, "RequiredElements", SP_PREFIX);
        REQUIRED_PARTS = new QName(SP_NS, "RequiredParts", SP_PREFIX);
        CONTENT_ENCRYPTED_ELEMENTS = new QName(SP_NS, "ContentEncryptedElements", SP_PREFIX);
        USERNAME_TOKEN = new QName(SP_NS, "UsernameToken", SP_PREFIX);
        WSS_USERNAME_TOKEN10 = new QName(SP_NS, "WssUsernameToken10", SP_PREFIX);
        WSS_USERNAME_TOKEN11 = new QName(SP_NS, "WssUsernameToken11", SP_PREFIX);
        ENCRYPTION_TOKEN = new QName(SP_NS, "EncryptionToken", SP_PREFIX);
        X509_TOKEN = new QName(SP_NS, "X509Token", SP_PREFIX);
        WSS_X509_V1_TOKEN_10 = new QName(SP_NS, "WssX509V1Token10", SP_PREFIX);
        WSS_X509_V3_TOKEN_10 = new QName(SP_NS, "WssX509V3Token10", SP_PREFIX);
        WSS_X509_PKCS7_TOKEN_10 = new QName(SP_NS, "WssX509Pkcs7Token10", SP_PREFIX);
        WSS_X509_PKI_PATH_V1_TOKEN_10 = new QName(SP_NS, "WssX509PkiPathV1Token10", SP_PREFIX);
        WSS_X509_V1_TOKEN_11 = new QName(SP_NS, "WssX509V1Token11", SP_PREFIX);
        WSS_X509_V3_TOKEN_11 = new QName(SP_NS, "WssX509V3Token11", SP_PREFIX);
        WSS_X509_PKCS7_TOKEN_11 = new QName(SP_NS, "WssX509Pkcs7Token11", SP_PREFIX);
        WSS_X509_PKI_PATH_V1_TOKEN_11 = new QName(SP_NS, "WssX509PkiPathV1Token11", SP_PREFIX);
        ISSUED_TOKEN = new QName(SP_NS, "IssuedToken", SP_PREFIX);
        SUPPORTING_TOKENS = new QName(SP_NS, "SupportingTokens", SP_PREFIX);
        SIGNED_SUPPORTING_TOKENS = new QName(SP_NS, "SignedSupportingTokens", SP_PREFIX);
        ENDORSING_SUPPORTING_TOKENS = new QName(SP_NS, "EndorsingSupportingTokens", SP_PREFIX);
        SIGNED_ENDORSING_SUPPORTING_TOKENS = new QName(SP_NS, "SignedEndorsingSupportingTokens", SP_PREFIX);
        ENCRYPTED_SUPPORTING_TOKENS = new QName(SP_NS, "EncryptedSupportingTokens", SP_PREFIX);
        SIGNED_ENCRYPTED_SUPPORTING_TOKENS = new QName(SP_NS, "SignedEncryptedSupportingTokens", SP_PREFIX);
        ENDORSING_ENCRYPTED_SUPPORTING_TOKENS = new QName(SP_NS, "EndorsingEncryptedSupportingTokens", SP_PREFIX);
        SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS = new QName(SP_NS, "SignedEndorsingEncryptedSupportingTokens", SP_PREFIX);
        PROTECTION_TOKEN = new QName(SP_NS, "ProtectionToken", SP_PREFIX);
        ASYMMETRIC_BINDING = new QName(SP_NS, "AsymmetricBinding", SP_PREFIX);
        SYMMETRIC_BINDING = new QName(SP_NS, "SymmetricBinding", SP_PREFIX);
        INITIATOR_TOKEN = new QName(SP_NS, "InitiatorToken", SP_PREFIX);
        INITIATOR_SIGNATURE_TOKEN = new QName(SP_NS, "InitiatorSignatureToken", SP_PREFIX);
        INITIATOR_ENCRYPTION_TOKEN = new QName(SP_NS, "InitiatorEncryptionToken", SP_PREFIX);
        RECIPIENT_TOKEN = new QName(SP_NS, "RecipientToken", SP_PREFIX);
        RECIPIENT_SIGNATURE_TOKEN = new QName(SP_NS, "RecipientSignatureToken", SP_PREFIX);
        RECIPIENT_ENCRYPTION_TOKEN = new QName(SP_NS, "RecipientEncryptionToken", SP_PREFIX);
        ENCRYPT_SIGNATURE = new QName(SP_NS, "EncryptSignature", SP_PREFIX);
        PROTECT_TOKENS = new QName(SP_NS, "ProtectTokens", SP_PREFIX);
        REQUIRE_KEY_IDENTIFIER_REFERENCE = new QName(SP_NS, "RequireKeyIdentifierReference", SP_PREFIX);
        REQUIRE_ISSUER_SERIAL_REFERENCE = new QName(SP_NS, "RequireIssuerSerialReference", SP_PREFIX);
        REQUIRE_EMBEDDED_TOKEN_REFERENCE = new QName(SP_NS, "RequireEmbeddedTokenReference", SP_PREFIX);
        REQUIRE_THUMBPRINT_REFERENCE = new QName(SP_NS, "RequireThumbprintReference", SP_PREFIX);
        MUST_SUPPORT_REF_KEY_IDENTIFIER = new QName(SP_NS, "MustSupportRefKeyIdentifier", SP_PREFIX);
        MUST_SUPPORT_REF_ISSUER_SERIAL = new QName(SP_NS, "MustSupportRefIssuerSerial", SP_PREFIX);
        MUST_SUPPORT_REF_EXTERNAL_URI = new QName(SP_NS, "MustSupportRefExternalURI", SP_PREFIX);
        MUST_SUPPORT_REF_EMBEDDED_TOKEN = new QName(SP_NS, "MustSupportRefEmbeddedToken", SP_PREFIX);
        MUST_SUPPORT_REF_THUMBPRINT = new QName(SP_NS, "MustSupportRefThumbprint", SP_PREFIX);
        MUST_SUPPORT_REF_ENCRYPTED_KEY = new QName(SP_NS, "MustSupportRefEncryptedKey", SP_PREFIX);
        WSS10 = new QName(SP_NS, "Wss10", SP_PREFIX);
        WSS11 = new QName(SP_NS, "Wss11", SP_PREFIX);
        REQUIRE_SIGNATURE_CONFIRMATION = new QName(SP_NS, "RequireSignatureConfirmation", SP_PREFIX);
        MUST_SUPPORT_CLIENT_CHALLENGE = new QName(SP_NS, "MustSupportClientChallenge", SP_PREFIX);
        MUST_SUPPORT_SERVER_CHALLENGE = new QName(SP_NS, "MustSupportServerChallenge", SP_PREFIX);
        REQUIRE_CLIENT_ENTROPY = new QName(SP_NS, "RequireClientEntropy", SP_PREFIX);
        REQUIRE_SERVER_ENTROPY = new QName(SP_NS, "RequireServerEntropy", SP_PREFIX);
        MUST_SUPPORT_ISSUED_TOKENS = new QName(SP_NS, "MustSupportIssuedTokens", SP_PREFIX);
        REQUIRE_REQUEST_SECURITY_TOKEN_COLLECTION = new QName(SP_NS, "RequireRequestSecurityTokenCollection", SP_PREFIX);
        REQUIRE_APPLIES_TO = new QName(SP_NS, "RequireAppliesTo", SP_PREFIX);
        ISSUER = new QName(SP_NS, "Issuer", SP_PREFIX);
        ISSUER_NAME = new QName(SP_NS, "IssuerName", SP_PREFIX);
        CLAIMS = new QName(WST_NS, "Claims", WST_PREFIX);
        REQUIRE_DERIVED_KEYS = new QName(SP_NS, "RequireDerivedKeys", SP_PREFIX);
        REQUIRE_IMPLIED_DERIVED_KEYS = new QName(SP_NS, "RequireImpliedDerivedKeys", SP_PREFIX);
        REQUIRE_EXPLICIT_DERIVED_KEYS = new QName(SP_NS, "RequireExplicitDerivedKeys", SP_PREFIX);
        REQUIRE_EXTERNAL_URI_REFERENCE = new QName(SP_NS, "RequireExternalUriReference", SP_PREFIX);
        SC13_SECURITY_CONTEXT_TOKEN = new QName(SP_NS, "SC13SecurityContextToken", SP_PREFIX);
        REQUIRE_EXTERNAL_REFERNCE = new QName(SP_NS, "RequireExternalReference", SP_PREFIX);
        REQUIRE_INTERNAL_REFERENCE = new QName(SP_NS, "RequireInternalReference", SP_PREFIX);
        REQUEST_SECURITY_TOKEN_TEMPLATE = new QName(SP_NS, "RequestSecurityTokenTemplate", SP_PREFIX);
        BOOTSTRAP_POLICY = new QName(SP_NS, "BootstrapPolicy", SP_PREFIX);
        XPATH = new QName(SP_NS, "XPath", SP_PREFIX);
        NO_PASSWORD = new QName(SP_NS, "NoPassword", SP_PREFIX);
        HASH_PASSWORD = new QName(SP_NS, "HashPassword", SP_PREFIX);
        HEADER = new QName(SP_NS, "Header", SP_PREFIX);
        BODY = new QName(SP_NS, "Body", SP_PREFIX);
        ATTACHMENTS = new QName(SP_NS, "Attachments", SP_PREFIX);
    }
}

