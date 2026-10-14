/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQPrefetchPolicy
implements Serializable {
    public static final int MAX_PREFETCH_SIZE = Short.MAX_VALUE;
    public static final int DEFAULT_QUEUE_PREFETCH = 1000;
    public static final int DEFAULT_QUEUE_BROWSER_PREFETCH = 500;
    public static final int DEFAULT_DURABLE_TOPIC_PREFETCH = 100;
    public static final int DEFAULT_OPTIMIZE_DURABLE_TOPIC_PREFETCH = 1000;
    public static final int DEFAULT_TOPIC_PREFETCH = Short.MAX_VALUE;
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQPrefetchPolicy.class);
    private int queuePrefetch = 1000;
    private int queueBrowserPrefetch = 500;
    private int topicPrefetch = Short.MAX_VALUE;
    private int durableTopicPrefetch = 100;
    private int optimizeDurableTopicPrefetch = 1000;
    private int maximumPendingMessageLimit;

    public int getDurableTopicPrefetch() {
        return this.durableTopicPrefetch;
    }

    public void setDurableTopicPrefetch(int durableTopicPrefetch) {
        this.durableTopicPrefetch = this.getMaxPrefetchLimit(durableTopicPrefetch);
    }

    public int getQueuePrefetch() {
        return this.queuePrefetch;
    }

    public void setQueuePrefetch(int queuePrefetch) {
        this.queuePrefetch = this.getMaxPrefetchLimit(queuePrefetch);
    }

    public int getQueueBrowserPrefetch() {
        return this.queueBrowserPrefetch;
    }

    public void setQueueBrowserPrefetch(int queueBrowserPrefetch) {
        this.queueBrowserPrefetch = this.getMaxPrefetchLimit(queueBrowserPrefetch);
    }

    public int getTopicPrefetch() {
        return this.topicPrefetch;
    }

    public void setTopicPrefetch(int topicPrefetch) {
        this.topicPrefetch = this.getMaxPrefetchLimit(topicPrefetch);
    }

    public int getOptimizeDurableTopicPrefetch() {
        return this.optimizeDurableTopicPrefetch;
    }

    public void setOptimizeDurableTopicPrefetch(int optimizeAcknowledgePrefetch) {
        this.optimizeDurableTopicPrefetch = optimizeAcknowledgePrefetch;
    }

    public int getMaximumPendingMessageLimit() {
        return this.maximumPendingMessageLimit;
    }

    public void setMaximumPendingMessageLimit(int maximumPendingMessageLimit) {
        this.maximumPendingMessageLimit = maximumPendingMessageLimit;
    }

    private int getMaxPrefetchLimit(int value) {
        int result = Math.min(value, Short.MAX_VALUE);
        if (result < value) {
            LOG.warn("maximum prefetch limit has been reset from " + value + " to " + Short.MAX_VALUE);
        }
        return result;
    }

    public void setAll(int i) {
        this.durableTopicPrefetch = this.getMaxPrefetchLimit(i);
        this.queueBrowserPrefetch = this.getMaxPrefetchLimit(i);
        this.queuePrefetch = this.getMaxPrefetchLimit(i);
        this.topicPrefetch = this.getMaxPrefetchLimit(i);
        this.optimizeDurableTopicPrefetch = this.getMaxPrefetchLimit(i);
    }

    public boolean equals(Object object) {
        if (object instanceof ActiveMQPrefetchPolicy) {
            ActiveMQPrefetchPolicy other = (ActiveMQPrefetchPolicy)object;
            return this.queuePrefetch == other.queuePrefetch && this.queueBrowserPrefetch == other.queueBrowserPrefetch && this.topicPrefetch == other.topicPrefetch && this.durableTopicPrefetch == other.durableTopicPrefetch && this.optimizeDurableTopicPrefetch == other.optimizeDurableTopicPrefetch;
        }
        return false;
    }
}

