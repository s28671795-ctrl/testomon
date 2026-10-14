/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.activemq.advisory.AdvisoryBroker;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerSubscriptionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.util.SubscriptionKey;
import org.apache.activemq.util.TypeConversionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkBridgeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkBridgeUtils.class);

    public static BrokerSubscriptionInfo getBrokerSubscriptionInfo(BrokerService brokerService, NetworkBridgeConfiguration config) {
        RegionBroker regionBroker = (RegionBroker)brokerService.getRegionBroker();
        TopicRegion topicRegion = (TopicRegion)regionBroker.getTopicRegion();
        HashSet<ConsumerInfo> subscriptionInfos = new HashSet<ConsumerInfo>();
        for (SubscriptionKey key : topicRegion.getDurableSubscriptions().keySet()) {
            DurableTopicSubscription sub = topicRegion.getDurableSubscriptions().get(key);
            if (sub == null || !NetworkBridgeUtils.matchesNetworkConfig(config, sub.getConsumerInfo().getDestination())) continue;
            ConsumerInfo ci = sub.getConsumerInfo().copy();
            ci.setClientId(key.getClientId());
            subscriptionInfos.add(ci);
        }
        for (Subscription sub : topicRegion.getSubscriptions().values()) {
            if (sub == null || !NetworkBridgeUtils.isForcedDurable(sub.getConsumerInfo(), config.getDynamicallyIncludedDestinations())) continue;
            subscriptionInfos.add(sub.getConsumerInfo().copy());
        }
        try {
            AdvisoryBroker ab = (AdvisoryBroker)brokerService.getBroker().getAdaptor(AdvisoryBroker.class);
            if (ab != null && brokerService.isUseVirtualDestSubs() && config.isUseVirtualDestSubs()) {
                for (ConsumerInfo info : ab.getVirtualDestinationConsumers().keySet()) {
                    if (!NetworkBridgeUtils.isForcedDurable(info, config.getDynamicallyIncludedDestinations())) continue;
                    subscriptionInfos.add(info.copy());
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Error processing virtualDestinationSubs for BrokerSubscriptionInfo");
            LOG.debug("Error processing virtualDestinationSubs for BrokerSubscriptionInfo", e);
        }
        BrokerSubscriptionInfo bsi = new BrokerSubscriptionInfo(brokerService.getBrokerName());
        bsi.setSubscriptionInfos(subscriptionInfos.toArray(new ConsumerInfo[0]));
        return bsi;
    }

    public static boolean isForcedDurable(ConsumerInfo info, List<ActiveMQDestination> dynamicallyIncludedDestinations) {
        return dynamicallyIncludedDestinations != null ? NetworkBridgeUtils.isForcedDurable(info, dynamicallyIncludedDestinations.toArray(new ActiveMQDestination[0]), null) : false;
    }

    public static boolean isForcedDurable(ConsumerInfo info, ActiveMQDestination[] dynamicallyIncludedDestinations, ActiveMQDestination[] staticallyIncludedDestinations) {
        if (info.isDurable() || info.getDestination().isQueue()) {
            return false;
        }
        ActiveMQDestination destination = info.getDestination();
        if (AdvisorySupport.isAdvisoryTopic(destination) || destination.isTemporary() || destination.isQueue()) {
            return false;
        }
        ActiveMQDestination matching = NetworkBridgeUtils.findMatchingDestination(dynamicallyIncludedDestinations, destination);
        if (matching != null) {
            return NetworkBridgeUtils.isDestForcedDurable(matching);
        }
        matching = NetworkBridgeUtils.findMatchingDestination(staticallyIncludedDestinations, destination);
        if (matching != null) {
            return NetworkBridgeUtils.isDestForcedDurable(matching);
        }
        return false;
    }

    public static boolean matchesNetworkConfig(NetworkBridgeConfiguration config, ActiveMQDestination destination) {
        List<ActiveMQDestination> includedDests = config.getDynamicallyIncludedDestinations();
        if (includedDests != null && includedDests.size() > 0) {
            for (ActiveMQDestination dest : includedDests) {
                DestinationFilter inclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !inclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean matchesDestinations(ActiveMQDestination[] dests, ActiveMQDestination destination) {
        if (dests != null && dests.length > 0) {
            for (ActiveMQDestination dest : dests) {
                DestinationFilter inclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !inclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return true;
            }
        }
        return false;
    }

    public static ActiveMQDestination findMatchingDestination(ActiveMQDestination[] dests, ActiveMQDestination destination) {
        if (dests != null && dests.length > 0) {
            for (ActiveMQDestination dest : dests) {
                DestinationFilter inclusionFilter = DestinationFilter.parseFilter(dest);
                if (dest == null || !inclusionFilter.matches(destination) || dest.getDestinationType() != destination.getDestinationType()) continue;
                return dest;
            }
        }
        return null;
    }

    public static boolean isDestForcedDurable(ActiveMQDestination destination) {
        Map<String, String> options;
        boolean isForceDurable = false;
        if (destination != null && (options = destination.getOptions()) != null) {
            isForceDurable = (Boolean)TypeConversionSupport.convert(options.get("forceDurable"), Boolean.TYPE);
        }
        return isForceDurable;
    }
}

