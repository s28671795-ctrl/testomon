/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.TransportFinder;

@NoJSR250Annotations(unlessNull={"bus"})
public final class ConduitInitiatorManagerImpl
implements ConduitInitiatorManager {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(ConduitInitiatorManagerImpl.class);
    Map<String, ConduitInitiator> conduitInitiators;
    Set<String> failed = new CopyOnWriteArraySet<String>();
    Set<String> loaded = new CopyOnWriteArraySet<String>();
    private Bus bus;

    public ConduitInitiatorManagerImpl() {
        this.conduitInitiators = new ConcurrentHashMap<String, ConduitInitiator>(8, 0.75f, 4);
    }

    public ConduitInitiatorManagerImpl(Bus b) {
        this.conduitInitiators = new ConcurrentHashMap<String, ConduitInitiator>(8, 0.75f, 4);
        this.setBus(b);
    }

    @Resource
    public void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.bus.setExtension(this, ConduitInitiatorManager.class);
        }
    }

    @Override
    public void registerConduitInitiator(String namespace, ConduitInitiator factory2) {
        this.conduitInitiators.put(namespace, factory2);
    }

    @Override
    public void deregisterConduitInitiator(String namespace) {
        this.conduitInitiators.remove(namespace);
    }

    @Override
    public ConduitInitiator getConduitInitiator(String namespace) throws BusException {
        ConduitInitiator factory2 = this.conduitInitiators.get(namespace);
        if (factory2 == null && !this.failed.contains(namespace)) {
            factory2 = new TransportFinder<ConduitInitiator>(this.bus, this.conduitInitiators, this.loaded, ConduitInitiator.class).findTransportForNamespace(namespace);
        }
        if (factory2 == null) {
            this.failed.add(namespace);
            throw new BusException(new Message("NO_CONDUIT_INITIATOR", BUNDLE, namespace));
        }
        return factory2;
    }

    @PreDestroy
    public void shutdown() {
    }

    @Override
    public ConduitInitiator getConduitInitiatorForUri(String uri) {
        return new TransportFinder<ConduitInitiator>(this.bus, this.conduitInitiators, this.loaded, ConduitInitiator.class).findTransportForURI(uri);
    }
}

