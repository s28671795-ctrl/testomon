/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.AbortSlowConsumerStrategyViewMBean;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface AbortSlowAckConsumerStrategyViewMBean
extends AbortSlowConsumerStrategyViewMBean {
    @MBeanInfo(value="returns the current max time since last ack setting")
    public long getMaxTimeSinceLastAck();

    @MBeanInfo(value="sets the duration (milliseconds) after which a consumer that doesn't ack a message will be marked as slow")
    public void setMaxTimeSinceLastAck(long var1);

    @MBeanInfo(value="returns the current value of the ignore idle consumers setting.")
    public boolean isIgnoreIdleConsumers();

    @MBeanInfo(value="sets whether consumers that are idle (no dispatched messages) should be included when checking for slow acks.")
    public void setIgnoreIdleConsumers(boolean var1);

    @MBeanInfo(value="returns the current value of the ignore network connector consumers setting.")
    public boolean isIgnoreNetworkConsumers();

    @MBeanInfo(value="sets whether consumers that are from network connector should be included when checking for slow acks.")
    public void setIgnoreNetworkConsumers(boolean var1);
}

