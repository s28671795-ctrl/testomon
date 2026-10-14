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
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.endpoint.ClientLifeCycleManager;
import org.apache.cxf.extension.BusExtension;

@NoJSR250Annotations
public class ClientLifeCycleManagerImpl
implements ClientLifeCycleManager,
BusExtension {
    private CopyOnWriteArrayList<ClientLifeCycleListener> listeners = new CopyOnWriteArrayList();

    public ClientLifeCycleManagerImpl() {
    }

    public ClientLifeCycleManagerImpl(Bus b) {
        Collection<ClientLifeCycleListener> l = b.getExtension(ConfiguredBeanLocator.class).getBeansOfType(ClientLifeCycleListener.class);
        if (l != null) {
            this.listeners.addAll(l);
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return ClientLifeCycleManager.class;
    }

    @Override
    public void registerListener(ClientLifeCycleListener listener) {
        this.listeners.addIfAbsent(listener);
    }

    @Override
    public void clientCreated(Client client) {
        for (ClientLifeCycleListener listener : this.listeners) {
            listener.clientCreated(client);
        }
    }

    @Override
    public void clientDestroyed(Client client) {
        ListIterator<ClientLifeCycleListener> li = this.listeners.listIterator(this.listeners.size());
        while (li.hasPrevious()) {
            li.previous().clientDestroyed(client);
        }
    }

    @Override
    public void unRegisterListener(ClientLifeCycleListener listener) {
        this.listeners.remove(listener);
    }
}

