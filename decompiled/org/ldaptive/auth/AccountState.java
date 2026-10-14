/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.time.ZonedDateTime;
import java.util.Arrays;
import javax.security.auth.login.LoginException;

public class AccountState {
    private final Warning[] accountWarnings;
    private final Error[] accountErrors;

    public AccountState(Warning ... warnings) {
        this.accountWarnings = warnings;
        this.accountErrors = null;
    }

    public AccountState(Error ... errors2) {
        this.accountWarnings = null;
        this.accountErrors = errors2;
    }

    public Warning[] getWarnings() {
        return this.accountWarnings;
    }

    public Warning getWarning() {
        return this.accountWarnings != null && this.accountWarnings.length > 0 ? this.accountWarnings[0] : null;
    }

    public Error[] getErrors() {
        return this.accountErrors;
    }

    public Error getError() {
        return this.accountErrors != null && this.accountErrors.length > 0 ? this.accountErrors[0] : null;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "accountWarnings=" + Arrays.toString(this.accountWarnings) + ", " + "accountErrors=" + Arrays.toString(this.accountErrors) + "]";
    }

    public static class DefaultWarning
    implements Warning {
        private final ZonedDateTime expiration;
        private final int loginsRemaining;

        public DefaultWarning(ZonedDateTime exp, int remaining) {
            this.expiration = exp;
            this.loginsRemaining = remaining;
        }

        @Override
        public ZonedDateTime getExpiration() {
            return this.expiration;
        }

        @Override
        public int getLoginsRemaining() {
            return this.loginsRemaining;
        }

        public String toString() {
            return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "expiration=" + this.expiration + ", " + "loginsRemaining=" + this.loginsRemaining + "]";
        }
    }

    public static interface Warning {
        public ZonedDateTime getExpiration();

        public int getLoginsRemaining();
    }

    public static interface Error {
        public int getCode();

        public String getMessage();

        public void throwSecurityException() throws LoginException;
    }
}

