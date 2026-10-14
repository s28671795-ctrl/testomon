/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jmdns.JmDNS
 *  javax.jmdns.ServiceEvent
 *  javax.jmdns.ServiceInfo
 *  javax.jmdns.ServiceListener
 */
package org.apache.activemq.transport.discovery.zeroconf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.apache.activemq.transport.discovery.zeroconf.JmDNSFactory;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.MapHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeroconfDiscoveryAgent
implements DiscoveryAgent,
ServiceListener {
    private static final Logger LOG = LoggerFactory.getLogger(ZeroconfDiscoveryAgent.class);
    private static final String TYPE_SUFFIX = "ActiveMQ-5.";
    private JmDNS jmdns;
    private InetAddress localAddress;
    private String localhost;
    private int weight;
    private int priority;
    private String typeSuffix = "ActiveMQ-5.";
    private DiscoveryListener listener;
    private String group = "default";
    private final CopyOnWriteArrayList<ServiceInfo> serviceInfos = new CopyOnWriteArrayList();

    @Override
    public void start() throws Exception {
        if (this.group == null) {
            throw new IOException("You must specify a group to discover");
        }
        String type = this.getType();
        if (!type.endsWith(".")) {
            LOG.warn("The type '{}' should end with '.' to be a valid Rendezvous type", (Object)type);
            type = type + ".";
        }
        try {
            this.getJmdns();
            if (this.listener != null) {
                LOG.info("Discovering service of type: {}", (Object)type);
                this.jmdns.addServiceListener(type, (ServiceListener)this);
            }
        }
        catch (IOException e) {
            JMSExceptionSupport.create("Failed to start JmDNS service: " + e, e);
        }
    }

    @Override
    public void stop() {
        if (this.jmdns != null) {
            for (ServiceInfo si : this.serviceInfos) {
                this.jmdns.unregisterService(si);
            }
            final JmDNS closeTarget = this.jmdns;
            Thread thread2 = new Thread(){

                @Override
                public void run() {
                    try {
                        if (JmDNSFactory.onClose(ZeroconfDiscoveryAgent.this.getLocalAddress())) {
                            closeTarget.close();
                        }
                    }
                    catch (IOException e) {
                        LOG.debug("Error closing JmDNS {}. This exception will be ignored.", (Object)ZeroconfDiscoveryAgent.this.getLocalhost(), (Object)e);
                    }
                }
            };
            thread2.setDaemon(true);
            thread2.start();
            this.jmdns = null;
        }
    }

    @Override
    public void registerService(String name) throws IOException {
        ServiceInfo si = this.createServiceInfo(name, new HashMap());
        this.serviceInfos.add(si);
        this.getJmdns().registerService(si);
    }

    public void addService(JmDNS jmDNS, String type, String name) {
        LOG.debug("addService with type: {} name: {}", (Object)type, (Object)name);
        if (this.listener != null) {
            this.listener.onServiceAdd(new DiscoveryEvent(name));
        }
        jmDNS.requestServiceInfo(type, name);
    }

    public void removeService(JmDNS jmDNS, String type, String name) {
        LOG.debug("removeService with type: {} name: {}", (Object)type, (Object)name);
        if (this.listener != null) {
            this.listener.onServiceRemove(new DiscoveryEvent(name));
        }
    }

    public void serviceAdded(ServiceEvent event) {
        this.addService(event.getDNS(), event.getType(), event.getName());
    }

    public void serviceRemoved(ServiceEvent event) {
        this.removeService(event.getDNS(), event.getType(), event.getName());
    }

    public void serviceResolved(ServiceEvent event) {
    }

    public void resolveService(JmDNS jmDNS, String type, String name, ServiceInfo serviceInfo) {
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public JmDNS getJmdns() throws IOException {
        if (this.jmdns == null) {
            this.jmdns = this.createJmDNS();
        }
        return this.jmdns;
    }

    public void setJmdns(JmDNS jmdns) {
        this.jmdns = jmdns;
    }

    public InetAddress getLocalAddress() throws UnknownHostException {
        if (this.localAddress == null) {
            this.localAddress = this.createLocalAddress();
        }
        return this.localAddress;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalhost() {
        return this.localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    protected ServiceInfo createServiceInfo(String name, Map map) {
        int port = MapHelper.getInt(map, "port", 0);
        String type = this.getType();
        LOG.debug("Registering service type: {} name: {} details: {}", type, name, map);
        return ServiceInfo.create((String)type, (String)(name + "." + type), (int)port, (int)this.weight, (int)this.priority, (String)"");
    }

    protected JmDNS createJmDNS() throws IOException {
        return JmDNSFactory.create(this.getLocalAddress());
    }

    protected InetAddress createLocalAddress() throws UnknownHostException {
        if (this.localhost != null) {
            return InetAddress.getByName(this.localhost);
        }
        return InetAddress.getLocalHost();
    }

    @Override
    public void setDiscoveryListener(DiscoveryListener listener) {
        this.listener = listener;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setType(String typeSuffix) {
        this.typeSuffix = typeSuffix;
    }

    public String getType() {
        if (this.typeSuffix == null || this.typeSuffix.isEmpty()) {
            this.typeSuffix = TYPE_SUFFIX;
        }
        return "_" + this.group + "." + this.typeSuffix;
    }

    @Override
    public void serviceFailed(DiscoveryEvent event) throws IOException {
    }
}

