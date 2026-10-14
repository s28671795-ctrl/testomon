/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.ObjectName;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.NetworkBridgeView;
import org.apache.activemq.broker.jmx.NetworkDestinationView;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.network.NetworkBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBeanBridgeDestination {
    private static final Logger LOG = LoggerFactory.getLogger(MBeanBridgeDestination.class);
    private final BrokerService brokerService;
    private final NetworkBridge bridge;
    private final NetworkBridgeView networkBridgeView;
    private final NetworkBridgeConfiguration networkBridgeConfiguration;
    private final Scheduler scheduler;
    private final Runnable purgeInactiveDestinationViewTask;
    private final Map<ActiveMQDestination, NetworkDestinationContainer> outboundDestinationViewMap = new ConcurrentHashMap<ActiveMQDestination, NetworkDestinationContainer>();
    private final Map<ActiveMQDestination, NetworkDestinationContainer> inboundDestinationViewMap = new ConcurrentHashMap<ActiveMQDestination, NetworkDestinationContainer>();

    public MBeanBridgeDestination(BrokerService brokerService, NetworkBridgeConfiguration networkBridgeConfiguration, NetworkBridge bridge, NetworkBridgeView networkBridgeView) {
        this.brokerService = brokerService;
        this.networkBridgeConfiguration = networkBridgeConfiguration;
        this.bridge = bridge;
        this.networkBridgeView = networkBridgeView;
        this.scheduler = brokerService.getScheduler();
        this.purgeInactiveDestinationViewTask = new Runnable(){

            @Override
            public void run() {
                MBeanBridgeDestination.this.purgeInactiveDestinationViews();
            }
        };
    }

    public void onOutboundMessage(Message message) {
        ActiveMQDestination destination = message.getDestination();
        NetworkDestinationContainer networkDestinationContainer = this.outboundDestinationViewMap.get(destination);
        if (networkDestinationContainer == null) {
            ObjectName bridgeObjectName = this.bridge.getMbeanObjectName();
            try {
                ObjectName objectName = BrokerMBeanSupport.createNetworkOutBoundDestinationObjectName(bridgeObjectName, destination);
                NetworkDestinationView networkDestinationView = new NetworkDestinationView(this.networkBridgeView, destination.getPhysicalName());
                AnnotatedMBean.registerMBean(this.brokerService.getManagementContext(), networkDestinationView, objectName);
                networkDestinationContainer = new NetworkDestinationContainer(networkDestinationView, objectName);
                this.outboundDestinationViewMap.put(destination, networkDestinationContainer);
                networkDestinationView.messageSent();
            }
            catch (Exception e) {
                LOG.warn("Failed to register " + destination, e);
            }
        } else {
            networkDestinationContainer.view.messageSent();
        }
    }

    public void onInboundMessage(Message message) {
        ActiveMQDestination destination = message.getDestination();
        NetworkDestinationContainer networkDestinationContainer = this.inboundDestinationViewMap.get(destination);
        if (networkDestinationContainer == null) {
            ObjectName bridgeObjectName = this.bridge.getMbeanObjectName();
            try {
                ObjectName objectName = BrokerMBeanSupport.createNetworkInBoundDestinationObjectName(bridgeObjectName, destination);
                NetworkDestinationView networkDestinationView = new NetworkDestinationView(this.networkBridgeView, destination.getPhysicalName());
                AnnotatedMBean.registerMBean(this.brokerService.getManagementContext(), networkDestinationView, objectName);
                this.networkBridgeView.addNetworkDestinationView(networkDestinationView);
                networkDestinationContainer = new NetworkDestinationContainer(networkDestinationView, objectName);
                this.inboundDestinationViewMap.put(destination, networkDestinationContainer);
                networkDestinationView.messageSent();
            }
            catch (Exception e) {
                LOG.warn("Failed to register " + destination, e);
            }
        } else {
            networkDestinationContainer.view.messageSent();
        }
    }

    public void start() {
        long period;
        if (this.networkBridgeConfiguration.isGcDestinationViews() && (period = this.networkBridgeConfiguration.getGcSweepTime()) > 0L) {
            this.scheduler.executePeriodically(this.purgeInactiveDestinationViewTask, period);
        }
    }

    public void stop() {
        if (!this.brokerService.isUseJmx()) {
            return;
        }
        this.scheduler.cancel(this.purgeInactiveDestinationViewTask);
        for (NetworkDestinationContainer networkDestinationContainer : this.inboundDestinationViewMap.values()) {
            try {
                this.brokerService.getManagementContext().unregisterMBean(networkDestinationContainer.objectName);
            }
            catch (Exception e) {
                LOG.error("Network bridge could not be unregistered in JMX: {}", (Object)e.getMessage(), (Object)e);
            }
        }
        for (NetworkDestinationContainer networkDestinationContainer : this.outboundDestinationViewMap.values()) {
            try {
                this.brokerService.getManagementContext().unregisterMBean(networkDestinationContainer.objectName);
            }
            catch (Exception e) {
                LOG.debug("Network bridge could not be unregistered in JMX: {}", (Object)e.getMessage(), (Object)e);
            }
        }
        this.inboundDestinationViewMap.clear();
        this.outboundDestinationViewMap.clear();
    }

    private void purgeInactiveDestinationViews() {
        if (!this.brokerService.isUseJmx()) {
            return;
        }
        this.purgeInactiveDestinationView(this.inboundDestinationViewMap);
        this.purgeInactiveDestinationView(this.outboundDestinationViewMap);
    }

    private void purgeInactiveDestinationView(Map<ActiveMQDestination, NetworkDestinationContainer> map) {
        long time = System.currentTimeMillis() - this.networkBridgeConfiguration.getGcSweepTime();
        Iterator<Map.Entry<ActiveMQDestination, NetworkDestinationContainer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ActiveMQDestination, NetworkDestinationContainer> entry = it.next();
            if (entry.getValue().view.getLastAccessTime() > time) continue;
            ObjectName objectName = entry.getValue().objectName;
            if (objectName != null) {
                try {
                    this.brokerService.getManagementContext().unregisterMBean(entry.getValue().objectName);
                }
                catch (Throwable e) {
                    LOG.debug("Network bridge could not be unregistered in JMX: {}", (Object)e.getMessage(), (Object)e);
                }
            }
            entry.getValue().view.close();
            it.remove();
        }
    }

    private static class NetworkDestinationContainer {
        private final NetworkDestinationView view;
        private final ObjectName objectName;

        private NetworkDestinationContainer(NetworkDestinationView view, ObjectName objectName) {
            this.view = view;
            this.objectName = objectName;
        }
    }
}

