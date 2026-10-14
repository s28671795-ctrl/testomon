/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import org.ldaptive.auth.AccountState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDirectoryAccountState
extends AccountState {
    private final Error edError;

    public EDirectoryAccountState(ZonedDateTime exp, int remaining) {
        super(new AccountState.DefaultWarning(exp, remaining));
        this.edError = null;
    }

    public EDirectoryAccountState(Error error) {
        super(error);
        this.edError = error;
    }

    public Error getEDirectoryError() {
        return this.edError;
    }

    public static enum Error implements AccountState.Error
    {
        FAILED_AUTHENTICATION(-669),
        PASSWORD_EXPIRED(-223),
        BAD_PASSWORD(-222),
        ACCOUNT_EXPIRED(-220),
        MAXIMUM_LOGINS_EXCEEDED(-217),
        LOGIN_TIME_LIMITED(-218),
        LOGIN_LOCKOUT(-197);

        private static final Pattern PATTERN;
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
                case FAILED_AUTHENTICATION: 
                case BAD_PASSWORD: {
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
            throw new IllegalStateException("Unknown edirectory error: " + this);
        }

        public static Error valueOf(int code) {
            for (Error e : Error.values()) {
                if (e.getCode() != code) continue;
                return e;
            }
            return null;
        }

        public static Error parse(String message) {
            Matcher matcher;
            if (message != null && (matcher = PATTERN.matcher(message)).find()) {
                try {
                    return Error.valueOf(Integer.parseInt(matcher.group(2)));
                }
                catch (NumberFormatException e) {
                    Logger l = LoggerFactory.getLogger(Error.class);
                    l.warn("Error parsing edirectory error", e);
                }
            }
            return null;
        }

        static {
            PATTERN = Pattern.compile("NDS error: (.+) \\((-\\d+)\\)");
        }
    }
}

