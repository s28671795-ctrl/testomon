/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.network.BridgeFactory;
import org.apache.activemq.network.NetworkBridgeFactory;

public class NetworkBridgeConfiguration {
    private boolean conduitSubscriptions = true;
    private boolean conduitNetworkQueueSubscriptions;
    private boolean useVirtualDestSubs;
    private boolean dynamicOnly;
    private boolean syncDurableSubs;
    private boolean dispatchAsync = true;
    private boolean decreaseNetworkConsumerPriority;
    private int consumerPriorityBase = -5;
    private boolean duplex;
    private boolean bridgeTempDestinations = true;
    private int prefetchSize = 1000;
    private int advisoryPrefetchSize = 0;
    private int advisoryAckPercentage = 75;
    private int networkTTL;
    private int consumerTTL = this.networkTTL = 1;
    private int messageTTL = this.networkTTL;
    private String brokerName = "localhost";
    private String brokerURL = "";
    private String userName;
    private String password;
    private String destinationFilter = null;
    private String name = "NC";
    private String clientIdToken = "_";
    protected List<ActiveMQDestination> excludedDestinations = new CopyOnWriteArrayList<ActiveMQDestination>();
    protected List<ActiveMQDestination> dynamicallyIncludedDestinations = new CopyOnWriteArrayList<ActiveMQDestination>();
    protected List<ActiveMQDestination> staticallyIncludedDestinations = new CopyOnWriteArrayList<ActiveMQDestination>();
    private boolean suppressDuplicateQueueSubscriptions = false;
    private boolean suppressDuplicateTopicSubscriptions = true;
    private boolean alwaysSyncSend = true;
    private boolean staticBridge = false;
    private boolean useCompression = false;
    private boolean advisoryForFailedForward = false;
    private boolean useBrokerNamesAsIdSeed = true;
    private boolean gcDestinationViews = true;
    private long gcSweepTime = 60000L;
    private boolean checkDuplicateMessagesOnDuplex = false;
    private BridgeFactory bridgeFactory = NetworkBridgeFactory.INSTANCE;

    public boolean isConduitSubscriptions() {
        return this.conduitSubscriptions;
    }

    public void setConduitSubscriptions(boolean conduitSubscriptions) {
        this.conduitSubscriptions = conduitSubscriptions;
    }

    public boolean isConduitNetworkQueueSubscriptions() {
        return this.conduitNetworkQueueSubscriptions;
    }

    public void setConduitNetworkQueueSubscriptions(boolean conduitNetworkQueueSubscriptions) {
        this.conduitNetworkQueueSubscriptions = conduitNetworkQueueSubscriptions;
    }

    public boolean isDynamicOnly() {
        return this.dynamicOnly;
    }

    public void setDynamicOnly(boolean dynamicOnly) {
        this.dynamicOnly = dynamicOnly;
    }

    public boolean isSyncDurableSubs() {
        return this.syncDurableSubs;
    }

    public void setSyncDurableSubs(boolean syncDurableSubs) {
        this.syncDurableSubs = syncDurableSubs;
    }

    public boolean isBridgeTempDestinations() {
        return this.bridgeTempDestinations;
    }

    public void setBridgeTempDestinations(boolean bridgeTempDestinations) {
        this.bridgeTempDestinations = bridgeTempDestinations;
    }

    public boolean isDecreaseNetworkConsumerPriority() {
        return this.decreaseNetworkConsumerPriority;
    }

    public void setDecreaseNetworkConsumerPriority(boolean decreaseNetworkConsumerPriority) {
        this.decreaseNetworkConsumerPriority = decreaseNetworkConsumerPriority;
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
    }

    public boolean isDuplex() {
        return this.duplex;
    }

    public void setDuplex(boolean duplex) {
        this.duplex = duplex;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getClientIdToken() {
        return this.clientIdToken;
    }

    public void setClientIdToken(String clientIdToken) {
        this.clientIdToken = clientIdToken;
    }

    public int getNetworkTTL() {
        return this.networkTTL;
    }

    public void setNetworkTTL(int networkTTL) {
        this.networkTTL = networkTTL;
        this.setConsumerTTL(networkTTL);
        this.setMessageTTL(networkTTL);
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrefetchSize() {
        return this.prefetchSize;
    }

    public void setPrefetchSize(int prefetchSize) {
        if (prefetchSize < 1) {
            throw new IllegalArgumentException("prefetchSize must be > 0 because network consumers do not poll for messages.");
        }
        this.prefetchSize = prefetchSize;
    }

    public int getAdvisoryPrefetchSize() {
        return this.advisoryPrefetchSize;
    }

    public void setAdvisoryPrefetchSize(int advisoryPrefetchSize) {
        this.advisoryPrefetchSize = advisoryPrefetchSize;
    }

    public int getAdvisoryAckPercentage() {
        return this.advisoryAckPercentage;
    }

    public void setAdvisoryAckPercentage(int advisoryAckPercentage) {
        this.advisoryAckPercentage = advisoryAckPercentage;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDestinationFilter() {
        if (this.destinationFilter == null) {
            if (this.dynamicallyIncludedDestinations != null && !this.dynamicallyIncludedDestinations.isEmpty()) {
                StringBuffer filter2 = new StringBuffer();
                String delimiter = "";
                for (ActiveMQDestination destination : this.dynamicallyIncludedDestinations) {
                    if (destination.isTemporary()) continue;
                    filter2.append(delimiter);
                    filter2.append("ActiveMQ.Advisory.Consumer.");
                    filter2.append(destination.getDestinationTypeAsString());
                    filter2.append(".");
                    filter2.append(destination.getPhysicalName());
                    delimiter = ",";
                    if (!this.useVirtualDestSubs) continue;
                    filter2.append(delimiter);
                    filter2.append("ActiveMQ.Advisory.VirtualDestination.Consumer.");
                    filter2.append(destination.getDestinationTypeAsString());
                    filter2.append(".");
                    filter2.append(destination.getPhysicalName());
                }
                return filter2.toString();
            }
            StringBuffer filter3 = new StringBuffer();
            filter3.append("ActiveMQ.Advisory.Consumer.");
            filter3.append(">");
            if (this.useVirtualDestSubs) {
                filter3.append(",");
                filter3.append("ActiveMQ.Advisory.VirtualDestination.Consumer.");
                filter3.append(">");
            }
            return filter3.toString();
        }
        if (!this.destinationFilter.startsWith("ActiveMQ.Advisory.Consumer.")) {
            return "ActiveMQ.Advisory.Consumer." + this.destinationFilter;
        }
        return this.destinationFilter;
    }

    public void setDestinationFilter(String destinationFilter) {
        this.destinationFilter = destinationFilter;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ActiveMQDestination> getExcludedDestinations() {
        return this.excludedDestinations;
    }

    public void setExcludedDestinations(List<ActiveMQDestination> excludedDestinations) {
        this.excludedDestinations = excludedDestinations;
    }

    public List<ActiveMQDestination> getDynamicallyIncludedDestinations() {
        return this.dynamicallyIncludedDestinations;
    }

    public void setDynamicallyIncludedDestinations(List<ActiveMQDestination> dynamicallyIncludedDestinations) {
        this.dynamicallyIncludedDestinations = dynamicallyIncludedDestinations;
    }

    public List<ActiveMQDestination> getStaticallyIncludedDestinations() {
        return this.staticallyIncludedDestinations;
    }

    public void setStaticallyIncludedDestinations(List<ActiveMQDestination> staticallyIncludedDestinations) {
        this.staticallyIncludedDestinations = staticallyIncludedDestinations;
    }

    public boolean isSuppressDuplicateQueueSubscriptions() {
        return this.suppressDuplicateQueueSubscriptions;
    }

    public void setSuppressDuplicateQueueSubscriptions(boolean val) {
        this.suppressDuplicateQueueSubscriptions = val;
    }

    public boolean isSuppressDuplicateTopicSubscriptions() {
        return this.suppressDuplicateTopicSubscriptions;
    }

    public void setSuppressDuplicateTopicSubscriptions(boolean val) {
        this.suppressDuplicateTopicSubscriptions = val;
    }

    public String getBrokerURL() {
        return this.brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public boolean isAlwaysSyncSend() {
        return this.alwaysSyncSend;
    }

    public void setAlwaysSyncSend(boolean alwaysSyncSend) {
        this.alwaysSyncSend = alwaysSyncSend;
    }

    public int getConsumerPriorityBase() {
        return this.consumerPriorityBase;
    }

    public void setConsumerPriorityBase(int consumerPriorityBase) {
        this.consumerPriorityBase = consumerPriorityBase;
    }

    public boolean isStaticBridge() {
        return this.staticBridge;
    }

    public void setStaticBridge(boolean staticBridge) {
        this.staticBridge = staticBridge;
    }

    public void setUseCompression(boolean useCompression) {
        this.useCompression = useCompression;
    }

    public boolean isUseCompression() {
        return this.useCompression;
    }

    public boolean isAdvisoryForFailedForward() {
        return this.advisoryForFailedForward;
    }

    public void setAdvisoryForFailedForward(boolean advisoryForFailedForward) {
        this.advisoryForFailedForward = advisoryForFailedForward;
    }

    public void setConsumerTTL(int consumerTTL) {
        this.consumerTTL = consumerTTL;
    }

    public int getConsumerTTL() {
        return this.consumerTTL;
    }

    public void setMessageTTL(int messageTTL) {
        this.messageTTL = messageTTL;
    }

    public int getMessageTTL() {
        return this.messageTTL;
    }

    public boolean isUseBrokerNamesAsIdSeed() {
        return this.useBrokerNamesAsIdSeed;
    }

    public void setUseBrokerNameAsIdSees(boolean val) {
        this.useBrokerNamesAsIdSeed = val;
    }

    public boolean isGcDestinationViews() {
        return this.gcDestinationViews;
    }

    public void setGcDestinationViews(boolean gcDestinationViews) {
        this.gcDestinationViews = gcDestinationViews;
    }

    public long getGcSweepTime() {
        return this.gcSweepTime;
    }

    public void setGcSweepTime(long gcSweepTime) {
        this.gcSweepTime = gcSweepTime;
    }

    public boolean isCheckDuplicateMessagesOnDuplex() {
        return this.checkDuplicateMessagesOnDuplex;
    }

    public void setCheckDuplicateMessagesOnDuplex(boolean checkDuplicateMessagesOnDuplex) {
        this.checkDuplicateMessagesOnDuplex = checkDuplicateMessagesOnDuplex;
    }

    public boolean isUseVirtualDestSubs() {
        return this.useVirtualDestSubs;
    }

    public BridgeFactory getBridgeFactory() {
        return this.bridgeFactory;
    }

    public void setBridgeFactory(BridgeFactory bridgeFactory) {
        this.bridgeFactory = bridgeFactory;
    }

    @Deprecated
    public boolean isUseVirtualDestSus() {
        return this.useVirtualDestSubs;
    }

    public void setUseVirtualDestSubs(boolean useVirtualDestSubs) {
        this.useVirtualDestSubs = useVirtualDestSubs;
    }
}

