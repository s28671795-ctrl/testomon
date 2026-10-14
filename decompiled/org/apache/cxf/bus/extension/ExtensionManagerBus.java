/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.bus.extension.ExtensionManager;
import org.apache.cxf.bus.extension.ExtensionManagerImpl;
import org.apache.cxf.bus.managers.BindingFactoryManagerImpl;
import org.apache.cxf.bus.managers.ConduitInitiatorManagerImpl;
import org.apache.cxf.bus.managers.DestinationFactoryManagerImpl;
import org.apache.cxf.buslifecycle.BusCreationListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.configuration.NullConfigurer;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.resource.DefaultResourceManager;
import org.apache.cxf.resource.ObjectTypeResolver;
import org.apache.cxf.resource.PropertiesResolver;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;
import org.apache.cxf.resource.SinglePropertyResolver;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactoryManager;

public class ExtensionManagerBus
extends AbstractBasicInterceptorProvider
implements Bus {
    public static final String BUS_PROPERTY_NAME = "bus";
    private static final String BUS_ID_PROPERTY_NAME = "org.apache.cxf.bus.id";
    protected final Map<Class<?>, Object> extensions;
    protected final Set<Class<?>> missingExtensions;
    protected String id;
    private Bus.BusState state;
    private final Collection<Feature> features = new CopyOnWriteArrayList<Feature>();
    private final Map<String, Object> properties = new ConcurrentHashMap<String, Object>(16, 0.75f, 4);
    private final ExtensionManagerImpl extensionManager;

    public ExtensionManagerBus(Map<Class<?>, Object> extensions, Map<String, Object> props, ClassLoader extensionClassLoader) {
        Configurer configurer;
        this.extensions = extensions == null ? new ConcurrentHashMap(16, 0.75f, 4) : new ConcurrentHashMap(extensions);
        this.missingExtensions = new CopyOnWriteArraySet();
        this.state = Bus.BusState.INITIAL;
        BusFactory.possiblySetDefaultBus(this);
        if (null != props) {
            this.properties.putAll(props);
        }
        if (null == (configurer = (Configurer)this.extensions.get(Configurer.class))) {
            configurer = new NullConfigurer();
            this.extensions.put(Configurer.class, configurer);
        }
        this.id = ExtensionManagerBus.getBusId(this.properties);
        DefaultResourceManager resourceManager = new DefaultResourceManager();
        this.properties.put(BUS_ID_PROPERTY_NAME, BUS_PROPERTY_NAME);
        this.properties.put(BUS_PROPERTY_NAME, this);
        this.properties.put("cxf", this);
        PropertiesResolver propertiesResolver = new PropertiesResolver(this.properties);
        resourceManager.addResourceResolver(propertiesResolver);
        SinglePropertyResolver busResolver = new SinglePropertyResolver(BUS_PROPERTY_NAME, this);
        resourceManager.addResourceResolver(busResolver);
        resourceManager.addResourceResolver(new ObjectTypeResolver(this));
        busResolver = new SinglePropertyResolver("cxf", this);
        resourceManager.addResourceResolver(busResolver);
        resourceManager.addResourceResolver(new ObjectTypeResolver(this));
        resourceManager.addResourceResolver(new ResourceResolver(){

            @Override
            public <T> T resolve(String resourceName, Class<T> resourceType) {
                if (ExtensionManagerBus.this.extensionManager != null) {
                    return ExtensionManagerBus.this.extensionManager.getExtension(resourceName, resourceType);
                }
                return null;
            }

            @Override
            public InputStream getAsStream(String name) {
                return null;
            }
        });
        this.extensions.put(ResourceManager.class, resourceManager);
        this.extensionManager = new ExtensionManagerImpl(new String[0], extensionClassLoader, this.extensions, (ResourceManager)resourceManager, (Bus)this);
        this.setState(Bus.BusState.INITIAL);
        if (null == this.getExtension(DestinationFactoryManager.class)) {
            new DestinationFactoryManagerImpl(this);
        }
        if (null == this.getExtension(ConduitInitiatorManager.class)) {
            new ConduitInitiatorManagerImpl(this);
        }
        if (null == this.getExtension(BindingFactoryManager.class)) {
            new BindingFactoryManagerImpl(this);
        }
        this.extensionManager.load(new String[]{"META-INF/cxf/bus-extensions.txt"});
        this.extensionManager.activateAllByType(ResourceResolver.class);
        this.extensions.put(ExtensionManager.class, this.extensionManager);
    }

    public ExtensionManagerBus(Map<Class<?>, Object> e, Map<String, Object> properties) {
        this(e, properties, Thread.currentThread().getContextClassLoader());
    }

    public ExtensionManagerBus(Map<Class<?>, Object> e) {
        this(e, null, Thread.currentThread().getContextClassLoader());
    }

    public ExtensionManagerBus() {
        this(null, null, Thread.currentThread().getContextClassLoader());
    }

    protected final void setState(Bus.BusState state) {
        this.state = state;
    }

    @Override
    public void setId(String i) {
        this.id = i;
    }

    @Override
    public final <T> T getExtension(Class<T> extensionType) {
        Object obj = this.extensions.get(extensionType);
        if (obj == null) {
            if (this.missingExtensions.contains(extensionType)) {
                return null;
            }
            ConfiguredBeanLocator loc = (ConfiguredBeanLocator)this.extensions.get(ConfiguredBeanLocator.class);
            if (loc == null) {
                loc = this.createConfiguredBeanLocator();
            }
            if (loc != null) {
                obj = loc.getBeanOfType(extensionType.getName(), extensionType);
                if (obj != null) {
                    this.extensions.put(extensionType, obj);
                } else {
                    Collection<T> objs = loc.getBeansOfType(extensionType);
                    if (objs != null && !objs.isEmpty()) {
                        this.extensions.put(extensionType, objs.iterator().next());
                    }
                    obj = this.extensions.get(extensionType);
                }
            }
        }
        if (null != obj) {
            return extensionType.cast(obj);
        }
        this.missingExtensions.add(extensionType);
        return null;
    }

    @Override
    public boolean hasExtensionByName(String name) {
        for (Class<?> c : this.extensions.keySet()) {
            if (!name.equals(c.getName())) continue;
            return true;
        }
        ConfiguredBeanLocator loc = (ConfiguredBeanLocator)this.extensions.get(ConfiguredBeanLocator.class);
        if (loc == null) {
            loc = this.createConfiguredBeanLocator();
        }
        if (loc != null) {
            return loc.hasBeanOfName(name);
        }
        return false;
    }

    protected final synchronized ConfiguredBeanLocator createConfiguredBeanLocator() {
        ConfiguredBeanLocator loc = (ConfiguredBeanLocator)this.extensions.get(ConfiguredBeanLocator.class);
        if (loc == null) {
            loc = this.extensionManager;
            this.setExtension(loc, ConfiguredBeanLocator.class);
        }
        return loc;
    }

    @Override
    public final <T> void setExtension(T extension, Class<T> extensionType) {
        if (extension == null) {
            this.extensions.remove(extensionType);
            this.missingExtensions.add(extensionType);
        } else {
            this.extensions.put(extensionType, extension);
            this.missingExtensions.remove(extensionType);
        }
    }

    @Override
    public String getId() {
        return null == this.id ? "cxf" + Integer.toString(Math.abs(this.hashCode())) : this.id;
    }

    public void initialize() {
        this.setState(Bus.BusState.INITIALIZING);
        Collection<BusCreationListener> ls = this.getExtension(ConfiguredBeanLocator.class).getBeansOfType(BusCreationListener.class);
        for (BusCreationListener l : ls) {
            l.busCreated(this);
        }
        this.doInitializeInternal();
        BusLifeCycleManager lifeCycleManager = this.getExtension(BusLifeCycleManager.class);
        if (null != lifeCycleManager) {
            lifeCycleManager.initComplete();
        }
        this.setState(Bus.BusState.RUNNING);
    }

    protected void doInitializeInternal() {
        this.extensionManager.initialize();
        this.initializeFeatures();
    }

    protected void loadAdditionalFeatures() {
    }

    protected void initializeFeatures() {
        this.loadAdditionalFeatures();
        for (Feature f : this.features) {
            f.initialize(this);
        }
    }

    public void shutdown() {
        this.shutdown(true);
    }

    protected void destroyBeans() {
        this.extensionManager.destroyBeans();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(boolean wait) {
        if (this.state == Bus.BusState.SHUTTING_DOWN) {
            return;
        }
        BusLifeCycleManager lifeCycleManager = this.getExtension(BusLifeCycleManager.class);
        if (null != lifeCycleManager) {
            lifeCycleManager.preShutdown();
        }
        ExtensionManagerBus extensionManagerBus = this;
        synchronized (extensionManagerBus) {
            this.state = Bus.BusState.SHUTTING_DOWN;
        }
        this.destroyBeans();
        extensionManagerBus = this;
        synchronized (extensionManagerBus) {
            this.state = Bus.BusState.SHUTDOWN;
            this.notifyAll();
        }
        if (null != lifeCycleManager) {
            lifeCycleManager.postShutdown();
        }
        if (BusFactory.getDefaultBus(false) == this) {
            BusFactory.setDefaultBus(null);
        }
        BusFactory.clearDefaultBusForAnyThread(this);
    }

    @Override
    public Bus.BusState getState() {
        return this.state;
    }

    @Override
    public Collection<Feature> getFeatures() {
        return this.features;
    }

    @Override
    public synchronized void setFeatures(Collection<? extends Feature> features) {
        this.features.clear();
        this.features.addAll(features);
        if (this.state == Bus.BusState.RUNNING) {
            this.initializeFeatures();
        }
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Map<String, Object> map) {
        this.properties.clear();
        this.properties.putAll(map);
    }

    @Override
    public Object getProperty(String s2) {
        return this.properties.get(s2);
    }

    @Override
    public void setProperty(String s2, Object o) {
        if (o == null) {
            this.properties.remove(s2);
        } else {
            this.properties.put(s2, o);
        }
    }

    private static String getBusId(Map<String, Object> properties) {
        String busId;
        if (null != properties && null != (busId = (String)properties.get(BUS_ID_PROPERTY_NAME)) && !busId.isEmpty()) {
            return busId;
        }
        busId = SystemPropertyAction.getPropertyOrNull(BUS_ID_PROPERTY_NAME);
        if (null != busId && !busId.isEmpty()) {
            return busId;
        }
        return null;
    }

    public static interface ExtensionFinder {
        public <T> T findExtension(Class<T> var1);
    }
}

