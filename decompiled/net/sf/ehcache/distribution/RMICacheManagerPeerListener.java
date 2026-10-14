/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheReplicator;
import net.sf.ehcache.distribution.RMICachePeer;
import net.sf.ehcache.distribution.TransactionalRMICachePeer;
import net.sf.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMICacheManagerPeerListener
implements CacheManagerPeerListener {
    private static final Logger LOG = LoggerFactory.getLogger(RMICacheManagerPeerListener.class.getName());
    private static final int MINIMUM_SENSIBLE_TIMEOUT = 200;
    private static final int NAMING_UNBIND_RETRY_INTERVAL = 400;
    private static final int NAMING_UNBIND_MAX_RETRIES = 10;
    protected final Map cachePeers = new HashMap();
    protected Status status = Status.STATUS_UNINITIALISED;
    protected Integer port;
    private Registry registry;
    private boolean registryCreated;
    private final String hostName;
    private CacheManager cacheManager;
    private Integer socketTimeoutMillis;
    private Integer remoteObjectPort;

    public RMICacheManagerPeerListener(String hostName, Integer port, Integer remoteObjectPort, CacheManager cacheManager, Integer socketTimeoutMillis) throws UnknownHostException {
        if (hostName != null && hostName.length() != 0) {
            this.hostName = hostName;
            if (hostName.equals("localhost")) {
                LOG.warn("Explicitly setting the listener hostname to 'localhost' is not recommended. It will only work if all CacheManager peers are on the same machine.");
            }
        } else {
            this.hostName = this.calculateHostAddress();
        }
        if (port == null || port == 0) {
            this.assignFreePort(false);
        } else {
            this.port = port;
        }
        this.remoteObjectPort = remoteObjectPort;
        this.cacheManager = cacheManager;
        if (socketTimeoutMillis == null || socketTimeoutMillis < 200) {
            throw new IllegalArgumentException("socketTimoutMillis must be a reasonable value greater than 200ms");
        }
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    protected void assignFreePort(boolean forced) throws IllegalStateException {
        if (this.status != Status.STATUS_UNINITIALISED) {
            throw new IllegalStateException("Cannot change the port of an already started listener.");
        }
        this.port = this.getFreePort();
        if (forced) {
            LOG.warn("Resolving RMI port conflict by automatically using a free TCP/IP port to listen on: " + this.port);
        } else {
            LOG.debug("Automatically finding a free TCP/IP port to listen on: " + this.port);
        }
    }

    protected String calculateHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    protected int getFreePort() throws IllegalArgumentException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            int n = serverSocket.getLocalPort();
            return n;
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not acquire a free port number.");
        }
        finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                }
                catch (Exception e) {
                    LOG.debug("Error closing ServerSocket: " + e.getMessage());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void init() throws CacheException {
        if (!this.status.equals(Status.STATUS_UNINITIALISED)) {
            return;
        }
        RMICachePeer rmiCachePeer2 = null;
        try {
            this.startRegistry();
            int counter = 0;
            this.populateListOfRemoteCachePeers();
            Map map = this.cachePeers;
            synchronized (map) {
                for (RMICachePeer rmiCachePeer2 : this.cachePeers.values()) {
                    this.bind(rmiCachePeer2.getUrl(), rmiCachePeer2);
                    ++counter;
                }
            }
            LOG.debug(counter + " RMICachePeers bound in registry for RMI listener");
            this.status = Status.STATUS_ALIVE;
        }
        catch (Exception e) {
            String url2 = null;
            if (rmiCachePeer2 != null) {
                url2 = rmiCachePeer2.getUrl();
            }
            throw new CacheException("Problem starting listener for RMICachePeer " + url2 + ". Initial cause was " + e.getMessage(), e);
        }
    }

    protected void bind(String peerName, RMICachePeer rmiCachePeer) throws Exception {
        Naming.rebind(peerName, rmiCachePeer);
    }

    protected String[] listBoundRMICachePeers() throws CacheException {
        try {
            return this.registry.list();
        }
        catch (RemoteException e) {
            throw new CacheException("Unable to list cache peers " + e.getMessage());
        }
    }

    protected Remote lookupPeer(String name) throws CacheException {
        try {
            return this.registry.lookup(name);
        }
        catch (Exception e) {
            throw new CacheException("Unable to lookup peer for replicated cache " + name + " " + e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void populateListOfRemoteCachePeers() throws RemoteException {
        String[] names = this.cacheManager.getCacheNames();
        for (int i = 0; i < names.length; ++i) {
            String name = names[i];
            Ehcache cache = this.cacheManager.getEhcache(name);
            Map map = this.cachePeers;
            synchronized (map) {
                if (this.cachePeers.get(name) == null && this.isDistributed(cache)) {
                    RMICachePeer peer = cache.getCacheConfiguration().getTransactionalMode().isTransactional() ? new TransactionalRMICachePeer(cache, this.hostName, this.port, this.remoteObjectPort, this.socketTimeoutMillis) : new RMICachePeer(cache, this.hostName, this.port, this.remoteObjectPort, this.socketTimeoutMillis);
                    this.cachePeers.put(name, peer);
                }
                continue;
            }
        }
    }

    protected boolean isDistributed(Ehcache cache) {
        Set<CacheEventListener> listeners = cache.getCacheEventNotificationService().getCacheEventListeners();
        for (CacheEventListener cacheEventListener : listeners) {
            if (!(cacheEventListener instanceof CacheReplicator)) continue;
            return true;
        }
        return false;
    }

    protected void startRegistry() throws RemoteException {
        try {
            this.registry = LocateRegistry.getRegistry(this.port);
            try {
                this.registry.list();
            }
            catch (RemoteException e) {
                this.registry = LocateRegistry.createRegistry(this.port);
                this.registryCreated = true;
            }
        }
        catch (ExportException exception) {
            LOG.error("Exception starting RMI registry. Error was " + exception.getMessage(), exception);
        }
    }

    protected void stopRegistry() throws RemoteException {
        if (this.registryCreated) {
            boolean success = UnicastRemoteObject.unexportObject(this.registry, true);
            if (success) {
                LOG.debug("rmiregistry unexported.");
            } else {
                LOG.warn("Could not unexport rmiregistry.");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dispose() throws CacheException {
        if (!this.status.equals(Status.STATUS_ALIVE)) {
            return;
        }
        try {
            int counter = 0;
            Map map = this.cachePeers;
            synchronized (map) {
                for (RMICachePeer rmiCachePeer : this.cachePeers.values()) {
                    this.disposeRMICachePeer(rmiCachePeer);
                    ++counter;
                }
                this.stopRegistry();
            }
            LOG.debug(counter + " RMICachePeers unbound from registry in RMI listener");
            this.status = Status.STATUS_SHUTDOWN;
        }
        catch (Exception e) {
            throw new CacheException("Problem unbinding remote cache peers. Initial cause was " + e.getMessage(), e);
        }
    }

    protected void disposeRMICachePeer(RMICachePeer rmiCachePeer) throws Exception {
        this.unbind(rmiCachePeer);
    }

    protected void unbind(RMICachePeer rmiCachePeer) throws Exception {
        String url2 = rmiCachePeer.getUrl();
        try {
            Naming.unbind(url2);
        }
        catch (NotBoundException e) {
            LOG.warn(url2 + " not bound therefore not unbinding.");
        }
        boolean unexported = UnicastRemoteObject.unexportObject(rmiCachePeer, false);
        for (int count = 1; count < 10 && !unexported; ++count) {
            try {
                Thread.sleep(400L);
            }
            catch (InterruptedException ie) {
                break;
            }
            unexported = UnicastRemoteObject.unexportObject(rmiCachePeer, false);
        }
        if (!unexported && !UnicastRemoteObject.unexportObject(rmiCachePeer, true)) {
            LOG.warn("Unable to unexport rmiCachePeer: " + rmiCachePeer.getUrl() + ".  Skipping.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List getBoundCachePeers() {
        ArrayList<RMICachePeer> cachePeerList = new ArrayList<RMICachePeer>();
        Map map = this.cachePeers;
        synchronized (map) {
            for (RMICachePeer rmiCachePeer : this.cachePeers.values()) {
                cachePeerList.add(rmiCachePeer);
            }
        }
        return cachePeerList;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public String getUniqueResourceIdentifier() {
        return "RMI listener port: " + this.port;
    }

    @Override
    public void attemptResolutionOfUniqueResourceConflict() throws IllegalStateException, CacheException {
        this.assignFreePort(true);
    }

    @Override
    public String getScheme() {
        return "RMI";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void notifyCacheAdded(String cacheName) throws CacheException {
        LOG.debug("Adding to RMI listener", (Object)cacheName);
        Map map = this.cachePeers;
        synchronized (map) {
            if (this.cachePeers.get(cacheName) != null) {
                return;
            }
        }
        Ehcache cache = this.cacheManager.getEhcache(cacheName);
        if (this.isDistributed(cache)) {
            RMICachePeer rmiCachePeer = null;
            String url2 = null;
            try {
                rmiCachePeer = cache.getCacheConfiguration().getTransactionalMode().isTransactional() ? new TransactionalRMICachePeer(cache, this.hostName, this.port, this.remoteObjectPort, this.socketTimeoutMillis) : new RMICachePeer(cache, this.hostName, this.port, this.remoteObjectPort, this.socketTimeoutMillis);
                url2 = rmiCachePeer.getUrl();
                this.bind(url2, rmiCachePeer);
            }
            catch (Exception e) {
                throw new CacheException("Problem starting listener for RMICachePeer " + url2 + ". Initial cause was " + e.getMessage(), e);
            }
            Map map2 = this.cachePeers;
            synchronized (map2) {
                this.cachePeers.put(cacheName, rmiCachePeer);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(this.cachePeers.size() + " RMICachePeers bound in registry for RMI listener");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void notifyCacheRemoved(String cacheName) {
        RMICachePeer rmiCachePeer;
        LOG.debug("Removing from RMI listener", (Object)cacheName);
        Map map = this.cachePeers;
        synchronized (map) {
            if (this.cachePeers.get(cacheName) == null) {
                return;
            }
        }
        Map map2 = this.cachePeers;
        synchronized (map2) {
            rmiCachePeer = (RMICachePeer)this.cachePeers.remove(cacheName);
        }
        String url2 = null;
        try {
            this.unbind(rmiCachePeer);
        }
        catch (Exception e) {
            throw new CacheException("Error removing Cache Peer " + url2 + " from listener. Message was: " + e.getMessage(), e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(this.cachePeers.size() + " RMICachePeers bound in registry for RMI listener");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addCachePeer(String name, RMICachePeer peer) {
        Map map = this.cachePeers;
        synchronized (map) {
            this.cachePeers.put(name, peer);
        }
    }
}

