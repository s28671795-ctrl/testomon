/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.MBeanInfo;

public interface NetworkDestinationViewMBean {
    @MBeanInfo(value="Name of this destination.")
    public String getName();

    @MBeanInfo(value="Resets statistics.")
    public void resetStats();

    @MBeanInfo(value="Number of messages that have been sent to the destination.")
    public long getCount();

    @MBeanInfo(value="rate of messages sent across the network destination.")
    public double getRate();
}

