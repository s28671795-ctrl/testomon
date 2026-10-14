/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ldap;

import org.jetbrains.annotations.Nullable;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResultCode;

public record LdapAuthorizationResponse(boolean success, String message, AuthenticationError resultCode) {
    public LdapAuthorizationResponse(AuthenticationResponse response) {
        this(response.isSuccess(), response.getDiagnosticMessage(), LdapAuthorizationResponse.fromResponse(response));
    }

    public LdapAuthorizationResponse(AuthenticationError error) {
        this(false, null, error);
    }

    @Nullable
    private static AuthenticationError fromResponse(AuthenticationResponse response) {
        if (response.getAuthenticationResultCode() == AuthenticationResultCode.DN_RESOLUTION_FAILURE) {
            return AuthenticationError.INVALID_DN;
        }
        if (response.getAuthenticationResultCode() == AuthenticationResultCode.INVALID_CREDENTIAL) {
            return AuthenticationError.INVALID_CREDENTIALS;
        }
        return null;
    }

    public static LdapAuthorizationResponse connectionError() {
        return new LdapAuthorizationResponse(AuthenticationError.CONNECTION_ERROR);
    }

    public static LdapAuthorizationResponse invalidCredentials() {
        return new LdapAuthorizationResponse(AuthenticationError.INVALID_CREDENTIALS);
    }

    public static LdapAuthorizationResponse invalidDn() {
        return new LdapAuthorizationResponse(AuthenticationError.INVALID_DN);
    }

    public static LdapAuthorizationResponse invalidFilter() {
        return new LdapAuthorizationResponse(AuthenticationError.INVALID_FILTER);
    }

    @Override
    public String toString() {
        return this.resultCode.name();
    }

    public static enum AuthenticationError {
        CONNECTION_ERROR,
        INVALID_CREDENTIALS,
        INVALID_DN,
        INVALID_FILTER;

    }
}

