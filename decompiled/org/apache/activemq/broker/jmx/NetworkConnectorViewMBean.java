/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.Service;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface NetworkConnectorViewMBean
extends Service {
    public String getName();

    public int getMessageTTL();

    public int getConsumerTTL();

    public int getPrefetchSize();

    @MBeanInfo(value="The prefetch setting for the advisory message consumer.  If set to <= 0 then this setting is disabled and the prefetchSize attribute is used instead for configuring the advisory consumer.")
    public int getAdvisoryPrefetchSize();

    public String getUserName();

    public boolean isBridgeTempDestinations();

    public boolean isConduitSubscriptions();

    public boolean isDecreaseNetworkConsumerPriority();

    public boolean isDispatchAsync();

    public boolean isDynamicOnly();

    public boolean isDuplex();

    public boolean isSuppressDuplicateQueueSubscriptions();

    public boolean isSuppressDuplicateTopicSubscriptions();

    public void setBridgeTempDestinations(boolean var1);

    public void setConduitSubscriptions(boolean var1);

    public void setDispatchAsync(boolean var1);

    public void setDynamicOnly(boolean var1);

    public void setMessageTTL(int var1);

    public void setConsumerTTL(int var1);

    public void setPassword(String var1);

    public void setPrefetchSize(int var1);

    public void setAdvisoryPrefetchSize(int var1);

    public void setUserName(String var1);

    public String getPassword();

    public void setDecreaseNetworkConsumerPriority(boolean var1);

    public void setSuppressDuplicateQueueSubscriptions(boolean var1);

    public void setSuppressDuplicateTopicSubscriptions(boolean var1);
}

