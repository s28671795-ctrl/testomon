/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.Service;

public interface NetworkBridgeViewMBean
extends Service {
    public String getLocalAddress();

    public String getRemoteAddress();

    public String getRemoteBrokerName();

    public String getRemoteBrokerId();

    public String getLocalBrokerName();

    public long getEnqueueCounter();

    public long getDequeueCounter();

    public long getReceivedCounter();

    public boolean isCreatedByDuplex();

    public void resetStats();
}

