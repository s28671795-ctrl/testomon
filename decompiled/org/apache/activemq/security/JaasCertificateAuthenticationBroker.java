/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.JaasCertificateCallbackHandler
 *  org.apache.activemq.jaas.UserPrincipal
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.security.cert.X509Certificate;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.jaas.JaasCertificateCallbackHandler;
import org.apache.activemq.jaas.UserPrincipal;
import org.apache.activemq.security.AuthenticationBroker;
import org.apache.activemq.security.JaasAuthenticationBroker;
import org.apache.activemq.security.JaasCertificateSecurityContext;
import org.apache.activemq.security.SecurityContext;

public class JaasCertificateAuthenticationBroker
extends BrokerFilter
implements AuthenticationBroker {
    private final String jaasConfiguration;

    public JaasCertificateAuthenticationBroker(Broker next, String jaasConfiguration) {
        super(next);
        this.jaasConfiguration = jaasConfiguration;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        if (context.getSecurityContext() == null) {
            if (!(info.getTransportContext() instanceof X509Certificate[])) {
                throw new SecurityException("Unable to authenticate transport without SSL certificate.");
            }
            ClassLoader original = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(JaasAuthenticationBroker.class.getClassLoader());
            try {
                SecurityContext s2 = this.authenticate(info.getUserName(), info.getPassword(), (X509Certificate[])info.getTransportContext());
                context.setSecurityContext(s2);
            }
            finally {
                Thread.currentThread().setContextClassLoader(original);
            }
        }
        super.addConnection(context, info);
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        super.removeConnection(context, info, error);
        context.setSecurityContext(null);
    }

    @Override
    public SecurityContext authenticate(String username, String password, X509Certificate[] peerCertificates2) throws SecurityException {
        try {
            JaasCertificateCallbackHandler callback = new JaasCertificateCallbackHandler(peerCertificates2);
            LoginContext lc = new LoginContext(this.jaasConfiguration, (CallbackHandler)callback);
            lc.login();
            Subject subject = lc.getSubject();
            String dnName = "";
            for (Principal principal : subject.getPrincipals()) {
                if (!(principal instanceof UserPrincipal)) continue;
                dnName = ((UserPrincipal)principal).getName();
                break;
            }
            return new JaasCertificateSecurityContext(dnName, subject, peerCertificates2);
        }
        catch (Exception e) {
            throw new SecurityException("User name [" + username + "] or password is invalid. " + e.getMessage(), e);
        }
    }
}

