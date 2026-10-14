/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class WSDLGetAuthenticatorInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private static final Logger LOG = LogUtils.getL7dLogger(WSDLGetAuthenticatorInterceptor.class);
    private String contextName;

    public WSDLGetAuthenticatorInterceptor() {
        super("read");
        this.getBefore().add("org.apache.cxf.frontend.WSDLGetInterceptor");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(Message message) throws Fault {
        Endpoint endpoint;
        String method2 = (String)message.get("org.apache.cxf.request.method");
        String query = (String)message.get(Message.QUERY_STRING);
        if (!"GET".equals(method2) || StringUtils.isEmpty(query)) {
            return;
        }
        Endpoint endpoint2 = endpoint = message.getExchange().getEndpoint();
        synchronized (endpoint2) {
            if (!StringUtils.isEmpty(this.contextName)) {
                AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
                if (policy == null) {
                    this.handle401response(message, endpoint);
                    return;
                }
                Subject subject = (Subject)this.authenticate(policy.getUserName(), policy.getPassword());
                if (subject == null) {
                    this.handle401response(message, endpoint);
                    return;
                }
            }
        }
    }

    private void handle401response(Message message, Endpoint e) {
        HttpServletResponse response = (HttpServletResponse)message.get("HTTP.RESPONSE");
        response.setHeader(HEADER_WWW_AUTHENTICATE, "Basic realm=\"" + this.contextName + "\"");
        response.setStatus(401);
        response.setContentLength(0);
        message.getInterceptorChain().pause();
    }

    public Object authenticate(String username, String password) {
        return this.doAuthenticate(username, password);
    }

    public Subject doAuthenticate(final String username, final String password) {
        try {
            Subject subject = new Subject();
            LoginContext loginContext = new LoginContext(this.getContextName(), subject, new CallbackHandler(){

                @Override
                public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                    for (int i = 0; i < callbacks.length; ++i) {
                        if (callbacks[i] instanceof NameCallback) {
                            ((NameCallback)callbacks[i]).setName(username);
                            continue;
                        }
                        if (callbacks[i] instanceof PasswordCallback) {
                            ((PasswordCallback)callbacks[i]).setPassword(password.toCharArray());
                            continue;
                        }
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            });
            loginContext.login();
            return subject;
        }
        catch (FailedLoginException e) {
            LOG.log(Level.FINE, "Login failed ", e);
            return null;
        }
        catch (AccountException e) {
            LOG.log(Level.WARNING, "Account failure ", e);
            return null;
        }
        catch (GeneralSecurityException e) {
            LOG.log(Level.SEVERE, "General Security Exception ", e);
            return null;
        }
    }

    public String getContextName() {
        return this.contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
}

