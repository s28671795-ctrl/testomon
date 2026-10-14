/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.Set;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.security.AuthorizationBroker;
import org.apache.activemq.security.AuthorizationMap;
import org.apache.activemq.security.SecurityContext;

public class AuthorizationDestinationFilter
extends DestinationFilter {
    private final AuthorizationBroker broker;

    public AuthorizationDestinationFilter(Destination destination, AuthorizationBroker broker) {
        super(destination);
        this.broker = broker;
    }

    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        SecurityContext securityContext = this.broker.checkSecurityContext(context);
        AuthorizationMap authorizationMap = this.broker.getAuthorizationMap();
        ActiveMQDestination destination = this.next.getActiveMQDestination();
        Set<?> allowedACLs = !destination.isTemporary() ? authorizationMap.getReadACLs(destination) : authorizationMap.getTempDestinationReadACLs();
        if (!securityContext.isBrokerContext() && allowedACLs != null && !securityContext.isInOneOf(allowedACLs)) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to read from: " + destination);
        }
        super.addSubscription(context, sub);
    }
}

