/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AccountState;

public class FreeIPAAccountState
extends AccountState {
    private final Error fError;

    public FreeIPAAccountState(ZonedDateTime exp, int remaining) {
        super(new AccountState.DefaultWarning(exp, remaining));
        this.fError = null;
    }

    public FreeIPAAccountState(Error error) {
        super(error);
        this.fError = error;
    }

    public Error getFreeIPAError() {
        return this.fError;
    }

    public static enum Error implements AccountState.Error
    {
        UNKNOWN(-1),
        FAILED_AUTHENTICATION(1),
        PASSWORD_EXPIRED(2),
        ACCOUNT_EXPIRED(3),
        MAXIMUM_LOGINS_EXCEEDED(4),
        LOGIN_TIME_LIMITED(5),
        LOGIN_LOCKOUT(6),
        ACCOUNT_NOT_FOUND(7),
        CREDENTIAL_NOT_FOUND(8),
        ACCOUNT_DISABLED(9);

        private final int code;

        private Error(int i) {
            this.code = i;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.name();
        }

        @Override
        public void throwSecurityException() throws LoginException {
            switch (this) {
                case ACCOUNT_NOT_FOUND: {
                    throw new AccountNotFoundException(this.name());
                }
                case FAILED_AUTHENTICATION: 
                case ACCOUNT_DISABLED: 
                case CREDENTIAL_NOT_FOUND: 
                case UNKNOWN: {
                    throw new FailedLoginException(this.name());
                }
                case PASSWORD_EXPIRED: {
                    throw new CredentialExpiredException(this.name());
                }
                case ACCOUNT_EXPIRED: {
                    throw new AccountExpiredException(this.name());
                }
                case MAXIMUM_LOGINS_EXCEEDED: 
                case LOGIN_TIME_LIMITED: 
                case LOGIN_LOCKOUT: {
                    throw new AccountLockedException(this.name());
                }
            }
            throw new IllegalStateException("Unknown FreeIPA error: " + this);
        }

        public static Error valueOf(int code) {
            for (Error e : Error.values()) {
                if (e.getCode() != code) continue;
                return e;
            }
            return ResultCode.valueOf(code) == ResultCode.SUCCESS ? null : UNKNOWN;
        }

        public static Error parse(ResultCode rc, String message) {
            Error error = null;
            if (rc != null && rc != ResultCode.SUCCESS) {
                if (rc == ResultCode.NO_SUCH_OBJECT) {
                    error = ACCOUNT_NOT_FOUND;
                } else if (rc == ResultCode.INVALID_CREDENTIALS) {
                    error = CREDENTIAL_NOT_FOUND;
                } else if (rc == ResultCode.INSUFFICIENT_ACCESS_RIGHTS) {
                    error = FAILED_AUTHENTICATION;
                } else if (rc == ResultCode.UNWILLING_TO_PERFORM) {
                    if ("Entry permanently locked.\n".equals(message)) {
                        error = LOGIN_LOCKOUT;
                    } else if ("Too many failed logins.\n".equals(message)) {
                        error = MAXIMUM_LOGINS_EXCEEDED;
                    } else if ("Account (Kerberos principal) is expired".equals(message)) {
                        error = ACCOUNT_EXPIRED;
                    } else if ("Account inactivated. Contact system administrator.".equals(message)) {
                        error = ACCOUNT_DISABLED;
                    }
                } else {
                    error = UNKNOWN;
                }
            }
            return error;
        }
    }
}

