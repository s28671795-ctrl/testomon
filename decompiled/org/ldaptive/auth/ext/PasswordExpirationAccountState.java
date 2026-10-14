/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.LoginException;
import org.ldaptive.auth.AccountState;

public class PasswordExpirationAccountState
extends AccountState {
    private final Error nError;

    public PasswordExpirationAccountState(ZonedDateTime exp) {
        super(new AccountState.DefaultWarning(exp, -1));
        this.nError = null;
    }

    public PasswordExpirationAccountState(Error error) {
        super(error);
        this.nError = error;
    }

    public Error getPasswordExpirationError() {
        return this.nError;
    }

    public static enum Error implements AccountState.Error
    {
        PASSWORD_EXPIRED;


        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMessage() {
            return this.name();
        }

        @Override
        public void throwSecurityException() throws LoginException {
            throw new CredentialExpiredException(this.name());
        }
    }
}

