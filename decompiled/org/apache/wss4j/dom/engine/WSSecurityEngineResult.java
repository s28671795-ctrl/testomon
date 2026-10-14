/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.engine;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.message.token.SignatureConfirmation;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.message.token.UsernameToken;

public class WSSecurityEngineResult
extends HashMap<String, Object> {
    private static final long serialVersionUID = 8877354445092724300L;
    public static final String TAG_SAML_ASSERTION = "saml-assertion";
    public static final String TAG_TIMESTAMP = "timestamp";
    public static final String TAG_SECURITY_CONTEXT_TOKEN = "security-context-token";
    public static final String TAG_USERNAME_TOKEN = "username-token";
    public static final String TAG_DERIVED_KEY_TOKEN = "derived-key-token";
    public static final String TAG_SIGNATURE_CONFIRMATION = "signature-confirmation";
    public static final String TAG_BINARY_SECURITY_TOKEN = "binary-security-token";
    public static final String TAG_TRANSFORMED_TOKEN = "transformed-token";
    public static final String TAG_VALIDATED_TOKEN = "validated-token";
    public static final String TAG_TOKEN_ELEMENT = "token-element";
    public static final String TAG_X509_CERTIFICATE = "x509-certificate";
    public static final String TAG_SIGNATURE_VALUE = "signature-value";
    public static final String TAG_X509_CERTIFICATES = "x509-certificates";
    public static final String TAG_X509_REFERENCE_TYPE = "x509-reference-type";
    public static final String TAG_ENCRYPTED_EPHEMERAL_KEY = "encrypted-ephemeral-key-bytes";
    public static final String TAG_SECRET = "secret";
    public static final String TAG_PUBLIC_KEY = "public-key";
    public static final String TAG_ACTION = "action";
    public static final String TAG_PRINCIPAL = "principal";
    public static final String TAG_SUBJECT = "subject";
    public static final String TAG_DATA_REF_URIS = "data-ref-uris";
    public static final String TAG_ENCRYPTED_KEY_TRANSPORT_METHOD = "encrypted-key-transport-method";
    public static final String TAG_SIGNATURE_METHOD = "signature-method";
    public static final String TAG_CANONICALIZATION_METHOD = "canonicalization-method";
    public static final String TAG_DELEGATION_CREDENTIAL = "delegation-credential";
    public static final String TAG_ID = "id";

    public WSSecurityEngineResult(int act) {
        this.put(TAG_ACTION, act);
    }

    public WSSecurityEngineResult(int act, SamlAssertionWrapper ass) {
        this.put(TAG_ACTION, act);
        this.put(TAG_SAML_ASSERTION, ass);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, ass.getElement());
    }

    public WSSecurityEngineResult(int act, Principal princ, X509Certificate[] certs, byte[] sv) {
        this.put(TAG_ACTION, act);
        this.put(TAG_PRINCIPAL, princ);
        this.put(TAG_X509_CERTIFICATES, certs);
        this.put(TAG_SIGNATURE_VALUE, sv);
        if (certs != null) {
            this.put(TAG_X509_CERTIFICATE, certs[0]);
        }
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
    }

    public WSSecurityEngineResult(int act, Principal princ, X509Certificate[] certs, List<WSDataRef> dataRefs, byte[] sv) {
        this(act, princ, certs, sv);
        this.put(TAG_DATA_REF_URIS, dataRefs);
    }

    public WSSecurityEngineResult(int act, byte[] decryptedKey, byte[] encryptedKeyBytes, List<WSDataRef> dataRefUris) {
        this.put(TAG_ACTION, act);
        this.put(TAG_SECRET, decryptedKey);
        this.put(TAG_ENCRYPTED_EPHEMERAL_KEY, encryptedKeyBytes);
        this.put(TAG_DATA_REF_URIS, dataRefUris);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
    }

    public WSSecurityEngineResult(int act, byte[] decryptedKey, byte[] encryptedKeyBytes, List<WSDataRef> dataRefUris, X509Certificate[] certs) {
        this.put(TAG_ACTION, act);
        this.put(TAG_SECRET, decryptedKey);
        this.put(TAG_ENCRYPTED_EPHEMERAL_KEY, encryptedKeyBytes);
        this.put(TAG_DATA_REF_URIS, dataRefUris);
        this.put(TAG_X509_CERTIFICATES, certs);
        if (certs != null) {
            this.put(TAG_X509_CERTIFICATE, certs[0]);
        }
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
    }

    public WSSecurityEngineResult(int act, List<WSDataRef> dataRefUris) {
        this.put(TAG_ACTION, act);
        this.put(TAG_DATA_REF_URIS, dataRefUris);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
    }

    public WSSecurityEngineResult(int act, Timestamp tstamp) {
        this.put(TAG_ACTION, act);
        this.put(TAG_TIMESTAMP, tstamp);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, tstamp.getElement());
    }

    public WSSecurityEngineResult(int act, SecurityContextToken sct) {
        this.put(TAG_ACTION, act);
        this.put(TAG_SECURITY_CONTEXT_TOKEN, sct);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, sct.getElement());
    }

    public WSSecurityEngineResult(int act, SignatureConfirmation sc) {
        this.put(TAG_ACTION, act);
        this.put(TAG_SIGNATURE_CONFIRMATION, sc);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, sc.getElement());
    }

    public WSSecurityEngineResult(int act, UsernameToken usernameToken) {
        this(act, usernameToken, null);
    }

    public WSSecurityEngineResult(int act, UsernameToken usernameToken, Principal principal) {
        this.put(TAG_ACTION, act);
        this.put(TAG_USERNAME_TOKEN, usernameToken);
        this.put(TAG_PRINCIPAL, principal);
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, usernameToken.getElement());
    }

    public WSSecurityEngineResult(int act, BinarySecurity token, X509Certificate[] certs) {
        this.put(TAG_ACTION, act);
        this.put(TAG_BINARY_SECURITY_TOKEN, token);
        this.put(TAG_X509_CERTIFICATES, certs);
        if (certs != null) {
            this.put(TAG_X509_CERTIFICATE, certs[0]);
        }
        this.put(TAG_VALIDATED_TOKEN, Boolean.FALSE);
        this.put(TAG_TOKEN_ELEMENT, token.getElement());
    }
}

