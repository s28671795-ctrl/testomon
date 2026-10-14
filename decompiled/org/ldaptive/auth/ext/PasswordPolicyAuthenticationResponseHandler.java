/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.ext.PasswordPolicyAccountState;
import org.ldaptive.control.PasswordPolicyControl;

public class PasswordPolicyAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    @Override
    public void handle(AuthenticationResponse response) {
        PasswordPolicyControl ppc = (PasswordPolicyControl)response.getControl("1.3.6.1.4.1.42.2.27.8.5.1");
        if (ppc != null) {
            if (ppc.getError() != null) {
                response.setAccountState(new PasswordPolicyAccountState(ppc.getError()));
            } else {
                ZonedDateTime exp = null;
                if (ppc.getTimeBeforeExpiration() >= 0) {
                    exp = ZonedDateTime.now().plusSeconds(ppc.getTimeBeforeExpiration());
                }
                if (exp != null || ppc.getGraceAuthNsRemaining() >= 0) {
                    response.setAccountState(new PasswordPolicyAccountState(exp, ppc.getGraceAuthNsRemaining()));
                }
            }
        }
    }
}

