/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

public class ConfigurationConstants {
    public static final String ACTION = "action";
    public static final String USERNAME_TOKEN = "UsernameToken";
    public static final String USERNAME_TOKEN_SIGNATURE = "UsernameTokenSignature";
    public static final String USERNAME_TOKEN_NO_PASSWORD = "UsernameTokenNoPassword";
    public static final String SAML_TOKEN_UNSIGNED = "SAMLTokenUnsigned";
    public static final String SAML_TOKEN_SIGNED = "SAMLTokenSigned";
    public static final String SIGNATURE = "Signature";
    public static final String ENCRYPT = "Encrypt";
    public static final String TIMESTAMP = "Timestamp";
    public static final String SIGNATURE_DERIVED = "SignatureDerived";
    public static final String ENCRYPT_DERIVED = "EncryptDerived";
    public static final String SIGNATURE_WITH_KERBEROS_TOKEN = "SignatureWithKerberosToken";
    public static final String ENCRYPT_WITH_KERBEROS_TOKEN = "EncryptWithKerberosToken";
    public static final String KERBEROS_TOKEN = "KerberosToken";
    public static final String CUSTOM_TOKEN = "CustomToken";
    public static final String ACTOR = "actor";
    public static final String USER = "user";
    public static final String ENCRYPTION_USER = "encryptionUser";
    public static final String SIGNATURE_USER = "signatureUser";
    public static final String USE_REQ_SIG_CERT = "useReqSigCert";
    public static final String PW_CALLBACK_CLASS = "passwordCallbackClass";
    public static final String PW_CALLBACK_REF = "passwordCallbackRef";
    public static final String SAML_CALLBACK_CLASS = "samlCallbackClass";
    public static final String SAML_CALLBACK_REF = "samlCallbackRef";
    public static final String SIG_PROP_FILE = "signaturePropFile";
    public static final String SIG_PROP_REF_ID = "signaturePropRefId";
    public static final String SIG_VER_PROP_FILE = "signatureVerificationPropFile";
    public static final String SIG_VER_PROP_REF_ID = "signatureVerificationPropRefId";
    public static final String DEC_PROP_FILE = "decryptionPropFile";
    public static final String DEC_PROP_REF_ID = "decryptionPropRefId";
    public static final String ENC_PROP_FILE = "encryptionPropFile";
    public static final String ENC_PROP_REF_ID = "encryptionPropRefId";
    public static final String ENABLE_SIGNATURE_CONFIRMATION = "enableSignatureConfirmation";
    public static final String MUST_UNDERSTAND = "mustUnderstand";
    public static final String IS_BSP_COMPLIANT = "isBSPCompliant";
    public static final String ADD_INCLUSIVE_PREFIXES = "addInclusivePrefixes";
    public static final String ADD_USERNAMETOKEN_NONCE = "addUsernameTokenNonce";
    public static final String ADD_USERNAMETOKEN_CREATED = "addUsernameTokenCreated";
    public static final String HANDLE_CUSTOM_PASSWORD_TYPES = "handleCustomPasswordTypes";
    public static final String ALLOW_USERNAMETOKEN_NOPASSWORD = "allowUsernameTokenNoPassword";
    public static final String ALLOW_NAMESPACE_QUALIFIED_PASSWORD_TYPES = "allowNamespaceQualifiedPasswordTypes";
    public static final String ENABLE_REVOCATION = "enableRevocation";
    public static final String USE_SINGLE_CERTIFICATE = "useSingleCertificate";
    public static final String USE_DERIVED_KEY_FOR_MAC = "useDerivedKeyForMAC";
    public static final String TIMESTAMP_PRECISION = "precisionInMilliseconds";
    public static final String TIMESTAMP_STRICT = "timestampStrict";
    public static final String REQUIRE_TIMESTAMP_EXPIRES = "requireTimestampExpires";
    public static final String ENC_SYM_ENC_KEY = "encryptSymmetricEncryptionKey";
    public static final String REQUIRE_SIGNED_ENCRYPTED_DATA_ELEMENTS = "requireSignedEncryptedDataElements";
    public static final String ALLOW_RSA15_KEY_TRANSPORT_ALGORITHM = "allowRSA15KeyTransportAlgorithm";
    public static final String VALIDATE_SAML_SUBJECT_CONFIRMATION = "validateSamlSubjectConfirmation";
    public static final String INCLUDE_SIGNATURE_TOKEN = "includeSignatureToken";
    public static final String INCLUDE_ENCRYPTION_TOKEN = "includeEncryptionToken";
    public static final String USE_2005_12_NAMESPACE = "use200512Namespace";
    public static final String GET_SECRET_KEY_FROM_CALLBACK_HANDLER = "getSecretKeyFromCallbackHandler";
    public static final String STORE_BYTES_IN_ATTACHMENT = "storeBytesInAttachment";
    @Deprecated
    public static final String EXPAND_XOP_INCLUDE_FOR_SIGNATURE = "expandXOPIncludeForSignature";
    public static final String EXPAND_XOP_INCLUDE = "expandXOPInclude";
    public static final String PASSWORD_TYPE = "passwordType";
    public static final String SIG_KEY_ID = "signatureKeyIdentifier";
    public static final String SIG_ALGO = "signatureAlgorithm";
    public static final String SIG_DIGEST_ALGO = "signatureDigestAlgorithm";
    public static final String SIG_C14N_ALGO = "signatureC14nAlgorithm";
    public static final String SIGNATURE_PARTS = "signatureParts";
    public static final String OPTIONAL_SIGNATURE_PARTS = "optionalSignatureParts";
    public static final String DERIVED_KEY_ITERATIONS = "derivedKeyIterations";
    public static final String ENC_KEY_ID = "encryptionKeyIdentifier";
    public static final String ENC_SYM_ALGO = "encryptionSymAlgorithm";
    public static final String ENC_KEY_TRANSPORT = "encryptionKeyTransportAlgorithm";
    public static final String ENCRYPTION_PARTS = "encryptionParts";
    public static final String OPTIONAL_ENCRYPTION_PARTS = "optionalEncryptionParts";
    public static final String ENC_DIGEST_ALGO = "encryptionDigestAlgorithm";
    public static final String ENC_MGF_ALGO = "encryptionMGFAlgorithm";
    public static final String TTL_USERNAMETOKEN = "utTimeToLive";
    public static final String TTL_FUTURE_USERNAMETOKEN = "utFutureTimeToLive";
    public static final String SIG_SUBJECT_CERT_CONSTRAINTS = "sigSubjectCertConstraints";
    public static final String SIG_ISSUER_CERT_CONSTRAINTS = "sigIssuerCertConstraints";
    public static final String SIG_CERT_CONSTRAINTS_SEPARATOR = "sigCertConstraintsSeparator";
    public static final String TTL_TIMESTAMP = "timeToLive";
    public static final String TTL_FUTURE_TIMESTAMP = "futureTimeToLive";
    public static final String VALIDATOR_MAP = "validatorMap";
    public static final String NONCE_CACHE_INSTANCE = "nonceCacheInstance";
    public static final String TIMESTAMP_CACHE_INSTANCE = "timestampCacheInstance";
    public static final String SAML_ONE_TIME_USE_CACHE_INSTANCE = "samlOneTimeUseCacheInstance";
    public static final String PASSWORD_ENCRYPTOR_INSTANCE = "passwordEncryptorInstance";
    public static final String DERIVED_TOKEN_REFERENCE = "derivedTokenReference";
    public static final String DERIVED_TOKEN_KEY_ID = "derivedTokenKeyIdentifier";
    public static final String DERIVED_SIGNATURE_KEY_LENGTH = "derivedSignatureKeyLength";
    public static final String DERIVED_ENCRYPTION_KEY_LENGTH = "derivedEncryptionKeyLength";

    protected ConfigurationConstants() {
    }
}

