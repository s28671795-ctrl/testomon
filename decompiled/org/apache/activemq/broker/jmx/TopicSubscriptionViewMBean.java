/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.MBeanInfo;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;

public interface TopicSubscriptionViewMBean
extends SubscriptionViewMBean {
    @MBeanInfo(value="Number of messages discared due to being a slow consumer")
    public int getDiscardedCount();

    @MBeanInfo(value="Maximum number of messages that can be pending")
    public int getMaximumPendingQueueSize();

    public void setMaximumPendingQueueSize(int var1);
}

