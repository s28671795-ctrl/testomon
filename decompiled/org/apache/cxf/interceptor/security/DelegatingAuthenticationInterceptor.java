/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.util.Collections;
import java.util.Map;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class DelegatingAuthenticationInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private Map<String, Interceptor<Message>> authenticationHandlers = Collections.emptyMap();

    public DelegatingAuthenticationInterceptor() {
        super("unmarshal");
    }

    public DelegatingAuthenticationInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message message) {
        String scheme2 = this.getAuthenticationScheme(message);
        Interceptor<Message> handler = this.authenticationHandlers.get(scheme2);
        if (handler == null) {
            throw new AuthenticationException();
        }
        handler.handleMessage(message);
    }

    public void setSchemeHandlers(Map<String, Interceptor<Message>> handlers) {
        this.authenticationHandlers = handlers;
    }

    protected String getAuthenticationScheme(Message message) {
        Map headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (headers == null || !headers.containsKey(AUTHORIZATION_HEADER)) {
            throw new AuthenticationException();
        }
        return ((String)headers.get(AUTHORIZATION_HEADER)).split(" ")[0].trim();
    }
}

