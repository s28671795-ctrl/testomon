/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;

public class NameDigestPasswordCallbackHandler
implements CallbackHandler {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(NameDigestPasswordCallbackHandler.class);
    private static final Logger LOG = LogUtils.getL7dLogger(NameDigestPasswordCallbackHandler.class);
    private static final String PASSWORD_CALLBACK_NAME = "setObject";
    private static final Class<?>[] PASSWORD_CALLBACK_TYPES = new Class[]{Object.class, char[].class, String.class};
    private String username;
    private String password;
    private String nonce;
    private String createdTime;
    private String passwordCallbackName;

    public NameDigestPasswordCallbackHandler(String username, String password, String nonce, String createdTime) {
        this(username, password, nonce, createdTime, null);
    }

    public NameDigestPasswordCallbackHandler(String username, String password, String nonce, String createdTime, String passwordCallbackName) {
        this.username = username;
        this.password = password;
        this.nonce = nonce;
        this.createdTime = createdTime;
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
            Message errorMsg = new Message("UNSUPPORTED_CALLBACK_TYPE", BUNDLE, callbacks[i].getClass().getName());
            LOG.info(errorMsg.toString());
            throw new UnsupportedCallbackException(callbacks[i], errorMsg.toString());
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
                Object[] args2 = new Object[]{arg == String.class ? this.password : (Object)this.password.toCharArray()};
                method2.invoke((Object)callback, args2);
                return true;
            }
            catch (Exception e) {
                LOG.fine(e.toString());
            }
        }
        return false;
    }

    public String getNonce() {
        return this.nonce;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }
}

