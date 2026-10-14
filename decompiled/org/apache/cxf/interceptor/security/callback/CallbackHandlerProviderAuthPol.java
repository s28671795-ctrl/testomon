/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.security.NamePasswordCallbackHandler;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProvider;
import org.apache.cxf.message.Message;

public class CallbackHandlerProviderAuthPol
implements CallbackHandlerProvider {
    @Override
    public CallbackHandler create(Message message) {
        AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
        if (policy == null) {
            return null;
        }
        return new NamePasswordCallbackHandler(policy.getUserName(), policy.getPassword());
    }
}

