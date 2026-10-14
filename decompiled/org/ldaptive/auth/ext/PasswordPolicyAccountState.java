/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import java.time.ZonedDateTime;
import org.ldaptive.auth.AccountState;
import org.ldaptive.control.PasswordPolicyControl;

public class PasswordPolicyAccountState
extends AccountState {
    private final PasswordPolicyControl.Error ppError;

    public PasswordPolicyAccountState(ZonedDateTime exp, int remaining) {
        super(new AccountState.DefaultWarning(exp, remaining));
        this.ppError = null;
    }

    public PasswordPolicyAccountState(PasswordPolicyControl.Error error) {
        super(error);
        this.ppError = error;
    }

    public PasswordPolicyControl.Error getPasswordPolicyError() {
        return this.ppError;
    }
}

