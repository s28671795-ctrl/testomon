/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.JassCredentialCallbackHandler
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.jaas.JassCredentialCallbackHandler;
import org.apache.activemq.security.AbstractAuthenticationBroker;
import org.apache.activemq.security.SecurityContext;

public class JaasAuthenticationBroker
extends AbstractAuthenticationBroker {
    private final String jassConfiguration;

    public JaasAuthenticationBroker(Broker next, String jassConfiguration) {
        super(next);
        this.jassConfiguration = jassConfiguration;
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        if (context.getSecurityContext() == null) {
            ClassLoader original = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(JaasAuthenticationBroker.class.getClassLoader());
            SecurityContext securityContext = null;
            try {
                securityContext = this.authenticate(info.getUserName(), info.getPassword(), null);
                context.setSecurityContext(securityContext);
                this.securityContexts.add(securityContext);
                super.addConnection(context, info);
            }
            catch (Exception error) {
                if (securityContext != null) {
                    this.securityContexts.remove(securityContext);
                }
                context.setSecurityContext(null);
                throw error;
            }
            finally {
                Thread.currentThread().setContextClassLoader(original);
            }
        } else {
            super.addConnection(context, info);
        }
    }

    @Override
    public SecurityContext authenticate(String username, String password, X509Certificate[] certificates) throws SecurityException {
        JaasSecurityContext result = null;
        JassCredentialCallbackHandler callback = new JassCredentialCallbackHandler(username, password);
        try {
            LoginContext lc = new LoginContext(this.jassConfiguration, (CallbackHandler)callback);
            lc.login();
            Subject subject = lc.getSubject();
            result = new JaasSecurityContext(username, subject);
        }
        catch (Exception ex) {
            throw new SecurityException("User name [" + username + "] or password is invalid.", ex);
        }
        return result;
    }

    static class JaasSecurityContext
    extends SecurityContext {
        private final Subject subject;

        public JaasSecurityContext(String userName, Subject subject) {
            super(userName);
            this.subject = subject;
        }

        @Override
        public Set<Principal> getPrincipals() {
            return this.subject.getPrincipals();
        }
    }
}

