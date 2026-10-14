/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.ConfigurableRMIClientSocketFactory;
import net.sf.ehcache.distribution.RmiEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMICachePeer
extends UnicastRemoteObject
implements CachePeer,
Remote {
    private static final Logger LOG = LoggerFactory.getLogger(RMICachePeer.class.getName());
    private final String hostname;
    private final Integer rmiRegistryPort;
    private Integer remoteObjectPort;
    private final Ehcache cache;

    public RMICachePeer(Ehcache cache, String hostName, Integer rmiRegistryPort, Integer remoteObjectPort, Integer socketTimeoutMillis) throws RemoteException {
        super(remoteObjectPort, new ConfigurableRMIClientSocketFactory(socketTimeoutMillis), ConfigurableRMIClientSocketFactory.getConfiguredRMISocketFactory());
        this.remoteObjectPort = remoteObjectPort;
        this.hostname = hostName;
        this.rmiRegistryPort = rmiRegistryPort;
        this.cache = cache;
    }

    @Override
    public final String getUrl() {
        return "//" + (this.hostname.contains(":") ? "[" + this.hostname + "]" : this.hostname) + ":" + this.rmiRegistryPort + "/" + this.cache.getName();
    }

    @Override
    public final String getUrlBase() {
        return "//" + (this.hostname.contains(":") ? "[" + this.hostname + "]" : this.hostname) + ":" + this.rmiRegistryPort;
    }

    @Override
    public List getKeys() throws RemoteException {
        List keys2 = this.cache.getKeys();
        if (keys2 instanceof Serializable) {
            return keys2;
        }
        return new ArrayList(keys2);
    }

    @Override
    public Element getQuiet(Serializable key) throws RemoteException {
        return this.cache.getQuiet(key);
    }

    @Override
    public List getElements(List keys2) throws RemoteException {
        if (keys2 == null) {
            return new ArrayList();
        }
        ArrayList<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < keys2.size(); ++i) {
            Serializable key = (Serializable)keys2.get(i);
            Element element = this.cache.getQuiet(key);
            if (element == null) continue;
            elements.add(element);
        }
        return elements;
    }

    @Override
    public void put(Element element) throws RemoteException, IllegalArgumentException, IllegalStateException {
        this.cache.put(element, true);
        if (LOG.isDebugEnabled()) {
            LOG.debug("RMICachePeer for cache " + this.cache.getName() + ": remote put received. Element is: " + element);
        }
    }

    @Override
    public boolean remove(Serializable key) throws RemoteException, IllegalStateException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("RMICachePeer for cache " + this.cache.getName() + ": remote remove received for key: " + key);
        }
        return this.cache.remove(key, true);
    }

    @Override
    public void removeAll() throws RemoteException, IllegalStateException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("RMICachePeer for cache " + this.cache.getName() + ": remote removeAll received");
        }
        this.cache.removeAll(true);
    }

    @Override
    public void send(List eventMessages) throws RemoteException {
        for (int i = 0; i < eventMessages.size(); ++i) {
            RmiEventMessage eventMessage = (RmiEventMessage)eventMessages.get(i);
            if (eventMessage.getType() == RmiEventMessage.RmiEventType.PUT) {
                this.put(eventMessage.getElement());
                continue;
            }
            if (eventMessage.getType() == RmiEventMessage.RmiEventType.REMOVE) {
                this.remove(eventMessage.getSerializableKey());
                continue;
            }
            if (eventMessage.getType() == RmiEventMessage.RmiEventType.REMOVE_ALL) {
                this.removeAll();
                continue;
            }
            LOG.error("Unknown event: " + eventMessage);
        }
    }

    @Override
    public final String getName() throws RemoteException {
        return this.cache.getName();
    }

    @Override
    public final String getGuid() throws RemoteException {
        return this.cache.getGuid();
    }

    final Ehcache getBoundCacheInstance() {
        return this.cache;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("URL: ");
        buffer.append(this.getUrl());
        buffer.append(" Remote Object Port: ");
        buffer.append(this.remoteObjectPort);
        return buffer.toString();
    }
}

