/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.security.AuthenticationBroker;
import org.apache.activemq.security.SecurityContext;

public abstract class AbstractAuthenticationBroker
extends BrokerFilter
implements AuthenticationBroker {
    protected final CopyOnWriteArrayList<SecurityContext> securityContexts = new CopyOnWriteArrayList();

    public AbstractAuthenticationBroker(Broker next) {
        super(next);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        this.next.removeDestination(context, destination, timeout2);
        for (SecurityContext sc : this.securityContexts) {
            sc.getAuthorizedWriteDests().remove(destination);
        }
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        super.removeConnection(context, info, error);
        if (this.securityContexts.remove(context.getSecurityContext())) {
            context.setSecurityContext(null);
        }
    }

    public void refresh() {
        for (SecurityContext sc : this.securityContexts) {
            sc.getAuthorizedWriteDests().clear();
        }
    }
}

