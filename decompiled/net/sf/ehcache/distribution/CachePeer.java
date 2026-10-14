/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import net.sf.ehcache.Element;

public interface CachePeer
extends Remote {
    public void put(Element var1) throws IllegalArgumentException, IllegalStateException, RemoteException;

    public boolean remove(Serializable var1) throws IllegalStateException, RemoteException;

    public void removeAll() throws RemoteException, IllegalStateException;

    public void send(List var1) throws RemoteException;

    public String getName() throws RemoteException;

    public String getGuid() throws RemoteException;

    public String getUrl() throws RemoteException;

    public String getUrlBase() throws RemoteException;

    public List getKeys() throws RemoteException;

    public Element getQuiet(Serializable var1) throws RemoteException;

    public List getElements(List var1) throws RemoteException;
}

