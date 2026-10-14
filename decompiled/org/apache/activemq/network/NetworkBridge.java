/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import javax.management.ObjectName;
import org.apache.activemq.Service;
import org.apache.activemq.network.NetworkBridgeListener;
import org.apache.activemq.network.NetworkBridgeStatistics;

public interface NetworkBridge
extends Service {
    public void serviceRemoteException(Throwable var1);

    public void serviceLocalException(Throwable var1);

    public void setNetworkBridgeListener(NetworkBridgeListener var1);

    public String getRemoteAddress();

    public String getRemoteBrokerName();

    public String getRemoteBrokerId();

    public String getLocalAddress();

    public String getLocalBrokerName();

    public long getEnqueueCounter();

    public long getDequeueCounter();

    public NetworkBridgeStatistics getNetworkBridgeStatistics();

    public void setMbeanObjectName(ObjectName var1);

    public ObjectName getMbeanObjectName();

    public void resetStats();
}

