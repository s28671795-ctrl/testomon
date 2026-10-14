/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.Period;
import java.time.ZonedDateTime;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.ext.FreeIPAAccountState;
import org.ldaptive.transcode.GeneralizedTimeValueTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeIPAAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    public static final String[] ATTRIBUTES = new String[]{"krbPasswordExpiration", "krbLoginFailedCount", "krbLastPwdChange"};
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Period expirationPeriod;
    private Period warningPeriod;
    private int maxLoginFailures;

    public FreeIPAAuthenticationResponseHandler() {
    }

    public FreeIPAAuthenticationResponseHandler(Period warning, int loginFailures) {
        this.setWarningPeriod(warning);
        this.setMaxLoginFailures(loginFailures);
    }

    public FreeIPAAuthenticationResponseHandler(Period expiration, Period warning, int loginFailures) {
        this.setExpirationPeriod(expiration);
        this.setWarningPeriod(warning);
        this.setMaxLoginFailures(loginFailures);
    }

    @Override
    public void handle(AuthenticationResponse response) {
        if (response.getResultCode() != ResultCode.SUCCESS) {
            FreeIPAAccountState.Error fError = FreeIPAAccountState.Error.parse(response.getResultCode(), response.getDiagnosticMessage());
            if (fError != null) {
                response.setAccountState(new FreeIPAAccountState(fError));
            }
        } else if (response.isSuccess()) {
            LdapEntry entry = response.getLdapEntry();
            LdapAttribute expTime = entry.getAttribute("krbPasswordExpiration");
            LdapAttribute failedLogins = entry.getAttribute("krbLoginFailedCount");
            LdapAttribute lastPwdChange = entry.getAttribute("krbLastPwdChange");
            ZonedDateTime exp = null;
            Integer loginRemaining = null;
            if (failedLogins != null && this.maxLoginFailures > 0) {
                loginRemaining = this.maxLoginFailures - Integer.parseInt(failedLogins.getStringValue());
            }
            if (expTime != null) {
                exp = (ZonedDateTime)expTime.getValue(new GeneralizedTimeValueTranscoder().decoder());
            } else if (this.expirationPeriod != null && lastPwdChange != null) {
                exp = ((ZonedDateTime)lastPwdChange.getValue(new GeneralizedTimeValueTranscoder().decoder())).plus(this.expirationPeriod);
            }
            if (exp != null) {
                if (this.warningPeriod != null) {
                    ZonedDateTime warn = exp.minus(this.warningPeriod);
                    if (ZonedDateTime.now().isAfter(warn)) {
                        response.setAccountState(new FreeIPAAccountState(exp, loginRemaining != null ? loginRemaining : 0));
                    }
                } else {
                    response.setAccountState(new FreeIPAAccountState(exp, loginRemaining != null ? loginRemaining : 0));
                }
            } else if (loginRemaining != null && loginRemaining < this.maxLoginFailures) {
                response.setAccountState(new FreeIPAAccountState(null, loginRemaining));
            }
        }
    }

    public int getMaxLoginFailures() {
        return this.maxLoginFailures;
    }

    public void setMaxLoginFailures(int loginFailures) {
        if (loginFailures < 0) {
            throw new IllegalArgumentException("Login failures must be >= 0");
        }
        this.maxLoginFailures = loginFailures;
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
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "expirationPeriod=" + this.expirationPeriod + ", " + "warningPeriod=" + this.warningPeriod + ", " + "maxLoginFailures=" + this.maxLoginFailures + "]";
    }
}

