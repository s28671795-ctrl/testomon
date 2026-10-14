/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.Set;
import javax.management.ObjectName;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.util.IOExceptionSupport;

public class ConnectionView
implements ConnectionViewMBean {
    private final Connection connection;
    private final ManagementContext managementContext;
    private String userName;

    public ConnectionView(Connection connection) {
        this(connection, null);
    }

    public ConnectionView(Connection connection, ManagementContext managementContext) {
        this.connection = connection;
        this.managementContext = managementContext;
    }

    @Override
    public void start() throws Exception {
        this.connection.start();
    }

    @Override
    public void stop() throws Exception {
        this.connection.stop();
    }

    @Override
    public boolean isSlow() {
        return this.connection.isSlow();
    }

    @Override
    public boolean isBlocked() {
        return this.connection.isBlocked();
    }

    @Override
    public boolean isConnected() {
        return this.connection.isConnected();
    }

    @Override
    public boolean isActive() {
        return this.connection.isActive();
    }

    @Override
    public int getDispatchQueueSize() {
        return this.connection.getDispatchQueueSize();
    }

    @Override
    public void resetStatistics() {
        this.connection.getStatistics().reset();
    }

    @Override
    public String getRemoteAddress() {
        return this.connection.getRemoteAddress();
    }

    @Override
    public String getClientId() {
        return this.connection.getConnectionId();
    }

    public String getConnectionId() {
        return this.connection.getConnectionId();
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public ObjectName[] getConsumers() {
        ObjectName[] result = null;
        if (this.connection != null && this.managementContext != null) {
            try {
                ObjectName query = this.createConsumerQueury(this.connection.getConnectionId());
                Set<ObjectName> names = this.managementContext.queryNames(query, null);
                result = names.toArray(new ObjectName[0]);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return result;
    }

    @Override
    public ObjectName[] getProducers() {
        ObjectName[] result = null;
        if (this.connection != null && this.managementContext != null) {
            try {
                ObjectName query = this.createProducerQueury(this.connection.getConnectionId());
                Set<ObjectName> names = this.managementContext.queryNames(query, null);
                result = names.toArray(new ObjectName[0]);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return result;
    }

    private ObjectName createConsumerQueury(String clientId) throws IOException {
        try {
            return BrokerMBeanSupport.createConsumerQueury(this.managementContext.getJmxDomainName(), clientId);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create(e);
        }
    }

    private ObjectName createProducerQueury(String clientId) throws IOException {
        try {
            return BrokerMBeanSupport.createProducerQueury(this.managementContext.getJmxDomainName(), clientId);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create(e);
        }
    }

    @Override
    public int getActiveTransactionCount() {
        return this.connection.getActiveTransactionCount();
    }

    @Override
    public Long getOldestActiveTransactionDuration() {
        return this.connection.getOldestActiveTransactionDuration();
    }
}

