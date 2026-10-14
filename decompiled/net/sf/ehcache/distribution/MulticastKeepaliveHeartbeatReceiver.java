/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.MulticastRMICacheManagerPeerProvider;
import net.sf.ehcache.distribution.PayloadUtil;
import net.sf.ehcache.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MulticastKeepaliveHeartbeatReceiver {
    private static final Logger LOG = LoggerFactory.getLogger(MulticastKeepaliveHeartbeatReceiver.class.getName());
    private ExecutorService processingThreadPool;
    private Set rmiUrlsProcessingQueue = Collections.synchronizedSet(new HashSet());
    private final InetAddress groupMulticastAddress;
    private final Integer groupMulticastPort;
    private MulticastReceiverThread receiverThread;
    private MulticastSocket socket;
    private volatile boolean stopped;
    private final MulticastRMICacheManagerPeerProvider peerProvider;
    private InetAddress hostAddress;

    public MulticastKeepaliveHeartbeatReceiver(MulticastRMICacheManagerPeerProvider peerProvider, InetAddress multicastAddress, Integer multicastPort, InetAddress hostAddress) {
        this.peerProvider = peerProvider;
        this.groupMulticastAddress = multicastAddress;
        this.groupMulticastPort = multicastPort;
        this.hostAddress = hostAddress;
    }

    final void init() throws IOException {
        this.socket = new MulticastSocket(this.groupMulticastPort);
        if (this.hostAddress != null) {
            this.socket.setInterface(this.hostAddress);
        }
        this.socket.joinGroup(this.groupMulticastAddress);
        this.receiverThread = new MulticastReceiverThread();
        this.receiverThread.start();
        this.processingThreadPool = Executors.newCachedThreadPool(new NamedThreadFactory("Multicast keep-alive Heartbeat Receiver"));
    }

    public final void dispose() {
        LOG.debug("dispose called");
        this.processingThreadPool.shutdownNow();
        this.stopped = true;
        this.receiverThread.interrupt();
    }

    private final class MulticastReceiverThread
    extends Thread {
        public MulticastReceiverThread() {
            super("Multicast Heartbeat Receiver Thread");
            this.setDaemon(true);
        }

        @Override
        public final void run() {
            byte[] buf = new byte[1500];
            block4: while (true) {
                try {
                    while (!MulticastKeepaliveHeartbeatReceiver.this.stopped) {
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        try {
                            MulticastKeepaliveHeartbeatReceiver.this.socket.receive(packet);
                            byte[] payload = packet.getData();
                            this.processPayload(payload);
                            continue block4;
                        }
                        catch (IOException e) {
                            if (MulticastKeepaliveHeartbeatReceiver.this.stopped) continue;
                            LOG.error("Error receiving heartbeat. " + e.getMessage() + ". Initial cause was " + e.getMessage(), e);
                        }
                    }
                    break;
                }
                catch (Throwable t2) {
                    LOG.error("Multicast receiver thread caught throwable. Cause was " + t2.getMessage() + ". Continuing...");
                    break;
                }
            }
        }

        private void processPayload(byte[] compressedPayload) {
            byte[] payload = PayloadUtil.ungzip(compressedPayload);
            String rmiUrls = new String(payload);
            if (this.self(rmiUrls)) {
                return;
            }
            rmiUrls = rmiUrls.trim();
            LOG.debug("rmiUrls received {}", (Object)rmiUrls);
            this.processRmiUrls(rmiUrls);
        }

        private void processRmiUrls(final String rmiUrls) {
            if (MulticastKeepaliveHeartbeatReceiver.this.rmiUrlsProcessingQueue.contains(rmiUrls)) {
                LOG.debug("We are already processing these rmiUrls. Another heartbeat came before we finished: {}", (Object)rmiUrls);
                return;
            }
            if (MulticastKeepaliveHeartbeatReceiver.this.processingThreadPool == null) {
                return;
            }
            MulticastKeepaliveHeartbeatReceiver.this.processingThreadPool.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        MulticastKeepaliveHeartbeatReceiver.this.rmiUrlsProcessingQueue.add(rmiUrls);
                        StringTokenizer stringTokenizer = new StringTokenizer(rmiUrls, "|");
                        while (stringTokenizer.hasMoreTokens()) {
                            if (MulticastKeepaliveHeartbeatReceiver.this.stopped) {
                                return;
                            }
                            String rmiUrl = stringTokenizer.nextToken();
                            MulticastReceiverThread.this.registerNotification(rmiUrl);
                            if (((MulticastKeepaliveHeartbeatReceiver)MulticastKeepaliveHeartbeatReceiver.this).peerProvider.peerUrls.containsKey(rmiUrl)) continue;
                            LOG.debug("Aborting processing of rmiUrls since failed to add rmiUrl: {}", (Object)rmiUrl);
                            return;
                        }
                    }
                    finally {
                        MulticastKeepaliveHeartbeatReceiver.this.rmiUrlsProcessingQueue.remove(rmiUrls);
                    }
                }
            });
        }

        private boolean self(String rmiUrls) {
            CacheManager cacheManager = MulticastKeepaliveHeartbeatReceiver.this.peerProvider.getCacheManager();
            CacheManagerPeerListener cacheManagerPeerListener = cacheManager.getCachePeerListener("RMI");
            if (cacheManagerPeerListener == null) {
                return false;
            }
            List boundCachePeers = cacheManagerPeerListener.getBoundCachePeers();
            if (boundCachePeers == null || boundCachePeers.size() == 0) {
                return false;
            }
            CachePeer peer = (CachePeer)boundCachePeers.get(0);
            try {
                String cacheManagerUrlBase = peer.getUrlBase();
                int baseUrlMatch = rmiUrls.indexOf(cacheManagerUrlBase);
                return baseUrlMatch != -1;
            }
            catch (RemoteException e) {
                LOG.error("Error geting url base", e);
                return false;
            }
        }

        private void registerNotification(String rmiUrl) {
            MulticastKeepaliveHeartbeatReceiver.this.peerProvider.registerPeer(rmiUrl);
        }

        @Override
        public final void interrupt() {
            try {
                MulticastKeepaliveHeartbeatReceiver.this.socket.leaveGroup(MulticastKeepaliveHeartbeatReceiver.this.groupMulticastAddress);
            }
            catch (IOException e) {
                LOG.error("Error leaving group");
            }
            MulticastKeepaliveHeartbeatReceiver.this.socket.close();
            super.interrupt();
        }
    }
}

