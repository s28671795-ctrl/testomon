/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.Period;
import java.time.ZonedDateTime;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.ad.transcode.FileTimeValueTranscoder;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.ext.ActiveDirectoryAccountState;

public class ActiveDirectoryAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    public static final String[] ATTRIBUTES = new String[]{"msDS-UserPasswordExpiryTimeComputed", "pwdLastSet"};
    private Period expirationPeriod;
    private Period warningPeriod;

    public ActiveDirectoryAuthenticationResponseHandler() {
    }

    public ActiveDirectoryAuthenticationResponseHandler(Period warning) {
        this.setWarningPeriod(warning);
    }

    public ActiveDirectoryAuthenticationResponseHandler(Period expiration, Period warning) {
        this.setExpirationPeriod(expiration);
        this.setWarningPeriod(warning);
    }

    @Override
    public void handle(AuthenticationResponse response) {
        ActiveDirectoryAccountState.Error adError;
        if (response.isSuccess()) {
            LdapEntry entry = response.getLdapEntry();
            LdapAttribute expTime = entry.getAttribute("msDS-UserPasswordExpiryTimeComputed");
            LdapAttribute pwdLastSet = entry.getAttribute("pwdLastSet");
            ZonedDateTime exp = null;
            if (expTime != null && !"9223372036854775807".equals(expTime.getStringValue())) {
                exp = (ZonedDateTime)expTime.getValue(new FileTimeValueTranscoder().decoder());
            } else if (this.expirationPeriod != null && pwdLastSet != null) {
                exp = ((ZonedDateTime)pwdLastSet.getValue(new FileTimeValueTranscoder().decoder())).plus(this.expirationPeriod);
            }
            if (exp != null) {
                if (this.warningPeriod != null) {
                    ZonedDateTime warn = exp.minus(this.warningPeriod);
                    if (ZonedDateTime.now().isAfter(warn)) {
                        response.setAccountState(new ActiveDirectoryAccountState(exp));
                    }
                } else {
                    response.setAccountState(new ActiveDirectoryAccountState(exp));
                }
            }
        } else if (response.getDiagnosticMessage() != null && (adError = ActiveDirectoryAccountState.Error.parse(response.getDiagnosticMessage())) != null) {
            response.setAccountState(new ActiveDirectoryAccountState(adError));
        }
    }

    public Period getExpirationPeriod() {
        return this.expirationPeriod;
    }

    public void setExpirationPeriod(Period period) {
        this.expirationPeriod = period;
    }

    public Period getWarningPeriod() {
        return this.warningPeriod;
    }

    public void setWarningPeriod(Period period) {
        this.warningPeriod = period;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "expirationPeriod=" + this.expirationPeriod + ", " + "warningPeriod=" + this.warningPeriod + "]";
    }
}

