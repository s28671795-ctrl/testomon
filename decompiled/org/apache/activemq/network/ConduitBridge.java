/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.network.DemandForwardingBridge;
import org.apache.activemq.network.DemandSubscription;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConduitBridge
extends DemandForwardingBridge {
    private static final Logger LOG = LoggerFactory.getLogger(ConduitBridge.class);

    public ConduitBridge(NetworkBridgeConfiguration configuration, Transport localBroker, Transport remoteBroker) {
        super(configuration, localBroker, remoteBroker);
    }

    @Override
    protected DemandSubscription createDemandSubscription(ConsumerInfo info) throws IOException {
        if (this.addToAlreadyInterestedConsumers(info, false)) {
            return null;
        }
        info.addNetworkConsumerId(info.getConsumerId());
        info.setSelector(null);
        return this.doCreateDemandSubscription(info);
    }

    protected boolean addToAlreadyInterestedConsumers(ConsumerInfo info, boolean isForcedDurable) {
        if (info.isNetworkSubscription() && info.getDestination().isQueue() && !this.configuration.isConduitNetworkQueueSubscriptions()) {
            return false;
        }
        boolean matched = false;
        for (DemandSubscription ds : this.subscriptionMapByLocalId.values()) {
            DestinationFilter filter2 = DestinationFilter.parseFilter(ds.getLocalInfo().getDestination());
            if (!this.canConduit(ds) || !filter2.matches(info.getDestination())) continue;
            LOG.debug("{} {} with ids {} matched (add interest) {}", this.configuration.getBrokerName(), info, info.getNetworkConsumerIds(), ds);
            if (!info.isDurable()) {
                ds.add(info.getConsumerId());
                if (isForcedDurable) {
                    this.forcedDurableRemoteId.add(info.getConsumerId());
                    ds.addForcedDurableConsumer(info.getConsumerId());
                }
            } else if (this.isProxyNSConsumerBrokerPath(info) && info.getSubscriptionName() != null && info.getSubscriptionName().startsWith("NC-DS_")) {
                BrokerId[] path = info.getBrokerPath();
                this.addProxyNetworkSubscriptionBrokerPath(ds, path, info.getSubscriptionName());
            } else if (this.isProxyNSConsumerClientId(info.getClientId()) && this.isProxyBridgeSubscription(info.getClientId(), info.getSubscriptionName())) {
                this.addProxyNetworkSubscriptionClientId(ds, info.getClientId(), info.getSubscriptionName());
            } else {
                ds.getDurableRemoteSubs().add(new SubscriptionInfo(info.getClientId(), info.getSubscriptionName()));
            }
            matched = true;
        }
        return matched;
    }

    private boolean canConduit(DemandSubscription ds) {
        return ds.isStaticallyIncluded() || ds.getRemoteInfo().getDestination().isTopic() || !ds.getRemoteInfo().isNetworkSubscription() || ds.getRemoteInfo().getDestination().isQueue() && this.configuration.isConduitNetworkQueueSubscriptions();
    }

    @Override
    protected void removeDemandSubscription(ConsumerId id) throws IOException {
        ArrayList<DemandSubscription> tmpList = new ArrayList<DemandSubscription>();
        for (DemandSubscription ds : this.subscriptionMapByLocalId.values()) {
            if (ds.remove(id)) {
                LOG.debug("{} on {} from {} removed interest for: {} from {}", this.configuration.getBrokerName(), this.localBroker, this.remoteBrokerName, id, ds);
            }
            if (!ds.isEmpty()) continue;
            tmpList.add(ds);
        }
        for (DemandSubscription ds : tmpList) {
            this.removeSubscription(ds);
            LOG.debug("{} on {} from {} removed {}", this.configuration.getBrokerName(), this.localBroker, this.remoteBrokerName, ds);
        }
    }
}

