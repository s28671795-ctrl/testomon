/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.Endpoint;

public class BaseEndpoint
implements Endpoint {
    private String name;
    private BrokerInfo brokerInfo;

    public BaseEndpoint(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String toString() {
        String brokerText = "";
        BrokerId brokerId = this.getBrokerId();
        if (brokerId != null) {
            brokerText = " broker: " + brokerId;
        }
        return "Endpoint[name:" + this.name + brokerText + "]";
    }

    @Override
    public BrokerId getBrokerId() {
        if (this.brokerInfo != null) {
            return this.brokerInfo.getBrokerId();
        }
        return null;
    }

    @Override
    public BrokerInfo getBrokerInfo() {
        return this.brokerInfo;
    }

    @Override
    public void setBrokerInfo(BrokerInfo brokerInfo) {
        this.brokerInfo = brokerInfo;
    }
}

