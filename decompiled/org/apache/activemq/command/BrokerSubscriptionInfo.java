/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class BrokerSubscriptionInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 92;
    BrokerId brokerId;
    String brokerName;
    ConsumerInfo[] subscriptionInfos;

    public BrokerSubscriptionInfo() {
    }

    public BrokerSubscriptionInfo(String brokerName) {
        this.brokerName = brokerName;
    }

    public BrokerSubscriptionInfo copy() {
        BrokerSubscriptionInfo copy = new BrokerSubscriptionInfo();
        this.copy(copy);
        return copy;
    }

    private void copy(BrokerSubscriptionInfo copy) {
        super.copy(copy);
        copy.subscriptionInfos = this.subscriptionInfos;
        copy.brokerName = this.brokerName;
        copy.brokerId = this.brokerId;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processBrokerSubscriptionInfo(this);
    }

    @Override
    public byte getDataStructureType() {
        return 92;
    }

    public BrokerId getBrokerId() {
        return this.brokerId;
    }

    public void setBrokerId(BrokerId brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public ConsumerInfo[] getSubscriptionInfos() {
        return this.subscriptionInfos;
    }

    public void setSubscriptionInfos(ConsumerInfo[] subscriptionInfos) {
        this.subscriptionInfos = subscriptionInfos;
    }
}

