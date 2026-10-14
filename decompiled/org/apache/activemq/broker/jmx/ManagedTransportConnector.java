/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.ManagedTransportConnection;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;

public class ManagedTransportConnector
extends TransportConnector {
    static long nextConnectionId = 1L;
    private final ManagementContext managementContext;
    private final ObjectName connectorName;

    public ManagedTransportConnector(ManagementContext context, ObjectName connectorName, TransportServer server) {
        super(server);
        this.managementContext = context;
        this.connectorName = connectorName;
    }

    public ManagedTransportConnector asManagedConnector(MBeanServer mbeanServer, ObjectName connectorName) throws IOException, URISyntaxException {
        return this;
    }

    @Override
    protected Connection createConnection(Transport transport) throws IOException {
        return new ManagedTransportConnection(this, transport, this.getBroker(), this.isDisableAsyncDispatch() ? null : this.getTaskRunnerFactory(), this.getBrokerService().getTaskRunnerFactory(), this.managementContext, this.connectorName);
    }

    protected static synchronized long getNextConnectionId() {
        return nextConnectionId++;
    }
}

