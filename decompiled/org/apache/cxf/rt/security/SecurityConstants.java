/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SecurityConstants {
    public static final String USERNAME = "security.username";
    public static final String PASSWORD = "security.password";
    public static final String SIGNATURE_USERNAME = "security.signature.username";
    public static final String SIGNATURE_PASSWORD = "security.signature.password";
    public static final String ENCRYPT_USERNAME = "security.encryption.username";
    public static final String CALLBACK_HANDLER = "security.callback-handler";
    public static final String SAML_CALLBACK_HANDLER = "security.saml-callback-handler";
    public static final String SIGNATURE_PROPERTIES = "security.signature.properties";
    public static final String ENCRYPT_PROPERTIES = "security.encryption.properties";
    public static final String SIGNATURE_CRYPTO = "security.signature.crypto";
    public static final String ENCRYPT_CRYPTO = "security.encryption.crypto";
    public static final String ENCRYPT_CERT = "security.encryption.certificate";
    public static final String ENABLE_REVOCATION = "security.enableRevocation";
    public static final String ENABLE_UNSIGNED_SAML_ASSERTION_PRINCIPAL = "security.enable.unsigned-saml-assertion.principal";
    public static final String ENABLE_UT_NOPASSWORD_PRINCIPAL = "security.enable.ut-no-password.principal";
    public static final String VALIDATE_SAML_SUBJECT_CONFIRMATION = "security.validate.saml.subject.conf";
    public static final String SC_FROM_JAAS_SUBJECT = "security.sc.jaas-subject";
    public static final String AUDIENCE_RESTRICTION_VALIDATION = "security.validate.audience-restriction";
    public static final String SAML_ROLE_ATTRIBUTENAME = "security.saml-role-attributename";
    public static final String SUBJECT_CERT_CONSTRAINTS = "security.subject.cert.constraints";
    public static final String CERT_CONSTRAINTS_SEPARATOR = "security.cert.constraints.separator";
    public static final String STS_CLIENT = "security.sts.client";
    public static final String STS_APPLIES_TO = "security.sts.applies-to";
    public static final String STS_TOKEN_USE_CERT_FOR_KEYINFO = "security.sts.token.usecert";
    public static final String STS_TOKEN_DO_CANCEL = "security.sts.token.do.cancel";
    public static final String STS_ISSUE_AFTER_FAILED_RENEW = "security.issue.after.failed.renew";
    public static final String CACHE_ISSUED_TOKEN_IN_ENDPOINT = "security.cache.issued.token.in.endpoint";
    public static final String DISABLE_STS_CLIENT_WSMEX_CALL_USING_EPR_ADDRESS = "security.sts.disable-wsmex-call-using-epr-address";
    public static final String PREFER_WSMEX_OVER_STS_CLIENT_CONFIG = "security.sts.prefer-wsmex";
    public static final String STS_CLIENT_SOAP12_BINDING = "security.sts.client-soap12-binding";
    public static final String STS_TOKEN_CRYPTO = "security.sts.token.crypto";
    public static final String STS_TOKEN_PROPERTIES = "security.sts.token.properties";
    public static final String STS_TOKEN_USERNAME = "security.sts.token.username";
    public static final String STS_TOKEN_ACT_AS = "security.sts.token.act-as";
    public static final String STS_TOKEN_ON_BEHALF_OF = "security.sts.token.on-behalf-of";
    public static final String STS_TOKEN_IMMINENT_EXPIRY_VALUE = "security.sts.token.imminent-expiry-value";
    public static final String STS_TOKEN_CACHER_IMPL = "security.sts.token.cacher.impl";
    public static final String STS_CHECK_FOR_RECURSIVE_CALL = "security.sts.check.for.recursive.call";
    public static final String AUDIENCE_RESTRICTIONS = "security.audience-restrictions";
    public static final Set<String> COMMON_PROPERTIES;

    protected SecurityConstants() {
    }

    static {
        HashSet<String> s2 = new HashSet<String>(Arrays.asList(USERNAME, PASSWORD, SIGNATURE_USERNAME, ENCRYPT_USERNAME, CALLBACK_HANDLER, SAML_CALLBACK_HANDLER, SIGNATURE_PROPERTIES, SIGNATURE_CRYPTO, ENCRYPT_PROPERTIES, ENCRYPT_CRYPTO, ENCRYPT_CERT, ENABLE_REVOCATION, SUBJECT_CERT_CONSTRAINTS, ENABLE_UNSIGNED_SAML_ASSERTION_PRINCIPAL, ENABLE_UT_NOPASSWORD_PRINCIPAL, AUDIENCE_RESTRICTION_VALIDATION, SAML_ROLE_ATTRIBUTENAME, ENABLE_UNSIGNED_SAML_ASSERTION_PRINCIPAL, SC_FROM_JAAS_SUBJECT, STS_TOKEN_USE_CERT_FOR_KEYINFO, STS_TOKEN_DO_CANCEL, CACHE_ISSUED_TOKEN_IN_ENDPOINT, DISABLE_STS_CLIENT_WSMEX_CALL_USING_EPR_ADDRESS, STS_TOKEN_CRYPTO, STS_TOKEN_PROPERTIES, STS_TOKEN_USERNAME, STS_TOKEN_ACT_AS, STS_TOKEN_ON_BEHALF_OF, STS_CLIENT, STS_APPLIES_TO, CACHE_ISSUED_TOKEN_IN_ENDPOINT, PREFER_WSMEX_OVER_STS_CLIENT_CONFIG, STS_TOKEN_IMMINENT_EXPIRY_VALUE, STS_TOKEN_CACHER_IMPL, AUDIENCE_RESTRICTIONS, STS_CHECK_FOR_RECURSIVE_CALL));
        COMMON_PROPERTIES = Collections.unmodifiableSet(s2);
    }
}

