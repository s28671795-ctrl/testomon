/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.AbstractResult;
import org.ldaptive.Connection;
import org.ldaptive.LdapUtils;
import org.ldaptive.Result;
import org.ldaptive.auth.AuthenticationResultCode;

public class AuthenticationHandlerResponse
extends AbstractResult {
    private static final int HASH_CODE_SEED = 10429;
    private AuthenticationResultCode authenticationResultCode;
    private Connection connection;

    private AuthenticationHandlerResponse() {
    }

    public <T extends Result> AuthenticationHandlerResponse(T result, AuthenticationResultCode code, Connection conn) {
        this.copyValues(result);
        this.authenticationResultCode = code;
        this.connection = conn;
    }

    public AuthenticationResultCode getAuthenticationResultCode() {
        return this.authenticationResultCode;
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public boolean isSuccess() {
        return AuthenticationResultCode.AUTHENTICATION_HANDLER_SUCCESS == this.authenticationResultCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AuthenticationHandlerResponse) {
            AuthenticationHandlerResponse v = (AuthenticationHandlerResponse)o;
            return super.equals(o) && LdapUtils.areEqual((Object)this.authenticationResultCode, (Object)v.authenticationResultCode) && LdapUtils.areEqual(this.connection, v.connection);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10429, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.authenticationResultCode, this.connection});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "connection=" + this.connection + ", " + "authenticationResultCode=" + (Object)((Object)this.authenticationResultCode) + ", " + "resultCode=" + (Object)((Object)this.getResultCode()) + ", " + "matchedDN=" + this.getMatchedDN() + ", " + "diagnosticMessage=" + this.getEncodedDiagnosticMessage() + ", " + "referralURLs=" + Arrays.toString(this.getReferralURLs()) + ", " + "messageID=" + this.getMessageID() + ", " + "controls=" + Arrays.toString(this.getControls()) + "]";
    }

    protected static Builder builder() {
        return new Builder();
    }

    protected static class Builder
    extends AbstractResult.AbstractBuilder<Builder, AuthenticationHandlerResponse> {
        protected Builder() {
            super(new AuthenticationHandlerResponse());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder resultCode(AuthenticationResultCode code) {
            ((AuthenticationHandlerResponse)this.object).authenticationResultCode = code;
            return this;
        }

        public Builder connection(Connection conn) {
            ((AuthenticationHandlerResponse)this.object).connection = conn;
            return this;
        }
    }
}

