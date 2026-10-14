/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.message.Message;

public interface CallbackHandlerProvider {
    public CallbackHandler create(Message var1);
}

