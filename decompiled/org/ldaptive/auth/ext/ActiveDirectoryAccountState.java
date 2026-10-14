/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import org.ldaptive.auth.AccountState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDirectoryAccountState
extends AccountState {
    private final Error adError;

    public ActiveDirectoryAccountState(ZonedDateTime exp) {
        super(new AccountState.DefaultWarning(exp, -1));
        this.adError = null;
    }

    public ActiveDirectoryAccountState(Error error) {
        super(error);
        this.adError = error;
    }

    public Error getActiveDirectoryError() {
        return this.adError;
    }

    public static enum Error implements AccountState.Error
    {
        NO_SUCH_USER(1317),
        LOGON_FAILURE(1326),
        INVALID_LOGON_HOURS(1328),
        INVALID_WORKSTATION(1329),
        PASSWORD_EXPIRED(1330),
        ACCOUNT_DISABLED(1331),
        ACCOUNT_EXPIRED(1793),
        PASSWORD_MUST_CHANGE(1907),
        ACCOUNT_LOCKED_OUT(1909);

        private static final int HEX_RADIX = 16;
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
                case NO_SUCH_USER: {
                    throw new AccountNotFoundException(this.name());
                }
                case LOGON_FAILURE: {
                    throw new FailedLoginException(this.name());
                }
                case INVALID_LOGON_HOURS: 
                case ACCOUNT_DISABLED: 
                case ACCOUNT_LOCKED_OUT: {
                    throw new AccountLockedException(this.name());
                }
                case INVALID_WORKSTATION: {
                    throw new AccountException(this.name());
                }
                case PASSWORD_EXPIRED: 
                case PASSWORD_MUST_CHANGE: {
                    throw new CredentialExpiredException(this.name());
                }
                case ACCOUNT_EXPIRED: {
                    throw new AccountExpiredException(this.name());
                }
            }
            throw new IllegalStateException("Unknown active directory error: " + this);
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
                    return Error.valueOf(Integer.parseInt(matcher.group(1).toUpperCase(), 16));
                }
                catch (NumberFormatException e) {
                    Logger l = LoggerFactory.getLogger(Error.class);
                    l.warn("Error parsing active directory error", e);
                }
            }
            return null;
        }

        static {
            PATTERN = Pattern.compile("data (\\w+)");
        }
    }
}

