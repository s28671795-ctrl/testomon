/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.MBeanInfo;

public interface PersistenceAdapterViewMBean {
    @MBeanInfo(value="Name of this persistence adapter.")
    public String getName();

    @MBeanInfo(value="Inflight transactions.")
    public String getTransactions();

    @MBeanInfo(value="Current data.")
    public String getData();

    @MBeanInfo(value="Current size.")
    public long getSize();

    @MBeanInfo(value="Statistics related to the PersistentAdapter.")
    public String getStatistics();

    @MBeanInfo(value="Resets statistics.")
    public String resetStatistics();
}

