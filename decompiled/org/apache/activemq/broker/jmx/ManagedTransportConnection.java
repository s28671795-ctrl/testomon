/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import javax.management.ObjectName;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.ConnectionView;
import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.IOExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagedTransportConnection
extends TransportConnection {
    private static final Logger LOG = LoggerFactory.getLogger(ManagedTransportConnection.class);
    private final ManagementContext managementContext;
    private final ObjectName connectorName;
    private final ConnectionViewMBean mbean;
    private ObjectName byClientIdName;
    private ObjectName byAddressName;
    private final boolean populateUserName;

    public ManagedTransportConnection(TransportConnector connector, Transport transport, Broker broker, TaskRunnerFactory factory2, TaskRunnerFactory stopFactory, ManagementContext context, ObjectName connectorName) throws IOException {
        super(connector, transport, broker, factory2, stopFactory);
        this.managementContext = context;
        this.connectorName = connectorName;
        this.mbean = new ConnectionView(this, this.managementContext);
        this.populateUserName = broker.getBrokerService().isPopulateUserNameInMBeans();
        if (this.managementContext.isAllowRemoteAddressInMBeanNames()) {
            this.byAddressName = this.createObjectName("remoteAddress", transport.getRemoteAddress());
            this.registerMBean(this.byAddressName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stopAsync() {
        super.stopAsync();
        ManagedTransportConnection managedTransportConnection = this;
        synchronized (managedTransportConnection) {
            this.unregisterMBean(this.byClientIdName);
            this.unregisterMBean(this.byAddressName);
            this.byClientIdName = null;
            this.byAddressName = null;
        }
    }

    @Override
    public Response processAddConnection(ConnectionInfo info) throws Exception {
        Response answer = super.processAddConnection(info);
        String clientId = info.getClientId();
        if (this.populateUserName) {
            ((ConnectionView)this.mbean).setUserName(info.getUserName());
        }
        if (clientId != null && this.byClientIdName == null) {
            this.byClientIdName = this.createObjectName("clientId", clientId);
            this.registerMBean(this.byClientIdName);
        }
        return answer;
    }

    protected void registerMBean(ObjectName name) {
        if (name != null) {
            try {
                AnnotatedMBean.registerMBean(this.managementContext, this.mbean, name);
            }
            catch (Throwable e) {
                LOG.warn("Failed to register MBean {}", (Object)name);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

    protected void unregisterMBean(ObjectName name) {
        if (name != null) {
            try {
                this.managementContext.unregisterMBean(name);
            }
            catch (Throwable e) {
                LOG.warn("Failed to unregister MBean {}", (Object)name);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

    protected ObjectName createObjectName(String type, String value) throws IOException {
        try {
            return BrokerMBeanSupport.createConnectionViewByType(this.connectorName, type, value);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create(e);
        }
    }
}

