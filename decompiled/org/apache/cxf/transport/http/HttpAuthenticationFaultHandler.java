/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class HttpAuthenticationFaultHandler
extends AbstractPhaseInterceptor<Message> {
    String authenticationType = "Basic";
    String realm = "CXF service";

    public HttpAuthenticationFaultHandler() {
        super("unmarshal");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
    }

    @Override
    public void handleFault(Message message) {
        Exception ex = message.getContent(Exception.class);
        if (ex instanceof AuthenticationException) {
            HttpServletResponse resp = (HttpServletResponse)message.getExchange().getInMessage().get("HTTP.RESPONSE");
            resp.setStatus(401);
            resp.setHeader("WWW-Authenticate", this.authenticationType + " realm=\"" + this.realm + "\"");
            resp.setContentType("text/plain");
            try {
                resp.getOutputStream().write(ex.getMessage().getBytes());
                resp.getOutputStream().flush();
                message.getInterceptorChain().setFaultObserver(null);
                message.getInterceptorChain().abort();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }
}

