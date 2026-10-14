/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.PayloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MulticastKeepaliveHeartbeatSender {
    private static final Logger LOG = LoggerFactory.getLogger(MulticastKeepaliveHeartbeatSender.class.getName());
    private static final int DEFAULT_HEARTBEAT_INTERVAL = 5000;
    private static final int MINIMUM_HEARTBEAT_INTERVAL = 1000;
    private static final int MAXIMUM_PEERS_PER_SEND = 150;
    private static final int ONE_HUNDRED_MS = 100;
    private static long heartBeatInterval = 5000L;
    private static long heartBeatStaleTime = -1L;
    private final InetAddress groupMulticastAddress;
    private final Integer groupMulticastPort;
    private final Integer timeToLive;
    private MulticastServerThread serverThread;
    private volatile boolean stopped;
    private final CacheManager cacheManager;
    private InetAddress hostAddress;

    public MulticastKeepaliveHeartbeatSender(CacheManager cacheManager, InetAddress multicastAddress, Integer multicastPort, Integer timeToLive, InetAddress hostAddress) {
        this.cacheManager = cacheManager;
        this.groupMulticastAddress = multicastAddress;
        this.groupMulticastPort = multicastPort;
        this.timeToLive = timeToLive;
        this.hostAddress = hostAddress;
    }

    public final void init() {
        this.serverThread = new MulticastServerThread();
        this.serverThread.start();
    }

    public final synchronized void dispose() {
        this.stopped = true;
        this.notifyAll();
        this.serverThread.interrupt();
    }

    public static void setHeartBeatInterval(long heartBeatInterval) {
        if (heartBeatInterval < 1000L) {
            LOG.warn("Trying to set heartbeat interval too low. Using MINIMUM_HEARTBEAT_INTERVAL instead.");
            MulticastKeepaliveHeartbeatSender.heartBeatInterval = 1000L;
        } else {
            MulticastKeepaliveHeartbeatSender.heartBeatInterval = heartBeatInterval;
        }
    }

    public static void setHeartBeatStaleTime(long heartBeatStaleTime) {
        MulticastKeepaliveHeartbeatSender.heartBeatStaleTime = heartBeatStaleTime;
    }

    public static long getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public static long getHeartBeatStaleTime() {
        if (heartBeatStaleTime < 0L) {
            return heartBeatInterval * 2L + 100L;
        }
        return heartBeatStaleTime;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    private final class MulticastServerThread
    extends Thread {
        private MulticastSocket socket;
        private List compressedUrlListList;
        private int cachePeersHash;

        public MulticastServerThread() {
            super("Multicast Heartbeat Sender Thread");
            this.compressedUrlListList = new ArrayList();
            this.setDaemon(true);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public final void run() {
            while (!MulticastKeepaliveHeartbeatSender.this.stopped) {
                try {
                    this.socket = new MulticastSocket(MulticastKeepaliveHeartbeatSender.this.groupMulticastPort);
                    if (MulticastKeepaliveHeartbeatSender.this.hostAddress != null) {
                        this.socket.setInterface(MulticastKeepaliveHeartbeatSender.this.hostAddress);
                    }
                    this.socket.setTimeToLive(MulticastKeepaliveHeartbeatSender.this.timeToLive);
                    this.socket.joinGroup(MulticastKeepaliveHeartbeatSender.this.groupMulticastAddress);
                    while (!MulticastKeepaliveHeartbeatSender.this.stopped) {
                        List buffers = this.createCachePeersPayload();
                        for (byte[] buffer : buffers) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, MulticastKeepaliveHeartbeatSender.this.groupMulticastAddress, MulticastKeepaliveHeartbeatSender.this.groupMulticastPort);
                            this.socket.send(packet);
                        }
                        try {
                            MulticastServerThread iter = this;
                            synchronized (iter) {
                                this.wait(heartBeatInterval);
                            }
                        }
                        catch (InterruptedException e) {
                            if (MulticastKeepaliveHeartbeatSender.this.stopped) continue;
                            LOG.error("Error receiving heartbeat. Initial cause was " + e.getMessage(), e);
                        }
                    }
                }
                catch (IOException e) {
                    LOG.debug("Error on multicast socket", e);
                }
                catch (Throwable e) {
                    LOG.info("Unexpected throwable in run thread. Continuing..." + e.getMessage(), e);
                }
                finally {
                    this.closeSocket();
                }
                if (MulticastKeepaliveHeartbeatSender.this.stopped) continue;
                try {
                    MulticastServerThread.sleep(heartBeatInterval);
                }
                catch (InterruptedException e) {
                    LOG.error("Sleep after error interrupted. Initial cause was " + e.getMessage(), e);
                }
            }
        }

        private List createCachePeersPayload() {
            CacheManagerPeerListener cacheManagerPeerListener = MulticastKeepaliveHeartbeatSender.this.cacheManager.getCachePeerListener("RMI");
            if (cacheManagerPeerListener == null) {
                LOG.warn("The RMICacheManagerPeerListener is missing. You need to configure a cacheManagerPeerListenerFactory with class=\"net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory\" in ehcache.xml.");
                return new ArrayList();
            }
            List localCachePeers = cacheManagerPeerListener.getBoundCachePeers();
            int newCachePeersHash = localCachePeers.hashCode();
            if (this.cachePeersHash != newCachePeersHash) {
                this.cachePeersHash = newCachePeersHash;
                this.compressedUrlListList = PayloadUtil.createCompressedPayloadList(localCachePeers, 150);
            }
            return this.compressedUrlListList;
        }

        @Override
        public final void interrupt() {
            this.closeSocket();
            super.interrupt();
        }

        private void closeSocket() {
            block6: {
                try {
                    if (this.socket == null || this.socket.isClosed()) break block6;
                    try {
                        this.socket.leaveGroup(MulticastKeepaliveHeartbeatSender.this.groupMulticastAddress);
                    }
                    catch (IOException e) {
                        LOG.error("Error leaving multicast group. Message was " + e.getMessage());
                    }
                    this.socket.close();
                }
                catch (NoSuchMethodError e) {
                    LOG.debug("socket.isClosed is not supported by JDK1.3");
                    try {
                        this.socket.leaveGroup(MulticastKeepaliveHeartbeatSender.this.groupMulticastAddress);
                    }
                    catch (IOException ex) {
                        LOG.error("Error leaving multicast group. Message was " + ex.getMessage());
                    }
                    this.socket.close();
                }
            }
        }
    }
}

