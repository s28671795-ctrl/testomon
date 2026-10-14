/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityToken;

import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class WSSecurityTokenConstants
extends SecurityTokenConstants {
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_MAIN_SIGNATURE = new SecurityTokenConstants.TokenUsage("MainSignature");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_MAIN_ENCRYPTION = new SecurityTokenConstants.TokenUsage("MainEncryption");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("SupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_SIGNED_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("SignedSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_ENDORSING_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("EndorsingSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_SIGNED_ENDORSING_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("SignedEndorsingSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_SIGNED_ENCRYPTED_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("SignedEncryptedSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_ENCRYPTED_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("EncryptedSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("EndorsingEncryptedSupportingTokens");
    public static final SecurityTokenConstants.TokenUsage TOKENUSAGE_SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS = new SecurityTokenConstants.TokenUsage("SignedEndorsingEncryptedSupportingTokens");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE = new SecurityTokenConstants.KeyIdentifier("SecurityTokenDirectReference");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_THUMBPRINT_IDENTIFIER = new SecurityTokenConstants.KeyIdentifier("ThumbprintIdentifier");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER = new SecurityTokenConstants.KeyIdentifier("EncryptedKeySha1Identifier");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER = new SecurityTokenConstants.KeyIdentifier("KerberosSha1Identifier");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_EMBEDDED_KEY_IDENTIFIER_REF = new SecurityTokenConstants.KeyIdentifier("EmbeddedKeyIdentifierRef");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_USERNAME_TOKEN_REFERENCE = new SecurityTokenConstants.KeyIdentifier("UsernameTokenReference");
    public static final SecurityTokenConstants.KeyIdentifier KEYIDENTIFIER_EXTERNAL_REFERENCE = new SecurityTokenConstants.KeyIdentifier("ExternalReference");
    public static final SecurityTokenConstants.TokenType USERNAME_TOKEN = new SecurityTokenConstants.TokenType("UsernameToken");
    public static final SecurityTokenConstants.TokenType SECURITY_CONTEXT_TOKEN = new SecurityTokenConstants.TokenType("SecurityContextToken");
    public static final SecurityTokenConstants.TokenType SAML_10_TOKEN = new SecurityTokenConstants.TokenType("Saml10Token");
    public static final SecurityTokenConstants.TokenType SAML_11_TOKEN = new SecurityTokenConstants.TokenType("Saml11Token");
    public static final SecurityTokenConstants.TokenType SAML_20_TOKEN = new SecurityTokenConstants.TokenType("Saml20Token");
    public static final SecurityTokenConstants.TokenType ISSUED_TOKEN = new SecurityTokenConstants.TokenType("IssuedToken");
    public static final SecurityTokenConstants.TokenType SECURE_CONVERSATION_TOKEN = new SecurityTokenConstants.TokenType("SecureConversationToken");
    public static final SecurityTokenConstants.TokenType HTTPS_TOKEN = new SecurityTokenConstants.TokenType("HttpsToken");
    public static final SecurityTokenConstants.TokenType KERBEROS_TOKEN = new SecurityTokenConstants.TokenType("KerberosToken");
    public static final SecurityTokenConstants.TokenType SPNEGO_CONTEXT_TOKEN = new SecurityTokenConstants.TokenType("SpnegoContextToken");
    public static final SecurityTokenConstants.TokenType REL_TOKEN = new SecurityTokenConstants.TokenType("RelToken");
}

