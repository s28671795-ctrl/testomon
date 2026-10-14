/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastDiscoveryAgent
implements DiscoveryAgent,
Runnable {
    public static final String DEFAULT_DISCOVERY_URI_STRING = "multicast://239.255.2.3:6155";
    public static final String DEFAULT_HOST_STR = "default";
    public static final String DEFAULT_HOST_IP = System.getProperty("activemq.partition.discovery", "239.255.2.3");
    public static final int DEFAULT_PORT = 6155;
    private static final Logger LOG = LoggerFactory.getLogger(MulticastDiscoveryAgent.class);
    private static final String TYPE_SUFFIX = "ActiveMQ-4.";
    private static final String ALIVE = "alive.";
    private static final String DEAD = "dead.";
    private static final String DELIMITER = "%";
    private static final int BUFF_SIZE = 8192;
    private static final int DEFAULT_IDLE_TIME = 500;
    private static final int HEARTBEAT_MISS_BEFORE_DEATH = 10;
    private long initialReconnectDelay = 5000L;
    private long maxReconnectDelay = 30000L;
    private long backOffMultiplier = 2L;
    private boolean useExponentialBackOff;
    private int maxReconnectAttempts;
    private int timeToLive = 1;
    private boolean loopBackMode;
    private Map<String, RemoteBrokerData> brokersByService = new ConcurrentHashMap<String, RemoteBrokerData>();
    private String group = "default";
    private URI discoveryURI;
    private InetAddress inetAddress;
    private SocketAddress sockAddress;
    private DiscoveryListener discoveryListener;
    private String selfService;
    private MulticastSocket mcast;
    private Thread runner;
    private long keepAliveInterval = 500L;
    private String mcInterface;
    private String mcNetworkInterface;
    private String mcJoinNetworkInterface;
    private long lastAdvertizeTime;
    private AtomicBoolean started = new AtomicBoolean(false);
    private boolean reportAdvertizeFailed = true;
    private ExecutorService executor = null;

    @Override
    public void setDiscoveryListener(DiscoveryListener listener) {
        this.discoveryListener = listener;
    }

    @Override
    public void registerService(String name) throws IOException {
        this.selfService = name;
        if (this.started.get()) {
            this.doAdvertizeSelf();
        }
    }

    public boolean isLoopBackMode() {
        return this.loopBackMode;
    }

    public void setLoopBackMode(boolean loopBackMode) {
        this.loopBackMode = loopBackMode;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public URI getDiscoveryURI() {
        return this.discoveryURI;
    }

    public void setDiscoveryURI(URI discoveryURI) {
        this.discoveryURI = discoveryURI;
    }

    public long getKeepAliveInterval() {
        return this.keepAliveInterval;
    }

    public void setKeepAliveInterval(long keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public void setInterface(String mcInterface) {
        this.mcInterface = mcInterface;
    }

    public void setNetworkInterface(String mcNetworkInterface) {
        this.mcNetworkInterface = mcNetworkInterface;
    }

    public void setJoinNetworkInterface(String mcJoinNetwrokInterface) {
        this.mcJoinNetworkInterface = mcJoinNetwrokInterface;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            if (this.group == null || this.group.length() == 0) {
                throw new IOException("You must specify a group to discover");
            }
            String type = this.getType();
            if (!type.endsWith(".")) {
                LOG.warn("The type '" + type + "' should end with '.' to be a valid Discovery type");
                type = type + ".";
            }
            if (this.discoveryURI == null) {
                this.discoveryURI = new URI(DEFAULT_DISCOVERY_URI_STRING);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("start - discoveryURI = " + this.discoveryURI);
            }
            String myHost = this.discoveryURI.getHost();
            int myPort = this.discoveryURI.getPort();
            if (DEFAULT_HOST_STR.equals(myHost)) {
                myHost = DEFAULT_HOST_IP;
            }
            if (myPort < 0) {
                myPort = 6155;
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("start - myHost = " + myHost);
                LOG.trace("start - myPort = " + myPort);
                LOG.trace("start - group  = " + this.group);
                LOG.trace("start - interface  = " + this.mcInterface);
                LOG.trace("start - network interface  = " + this.mcNetworkInterface);
                LOG.trace("start - join network interface  = " + this.mcJoinNetworkInterface);
            }
            this.inetAddress = InetAddress.getByName(myHost);
            this.sockAddress = new InetSocketAddress(this.inetAddress, myPort);
            this.mcast = new MulticastSocket(myPort);
            this.mcast.setLoopbackMode(this.loopBackMode);
            this.mcast.setTimeToLive(this.getTimeToLive());
            if (this.mcJoinNetworkInterface != null) {
                this.mcast.joinGroup(this.sockAddress, NetworkInterface.getByName(this.mcJoinNetworkInterface));
            } else {
                this.mcast.setNetworkInterface(this.findNetworkInterface());
                this.mcast.joinGroup(this.inetAddress);
            }
            this.mcast.setSoTimeout((int)this.keepAliveInterval);
            if (this.mcInterface != null) {
                this.mcast.setInterface(InetAddress.getByName(this.mcInterface));
            }
            if (this.mcNetworkInterface != null) {
                this.mcast.setNetworkInterface(NetworkInterface.getByName(this.mcNetworkInterface));
            }
            this.runner = new Thread(this);
            this.runner.setName(this.toString() + ":" + this.runner.getName());
            this.runner.setDaemon(true);
            this.runner.start();
            this.doAdvertizeSelf();
        }
    }

    private NetworkInterface findNetworkInterface() throws SocketException {
        Enumeration<NetworkInterface> ifcs = NetworkInterface.getNetworkInterfaces();
        ArrayList<NetworkInterface> possibles = new ArrayList<NetworkInterface>();
        while (ifcs.hasMoreElements()) {
            NetworkInterface ni = ifcs.nextElement();
            try {
                if (!ni.supportsMulticast() || !ni.isUp()) continue;
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    if (ia == null || !(ia.getAddress() instanceof Inet4Address) || ia.getAddress().isLoopbackAddress() || ni.getDisplayName() != null && ni.getDisplayName().startsWith("vnic")) continue;
                    possibles.add(ni);
                }
            }
            catch (SocketException socketException) {
            }
        }
        return possibles.isEmpty() ? null : (NetworkInterface)possibles.get(possibles.size() - 1);
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            this.doAdvertizeSelf();
            if (this.mcast != null) {
                this.mcast.close();
            }
            if (this.runner != null) {
                this.runner.interrupt();
            }
            if (this.executor != null) {
                ThreadPoolUtils.shutdownNow(this.executor);
                this.executor = null;
            }
        }
    }

    public String getType() {
        return this.group + "." + TYPE_SUFFIX;
    }

    @Override
    public void run() {
        byte[] buf = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
        while (this.started.get()) {
            this.doTimeKeepingServices();
            try {
                this.mcast.receive(packet);
                if (packet.getLength() <= 0) continue;
                String str = new String(packet.getData(), packet.getOffset(), packet.getLength());
                this.processData(str);
            }
            catch (SocketTimeoutException str) {
            }
            catch (IOException e) {
                if (!this.started.get()) continue;
                LOG.error("failed to process packet: " + e);
            }
        }
    }

    private void processData(String str) {
        if (this.discoveryListener != null && str.startsWith(this.getType())) {
            String payload = str.substring(this.getType().length());
            if (payload.startsWith(ALIVE)) {
                String brokerName = this.getBrokerName(payload.substring(ALIVE.length()));
                String service2 = payload.substring(ALIVE.length() + brokerName.length() + 2);
                this.processAlive(brokerName, service2);
            } else {
                String brokerName = this.getBrokerName(payload.substring(DEAD.length()));
                String service3 = payload.substring(DEAD.length() + brokerName.length() + 2);
                this.processDead(service3);
            }
        }
    }

    private void doTimeKeepingServices() {
        if (this.started.get()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime < this.lastAdvertizeTime || currentTime - this.keepAliveInterval > this.lastAdvertizeTime) {
                this.doAdvertizeSelf();
                this.lastAdvertizeTime = currentTime;
            }
            this.doExpireOldServices();
        }
    }

    private void doAdvertizeSelf() {
        block3: {
            if (this.selfService != null) {
                String payload = this.getType();
                payload = payload + (this.started.get() ? ALIVE : DEAD);
                payload = payload + "%localhost%";
                payload = payload + this.selfService;
                try {
                    byte[] data = payload.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, 0, data.length, this.sockAddress);
                    this.mcast.send(packet);
                }
                catch (IOException e) {
                    if (!this.reportAdvertizeFailed) break block3;
                    this.reportAdvertizeFailed = false;
                    LOG.error("Failed to advertise our service: " + payload, e);
                    if (!"Operation not permitted".equals(e.getMessage())) break block3;
                    LOG.error("The 'Operation not permitted' error has been know to be caused by improper firewall/network setup.  Please make sure that the OS is properly configured to allow multicast traffic over: " + this.mcast.getLocalAddress());
                }
            }
        }
    }

    private void processAlive(String brokerName, String service2) {
        if (this.selfService == null || !service2.equals(this.selfService)) {
            RemoteBrokerData data = this.brokersByService.get(service2);
            if (data == null) {
                data = new RemoteBrokerData(brokerName, service2);
                this.brokersByService.put(service2, data);
                this.fireServiceAddEvent(data);
                this.doAdvertizeSelf();
            } else {
                data.updateHeartBeat();
                if (data.doRecovery()) {
                    this.fireServiceAddEvent(data);
                }
            }
        }
    }

    private void processDead(String service2) {
        RemoteBrokerData data;
        if (!service2.equals(this.selfService) && (data = this.brokersByService.remove(service2)) != null && !data.isFailed()) {
            this.fireServiceRemovedEvent(data);
        }
    }

    private void doExpireOldServices() {
        long expireTime = System.currentTimeMillis() - this.keepAliveInterval * 10L;
        for (RemoteBrokerData data : this.brokersByService.values()) {
            if (data.getLastHeartBeat() >= expireTime) continue;
            this.processDead(data.getServiceName());
        }
    }

    private String getBrokerName(String str) {
        String result = null;
        int start2 = str.indexOf(DELIMITER);
        if (start2 >= 0) {
            int end = str.indexOf(DELIMITER, start2 + 1);
            result = str.substring(start2 + 1, end);
        }
        return result;
    }

    @Override
    public void serviceFailed(DiscoveryEvent event) throws IOException {
        RemoteBrokerData data = this.brokersByService.get(event.getServiceName());
        if (data != null && data.markFailed()) {
            this.fireServiceRemovedEvent(data);
        }
    }

    private void fireServiceRemovedEvent(final RemoteBrokerData data) {
        if (this.discoveryListener != null && this.started.get()) {
            this.getExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    DiscoveryListener discoveryListener = MulticastDiscoveryAgent.this.discoveryListener;
                    if (discoveryListener != null) {
                        discoveryListener.onServiceRemove(data);
                    }
                }
            });
        }
    }

    private void fireServiceAddEvent(final RemoteBrokerData data) {
        if (this.discoveryListener != null && this.started.get()) {
            this.getExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    DiscoveryListener discoveryListener = MulticastDiscoveryAgent.this.discoveryListener;
                    if (discoveryListener != null) {
                        discoveryListener.onServiceAdd(data);
                    }
                }
            });
        }
    }

    private ExecutorService getExecutor() {
        if (this.executor == null) {
            final String threadName = "Notifier-" + this.toString();
            this.executor = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){

                @Override
                public Thread newThread(Runnable runable) {
                    Thread t2 = new Thread(runable, threadName);
                    t2.setDaemon(true);
                    return t2;
                }
            });
        }
        return this.executor;
    }

    public long getBackOffMultiplier() {
        return this.backOffMultiplier;
    }

    public void setBackOffMultiplier(long backOffMultiplier) {
        this.backOffMultiplier = backOffMultiplier;
    }

    public long getInitialReconnectDelay() {
        return this.initialReconnectDelay;
    }

    public void setInitialReconnectDelay(long initialReconnectDelay) {
        this.initialReconnectDelay = initialReconnectDelay;
    }

    public int getMaxReconnectAttempts() {
        return this.maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    public long getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    public void setMaxReconnectDelay(long maxReconnectDelay) {
        this.maxReconnectDelay = maxReconnectDelay;
    }

    public boolean isUseExponentialBackOff() {
        return this.useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String toString() {
        return "MulticastDiscoveryAgent-" + (this.selfService != null ? "advertise:" + this.selfService : "listener:" + this.discoveryListener);
    }

    class RemoteBrokerData
    extends DiscoveryEvent {
        long lastHeartBeat;
        long recoveryTime;
        int failureCount;
        boolean failed;

        public RemoteBrokerData(String brokerName, String service2) {
            super(service2);
            this.setBrokerName(brokerName);
            this.lastHeartBeat = System.currentTimeMillis();
        }

        public synchronized void updateHeartBeat() {
            this.lastHeartBeat = System.currentTimeMillis();
            if (!this.failed && this.failureCount > 0 && this.lastHeartBeat - this.recoveryTime > 60000L) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("I now think that the " + this.serviceName + " service has recovered.");
                }
                this.failureCount = 0;
                this.recoveryTime = 0L;
            }
        }

        public synchronized long getLastHeartBeat() {
            return this.lastHeartBeat;
        }

        public synchronized boolean markFailed() {
            if (!this.failed) {
                long reconnectDelay;
                this.failed = true;
                ++this.failureCount;
                if (!MulticastDiscoveryAgent.this.useExponentialBackOff) {
                    reconnectDelay = MulticastDiscoveryAgent.this.initialReconnectDelay;
                } else {
                    reconnectDelay = (long)Math.pow(MulticastDiscoveryAgent.this.backOffMultiplier, this.failureCount);
                    if (reconnectDelay > MulticastDiscoveryAgent.this.maxReconnectDelay) {
                        reconnectDelay = MulticastDiscoveryAgent.this.maxReconnectDelay;
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Remote failure of " + this.serviceName + " while still receiving multicast advertisements.  Advertising events will be suppressed for " + reconnectDelay + " ms, the current failure count is: " + this.failureCount);
                }
                this.recoveryTime = System.currentTimeMillis() + reconnectDelay;
                return true;
            }
            return false;
        }

        public synchronized boolean doRecovery() {
            if (!this.failed) {
                return false;
            }
            if (MulticastDiscoveryAgent.this.maxReconnectAttempts > 0 && this.failureCount > MulticastDiscoveryAgent.this.maxReconnectAttempts) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Max reconnect attempts of the " + this.serviceName + " service has been reached.");
                }
                return false;
            }
            if (System.currentTimeMillis() < this.recoveryTime) {
                return false;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Resuming event advertisement of the " + this.serviceName + " service.");
            }
            this.failed = false;
            return true;
        }

        public boolean isFailed() {
            return this.failed;
        }
    }
}

