/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.util.Arrays;
import java.util.List;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.NetworkBridgeFilter;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.network.NetworkBridgeFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionalNetworkBridgeFilterFactory
implements NetworkBridgeFilterFactory {
    boolean replayWhenNoConsumers = false;
    int replayDelay = 0;
    int rateLimit = 0;
    int rateDuration = 1000;

    @Override
    public NetworkBridgeFilter create(ConsumerInfo info, BrokerId[] remoteBrokerPath, int messageTTL, int consumerTTL) {
        ConditionalNetworkBridgeFilter filter2 = new ConditionalNetworkBridgeFilter();
        filter2.setNetworkBrokerId(remoteBrokerPath[0]);
        filter2.setMessageTTL(messageTTL);
        filter2.setConsumerTTL(consumerTTL);
        filter2.setAllowReplayWhenNoConsumers(this.isReplayWhenNoConsumers());
        filter2.setRateLimit(this.getRateLimit());
        filter2.setRateDuration(this.getRateDuration());
        filter2.setReplayDelay(this.getReplayDelay());
        return filter2;
    }

    public void setReplayWhenNoConsumers(boolean replayWhenNoConsumers) {
        this.replayWhenNoConsumers = replayWhenNoConsumers;
    }

    public boolean isReplayWhenNoConsumers() {
        return this.replayWhenNoConsumers;
    }

    public void setRateLimit(int rateLimit) {
        this.rateLimit = rateLimit;
    }

    public int getRateLimit() {
        return this.rateLimit;
    }

    public int getRateDuration() {
        return this.rateDuration;
    }

    public void setRateDuration(int rateDuration) {
        this.rateDuration = rateDuration;
    }

    public int getReplayDelay() {
        return this.replayDelay;
    }

    public void setReplayDelay(int replayDelay) {
        this.replayDelay = replayDelay;
    }

    private static class ConditionalNetworkBridgeFilter
    extends NetworkBridgeFilter {
        static final Logger LOG = LoggerFactory.getLogger(ConditionalNetworkBridgeFilter.class);
        private int rateLimit;
        private int rateDuration = 1000;
        private boolean allowReplayWhenNoConsumers = true;
        private int replayDelay = 1000;
        private int matchCount;
        private long rateDurationEnd;

        private ConditionalNetworkBridgeFilter() {
        }

        @Override
        protected boolean matchesForwardingFilter(Message message, MessageEvaluationContext mec) {
            boolean match = true;
            if (mec.getDestination().isQueue() && ConditionalNetworkBridgeFilter.contains(message.getBrokerPath(), this.networkBrokerId)) {
                boolean bl = match = this.allowReplayWhenNoConsumers && this.hasNoLocalConsumers(message, mec) && this.hasNotJustArrived(message);
                if (match) {
                    LOG.trace("Replaying [{}] for [{}] back to origin in the absence of a local consumer", (Object)message.getMessageId(), (Object)message.getDestination());
                } else {
                    LOG.trace("Suppressing replay of [{}] for [{}] back to origin {}", message.getMessageId(), message.getDestination(), Arrays.asList(message.getBrokerPath()));
                }
            } else {
                match = super.matchesForwardingFilter(message, mec);
            }
            if (match && this.rateLimitExceeded()) {
                LOG.trace("Throttled network consumer rejecting [{}] for [{}] {}>{}/{}", message.getMessageId(), message.getDestination(), this.matchCount, this.rateLimit, this.rateDuration);
                match = false;
            }
            return match;
        }

        private boolean hasNotJustArrived(Message message) {
            return this.replayDelay == 0 || message.getBrokerInTime() + (long)this.replayDelay < System.currentTimeMillis();
        }

        private boolean hasNoLocalConsumers(Message message, MessageEvaluationContext mec) {
            Destination regionDestination = (Destination)mec.getMessageReference().getRegionDestination();
            List<Subscription> consumers = regionDestination.getConsumers();
            for (Subscription sub : consumers) {
                if (sub.getConsumerInfo().isNetworkSubscription() || sub.getConsumerInfo().isBrowser()) continue;
                LOG.trace("Not replaying [{}] for [{}] to origin due to existing local consumer: {}", message.getMessageId(), message.getDestination(), sub.getConsumerInfo());
                return false;
            }
            return true;
        }

        private boolean rateLimitExceeded() {
            if (this.rateLimit == 0) {
                return false;
            }
            if (this.rateDurationEnd < System.currentTimeMillis()) {
                this.rateDurationEnd = System.currentTimeMillis() + (long)this.rateDuration;
                this.matchCount = 0;
            }
            return ++this.matchCount > this.rateLimit;
        }

        public void setReplayDelay(int replayDelay) {
            this.replayDelay = replayDelay;
        }

        public void setRateLimit(int rateLimit) {
            this.rateLimit = rateLimit;
        }

        public void setRateDuration(int rateDuration) {
            this.rateDuration = rateDuration;
        }

        public void setAllowReplayWhenNoConsumers(boolean allowReplayWhenNoConsumers) {
            this.allowReplayWhenNoConsumers = allowReplayWhenNoConsumers;
        }
    }
}

