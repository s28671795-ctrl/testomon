/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class SecurityConstants
extends org.apache.cxf.rt.security.SecurityConstants {
    public static final String ACTOR = "ws-security.actor";
    public static final String VALIDATE_TOKEN = "ws-security.validate.token";
    public static final String ALWAYS_ENCRYPT_UT = "ws-security.username-token.always.encrypted";
    public static final String IS_BSP_COMPLIANT = "ws-security.is-bsp-compliant";
    public static final String ENABLE_NONCE_CACHE = "ws-security.enable.nonce.cache";
    public static final String ENABLE_TIMESTAMP_CACHE = "ws-security.enable.timestamp.cache";
    public static final String ENABLE_STREAMING_SECURITY = "ws-security.enable.streaming";
    public static final String RETURN_SECURITY_ERROR = "ws-security.return.security.error";
    public static final String MUST_UNDERSTAND = "ws-security.must-understand";
    public static final String ENABLE_SAML_ONE_TIME_USE_CACHE = "ws-security.enable.saml.cache";
    public static final String STORE_BYTES_IN_ATTACHMENT = "ws-security.store.bytes.in.attachment";
    public static final String USE_ATTACHMENT_ENCRYPTION_CONTENT_ONLY_TRANSFORM = "ws-security.swa.encryption.attachment.transform.content";
    public static final String USE_STR_TRANSFORM = "ws-security.use.str.transform";
    public static final String ADD_INCLUSIVE_PREFIXES = "ws-security.add.inclusive.prefixes";
    public static final String DISABLE_REQ_CLIENT_CERT_CHECK = "ws-security.disable.require.client.cert.check";
    public static final String EXPAND_XOP_INCLUDE = "ws-security.expand.xop.include";
    public static final String TIMESTAMP_TTL = "ws-security.timestamp.timeToLive";
    public static final String TIMESTAMP_FUTURE_TTL = "ws-security.timestamp.futureTimeToLive";
    public static final String USERNAMETOKEN_TTL = "ws-security.usernametoken.timeToLive";
    public static final String USERNAMETOKEN_FUTURE_TTL = "ws-security.usernametoken.futureTimeToLive";
    public static final String SPNEGO_CLIENT_ACTION = "ws-security.spnego.client.action";
    public static final String NONCE_CACHE_INSTANCE = "ws-security.nonce.cache.instance";
    public static final String TIMESTAMP_CACHE_INSTANCE = "ws-security.timestamp.cache.instance";
    public static final String SAML_ONE_TIME_USE_CACHE_INSTANCE = "ws-security.saml.cache.instance";
    public static final String CACHE_CONFIG_FILE = "ws-security.cache.config.file";
    public static final String TOKEN_STORE_CACHE_INSTANCE = "org.apache.cxf.ws.security.tokenstore.TokenStore";
    public static final String CACHE_IDENTIFIER = "ws-security.cache.identifier";
    public static final String SUBJECT_ROLE_CLASSIFIER = "ws-security.role.classifier";
    public static final String SUBJECT_ROLE_CLASSIFIER_TYPE = "ws-security.role.classifier.type";
    public static final String ASYMMETRIC_SIGNATURE_ALGORITHM = "ws-security.asymmetric.signature.algorithm";
    public static final String SYMMETRIC_SIGNATURE_ALGORITHM = "ws-security.symmetric.signature.algorithm";
    public static final String PASSWORD_ENCRYPTOR_INSTANCE = "ws-security.password.encryptor.instance";
    public static final String DELEGATED_CREDENTIAL = "ws-security.delegated.credential";
    public static final String SECURITY_CONTEXT_CREATOR = "ws-security.security.context.creator";
    public static final String SECURITY_TOKEN_LIFETIME = "ws-security.security.token.lifetime";
    public static final String USERNAME_TOKEN_VALIDATOR = "ws-security.ut.validator";
    public static final String SAML1_TOKEN_VALIDATOR = "ws-security.saml1.validator";
    public static final String SAML2_TOKEN_VALIDATOR = "ws-security.saml2.validator";
    public static final String TIMESTAMP_TOKEN_VALIDATOR = "ws-security.timestamp.validator";
    public static final String SIGNATURE_TOKEN_VALIDATOR = "ws-security.signature.validator";
    public static final String BST_TOKEN_VALIDATOR = "ws-security.bst.validator";
    public static final String SCT_TOKEN_VALIDATOR = "ws-security.sct.validator";
    public static final String POLICY_VALIDATOR_MAP = "ws-security.policy.validator.map";
    public static final String KERBEROS_REQUEST_CREDENTIAL_DELEGATION = "ws-security.kerberos.request.credential.delegation";
    public static final String KERBEROS_USE_CREDENTIAL_DELEGATION = "ws-security.kerberos.use.credential.delegation";
    public static final String KERBEROS_IS_USERNAME_IN_SERVICENAME_FORM = "ws-security.kerberos.is.username.in.servicename.form";
    public static final String KERBEROS_JAAS_CONTEXT_NAME = "ws-security.kerberos.jaas.context";
    public static final String KERBEROS_SPN = "ws-security.kerberos.spn";
    public static final String KERBEROS_CLIENT = "ws-security.kerberos.client";
    public static final String TOKEN = "ws-security.token";
    public static final String TOKEN_ID = "ws-security.token.id";
    public static final String TOKEN_ELEMENT = "ws-security.token.element";
    public static final Set<String> ALL_PROPERTIES;

    private SecurityConstants() {
    }

    static {
        HashSet<String> s2 = new HashSet<String>(Arrays.asList(ACTOR, VALIDATE_TOKEN, ALWAYS_ENCRYPT_UT, IS_BSP_COMPLIANT, ENABLE_NONCE_CACHE, ENABLE_TIMESTAMP_CACHE, TIMESTAMP_TTL, TIMESTAMP_FUTURE_TTL, KERBEROS_CLIENT, SPNEGO_CLIENT_ACTION, KERBEROS_JAAS_CONTEXT_NAME, KERBEROS_SPN, NONCE_CACHE_INSTANCE, TIMESTAMP_CACHE_INSTANCE, CACHE_CONFIG_FILE, TOKEN_STORE_CACHE_INSTANCE, USERNAME_TOKEN_VALIDATOR, SAML1_TOKEN_VALIDATOR, SAML2_TOKEN_VALIDATOR, TIMESTAMP_TOKEN_VALIDATOR, SIGNATURE_TOKEN_VALIDATOR, BST_TOKEN_VALIDATOR, SCT_TOKEN_VALIDATOR, TOKEN, TOKEN_ID, SUBJECT_ROLE_CLASSIFIER, SUBJECT_ROLE_CLASSIFIER_TYPE, MUST_UNDERSTAND, ASYMMETRIC_SIGNATURE_ALGORITHM, PASSWORD_ENCRYPTOR_INSTANCE, ENABLE_SAML_ONE_TIME_USE_CACHE, SAML_ONE_TIME_USE_CACHE_INSTANCE, ENABLE_STREAMING_SECURITY, RETURN_SECURITY_ERROR, CACHE_IDENTIFIER, DELEGATED_CREDENTIAL, KERBEROS_USE_CREDENTIAL_DELEGATION, KERBEROS_IS_USERNAME_IN_SERVICENAME_FORM, KERBEROS_REQUEST_CREDENTIAL_DELEGATION, POLICY_VALIDATOR_MAP, STORE_BYTES_IN_ATTACHMENT, USE_ATTACHMENT_ENCRYPTION_CONTENT_ONLY_TRANSFORM, SYMMETRIC_SIGNATURE_ALGORITHM, SECURITY_CONTEXT_CREATOR, SECURITY_TOKEN_LIFETIME, DISABLE_REQ_CLIENT_CERT_CHECK, EXPAND_XOP_INCLUDE));
        for (String commonProperty : COMMON_PROPERTIES) {
            s2.add(commonProperty);
            s2.add("ws-" + commonProperty);
        }
        ALL_PROPERTIES = Collections.unmodifiableSet(s2);
    }
}

