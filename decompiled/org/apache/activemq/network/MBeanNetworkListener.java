/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.NetworkBridgeView;
import org.apache.activemq.command.Message;
import org.apache.activemq.network.MBeanBridgeDestination;
import org.apache.activemq.network.NetworkBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBeanNetworkListener
implements NetworkBridgeListener {
    private static final Logger LOG = LoggerFactory.getLogger(MBeanNetworkListener.class);
    private final BrokerService brokerService;
    private final ObjectName connectorName;
    private final NetworkBridgeConfiguration networkBridgeConfiguration;
    private boolean createdByDuplex = false;
    private Map<NetworkBridge, MBeanBridgeDestination> destinationObjectNameMap = new ConcurrentHashMap<NetworkBridge, MBeanBridgeDestination>();

    public MBeanNetworkListener(BrokerService brokerService, NetworkBridgeConfiguration networkBridgeConfiguration, ObjectName connectorName) {
        this.brokerService = brokerService;
        this.networkBridgeConfiguration = networkBridgeConfiguration;
        this.connectorName = connectorName;
    }

    @Override
    public void bridgeFailed() {
    }

    @Override
    public void onStart(NetworkBridge bridge) {
        if (!this.brokerService.isUseJmx()) {
            return;
        }
        NetworkBridgeView view = new NetworkBridgeView(bridge);
        view.setCreateByDuplex(this.createdByDuplex);
        try {
            ObjectName objectName = this.createNetworkBridgeObjectName(bridge);
            AnnotatedMBean.registerMBean(this.brokerService.getManagementContext(), view, objectName);
            bridge.setMbeanObjectName(objectName);
            MBeanBridgeDestination mBeanBridgeDestination = new MBeanBridgeDestination(this.brokerService, this.networkBridgeConfiguration, bridge, view);
            this.destinationObjectNameMap.put(bridge, mBeanBridgeDestination);
            mBeanBridgeDestination.start();
            LOG.debug("registered: {} as: {}", (Object)bridge, (Object)objectName);
        }
        catch (Throwable e) {
            LOG.debug("Network bridge could not be registered in JMX: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    @Override
    public void onStop(NetworkBridge bridge) {
        if (!this.brokerService.isUseJmx()) {
            return;
        }
        try {
            MBeanBridgeDestination mBeanBridgeDestination;
            ObjectName objectName = bridge.getMbeanObjectName();
            if (objectName != null) {
                this.brokerService.getManagementContext().unregisterMBean(objectName);
            }
            if ((mBeanBridgeDestination = this.destinationObjectNameMap.remove(bridge)) != null) {
                mBeanBridgeDestination.stop();
            }
        }
        catch (Throwable e) {
            LOG.debug("Network bridge could not be unregistered in JMX: {}", (Object)e.getMessage(), (Object)e);
        }
    }

    protected ObjectName createNetworkBridgeObjectName(NetworkBridge bridge) throws MalformedObjectNameException {
        return BrokerMBeanSupport.createNetworkBridgeObjectName(this.connectorName, bridge.getRemoteAddress());
    }

    public void setCreatedByDuplex(boolean createdByDuplex) {
        this.createdByDuplex = createdByDuplex;
    }

    @Override
    public void onOutboundMessage(NetworkBridge bridge, Message message) {
        MBeanBridgeDestination mBeanBridgeDestination = this.destinationObjectNameMap.get(bridge);
        if (mBeanBridgeDestination != null) {
            mBeanBridgeDestination.onOutboundMessage(message);
        }
    }

    @Override
    public void onInboundMessage(NetworkBridge bridge, Message message) {
        MBeanBridgeDestination mBeanBridgeDestination = this.destinationObjectNameMap.get(bridge);
        if (mBeanBridgeDestination != null) {
            mBeanBridgeDestination.onInboundMessage(message);
        }
    }
}

