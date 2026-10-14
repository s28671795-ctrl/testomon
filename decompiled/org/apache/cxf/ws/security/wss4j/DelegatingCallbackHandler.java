/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class DelegatingCallbackHandler
implements CallbackHandler {
    private CallbackHandler pwdHandler;

    public DelegatingCallbackHandler(CallbackHandler pwdHandler) {
        this.pwdHandler = pwdHandler;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback c : callbacks) {
            this.handleCallback(c);
        }
        if (this.pwdHandler != null) {
            this.pwdHandler.handle(callbacks);
        }
    }

    protected void handleCallback(Callback c) throws IOException {
    }
}

