/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.common.security.UsernameToken;
import org.apache.cxf.interceptor.security.NameDigestPasswordCallbackHandler;
import org.apache.cxf.interceptor.security.NamePasswordCallbackHandler;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProvider;
import org.apache.cxf.message.Message;

public class CallbackHandlerProviderUsernameToken
implements CallbackHandlerProvider {
    @Override
    public CallbackHandler create(Message message) {
        SecurityToken token = message.get(SecurityToken.class);
        if (!(token instanceof UsernameToken)) {
            return null;
        }
        UsernameToken ut = (UsernameToken)token;
        if (ut.getPasswordType().endsWith("PasswordDigest")) {
            return new NameDigestPasswordCallbackHandler(ut.getName(), ut.getPassword(), ut.getNonce(), ut.getCreatedTime());
        }
        return new NamePasswordCallbackHandler(ut.getName(), ut.getPassword());
    }
}

