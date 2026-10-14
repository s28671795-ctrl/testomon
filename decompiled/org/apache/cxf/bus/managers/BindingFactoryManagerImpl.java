/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.AbstractBindingFactory;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.helpers.CastUtils;

@NoJSR250Annotations(unlessNull={"bus"})
public final class BindingFactoryManagerImpl
implements BindingFactoryManager {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(BindingFactoryManagerImpl.class);
    Map<String, BindingFactory> bindingFactories;
    Set<String> failed = new CopyOnWriteArraySet<String>();
    Set<String> loaded = new CopyOnWriteArraySet<String>();
    Bus bus;

    public BindingFactoryManagerImpl() {
        this.bindingFactories = new ConcurrentHashMap<String, BindingFactory>(8, 0.75f, 4);
    }

    public BindingFactoryManagerImpl(Bus b) {
        this.bindingFactories = new ConcurrentHashMap<String, BindingFactory>(8, 0.75f, 4);
        this.setBus(b);
    }

    @Resource
    public void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.bus.setExtension(this, BindingFactoryManager.class);
        }
    }

    @Override
    public void registerBindingFactory(String name, BindingFactory factory2) {
        this.bindingFactories.put(name, factory2);
    }

    @Override
    public void unregisterBindingFactory(String name) {
        this.bindingFactories.remove(name);
    }

    @Override
    public BindingFactory getBindingFactory(String namespace) throws BusException {
        BindingFactory factory2 = this.bindingFactories.get(namespace);
        if (null == factory2) {
            if (!this.failed.contains(namespace)) {
                factory2 = this.loadActivationNamespace(namespace);
                if (factory2 == null) {
                    factory2 = this.loadDefaultNamespace(namespace);
                }
                if (factory2 == null) {
                    factory2 = this.loadAll(namespace);
                }
            }
            if (factory2 == null) {
                this.failed.add(namespace);
                throw new BusException(new Message("NO_BINDING_FACTORY_EXC", BUNDLE, namespace));
            }
        }
        return factory2;
    }

    private BindingFactory loadAll(final String namespace) {
        ConfiguredBeanLocator.BeanLoaderListener<BindingFactory> listener = new ConfiguredBeanLocator.BeanLoaderListener<BindingFactory>(){

            @Override
            public boolean beanLoaded(String name, BindingFactory bean) {
                block6: {
                    BindingFactoryManagerImpl.this.loaded.add(name);
                    if (BindingFactoryManagerImpl.this.bindingFactories.containsKey(namespace)) break block6;
                    if (bean instanceof AbstractBindingFactory) {
                        for (String ns : ((AbstractBindingFactory)bean).getActivationNamespaces()) {
                            BindingFactoryManagerImpl.this.registerBindingFactory(ns, bean);
                        }
                    } else {
                        try {
                            Method m4 = bean.getClass().getMethod("getActivationNamespace", new Class[0]);
                            Collection<String> c = CastUtils.cast((Collection)m4.invoke((Object)bean, new Object[0]));
                            for (String s2 : c) {
                                BindingFactoryManagerImpl.this.registerBindingFactory(s2, bean);
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
                return BindingFactoryManagerImpl.this.bindingFactories.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends BindingFactory> type) {
                return !BindingFactoryManagerImpl.this.bindingFactories.containsKey(namespace) && !BindingFactoryManagerImpl.this.loaded.contains(name);
            }
        };
        this.bus.getExtension(ConfiguredBeanLocator.class).loadBeansOfType(BindingFactory.class, listener);
        return this.bindingFactories.get(namespace);
    }

    private BindingFactory loadDefaultNamespace(final String namespace) {
        ConfiguredBeanLocator.BeanLoaderListener<BindingFactory> listener = new ConfiguredBeanLocator.BeanLoaderListener<BindingFactory>(){

            @Override
            public boolean beanLoaded(String name, BindingFactory bean) {
                BindingFactoryManagerImpl.this.loaded.add(name);
                return BindingFactoryManagerImpl.this.bindingFactories.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends BindingFactory> type) {
                if (!BindingFactoryManagerImpl.this.loaded.contains(name)) {
                    try {
                        Field f = type.getField("DEFAULT_NAMESPACES");
                        Object o = f.get(null);
                        if (o instanceof Collection) {
                            Collection c = CastUtils.cast((Collection)o);
                            return c.contains(namespace);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                return false;
            }
        };
        this.bus.getExtension(ConfiguredBeanLocator.class).loadBeansOfType(BindingFactory.class, listener);
        return this.bindingFactories.get(namespace);
    }

    private BindingFactory loadActivationNamespace(final String namespace) {
        final ConfiguredBeanLocator locator = this.bus.getExtension(ConfiguredBeanLocator.class);
        ConfiguredBeanLocator.BeanLoaderListener<BindingFactory> listener = new ConfiguredBeanLocator.BeanLoaderListener<BindingFactory>(){

            @Override
            public boolean beanLoaded(String name, BindingFactory bean) {
                BindingFactoryManagerImpl.this.loaded.add(name);
                return BindingFactoryManagerImpl.this.bindingFactories.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends BindingFactory> type) {
                if (BindingFactoryManagerImpl.this.loaded.contains(name)) {
                    return false;
                }
                try {
                    type.getField("DEFAULT_NAMESPACES");
                    return false;
                }
                catch (Exception exception) {
                    return locator.hasConfiguredPropertyValue(name, "activationNamespaces", namespace);
                }
            }
        };
        locator.loadBeansOfType(BindingFactory.class, listener);
        return this.bindingFactories.get(namespace);
    }
}

