/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.Mechanism;

public class CramMD5BindRequest
extends DefaultSaslClientRequest {
    public static final Mechanism MECHANISM = Mechanism.CRAM_MD5;
    private final String authenticationID;
    private final String password;

    public CramMD5BindRequest(String authID, String pass) {
        this.authenticationID = authID;
        this.password = pass;
    }

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback)callback).setName(this.authenticationID);
                continue;
            }
            if (callback instanceof PasswordCallback && this.password != null) {
                ((PasswordCallback)callback).setPassword(this.password.toCharArray());
                continue;
            }
            throw new UnsupportedCallbackException(callback);
        }
    }

    @Override
    public Mechanism getMechanism() {
        return MECHANISM;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "authenticationID=" + this.authenticationID;
    }
}

