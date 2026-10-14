/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.net.UnknownHostException;
import java.util.Properties;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheManagerPeerListenerFactory;
import net.sf.ehcache.distribution.RMICacheManagerPeerListener;
import net.sf.ehcache.util.PropertyUtil;

public class RMICacheManagerPeerListenerFactory
extends CacheManagerPeerListenerFactory {
    public static final Integer DEFAULT_SOCKET_TIMEOUT_MILLIS = 120000;
    private static final String HOSTNAME = "hostName";
    private static final String PORT = "port";
    private static final String REMOTE_OBJECT_PORT = "remoteObjectPort";
    private static final String SOCKET_TIMEOUT_MILLIS = "socketTimeoutMillis";

    @Override
    public final CacheManagerPeerListener createCachePeerListener(CacheManager cacheManager, Properties properties) throws CacheException {
        String hostName = PropertyUtil.extractAndLogProperty(HOSTNAME, properties);
        String portString = PropertyUtil.extractAndLogProperty(PORT, properties);
        Integer port = null;
        port = portString != null && portString.length() != 0 ? Integer.valueOf(portString) : Integer.valueOf(0);
        String remoteObjectPortString = PropertyUtil.extractAndLogProperty(REMOTE_OBJECT_PORT, properties);
        Integer remoteObjectPort = null;
        remoteObjectPort = remoteObjectPortString != null && remoteObjectPortString.length() != 0 ? Integer.valueOf(remoteObjectPortString) : Integer.valueOf(0);
        String socketTimeoutMillisString = PropertyUtil.extractAndLogProperty(SOCKET_TIMEOUT_MILLIS, properties);
        Integer socketTimeoutMillis = socketTimeoutMillisString == null || socketTimeoutMillisString.length() == 0 ? DEFAULT_SOCKET_TIMEOUT_MILLIS : Integer.valueOf(socketTimeoutMillisString);
        return this.doCreateCachePeerListener(hostName, port, remoteObjectPort, cacheManager, socketTimeoutMillis);
    }

    protected CacheManagerPeerListener doCreateCachePeerListener(String hostName, Integer port, Integer remoteObjectPort, CacheManager cacheManager, Integer socketTimeoutMillis) {
        try {
            return new RMICacheManagerPeerListener(hostName, port, remoteObjectPort, cacheManager, socketTimeoutMillis);
        }
        catch (UnknownHostException e) {
            throw new CacheException("Unable to create CacheManagerPeerListener. Initial cause was " + e.getMessage(), e);
        }
    }
}

