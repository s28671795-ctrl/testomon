/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.Extension;
import org.apache.cxf.bus.extension.ExtensionException;
import org.apache.cxf.bus.extension.ExtensionManager;
import org.apache.cxf.bus.extension.ExtensionRegistry;
import org.apache.cxf.bus.extension.TextExtensionFragmentParser;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.resource.ObjectTypeResolver;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.SinglePropertyResolver;

public class ExtensionManagerImpl
implements ExtensionManager,
ConfiguredBeanLocator {
    public static final String EXTENSIONMANAGER_PROPERTY_NAME = "extensionManager";
    public static final String ACTIVATION_NAMESPACES_PROPERTY_NAME = "activationNamespaces";
    public static final String ACTIVATION_NAMESPACES_SETTER_METHOD_NAME = "setActivationNamespaces";
    public static final String BUS_EXTENSION_RESOURCE = "META-INF/cxf/bus-extensions.txt";
    private final ClassLoader loader;
    private ResourceManager resourceManager;
    private Map<String, Extension> all = new ConcurrentHashMap<String, Extension>();
    private List<Extension> ordered = new CopyOnWriteArrayList<Extension>();
    private final Map<Class<?>, Object> activated;
    private final Bus bus;

    public ExtensionManagerImpl(ClassLoader cl, Map<Class<?>, Object> initialExtensions, ResourceManager rm, Bus b) {
        this(new String[]{BUS_EXTENSION_RESOURCE}, cl, initialExtensions, rm, b);
    }

    public ExtensionManagerImpl(String resource, ClassLoader cl, Map<Class<?>, Object> initialExtensions, ResourceManager rm, Bus b) {
        this(new String[]{resource}, cl, initialExtensions, rm, b);
    }

    public ExtensionManagerImpl(String[] resources, ClassLoader cl, Map<Class<?>, Object> initialExtensions, ResourceManager rm, Bus b) {
        this.loader = cl;
        this.bus = b;
        this.activated = initialExtensions;
        this.resourceManager = rm;
        SinglePropertyResolver extensionManagerResolver = new SinglePropertyResolver(EXTENSIONMANAGER_PROPERTY_NAME, this);
        this.resourceManager.addResourceResolver(extensionManagerResolver);
        this.resourceManager.addResourceResolver(new ObjectTypeResolver(this));
        this.load(resources);
        for (Map.Entry<String, Extension> ext : ExtensionRegistry.getRegisteredExtensions().entrySet()) {
            if (this.all.containsKey(ext.getKey())) continue;
            this.all.put(ext.getKey(), ext.getValue());
            this.ordered.add(ext.getValue());
        }
    }

    final void load(String[] resources) {
        if (resources == null) {
            return;
        }
        try {
            for (String resource : resources) {
                this.load(resource);
            }
        }
        catch (IOException ex) {
            throw new ExtensionException(ex);
        }
    }

    public void add(Extension ex) {
        this.all.put(ex.getName(), ex);
        this.ordered.add(ex);
    }

    public void initialize() {
        for (Extension e : this.ordered) {
            if (e.isDeferred() || e.getLoadedObject() != null) continue;
            this.loadAndRegister(e);
        }
    }

    public void removeBeansOfNames(List<String> names) {
        for (String s2 : names) {
            Extension ex = this.all.remove(s2);
            if (ex == null) continue;
            this.ordered.remove(ex);
        }
    }

    @Override
    public void activateAll() {
        for (Extension e : this.ordered) {
            if (e.getLoadedObject() != null) continue;
            this.loadAndRegister(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> void activateAllByType(Class<T> type) {
        for (Extension e : this.ordered) {
            Class<?> cls;
            if (e.getLoadedObject() != null || (cls = e.getClassObject(this.loader)) == null || !type.isAssignableFrom(cls)) continue;
            Extension extension = e;
            synchronized (extension) {
                this.loadAndRegister(e);
            }
        }
    }

    @Override
    public boolean hasBeanOfName(String name) {
        return this.all.containsKey(name);
    }

    final void load(String resource) throws IOException {
        if (this.loader != this.getClass().getClassLoader()) {
            this.load(resource, this.getClass().getClassLoader());
        }
        this.load(resource, this.loader);
    }

    final synchronized void load(String resource, ClassLoader l) throws IOException {
        Enumeration<URL> urls2 = l.getResources(resource);
        while (urls2.hasMoreElements()) {
            final URL url2 = urls2.nextElement();
            try {
                InputStream is = AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>(){

                    @Override
                    public InputStream run() throws Exception {
                        return url2.openStream();
                    }
                });
                Throwable throwable = null;
                try {
                    List<Extension> exts = new TextExtensionFragmentParser(this.loader).getExtensions(is);
                    for (Extension e : exts) {
                        if (this.loader != l) {
                            e.classloader = l;
                        }
                        if (this.all.containsKey(e.getName())) continue;
                        this.all.put(e.getName(), e);
                        this.ordered.add(e);
                    }
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    is.close();
                }
            }
            catch (PrivilegedActionException pae) {
                throw (IOException)pae.getException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void loadAndRegister(Extension e) {
        Class<?> cls = null;
        cls = null != e.getInterfaceName() && !"".equals(e.getInterfaceName()) ? e.loadInterface(this.loader) : e.getClassObject(this.loader);
        if (null != this.activated && null != cls && null != this.activated.get(cls)) {
            return;
        }
        Extension extension = e;
        synchronized (extension) {
            Configurer configurer;
            Object obj = e.load(this.loader, this.bus);
            if (obj == null) {
                return;
            }
            if (null != this.activated && null != (configurer = (Configurer)this.activated.get(Configurer.class))) {
                configurer.configureBean(obj);
            }
            SinglePropertyResolver namespacesResolver = null;
            if (null != e.getNamespaces()) {
                namespacesResolver = new SinglePropertyResolver(ACTIVATION_NAMESPACES_PROPERTY_NAME, e.getNamespaces());
                this.resourceManager.addResourceResolver(namespacesResolver);
            }
            if (e.getNamespaces() != null && !e.getNamespaces().isEmpty()) {
                this.invokeSetterActivationNSMethod(obj, e.getNamespaces());
            }
            ResourceInjector injector = new ResourceInjector(this.resourceManager);
            try {
                injector.inject(obj);
                injector.construct(obj);
            }
            finally {
                if (null != namespacesResolver) {
                    this.resourceManager.removeResourceResolver(namespacesResolver);
                }
            }
            if (null != this.activated) {
                if (cls == null) {
                    cls = obj.getClass();
                }
                this.activated.put(cls, obj);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T getExtension(String name, Class<T> type) {
        Class<?> cls;
        if (name == null) {
            return null;
        }
        Extension e = this.all.get(name);
        if (e != null && (cls = e.getClassObject(this.loader)) != null && type.isAssignableFrom(cls)) {
            Extension extension = e;
            synchronized (extension) {
                if (e.getLoadedObject() == null) {
                    this.loadAndRegister(e);
                }
                return type.cast(e.getLoadedObject());
            }
        }
        return null;
    }

    private void invokeSetterActivationNSMethod(Object target, Object value) {
        String methodName = ACTIVATION_NAMESPACES_SETTER_METHOD_NAME;
        for (Class<?> clazz = target.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; ++i) {
                Class<?> paramType;
                Method method2 = methods[i];
                Class<?>[] params = method2.getParameterTypes();
                if (!method2.getName().equals(methodName) || params.length != 1 || !(paramType = params[0]).isInstance(value)) continue;
                try {
                    method2.invoke(target, value);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getBeanNamesOfType(Class<?> type) {
        LinkedList<String> ret = new LinkedList<String>();
        for (Extension ex : this.ordered) {
            Class<?> cls = ex.getClassObject(this.loader);
            if (cls == null || !type.isAssignableFrom(cls)) continue;
            Extension extension = ex;
            synchronized (extension) {
                ret.add(ex.getName());
            }
        }
        return ret;
    }

    @Override
    public <T> T getBeanOfType(String name, Class<T> type) {
        if (name == null) {
            return null;
        }
        Extension ex = this.all.get(name);
        if (ex != null) {
            if (ex.getLoadedObject() == null) {
                this.loadAndRegister(ex);
            }
            return type.cast(ex.getLoadedObject());
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> Collection<? extends T> getBeansOfType(Class<T> type) {
        Class<?> cls;
        LinkedList<T> ret = new LinkedList<T>();
        Extension ext = this.all.get(type.getName());
        if (ext != null && (cls = ext.getClassObject(this.loader)) != null && type.isAssignableFrom(cls)) {
            Extension extension = ext;
            synchronized (extension) {
                if (ext.getLoadedObject() == null) {
                    this.loadAndRegister(ext);
                }
                if (ext.getLoadedObject() != null) {
                    ret.add(type.cast(ext.getLoadedObject()));
                }
            }
        }
        for (Extension ex : this.ordered) {
            Class<?> cls2;
            if (ex == ext || (cls2 = ex.getClassObject(this.loader)) == null || !type.isAssignableFrom(cls2)) continue;
            Extension extension = ex;
            synchronized (extension) {
                if (ex.getLoadedObject() == null) {
                    this.loadAndRegister(ex);
                }
                if (ex.getLoadedObject() != null) {
                    ret.add(type.cast(ex.getLoadedObject()));
                }
            }
        }
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> boolean loadBeansOfType(Class<T> type, ConfiguredBeanLocator.BeanLoaderListener<T> listener) {
        boolean loaded = false;
        for (Extension ex : this.ordered) {
            Class<?> cls = ex.getClassObject(this.loader);
            if (cls == null || !type.isAssignableFrom(cls)) continue;
            Extension extension = ex;
            synchronized (extension) {
                if (listener.loadBean(ex.getName(), cls.asSubclass(type))) {
                    if (ex.getLoadedObject() == null) {
                        this.loadAndRegister(ex);
                    }
                    if (listener.beanLoaded(ex.getName(), type.cast(ex.getLoadedObject()))) {
                        return true;
                    }
                    loaded = true;
                }
            }
        }
        return loaded;
    }

    @Override
    public boolean hasConfiguredPropertyValue(String beanName, String propertyName, String value) {
        if (beanName == null) {
            return false;
        }
        Extension ex = this.all.get(beanName);
        return ex != null && ex.getNamespaces() != null && ex.getNamespaces().contains(value);
    }

    public void destroyBeans() {
        for (Extension ex : this.ordered) {
            if (ex.getLoadedObject() == null) continue;
            ResourceInjector injector = new ResourceInjector(this.resourceManager);
            injector.destroy(ex.getLoadedObject());
        }
    }
}

