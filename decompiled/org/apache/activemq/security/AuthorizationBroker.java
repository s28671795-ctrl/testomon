/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.Arrays;
import java.util.Set;
import javax.jms.Destination;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.CompositeDestinationInterceptor;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.security.AuthorizationDestinationInterceptor;
import org.apache.activemq.security.AuthorizationMap;
import org.apache.activemq.security.SecurityAdminMBean;
import org.apache.activemq.security.SecurityContext;

public class AuthorizationBroker
extends BrokerFilter
implements SecurityAdminMBean {
    private volatile AuthorizationMap authorizationMap;

    public AuthorizationBroker(Broker next, AuthorizationMap authorizationMap) {
        super(next);
        this.authorizationMap = authorizationMap;
        RegionBroker regionBroker = (RegionBroker)next.getAdaptor(RegionBroker.class);
        CompositeDestinationInterceptor compositeInterceptor = (CompositeDestinationInterceptor)regionBroker.getDestinationInterceptor();
        DestinationInterceptor[] interceptors = compositeInterceptor.getInterceptors();
        interceptors = Arrays.copyOf(interceptors, interceptors.length + 1);
        interceptors[interceptors.length - 1] = new AuthorizationDestinationInterceptor(this);
        compositeInterceptor.setInterceptors(interceptors);
    }

    public AuthorizationMap getAuthorizationMap() {
        return this.authorizationMap;
    }

    public void setAuthorizationMap(AuthorizationMap map) {
        this.authorizationMap = map;
    }

    protected SecurityContext checkSecurityContext(ConnectionContext context) throws SecurityException {
        SecurityContext securityContext = context.getSecurityContext();
        if (securityContext == null) {
            throw new SecurityException("User is not authenticated.");
        }
        return securityContext;
    }

    protected boolean checkDestinationAdmin(SecurityContext securityContext, ActiveMQDestination destination) {
        org.apache.activemq.broker.region.Destination existing = this.getDestinationMap(destination).get(destination);
        if (existing != null) {
            return true;
        }
        if (!securityContext.isBrokerContext()) {
            Set<?> allowedACLs = null;
            allowedACLs = !destination.isTemporary() ? this.authorizationMap.getAdminACLs(destination) : this.authorizationMap.getTempDestinationAdminACLs();
            if (allowedACLs != null && !securityContext.isInOneOf(allowedACLs)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        if (!this.checkDestinationAdmin(securityContext, info.getDestination())) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to create: " + info.getDestination());
        }
        super.addDestinationInfo(context, info);
    }

    @Override
    public org.apache.activemq.broker.region.Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean create) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        if (!this.checkDestinationAdmin(securityContext, destination)) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to create: " + destination);
        }
        return super.addDestination(context, destination, create);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        if (!this.checkDestinationAdmin(securityContext, destination)) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to remove: " + destination);
        }
        securityContext.getAuthorizedWriteDests().remove(destination);
        super.removeDestination(context, destination, timeout2);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        if (!this.checkDestinationAdmin(securityContext, info.getDestination())) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to remove: " + info.getDestination());
        }
        securityContext.getAuthorizedWriteDests().remove(info.getDestination());
        super.removeDestinationInfo(context, info);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        Set<?> allowedACLs = null;
        allowedACLs = !info.getDestination().isTemporary() ? this.authorizationMap.getReadACLs(info.getDestination()) : this.authorizationMap.getTempDestinationReadACLs();
        if (!securityContext.isBrokerContext() && allowedACLs != null && !securityContext.isInOneOf(allowedACLs)) {
            throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to read from: " + info.getDestination());
        }
        return super.addConsumer(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(context);
        if (!securityContext.isBrokerContext() && info.getDestination() != null) {
            Set<?> allowedACLs = null;
            allowedACLs = !info.getDestination().isTemporary() ? this.authorizationMap.getWriteACLs(info.getDestination()) : this.authorizationMap.getTempDestinationWriteACLs();
            if (allowedACLs != null && !securityContext.isInOneOf(allowedACLs)) {
                throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to write to: " + info.getDestination());
            }
            securityContext.getAuthorizedWriteDests().put(info.getDestination(), info.getDestination());
        }
        super.addProducer(context, info);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        SecurityContext securityContext = this.checkSecurityContext(producerExchange.getConnectionContext());
        if (!securityContext.isBrokerContext() && !securityContext.getAuthorizedWriteDests().containsValue(messageSend.getDestination())) {
            Set<?> allowedACLs = null;
            allowedACLs = !messageSend.getDestination().isTemporary() ? this.authorizationMap.getWriteACLs(messageSend.getDestination()) : this.authorizationMap.getTempDestinationWriteACLs();
            if (allowedACLs != null && !securityContext.isInOneOf(allowedACLs)) {
                throw new SecurityException("User " + securityContext.getUserName() + " is not authorized to write to: " + messageSend.getDestination());
            }
            securityContext.getAuthorizedWriteDests().put(messageSend.getDestination(), messageSend.getDestination());
        }
        super.send(producerExchange, messageSend);
    }

    @Override
    public void addQueueRole(String queue, String operation, String role) {
        this.addDestinationRole(new ActiveMQQueue(queue), operation, role);
    }

    @Override
    public void addTopicRole(String topic, String operation, String role) {
        this.addDestinationRole(new ActiveMQTopic(topic), operation, role);
    }

    @Override
    public void removeQueueRole(String queue, String operation, String role) {
        this.removeDestinationRole(new ActiveMQQueue(queue), operation, role);
    }

    @Override
    public void removeTopicRole(String topic, String operation, String role) {
        this.removeDestinationRole(new ActiveMQTopic(topic), operation, role);
    }

    public void addDestinationRole(Destination destination, String operation, String role) {
    }

    public void removeDestinationRole(Destination destination, String operation, String role) {
    }

    @Override
    public void addRole(String role) {
    }

    @Override
    public void addUserRole(String user, String role) {
    }

    @Override
    public void removeRole(String role) {
    }

    @Override
    public void removeUserRole(String user, String role) {
    }
}

