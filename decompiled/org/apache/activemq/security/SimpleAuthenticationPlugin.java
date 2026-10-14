/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.GroupPrincipal
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationBroker;

public class SimpleAuthenticationPlugin
implements BrokerPlugin {
    private Map<String, String> userPasswords = new HashMap<String, String>();
    private Map<String, Set<Principal>> userGroups = new HashMap<String, Set<Principal>>();
    private static final String DEFAULT_ANONYMOUS_USER = "anonymous";
    private static final String DEFAULT_ANONYMOUS_GROUP = "anonymous";
    private String anonymousUser = "anonymous";
    private String anonymousGroup = "anonymous";
    private boolean anonymousAccessAllowed = false;

    public SimpleAuthenticationPlugin() {
    }

    public SimpleAuthenticationPlugin(List<?> users) {
        this.setUsers(users);
    }

    @Override
    public Broker installPlugin(Broker parent) {
        SimpleAuthenticationBroker broker = new SimpleAuthenticationBroker(parent, this.userPasswords, this.userGroups);
        broker.setAnonymousAccessAllowed(this.anonymousAccessAllowed);
        broker.setAnonymousUser(this.anonymousUser);
        broker.setAnonymousGroup(this.anonymousGroup);
        return broker;
    }

    public Map<String, Set<Principal>> getUserGroups() {
        return this.userGroups;
    }

    public void setUsers(List<?> users) {
        this.userPasswords.clear();
        this.userGroups.clear();
        for (AuthenticationUser user : users) {
            this.userPasswords.put(user.getUsername(), user.getPassword());
            HashSet<GroupPrincipal> groups2 = new HashSet<GroupPrincipal>();
            if (user.getGroups() != null) {
                StringTokenizer iter = new StringTokenizer(user.getGroups(), ",");
                while (iter.hasMoreTokens()) {
                    String name = iter.nextToken().trim();
                    groups2.add(new GroupPrincipal(name));
                }
            }
            this.userGroups.put(user.getUsername(), groups2);
        }
    }

    public void setAnonymousAccessAllowed(boolean anonymousAccessAllowed) {
        this.anonymousAccessAllowed = anonymousAccessAllowed;
    }

    public boolean isAnonymousAccessAllowed() {
        return this.anonymousAccessAllowed;
    }

    public void setAnonymousUser(String anonymousUser) {
        this.anonymousUser = anonymousUser;
    }

    public String getAnonymousUser() {
        return this.anonymousUser;
    }

    public void setAnonymousGroup(String anonymousGroup) {
        this.anonymousGroup = anonymousGroup;
    }

    public String getAnonymousGroup() {
        return this.anonymousGroup;
    }

    public void setUserGroups(Map<String, Set<Principal>> userGroups) {
        this.userGroups = userGroups;
    }

    public Map<String, String> getUserPasswords() {
        return this.userPasswords;
    }

    public void setUserPasswords(Map<String, String> userPasswords) {
        this.userPasswords = userPasswords;
    }
}

