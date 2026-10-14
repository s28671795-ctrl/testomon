/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamePasswordCallbackHandler
implements CallbackHandler {
    private static final Logger LOG = LoggerFactory.getLogger(NamePasswordCallbackHandler.class);
    private static final String PASSWORD_CALLBACK_NAME = "setObject";
    private static final Class<?>[] PASSWORD_CALLBACK_TYPES = new Class[]{Object.class, char[].class, String.class};
    private String username;
    private String password;
    private String passwordCallbackName;

    public NamePasswordCallbackHandler(String username, String password) {
        this(username, password, null);
    }

    public NamePasswordCallbackHandler(String username, String password, String passwordCallbackName) {
        this.username = username;
        this.password = password;
        this.passwordCallbackName = passwordCallbackName;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; ++i) {
            Callback callback = callbacks[i];
            if (this.handleCallback(callback)) continue;
            if (callback instanceof NameCallback) {
                ((NameCallback)callback).setName(this.username);
                continue;
            }
            if (callback instanceof PasswordCallback) {
                PasswordCallback pwCallback = (PasswordCallback)callback;
                pwCallback.setPassword(this.password.toCharArray());
                continue;
            }
            if (this.invokePasswordCallback(callback)) continue;
            LOG.info("Unsupported callback type " + callbacks[i].getClass().getName());
            throw new UnsupportedCallbackException(callbacks[i], "Unsupported callback type " + callbacks[i].getClass().getName());
        }
    }

    protected boolean handleCallback(Callback callback) {
        return false;
    }

    private boolean invokePasswordCallback(Callback callback) {
        String cbname = this.passwordCallbackName == null ? PASSWORD_CALLBACK_NAME : this.passwordCallbackName;
        for (Class<?> arg : PASSWORD_CALLBACK_TYPES) {
            try {
                Method method2 = callback.getClass().getMethod(cbname, arg);
                method2.invoke((Object)callback, arg == String.class ? this.password : (Object)this.password.toCharArray());
                return true;
            }
            catch (Exception e) {
                LOG.warn(e.toString());
            }
        }
        return false;
    }
}

