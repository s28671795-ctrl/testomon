/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;

@NoJSR250Annotations(unlessNull={"bus"})
public class ServerRegistryImpl
implements ServerRegistry,
BusLifeCycleListener {
    CopyOnWriteArrayList<Server> serversList = new CopyOnWriteArrayList();
    Bus bus;
    BusLifeCycleManager lifeCycleManager;

    public ServerRegistryImpl() {
    }

    public ServerRegistryImpl(Bus b) {
        this.setBus(b);
    }

    public Bus getBus() {
        return this.bus;
    }

    @Resource
    public final void setBus(Bus bus) {
        this.bus = bus;
        if (null != bus) {
            bus.setExtension(this, ServerRegistry.class);
            this.lifeCycleManager = bus.getExtension(BusLifeCycleManager.class);
            if (null != this.lifeCycleManager) {
                this.lifeCycleManager.registerLifeCycleListener(this);
            }
        }
    }

    @Override
    public void register(Server server) {
        this.serversList.addIfAbsent(server);
    }

    @Override
    public void unregister(Server server) {
        this.serversList.remove(server);
    }

    @Override
    public List<Server> getServers() {
        return this.serversList;
    }

    @Override
    public void initComplete() {
    }

    @Override
    @PreDestroy
    public void preShutdown() {
        Object[] servers = this.serversList.toArray();
        for (int i = 0; i < servers.length; ++i) {
            Server server = (Server)servers[i];
            server.destroy();
        }
    }

    @Override
    public void postShutdown() {
        this.serversList.clear();
    }
}

