/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public abstract class WSSecurityEventConstants
extends SecurityEventConstants {
    public static final SecurityEventConstants.Event NO_SECURITY = new SecurityEventConstants.Event("NoSecurity");
    public static final SecurityEventConstants.Event OPERATION = new SecurityEventConstants.Event("Operation");
    public static final SecurityEventConstants.Event TIMESTAMP = new SecurityEventConstants.Event("Timestamp");
    public static final SecurityEventConstants.Event SIGNED_PART = new SecurityEventConstants.Event("SignedPart");
    public static final SecurityEventConstants.Event ENCRYPTED_PART = new SecurityEventConstants.Event("EncryptedPart");
    public static final SecurityEventConstants.Event REQUIRED_ELEMENT = new SecurityEventConstants.Event("RequiredElement");
    public static final SecurityEventConstants.Event REQUIRED_PART = new SecurityEventConstants.Event("RequiredPart");
    public static final SecurityEventConstants.Event ISSUED_TOKEN = new SecurityEventConstants.Event("IssuedToken");
    public static final SecurityEventConstants.Event KERBEROS_TOKEN = new SecurityEventConstants.Event("KerberosToken");
    public static final SecurityEventConstants.Event SAML_TOKEN = new SecurityEventConstants.Event("SamlToken");
    public static final SecurityEventConstants.Event SECURITY_CONTEXT_TOKEN = new SecurityEventConstants.Event("SecurityContextToken");
    public static final SecurityEventConstants.Event REL_TOKEN = new SecurityEventConstants.Event("RelToken");
    public static final SecurityEventConstants.Event USERNAME_TOKEN = new SecurityEventConstants.Event("UsernameToken");
    public static final SecurityEventConstants.Event HTTPS_TOKEN = new SecurityEventConstants.Event("HttpsToken");
    public static final SecurityEventConstants.Event DERIVED_KEY_TOKEN = new SecurityEventConstants.Event("DerivedKeyToken");
    public static final SecurityEventConstants.Event SIGNATURE_CONFIRMATION = new SecurityEventConstants.Event("SignatureConfirmation");
}

