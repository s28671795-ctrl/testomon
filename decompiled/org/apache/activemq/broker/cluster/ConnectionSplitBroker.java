/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.cluster;

import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionSplitBroker
extends BrokerFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionSplitBroker.class);
    private List<ConsumerInfo> networkConsumerList = new ArrayList<ConsumerInfo>();

    public ConnectionSplitBroker(Broker next) {
        super(next);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        ActiveMQDestination dest = info.getDestination();
        List<ConsumerInfo> list = this.networkConsumerList;
        synchronized (list) {
            if (info.isNetworkSubscription()) {
                this.networkConsumerList.add(info);
            } else if (!this.networkConsumerList.isEmpty()) {
                ArrayList<ConsumerInfo> gcList = new ArrayList<ConsumerInfo>();
                for (ConsumerInfo nc : this.networkConsumerList) {
                    if (nc.isNetworkConsumersEmpty()) continue;
                    for (ConsumerId id : nc.getNetworkConsumerIds()) {
                        if (!id.equals(info.getConsumerId())) continue;
                        nc.removeNetworkConsumerId(id);
                        if (!nc.isNetworkConsumersEmpty()) continue;
                        gcList.add(nc);
                    }
                }
                for (ConsumerInfo nc : gcList) {
                    this.networkConsumerList.remove(nc);
                    super.removeConsumer(context, nc);
                    LOG.warn("Removed stale network consumer {}", (Object)nc);
                }
            }
        }
        return super.addConsumer(context, info);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (info.isNetworkSubscription()) {
            List<ConsumerInfo> list = this.networkConsumerList;
            synchronized (list) {
                this.networkConsumerList.remove(info);
            }
        }
        super.removeConsumer(context, info);
    }
}

