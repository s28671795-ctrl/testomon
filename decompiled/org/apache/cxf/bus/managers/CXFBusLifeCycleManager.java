/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;

@NoJSR250Annotations(unlessNull={"bus"})
public class CXFBusLifeCycleManager
implements BusLifeCycleManager {
    private final CopyOnWriteArrayList<BusLifeCycleListener> listeners = new CopyOnWriteArrayList();
    private Bus bus;
    private boolean initCalled;
    private boolean preShutdownCalled;
    private boolean postShutdownCalled;

    public CXFBusLifeCycleManager() {
    }

    public CXFBusLifeCycleManager(Bus b) {
        this.setBus(b);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.bus.setExtension(this, BusLifeCycleManager.class);
        }
    }

    @Override
    public final void registerLifeCycleListener(BusLifeCycleListener listener) {
        this.listeners.addIfAbsent(listener);
        if (this.initCalled) {
            listener.initComplete();
        }
    }

    @Override
    public void unregisterLifeCycleListener(BusLifeCycleListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void initComplete() {
        if (this.bus != null) {
            this.bus.getExtension(ConfiguredBeanLocator.class).getBeansOfType(BusLifeCycleListener.class);
        }
        this.preShutdownCalled = false;
        this.postShutdownCalled = false;
        this.initCalled = true;
        for (BusLifeCycleListener listener : this.listeners) {
            listener.initComplete();
        }
    }

    @Override
    public void preShutdown() {
        if (!this.preShutdownCalled) {
            this.preShutdownCalled = true;
            ListIterator<BusLifeCycleListener> li = this.listeners.listIterator(this.listeners.size());
            while (li.hasPrevious()) {
                li.previous().preShutdown();
            }
        }
    }

    @Override
    public void postShutdown() {
        if (!this.preShutdownCalled) {
            this.preShutdown();
        }
        if (!this.postShutdownCalled) {
            this.postShutdownCalled = true;
            ListIterator<BusLifeCycleListener> li = this.listeners.listIterator(this.listeners.size());
            while (li.hasPrevious()) {
                li.previous().postShutdown();
            }
        }
    }
}

