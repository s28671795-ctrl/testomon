/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.RMICachePeer;
import net.sf.ehcache.util.CacheTransactionHelper;

public class TransactionalRMICachePeer
extends RMICachePeer {
    private final Ehcache cache;

    public TransactionalRMICachePeer(Ehcache cache, String hostName, Integer rmiRegistryPort, Integer remoteObjectPort, Integer socketTimeoutMillis) throws RemoteException {
        super(cache, hostName, rmiRegistryPort, remoteObjectPort, socketTimeoutMillis);
        this.cache = cache;
    }

    @Override
    public List getKeys() throws RemoteException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            List list = super.getKeys();
            return list;
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Element getQuiet(Serializable key) throws RemoteException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            Element element = super.getQuiet(key);
            return element;
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List getElements(List keys2) throws RemoteException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            List list = super.getElements(keys2);
            return list;
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    @Override
    public void put(Element element) throws RemoteException, IllegalArgumentException, IllegalStateException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            super.put(element);
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(Serializable key) throws RemoteException, IllegalStateException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            boolean bl = super.remove(key);
            return bl;
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    @Override
    public void removeAll() throws RemoteException, IllegalStateException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            super.removeAll();
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }

    @Override
    public void send(List eventMessages) throws RemoteException {
        boolean started = CacheTransactionHelper.isTransactionStarted(this.cache);
        if (!started) {
            CacheTransactionHelper.beginTransactionIfNeeded(this.cache);
        }
        try {
            super.send(eventMessages);
        }
        finally {
            if (!started) {
                CacheTransactionHelper.commitTransactionIfNeeded(this.cache);
            }
        }
    }
}

