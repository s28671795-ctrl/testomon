/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.ext.PasswordExpirationAccountState;
import org.ldaptive.control.PasswordExpiredControl;
import org.ldaptive.control.PasswordExpiringControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordExpirationAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(AuthenticationResponse response) {
        PasswordExpiredControl expiredControl;
        PasswordExpiringControl expiringControl = (PasswordExpiringControl)response.getControl("2.16.840.1.113730.3.4.5");
        if (expiringControl != null) {
            if (expiringControl.getTimeBeforeExpiration() > 0) {
                ZonedDateTime exp = ZonedDateTime.now().plusSeconds(expiringControl.getTimeBeforeExpiration());
                response.setAccountState(new PasswordExpirationAccountState(exp));
            } else {
                this.logger.warn("Received password expiring control with non-positive value: {}", (Object)expiringControl);
            }
        }
        if (response.getAccountState() == null && (expiredControl = (PasswordExpiredControl)response.getControl("2.16.840.1.113730.3.4.4")) != null) {
            response.setAccountState(new PasswordExpirationAccountState(PasswordExpirationAccountState.Error.PASSWORD_EXPIRED));
        }
    }
}

