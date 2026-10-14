/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.Period;
import java.time.ZonedDateTime;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.ext.EDirectoryAccountState;
import org.ldaptive.transcode.GeneralizedTimeValueTranscoder;

public class EDirectoryAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    public static final String[] ATTRIBUTES = new String[]{"passwordExpirationTime", "loginGraceRemaining"};
    private Period warningPeriod;

    public EDirectoryAuthenticationResponseHandler() {
    }

    public EDirectoryAuthenticationResponseHandler(Period warning) {
        this.setWarningPeriod(warning);
    }

    @Override
    public void handle(AuthenticationResponse response) {
        if (response.getDiagnosticMessage() != null) {
            EDirectoryAccountState.Error edError = EDirectoryAccountState.Error.parse(response.getDiagnosticMessage());
            if (edError != null) {
                response.setAccountState(new EDirectoryAccountState(edError));
            }
        } else if (response.isSuccess()) {
            int loginRemainingValue;
            LdapEntry entry = response.getLdapEntry();
            LdapAttribute expTime = entry.getAttribute("passwordExpirationTime");
            LdapAttribute loginRemaining = entry.getAttribute("loginGraceRemaining");
            int n = loginRemainingValue = loginRemaining != null ? Integer.parseInt(loginRemaining.getStringValue()) : 0;
            if (expTime != null) {
                ZonedDateTime exp = (ZonedDateTime)expTime.getValue(new GeneralizedTimeValueTranscoder().decoder());
                if (this.warningPeriod != null) {
                    ZonedDateTime warn = exp.minus(this.warningPeriod);
                    if (ZonedDateTime.now().isAfter(warn)) {
                        response.setAccountState(new EDirectoryAccountState(exp, loginRemainingValue));
                    }
                } else {
                    response.setAccountState(new EDirectoryAccountState(exp, loginRemainingValue));
                }
            } else if (loginRemaining != null) {
                response.setAccountState(new EDirectoryAccountState(null, loginRemainingValue));
            }
        }
    }

    public Period getWarningPeriod() {
        return this.warningPeriod;
    }

    public void setWarningPeriod(Period period) {
        this.warningPeriod = period;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "warningPeriod=" + this.warningPeriod + "]";
    }
}

