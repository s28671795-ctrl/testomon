/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.AbstractResult;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.auth.AccountState;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.AuthenticationResultCode;

public class AuthenticationResponse
extends AbstractResult {
    private static final int HASH_CODE_SEED = 10427;
    private AuthenticationHandlerResponse authenticationHandlerResponse;
    private String resolvedDn;
    private LdapEntry ldapEntry;
    private AccountState accountState;

    private AuthenticationResponse() {
    }

    public AuthenticationResponse(AuthenticationHandlerResponse response, String dn, LdapEntry entry) {
        this.copyValues(response);
        this.authenticationHandlerResponse = response;
        this.resolvedDn = dn;
        this.ldapEntry = entry;
    }

    @Override
    public boolean isSuccess() {
        return AuthenticationResultCode.AUTHENTICATION_HANDLER_SUCCESS == this.authenticationHandlerResponse.getAuthenticationResultCode();
    }

    public AuthenticationResultCode getAuthenticationResultCode() {
        return this.authenticationHandlerResponse.getAuthenticationResultCode();
    }

    public AuthenticationHandlerResponse getAuthenticationHandlerResponse() {
        return this.authenticationHandlerResponse;
    }

    public String getResolvedDn() {
        return this.resolvedDn;
    }

    public LdapEntry getLdapEntry() {
        return this.ldapEntry;
    }

    public AccountState getAccountState() {
        return this.accountState;
    }

    public void setAccountState(AccountState state) {
        this.accountState = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AuthenticationResponse) {
            AuthenticationResponse v = (AuthenticationResponse)o;
            return super.equals(o) && LdapUtils.areEqual(this.authenticationHandlerResponse, v.authenticationHandlerResponse) && LdapUtils.areEqual(this.resolvedDn, v.resolvedDn) && LdapUtils.areEqual(this.ldapEntry, v.ldapEntry) && LdapUtils.areEqual(this.accountState, v.accountState);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10427, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.authenticationHandlerResponse, this.resolvedDn, this.ldapEntry, this.accountState});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "authenticationHandlerResponse=" + this.authenticationHandlerResponse + ", " + "resolvedDn=" + this.resolvedDn + ", " + "ldapEntry=" + this.ldapEntry + ", " + "accountState=" + this.accountState + ", " + "resultCode=" + (Object)((Object)this.getResultCode()) + ", " + "matchedDN=" + this.getMatchedDN() + ", " + "diagnosticMessage=" + this.getEncodedDiagnosticMessage() + ", " + "referralURLs=" + Arrays.toString(this.getReferralURLs()) + ", " + "messageID=" + this.getMessageID() + ", " + "controls=" + Arrays.toString(this.getControls()) + "]";
    }

    protected static Builder builder() {
        return new Builder();
    }

    protected static class Builder
    extends AbstractResult.AbstractBuilder<Builder, AuthenticationResponse> {
        protected Builder() {
            super(new AuthenticationResponse());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder response(AuthenticationHandlerResponse response) {
            ((AuthenticationResponse)this.object).copyValues(response);
            ((AuthenticationResponse)this.object).authenticationHandlerResponse = response;
            return this;
        }

        public Builder dn(String dn) {
            ((AuthenticationResponse)this.object).resolvedDn = dn;
            return this;
        }

        public Builder entry(LdapEntry entry) {
            ((AuthenticationResponse)this.object).ldapEntry = entry;
            return this;
        }

        public Builder state(AccountState state) {
            ((AuthenticationResponse)this.object).accountState = state;
            return this;
        }
    }
}

