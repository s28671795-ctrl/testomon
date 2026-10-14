/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.io.IOException;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.network.ConduitBridge;
import org.apache.activemq.network.DemandSubscription;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.NetworkBridgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurableConduitBridge
extends ConduitBridge {
    private static final Logger LOG = LoggerFactory.getLogger(DurableConduitBridge.class);

    public String toString() {
        return "DurableConduitBridge:" + this.configuration.getBrokerName() + "->" + this.getRemoteBrokerName();
    }

    public DurableConduitBridge(NetworkBridgeConfiguration configuration, Transport localBroker, Transport remoteBroker) {
        super(configuration, localBroker, remoteBroker);
    }

    @Override
    protected void setupStaticDestinations() {
        ActiveMQDestination[] dests;
        super.setupStaticDestinations();
        ActiveMQDestination[] activeMQDestinationArray = dests = this.configuration.isDynamicOnly() ? null : this.durableDestinations;
        if (dests != null) {
            block4: for (ActiveMQDestination dest : dests) {
                String subName;
                String candidateSubName;
                TopicRegion topicRegion;
                RegionBroker regionBroker;
                if (this.isPermissableDestination(dest) && !this.doesConsumerExist(dest)) {
                    block9: {
                        try {
                            if (!dest.isTopic()) break block9;
                            regionBroker = (RegionBroker)this.brokerService.getRegionBroker();
                            topicRegion = (TopicRegion)regionBroker.getTopicRegion();
                            candidateSubName = this.getSubscriberName(dest);
                            for (Subscription subscription : topicRegion.getDurableSubscriptions().values()) {
                                DemandSubscription sub;
                                subName = subscription.getConsumerInfo().getSubscriptionName();
                                String clientId = subscription.getContext().getClientId();
                                if (subName == null || !subName.equals(candidateSubName) || !clientId.startsWith(this.configuration.getName()) || (sub = this.createDemandSubscription(dest, subName)) == null) continue;
                                sub.getLocalInfo().setSubscriptionName(this.getSubscriberName(dest));
                                sub.setStaticallyIncluded(true);
                                this.addSubscription(sub);
                                break;
                            }
                        }
                        catch (IOException e) {
                            LOG.error("Failed to add static destination {}", (Object)dest, (Object)e);
                        }
                    }
                    LOG.trace("Forwarding messages for durable destination: {}", (Object)dest);
                    continue;
                }
                if (!this.configuration.isSyncDurableSubs() || this.isPermissableDestination(dest) || !dest.isTopic()) continue;
                regionBroker = (RegionBroker)this.brokerService.getRegionBroker();
                topicRegion = (TopicRegion)regionBroker.getTopicRegion();
                candidateSubName = this.getSubscriberName(dest);
                for (Subscription subscription : topicRegion.getDurableSubscriptions().values()) {
                    subName = subscription.getConsumerInfo().getSubscriptionName();
                    if (subName == null || !subName.equals(candidateSubName) || !(subscription instanceof DurableTopicSubscription)) continue;
                    try {
                        DurableTopicSubscription durableSub = (DurableTopicSubscription)subscription;
                        if (!durableSub.getSubscriptionKey().getClientId().equals(this.localClientId)) continue block4;
                        RemoveSubscriptionInfo sending = new RemoveSubscriptionInfo();
                        sending.setClientId(this.localClientId);
                        sending.setSubscriptionName(subName);
                        sending.setConnectionId(this.localConnectionInfo.getConnectionId());
                        this.localBroker.oneway(sending);
                    }
                    catch (IOException e) {
                        LOG.debug("Exception removing NC durable subscription: {}", (Object)subName, (Object)e);
                        this.serviceRemoteException(e);
                    }
                    continue block4;
                }
            }
        }
    }

    @Override
    protected DemandSubscription createDemandSubscription(ConsumerInfo info) throws IOException {
        ConsumerId forcedDurableId;
        boolean isForcedDurable = NetworkBridgeUtils.isForcedDurable(info, this.dynamicallyIncludedDestinations, this.staticallyIncludedDestinations);
        if (this.addToAlreadyInterestedConsumers(info, isForcedDurable)) {
            return null;
        }
        info.addNetworkConsumerId(info.getConsumerId());
        ConsumerId consumerId = forcedDurableId = isForcedDurable ? info.getConsumerId() : null;
        if (info.isDurable() || isForcedDurable) {
            info.setSubscriptionName(this.getSubscriberName(info.getDestination()));
            info.setConsumerId(new ConsumerId(this.localSessionInfo.getSessionId(), this.consumerIdGenerator.getNextSequenceId()));
        }
        info.setSelector(null);
        DemandSubscription demandSubscription = this.doCreateDemandSubscription(info);
        if (forcedDurableId != null) {
            demandSubscription.addForcedDurableConsumer(forcedDurableId);
            this.forcedDurableRemoteId.add(forcedDurableId);
        }
        return demandSubscription;
    }

    protected String getSubscriberName(ActiveMQDestination dest) {
        String subscriberName = "NC-DS_" + this.configuration.getBrokerName() + "_" + dest.getPhysicalName();
        return subscriberName;
    }

    protected boolean doesConsumerExist(ActiveMQDestination dest) {
        DestinationFilter filter2 = DestinationFilter.parseFilter(dest);
        for (DemandSubscription ds : this.subscriptionMapByLocalId.values()) {
            if (!filter2.matches(ds.getLocalInfo().getDestination())) continue;
            return true;
        }
        return false;
    }
}

