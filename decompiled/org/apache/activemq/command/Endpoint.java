/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;

public interface Endpoint {
    public String getName();

    public BrokerId getBrokerId();

    public BrokerInfo getBrokerInfo();

    public void setBrokerInfo(BrokerInfo var1);
}

