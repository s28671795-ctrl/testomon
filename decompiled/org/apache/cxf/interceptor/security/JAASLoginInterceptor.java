/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.interceptor.security.NamePasswordCallbackHandler;
import org.apache.cxf.interceptor.security.RolePrefixSecurityContextImpl;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProvider;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProviderAuthPol;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProviderUsernameToken;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;

public class JAASLoginInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String ROLE_CLASSIFIER_PREFIX = "prefix";
    public static final String ROLE_CLASSIFIER_CLASS_NAME = "classname";
    private static final Logger LOG = LogUtils.getL7dLogger(JAASLoginInterceptor.class);
    private String contextName = "";
    private Configuration loginConfig;
    private String roleClassifier;
    private String roleClassifierType = "prefix";
    private boolean reportFault;
    private boolean useDoAs = true;
    private List<CallbackHandlerProvider> callbackHandlerProviders = new ArrayList<CallbackHandlerProvider>();
    private boolean allowAnonymous = true;
    private boolean allowNamedPrincipals;

    public JAASLoginInterceptor() {
        this("unmarshal");
    }

    public JAASLoginInterceptor(String phase) {
        super(phase);
        this.callbackHandlerProviders.add(new CallbackHandlerProviderAuthPol());
        this.callbackHandlerProviders.add(new CallbackHandlerProviderUsernameToken());
    }

    public void setContextName(String name) {
        this.contextName = name;
    }

    public String getContextName() {
        return this.contextName;
    }

    @Deprecated
    public void setRolePrefix(String name) {
        this.setRoleClassifier(name);
    }

    public void setRoleClassifier(String value) {
        this.roleClassifier = value;
    }

    public String getRoleClassifier() {
        return this.roleClassifier;
    }

    public void setRoleClassifierType(String value) {
        if (!ROLE_CLASSIFIER_PREFIX.equals(value) && !ROLE_CLASSIFIER_CLASS_NAME.equals(value)) {
            throw new IllegalArgumentException("Unsupported role classifier");
        }
        this.roleClassifierType = value;
    }

    public String getRoleClassifierType() {
        return this.roleClassifierType;
    }

    public void setReportFault(boolean reportFault) {
        this.reportFault = reportFault;
    }

    public void setUseDoAs(boolean useDoAs) {
        this.useDoAs = useDoAs;
    }

    private CallbackHandler getFirstCallbackHandler(Message message) {
        for (CallbackHandlerProvider cbp : this.callbackHandlerProviders) {
            CallbackHandler cbh = cbp.create(message);
            if (cbh == null) continue;
            return cbh;
        }
        return null;
    }

    @Override
    public void handleMessage(final Message message) {
        SecurityContext sc;
        if (this.allowNamedPrincipals && (sc = message.get(SecurityContext.class)) != null && sc.getUserPrincipal() != null && sc.getUserPrincipal().getName() != null) {
            return;
        }
        CallbackHandler handler = this.getFirstCallbackHandler(message);
        if (handler == null && !this.allowAnonymous) {
            throw new AuthenticationException("Authentication required but no authentication information was supplied");
        }
        try {
            LoginContext ctx = new LoginContext(this.getContextName(), null, handler, this.loginConfig);
            ctx.login();
            Subject subject = ctx.getSubject();
            String name = this.getUsername(handler);
            message.put(SecurityContext.class, this.createSecurityContext(name, subject));
            if (this.useDoAs) {
                Subject.doAs(subject, new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        InterceptorChain chain = message.getInterceptorChain();
                        if (chain != null) {
                            message.put("suspend.chain.on.current.interceptor", Boolean.TRUE);
                            chain.doIntercept(message);
                        }
                        return null;
                    }
                });
            }
        }
        catch (LoginException ex) {
            String errorMessage = "Authentication failed: " + ex.getMessage();
            LOG.log(Level.FINE, errorMessage, ex);
            if (this.reportFault) {
                AuthenticationException aex = new AuthenticationException(errorMessage);
                aex.initCause(ex);
                throw aex;
            }
            throw new AuthenticationException("Authentication failed (details can be found in server log)");
        }
    }

    private String getUsername(CallbackHandler handler) {
        if (handler == null) {
            return null;
        }
        try {
            NameCallback usernameCallBack = new NameCallback("user");
            handler.handle(new Callback[]{usernameCallBack});
            return usernameCallBack.getName();
        }
        catch (Exception e) {
            return null;
        }
    }

    protected CallbackHandler getCallbackHandler(String name, String password) {
        return new NamePasswordCallbackHandler(name, password);
    }

    protected SecurityContext createSecurityContext(String name, Subject subject) {
        if (this.getRoleClassifier() != null) {
            return new RolePrefixSecurityContextImpl(subject, this.getRoleClassifier(), this.getRoleClassifierType());
        }
        return new DefaultSecurityContext(name, subject);
    }

    public Configuration getLoginConfig() {
        return this.loginConfig;
    }

    public void setLoginConfig(Configuration loginConfig) {
        this.loginConfig = loginConfig;
    }

    public List<CallbackHandlerProvider> getCallbackHandlerProviders() {
        return this.callbackHandlerProviders;
    }

    public void setCallbackHandlerProviders(List<CallbackHandlerProvider> callbackHandlerProviders) {
        this.callbackHandlerProviders.clear();
        this.callbackHandlerProviders.addAll(callbackHandlerProviders);
    }

    public void addCallbackHandlerProviders(List<CallbackHandlerProvider> callbackHandlerProviders2) {
        this.callbackHandlerProviders.addAll(callbackHandlerProviders2);
    }

    public void setAllowAnonymous(boolean allowAnonymous) {
        this.allowAnonymous = allowAnonymous;
    }

    public void setAllowNamedPrincipals(boolean allowNamedPrincipals) {
        this.allowNamedPrincipals = allowNamedPrincipals;
    }
}

