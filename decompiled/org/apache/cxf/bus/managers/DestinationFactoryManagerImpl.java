/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.TransportFinder;

@NoJSR250Annotations(unlessNull={"bus"})
public final class DestinationFactoryManagerImpl
implements DestinationFactoryManager {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(DestinationFactoryManagerImpl.class);
    Map<String, DestinationFactory> destinationFactories;
    Set<String> failed = new CopyOnWriteArraySet<String>();
    Set<String> loaded = new CopyOnWriteArraySet<String>();
    Properties factoryNamespaceMappings;
    private Bus bus;

    public DestinationFactoryManagerImpl() {
        this.destinationFactories = new ConcurrentHashMap<String, DestinationFactory>(8, 0.75f, 4);
    }

    public DestinationFactoryManagerImpl(Bus b) {
        this.destinationFactories = new ConcurrentHashMap<String, DestinationFactory>(8, 0.75f, 4);
        this.setBus(b);
    }

    public DestinationFactoryManagerImpl(Map<String, DestinationFactory> destinationFactories) {
        this.destinationFactories = destinationFactories;
    }

    public DestinationFactoryManagerImpl(Map<String, DestinationFactory> destinationFactories, Bus b) {
        this.destinationFactories = destinationFactories;
        this.setBus(b);
    }

    @Resource
    public void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.bus.setExtension(this, DestinationFactoryManager.class);
        }
    }

    @Override
    public void registerDestinationFactory(String namespace, DestinationFactory factory2) {
        this.destinationFactories.put(namespace, factory2);
    }

    @Override
    public void deregisterDestinationFactory(String namespace) {
        this.destinationFactories.remove(namespace);
    }

    @Override
    public DestinationFactory getDestinationFactory(String namespace) throws BusException {
        DestinationFactory factory2 = this.destinationFactories.get(namespace);
        if (factory2 == null && !this.failed.contains(namespace)) {
            factory2 = new TransportFinder<DestinationFactory>(this.bus, this.destinationFactories, this.loaded, DestinationFactory.class).findTransportForNamespace(namespace);
        }
        if (factory2 == null) {
            this.failed.add(namespace);
            throw new BusException(new Message("NO_DEST_FACTORY", BUNDLE, namespace));
        }
        return factory2;
    }

    @Override
    public DestinationFactory getDestinationFactoryForUri(String uri) {
        return new TransportFinder<DestinationFactory>(this.bus, this.destinationFactories, this.loaded, DestinationFactory.class).findTransportForURI(uri);
    }

    @Override
    public Set<String> getRegisteredDestinationFactoryNames() {
        return this.destinationFactories == null ? Collections.emptySet() : Collections.unmodifiableSet(this.destinationFactories.keySet());
    }
}

