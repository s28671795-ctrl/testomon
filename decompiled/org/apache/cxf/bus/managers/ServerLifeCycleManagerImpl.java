/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Collection;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerLifeCycleListener;
import org.apache.cxf.endpoint.ServerLifeCycleManager;
import org.apache.cxf.extension.BusExtension;

@NoJSR250Annotations
public class ServerLifeCycleManagerImpl
implements ServerLifeCycleManager,
BusExtension {
    private CopyOnWriteArrayList<ServerLifeCycleListener> listeners = new CopyOnWriteArrayList();

    public ServerLifeCycleManagerImpl() {
    }

    public ServerLifeCycleManagerImpl(Bus b) {
        Collection<ServerLifeCycleListener> l = b.getExtension(ConfiguredBeanLocator.class).getBeansOfType(ServerLifeCycleListener.class);
        if (l != null) {
            this.listeners.addAll(l);
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return ServerLifeCycleManager.class;
    }

    @Override
    public synchronized void registerListener(ServerLifeCycleListener listener) {
        this.listeners.addIfAbsent(listener);
    }

    @Override
    public void startServer(Server server) {
        for (ServerLifeCycleListener listener : this.listeners) {
            listener.startServer(server);
        }
    }

    @Override
    public void stopServer(Server server) {
        ListIterator<ServerLifeCycleListener> li = this.listeners.listIterator(this.listeners.size());
        while (li.hasPrevious()) {
            li.previous().stopServer(server);
        }
    }

    @Override
    public synchronized void unRegisterListener(ServerLifeCycleListener listener) {
        this.listeners.remove(listener);
    }
}

