/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.cxf.Bus;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.transport.AbstractTransportFactory;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.DestinationFactory;

public class TransportFinder<T> {
    Map<String, T> map;
    Set<String> loaded;
    Class<T> cls;
    ConfiguredBeanLocator locator;

    public TransportFinder(Bus b, Map<String, T> m4, Set<String> l, Class<T> c) {
        this.map = m4;
        this.cls = c;
        this.locator = b.getExtension(ConfiguredBeanLocator.class);
        this.loaded = l;
    }

    public T findTransportForNamespace(String namespace) {
        if (this.locator == null) {
            return null;
        }
        T factory2 = this.loadActivationNamespaces(namespace);
        if (factory2 == null) {
            factory2 = this.loadDefaultNamespace(namespace);
        }
        if (factory2 == null) {
            factory2 = this.loadNoDefaultNamespace(namespace);
        }
        if (factory2 == null) {
            this.loadAll();
            factory2 = this.map.get(namespace);
        }
        return factory2;
    }

    public T findTransportForURI(String uri) {
        T factory2;
        if (this.locator == null) {
            return null;
        }
        if (uri.startsWith("/") || uri.indexOf(58) < 0) {
            uri = "http://" + uri;
        }
        if ((factory2 = this.checkForURI(uri)) == null && (factory2 = this.loadDefaultURIs(uri)) == null) {
            this.loadAll();
            factory2 = this.checkForURI(uri);
        }
        return factory2;
    }

    private static Set<String> getPrefixes(Object t2) {
        Set<String> prefixes = null;
        if (t2 instanceof AbstractTransportFactory) {
            AbstractTransportFactory atf = (AbstractTransportFactory)t2;
            prefixes = atf.getUriPrefixes();
        } else if (t2 instanceof DestinationFactory) {
            DestinationFactory atf = (DestinationFactory)t2;
            prefixes = atf.getUriPrefixes();
        } else if (t2 instanceof ConduitInitiator) {
            ConduitInitiator atf = (ConduitInitiator)t2;
            prefixes = atf.getUriPrefixes();
        }
        return prefixes;
    }

    private boolean hasPrefix(String uri, Collection<String> prefixes) {
        if (prefixes == null) {
            return false;
        }
        for (String prefix : prefixes) {
            if (!uri.startsWith(prefix)) continue;
            return true;
        }
        return false;
    }

    public T checkForURI(String uri) {
        for (T t2 : this.map.values()) {
            if (!this.hasPrefix(uri, TransportFinder.getPrefixes(t2))) continue;
            return t2;
        }
        return null;
    }

    private void loadAll() {
        ConfiguredBeanLocator.BeanLoaderListener listener = new ConfiguredBeanLocator.BeanLoaderListener<T>(){

            @Override
            public boolean beanLoaded(String name, T bean) {
                TransportFinder.this.loaded.add(name);
                TransportFinder.this.registerBean(bean);
                return false;
            }

            @Override
            public boolean loadBean(String name, Class<? extends T> type) {
                return !TransportFinder.this.loaded.contains(name);
            }
        };
        this.locator.loadBeansOfType(this.cls, listener);
    }

    private void registerBean(T bean) {
        block5: {
            block4: {
                if (!(bean instanceof AbstractTransportFactory)) break block4;
                if (((AbstractTransportFactory)bean).getTransportIds() == null) break block5;
                for (String ns : ((AbstractTransportFactory)bean).getTransportIds()) {
                    if (this.map.containsKey(ns)) continue;
                    this.map.put(ns, bean);
                }
                break block5;
            }
            try {
                Method m4 = bean.getClass().getMethod("getActivationNamespaces", new Class[0]);
                Collection<String> c = CastUtils.cast((Collection)m4.invoke(bean, new Object[0]));
                for (String s2 : c) {
                    if (this.map.containsKey(s2)) continue;
                    this.map.put(s2, bean);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private T loadActivationNamespaces(final String namespace) {
        ConfiguredBeanLocator.BeanLoaderListener listener = new ConfiguredBeanLocator.BeanLoaderListener<T>(){

            @Override
            public boolean beanLoaded(String name, T bean) {
                TransportFinder.this.loaded.add(name);
                if (!TransportFinder.this.map.containsKey(namespace)) {
                    TransportFinder.this.registerBean(bean);
                }
                return TransportFinder.this.map.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends T> type) {
                return TransportFinder.this.locator.hasConfiguredPropertyValue(name, "transportIds", namespace);
            }
        };
        this.locator.loadBeansOfType(this.cls, listener);
        return this.map.get(namespace);
    }

    private T loadDefaultURIs(String uri) {
        URIBeanLoaderListener listener = new URIBeanLoaderListener(uri){

            @Override
            public boolean loadBean(String name, Class<? extends T> type) {
                try {
                    Field f = type.getField("DEFAULT_URIS");
                    Object o = f.get(null);
                    if (o instanceof Collection) {
                        Collection c = CastUtils.cast((Collection)o);
                        return TransportFinder.this.hasPrefix(this.uri, c);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return false;
            }
        };
        this.locator.loadBeansOfType(this.cls, listener);
        return listener.getFactory();
    }

    private T loadDefaultNamespace(final String namespace) {
        ConfiguredBeanLocator.BeanLoaderListener listener = new ConfiguredBeanLocator.BeanLoaderListener<T>(){

            @Override
            public boolean beanLoaded(String name, T bean) {
                TransportFinder.this.loaded.add(name);
                TransportFinder.this.registerBean(bean);
                return TransportFinder.this.map.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends T> type) {
                if (TransportFinder.this.loaded.contains(name)) {
                    return false;
                }
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
                return false;
            }
        };
        this.locator.loadBeansOfType(this.cls, listener);
        return this.map.get(namespace);
    }

    private T loadNoDefaultNamespace(final String namespace) {
        ConfiguredBeanLocator.BeanLoaderListener listener = new ConfiguredBeanLocator.BeanLoaderListener<T>(){

            @Override
            public boolean beanLoaded(String name, T bean) {
                TransportFinder.this.loaded.add(name);
                TransportFinder.this.registerBean(bean);
                return TransportFinder.this.map.containsKey(namespace);
            }

            @Override
            public boolean loadBean(String name, Class<? extends T> type) {
                if (TransportFinder.this.loaded.contains(name)) {
                    return false;
                }
                try {
                    type.getField("DEFAULT_NAMESPACES");
                    return false;
                }
                catch (Exception exception) {
                    return true;
                }
            }
        };
        this.locator.loadBeansOfType(this.cls, listener);
        return this.map.get(namespace);
    }

    abstract class URIBeanLoaderListener
    implements ConfiguredBeanLocator.BeanLoaderListener<T> {
        T factory;
        String uri;

        URIBeanLoaderListener(String u) {
            this.uri = u;
        }

        public T getFactory() {
            return this.factory;
        }

        @Override
        public boolean beanLoaded(String name, T bean) {
            TransportFinder.this.registerBean(bean);
            if (TransportFinder.this.hasPrefix(this.uri, TransportFinder.getPrefixes(bean))) {
                this.factory = bean;
                return true;
            }
            return false;
        }
    }
}

