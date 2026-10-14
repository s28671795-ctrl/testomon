/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.command.ActiveMQDestination;

public abstract class SecurityContext {
    public static final SecurityContext BROKER_SECURITY_CONTEXT = new SecurityContext("ActiveMQBroker"){

        @Override
        public boolean isBrokerContext() {
            return true;
        }

        @Override
        public Set<Principal> getPrincipals() {
            return Collections.emptySet();
        }
    };
    final String userName;
    final ConcurrentMap<ActiveMQDestination, ActiveMQDestination> authorizedWriteDests = new ConcurrentHashMap<ActiveMQDestination, ActiveMQDestination>();

    public SecurityContext(String userName) {
        this.userName = userName;
    }

    public boolean isInOneOf(Set<?> allowedPrincipals) {
        Iterator<?> allowedIter = allowedPrincipals.iterator();
        HashSet<Principal> userPrincipals = new HashSet<Principal>(this.getPrincipals());
        while (allowedIter.hasNext()) {
            Iterator<Principal> userIter = userPrincipals.iterator();
            Object allowedPrincipal = allowedIter.next();
            while (userIter.hasNext()) {
                if (!allowedPrincipal.equals(userIter.next())) continue;
                return true;
            }
        }
        return false;
    }

    public abstract Set<Principal> getPrincipals();

    public String getUserName() {
        return this.userName;
    }

    public ConcurrentMap<ActiveMQDestination, ActiveMQDestination> getAuthorizedWriteDests() {
        return this.authorizedWriteDests;
    }

    public boolean isBrokerContext() {
        return false;
    }
}

