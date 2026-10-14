/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.GroupPrincipal
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.security.AbstractAuthenticationBroker;
import org.apache.activemq.security.SecurityContext;

public class SimpleAuthenticationBroker
extends AbstractAuthenticationBroker {
    private boolean anonymousAccessAllowed = false;
    private String anonymousUser;
    private String anonymousGroup;
    private Map<String, String> userPasswords;
    private Map<String, Set<Principal>> userGroups;

    public SimpleAuthenticationBroker(Broker next, Map<String, String> userPasswords, Map<String, Set<Principal>> userGroups) {
        super(next);
        this.userPasswords = userPasswords;
        this.userGroups = userGroups;
    }

    public void setAnonymousAccessAllowed(boolean anonymousAccessAllowed) {
        this.anonymousAccessAllowed = anonymousAccessAllowed;
    }

    public void setAnonymousUser(String anonymousUser) {
        this.anonymousUser = anonymousUser;
    }

    public void setAnonymousGroup(String anonymousGroup) {
        this.anonymousGroup = anonymousGroup;
    }

    public void setUserPasswords(Map<String, String> value) {
        this.userPasswords = value;
    }

    public void setUserGroups(Map<String, Set<Principal>> value) {
        this.userGroups = value;
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        SecurityContext securityContext = context.getSecurityContext();
        if (securityContext == null) {
            securityContext = this.authenticate(info.getUserName(), info.getPassword(), null);
            context.setSecurityContext(securityContext);
            this.securityContexts.add(securityContext);
        }
        try {
            super.addConnection(context, info);
        }
        catch (Exception e) {
            this.securityContexts.remove(securityContext);
            context.setSecurityContext(null);
            throw e;
        }
    }

    @Override
    public SecurityContext authenticate(String username, String password, X509Certificate[] certificates) throws SecurityException {
        SecurityContext securityContext = null;
        if (this.anonymousAccessAllowed && username == null && password == null) {
            username = this.anonymousUser;
            securityContext = new SecurityContext(username){

                @Override
                public Set<Principal> getPrincipals() {
                    HashSet<Principal> groups2 = new HashSet<Principal>();
                    groups2.add((Principal)new GroupPrincipal(SimpleAuthenticationBroker.this.anonymousGroup));
                    return groups2;
                }
            };
        } else {
            String pw = this.userPasswords.get(username);
            if (pw == null || !pw.equals(password)) {
                throw new SecurityException("User name [" + username + "] or password is invalid.");
            }
            final Set<Principal> groups2 = this.userGroups.get(username);
            securityContext = new SecurityContext(username){

                @Override
                public Set<Principal> getPrincipals() {
                    return groups2;
                }
            };
        }
        return securityContext;
    }
}

