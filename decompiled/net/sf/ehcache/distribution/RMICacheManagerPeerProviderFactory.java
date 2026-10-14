/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.StringTokenizer;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CacheManagerPeerProviderFactory;
import net.sf.ehcache.distribution.ManualRMICacheManagerPeerProvider;
import net.sf.ehcache.distribution.MulticastRMICacheManagerPeerProvider;
import net.sf.ehcache.distribution.RMICacheManagerPeerProvider;
import net.sf.ehcache.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMICacheManagerPeerProviderFactory
extends CacheManagerPeerProviderFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RMICacheManagerPeerProviderFactory.class.getName());
    private static final String HOST_NAME = "hostName";
    private static final String PEER_DISCOVERY = "peerDiscovery";
    private static final String AUTOMATIC_PEER_DISCOVERY = "automatic";
    private static final String MANUALLY_CONFIGURED_PEER_DISCOVERY = "manual";
    private static final String RMI_URLS = "rmiUrls";
    private static final String MULTICAST_GROUP_PORT = "multicastGroupPort";
    private static final String MULTICAST_GROUP_ADDRESS = "multicastGroupAddress";
    private static final String MULTICAST_PACKET_TTL = "timeToLive";
    private static final int MAXIMUM_TTL = 255;

    @Override
    public CacheManagerPeerProvider createCachePeerProvider(CacheManager cacheManager, Properties properties) throws CacheException {
        String peerDiscovery = PropertyUtil.extractAndLogProperty(PEER_DISCOVERY, properties);
        if (peerDiscovery == null || peerDiscovery.equalsIgnoreCase(AUTOMATIC_PEER_DISCOVERY)) {
            try {
                return this.createAutomaticallyConfiguredCachePeerProvider(cacheManager, properties);
            }
            catch (IOException e) {
                throw new CacheException("Could not create CacheManagerPeerProvider. Initial cause was " + e.getMessage(), e);
            }
        }
        if (peerDiscovery.equalsIgnoreCase(MANUALLY_CONFIGURED_PEER_DISCOVERY)) {
            return this.createManuallyConfiguredCachePeerProvider(properties);
        }
        return null;
    }

    protected CacheManagerPeerProvider createManuallyConfiguredCachePeerProvider(Properties properties) {
        String rmiUrls = PropertyUtil.extractAndLogProperty(RMI_URLS, properties);
        if (rmiUrls == null || rmiUrls.length() == 0) {
            LOG.info("Starting manual peer provider with empty list of peers. No replication will occur unless peers are added.");
            rmiUrls = "";
        }
        rmiUrls = rmiUrls.trim();
        StringTokenizer stringTokenizer = new StringTokenizer(rmiUrls, "|");
        ManualRMICacheManagerPeerProvider rmiPeerProvider = new ManualRMICacheManagerPeerProvider();
        while (stringTokenizer.hasMoreTokens()) {
            String rmiUrl = stringTokenizer.nextToken();
            rmiUrl = rmiUrl.trim();
            ((RMICacheManagerPeerProvider)rmiPeerProvider).registerPeer(rmiUrl);
            LOG.debug("Registering peer {}", (Object)rmiUrl);
        }
        return rmiPeerProvider;
    }

    protected CacheManagerPeerProvider createAutomaticallyConfiguredCachePeerProvider(CacheManager cacheManager, Properties properties) throws IOException {
        Integer timeToLive;
        String hostName = PropertyUtil.extractAndLogProperty(HOST_NAME, properties);
        InetAddress hostAddress = null;
        if (hostName != null && hostName.length() != 0) {
            hostAddress = InetAddress.getByName(hostName);
            if (hostName.equals("localhost")) {
                LOG.warn("Explicitly setting the multicast hostname to 'localhost' is not recommended. It will only work if all CacheManager peers are on the same machine.");
            }
        }
        String groupAddressString = PropertyUtil.extractAndLogProperty(MULTICAST_GROUP_ADDRESS, properties);
        InetAddress groupAddress = InetAddress.getByName(groupAddressString);
        String multicastPortString = PropertyUtil.extractAndLogProperty(MULTICAST_GROUP_PORT, properties);
        Integer multicastPort = Integer.valueOf(multicastPortString);
        String packetTimeToLiveString = PropertyUtil.extractAndLogProperty(MULTICAST_PACKET_TTL, properties);
        if (packetTimeToLiveString == null) {
            timeToLive = 1;
            LOG.debug("No TTL set. Setting it to the default of 1, which means packets are limited to the same subnet.");
        } else {
            timeToLive = Integer.valueOf(packetTimeToLiveString);
            if (timeToLive < 0 || timeToLive > 255) {
                throw new CacheException("The TTL must be set to a value between 0 and 255");
            }
        }
        return new MulticastRMICacheManagerPeerProvider(cacheManager, groupAddress, multicastPort, timeToLive, hostAddress);
    }
}

