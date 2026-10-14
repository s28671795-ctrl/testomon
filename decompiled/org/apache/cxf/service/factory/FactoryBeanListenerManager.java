/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.service.factory.AnnotationsFactoryBeanListener;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.OldLoggingFactoryBeanListener;

@NoJSR250Annotations(unlessNull={"bus"})
public class FactoryBeanListenerManager {
    Bus bus;
    CopyOnWriteArrayList<FactoryBeanListener> listeners = new CopyOnWriteArrayList();

    public FactoryBeanListenerManager() {
        this.listeners.add(new AnnotationsFactoryBeanListener());
        this.listeners.add(new OldLoggingFactoryBeanListener());
    }

    public FactoryBeanListenerManager(Bus b) {
        this();
        this.setBus(b);
    }

    @Resource
    public final void setBus(Bus bus) {
        this.bus = bus;
        this.bus.setExtension(this, FactoryBeanListenerManager.class);
        ConfiguredBeanLocator loc = bus.getExtension(ConfiguredBeanLocator.class);
        if (loc != null) {
            for (FactoryBeanListener f : loc.getBeansOfType(FactoryBeanListener.class)) {
                this.listeners.add(0, f);
            }
        }
    }

    public List<FactoryBeanListener> getListeners() {
        return this.listeners;
    }

    public void addListener(FactoryBeanListener l) {
        this.listeners.addIfAbsent(l);
    }

    public void removeListener(FactoryBeanListener l) {
        this.listeners.remove(l);
    }
}

