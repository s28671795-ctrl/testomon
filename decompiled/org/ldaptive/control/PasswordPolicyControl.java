/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.LoginException;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.auth.AccountState;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.ResponseControl;

public class PasswordPolicyControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    public static final String OID = "1.3.6.1.4.1.42.2.27.8.5.1";
    private static final int HASH_CODE_SEED = 719;
    private int timeBeforeExpiration = -1;
    private int graceAuthNsRemaining = -1;
    private Error error;

    public PasswordPolicyControl() {
        super(OID);
    }

    public PasswordPolicyControl(boolean critical) {
        super(OID, critical);
    }

    @Override
    public boolean hasValue() {
        return false;
    }

    public int getTimeBeforeExpiration() {
        return this.timeBeforeExpiration;
    }

    public void setTimeBeforeExpiration(int time) {
        this.timeBeforeExpiration = time;
    }

    public int getGraceAuthNsRemaining() {
        return this.graceAuthNsRemaining;
    }

    public void setGraceAuthNsRemaining(int count) {
        this.graceAuthNsRemaining = count;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error e) {
        this.error = e;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PasswordPolicyControl && super.equals(o)) {
            PasswordPolicyControl v = (PasswordPolicyControl)o;
            return LdapUtils.areEqual(this.timeBeforeExpiration, v.timeBeforeExpiration) && LdapUtils.areEqual(this.graceAuthNsRemaining, v.graceAuthNsRemaining) && LdapUtils.areEqual(this.error, v.error);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(719, this.getOID(), this.getCriticality(), this.timeBeforeExpiration, this.graceAuthNsRemaining, this.error);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "timeBeforeExpiration=" + this.timeBeforeExpiration + ", " + "graceAuthNsRemaining=" + this.graceAuthNsRemaining + ", " + "error=" + this.error + "]";
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(TimeBeforeExpirationHandler.PATH, new TimeBeforeExpirationHandler(this));
        parser.registerHandler(GraceAuthnsRemainingHandler.PATH, new GraceAuthnsRemainingHandler(this));
        parser.registerHandler(ErrorHandler.PATH, new ErrorHandler(this));
        parser.parse(encoded);
    }

    private static class ErrorHandler
    extends AbstractParseHandler<PasswordPolicyControl> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(1)");

        ErrorHandler(PasswordPolicyControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            int errValue = IntegerType.decode(encoded).intValue();
            Error e = Error.valueOf(errValue);
            if (e == null) {
                throw new IllegalArgumentException("Unknown error code " + errValue);
            }
            ((PasswordPolicyControl)this.getObject()).setError(e);
        }
    }

    private static class GraceAuthnsRemainingHandler
    extends AbstractParseHandler<PasswordPolicyControl> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(0)/CTX(1)");

        GraceAuthnsRemainingHandler(PasswordPolicyControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((PasswordPolicyControl)this.getObject()).setGraceAuthNsRemaining(IntegerType.decode(encoded).intValue());
        }
    }

    private static class TimeBeforeExpirationHandler
    extends AbstractParseHandler<PasswordPolicyControl> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(0)/CTX(0)");

        TimeBeforeExpirationHandler(PasswordPolicyControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((PasswordPolicyControl)this.getObject()).setTimeBeforeExpiration(IntegerType.decode(encoded).intValue());
        }
    }

    public static enum Error implements AccountState.Error
    {
        PASSWORD_EXPIRED(0),
        ACCOUNT_LOCKED(1),
        CHANGE_AFTER_RESET(2),
        PASSWORD_MOD_NOT_ALLOWED(3),
        MUST_SUPPLY_OLD_PASSWORD(4),
        INSUFFICIENT_PASSWORD_QUALITY(5),
        PASSWORD_TOO_SHORT(6),
        PASSWORD_TOO_YOUNG(7),
        PASSWORD_IN_HISTORY(8);

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
                case PASSWORD_EXPIRED: 
                case CHANGE_AFTER_RESET: {
                    throw new CredentialExpiredException(this.name());
                }
                case ACCOUNT_LOCKED: {
                    throw new AccountLockedException(this.name());
                }
                case PASSWORD_MOD_NOT_ALLOWED: 
                case MUST_SUPPLY_OLD_PASSWORD: {
                    throw new AccountException(this.name());
                }
                case INSUFFICIENT_PASSWORD_QUALITY: 
                case PASSWORD_TOO_SHORT: 
                case PASSWORD_TOO_YOUNG: 
                case PASSWORD_IN_HISTORY: {
                    throw new CredentialException(this.name());
                }
            }
            throw new IllegalStateException("Unknown password policy error: " + this);
        }

        public static Error valueOf(int code) {
            for (Error e : Error.values()) {
                if (e.getCode() != code) continue;
                return e;
            }
            return null;
        }
    }
}

