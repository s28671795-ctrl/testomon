/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import org.apache.activemq.Service;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.region.ConnectionStatistics;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.Response;

public interface Connection
extends Service {
    public Connector getConnector();

    public void dispatchSync(Command var1);

    public void dispatchAsync(Command var1);

    public Response service(Command var1);

    public void serviceException(Throwable var1);

    public boolean isSlow();

    public boolean isBlocked();

    public boolean isConnected();

    public boolean isActive();

    public int getDispatchQueueSize();

    public ConnectionStatistics getStatistics();

    public boolean isManageable();

    public String getRemoteAddress();

    public void serviceExceptionAsync(IOException var1);

    public String getConnectionId();

    public boolean isNetworkConnection();

    public boolean isFaultTolerantConnection();

    public void updateClient(ConnectionControl var1);

    public int getActiveTransactionCount();

    public Long getOldestActiveTransactionDuration();
}

