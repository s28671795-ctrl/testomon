/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.jmx.MBeanInfo;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;

public interface DurableSubscriptionViewMBean
extends SubscriptionViewMBean {
    @Override
    @MBeanInfo(value="The subscription name.")
    public String getSubscriptionName();

    @MBeanInfo(value="Browse the composite data array of pending messages in this subscription")
    public CompositeData[] browse() throws OpenDataException;

    @MBeanInfo(value="Browse the tabular data of pending messages in this subscription")
    public TabularData browseAsTable() throws OpenDataException;

    @MBeanInfo(value="Destroy or delete this subscription")
    public void destroy() throws Exception;

    @MBeanInfo(value="The subscription has space for more messages in memory")
    public boolean doesCursorHaveSpace();

    @MBeanInfo(value="The subscription cursor is full")
    public boolean isCursorFull();

    @MBeanInfo(value="The subscription cursor has messages in memory")
    public boolean doesCursorHaveMessagesBuffered();

    @MBeanInfo(value="The subscription cursor memory usage bytes")
    public long getCursorMemoryUsage();

    @MBeanInfo(value="The subscription cursor memory usage %")
    public int getCursorPercentUsage();

    @MBeanInfo(value="The subscription cursor size or message count")
    public int cursorSize();

    @MBeanInfo(value="Remove a message from the subscription by JMS message ID.")
    public void removeMessage(@MBeanInfo(value="messageId") String var1) throws Exception;
}

