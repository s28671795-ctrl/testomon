/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.UserPrincipal
 */
package org.apache.activemq.broker;

import java.security.Principal;
import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;
import org.apache.activemq.jaas.UserPrincipal;
import org.apache.activemq.security.SecurityContext;

public class UserIDBroker
extends BrokerFilter {
    boolean useAuthenticatePrincipal = false;

    public UserIDBroker(Broker next) {
        super(next);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        Set<Principal> principals;
        SecurityContext securityContext;
        ConnectionContext context = producerExchange.getConnectionContext();
        String userID = context.getUserName();
        if (this.isUseAuthenticatePrincipal() && (securityContext = context.getSecurityContext()) != null && (principals = securityContext.getPrincipals()) != null) {
            for (Principal candidate : principals) {
                if (!(candidate instanceof UserPrincipal)) continue;
                userID = ((UserPrincipal)candidate).getName();
                break;
            }
        }
        messageSend.setUserID(userID);
        super.send(producerExchange, messageSend);
    }

    public boolean isUseAuthenticatePrincipal() {
        return this.useAuthenticatePrincipal;
    }

    public void setUseAuthenticatePrincipal(boolean useAuthenticatePrincipal) {
        this.useAuthenticatePrincipal = useAuthenticatePrincipal;
    }
}

