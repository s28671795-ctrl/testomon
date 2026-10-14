/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.SubscriptionView;
import org.apache.activemq.broker.jmx.TopicSubscriptionViewMBean;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.TopicSubscription;

public class TopicSubscriptionView
extends SubscriptionView
implements TopicSubscriptionViewMBean {
    public TopicSubscriptionView(String clientId, String userName, TopicSubscription subs) {
        super(clientId, userName, subs);
    }

    protected TopicSubscription getTopicSubscription() {
        return (TopicSubscription)this.subscription;
    }

    @Override
    public int getDiscardedCount() {
        TopicSubscription topicSubscription = this.getTopicSubscription();
        return topicSubscription != null ? topicSubscription.discarded() : 0;
    }

    @Override
    public int getMaximumPendingQueueSize() {
        TopicSubscription topicSubscription = this.getTopicSubscription();
        return topicSubscription != null ? topicSubscription.getMaximumPendingMessages() : 0;
    }

    @Override
    public void setMaximumPendingQueueSize(int max) {
        TopicSubscription topicSubscription = this.getTopicSubscription();
        if (topicSubscription != null) {
            topicSubscription.setMaximumPendingMessages(max);
        }
    }

    @Override
    public boolean isActive() {
        if (this.subscription instanceof DurableTopicSubscription) {
            return ((DurableTopicSubscription)this.subscription).isActive();
        }
        return super.isActive();
    }
}

