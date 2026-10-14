/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ExtendedBeanMetadata
 *  org.apache.aries.blueprint.services.ExtendedBlueprintContainer
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceReference
 *  org.osgi.service.blueprint.container.BlueprintContainer
 *  org.osgi.service.blueprint.container.NoSuchComponentException
 *  org.osgi.service.blueprint.reflect.BeanMetadata
 *  org.osgi.service.blueprint.reflect.BeanProperty
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 */
package org.apache.cxf.bus.blueprint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;
import org.apache.aries.blueprint.ExtendedBeanMetadata;
import org.apache.aries.blueprint.services.ExtendedBlueprintContainer;
import org.apache.cxf.bus.extension.ExtensionManagerImpl;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.container.NoSuchComponentException;
import org.osgi.service.blueprint.reflect.BeanMetadata;
import org.osgi.service.blueprint.reflect.BeanProperty;
import org.osgi.service.blueprint.reflect.ComponentMetadata;

public class BlueprintBeanLocator
implements ConfiguredBeanLocator {
    private static final Logger LOG = LogUtils.getL7dLogger(BlueprintBeanLocator.class);
    ConfiguredBeanLocator orig;
    BlueprintContainer container;
    BundleContext context;

    public BlueprintBeanLocator(ConfiguredBeanLocator orig, BlueprintContainer cont, BundleContext context) {
        this.orig = orig;
        this.container = cont;
        this.context = context;
        if (orig instanceof ExtensionManagerImpl) {
            ArrayList<String> names = new ArrayList<String>(this.container.getComponentIds());
            ((ExtensionManagerImpl)orig).removeBeansOfNames(names);
        }
    }

    static Class<?> getClassForMetaData(BlueprintContainer container, ComponentMetadata cmd) {
        Class cls = null;
        if (cmd instanceof BeanMetadata) {
            BeanMetadata bm = (BeanMetadata)cmd;
            if (bm instanceof ExtendedBeanMetadata) {
                cls = ((ExtendedBeanMetadata)bm).getRuntimeClass();
            }
            if (cls == null && container instanceof ExtendedBlueprintContainer && bm.getClassName() != null) {
                try {
                    cls = ((ExtendedBlueprintContainer)container).loadClass(bm.getClassName());
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
        }
        return cls;
    }

    private Class<?> getClassForMetaData(ComponentMetadata cmd) {
        return BlueprintBeanLocator.getClassForMetaData(this.container, cmd);
    }

    private ComponentMetadata getComponentMetadata(String id) {
        try {
            return this.container.getComponentMetadata(id);
        }
        catch (NoSuchComponentException nsce) {
            return null;
        }
    }

    @Override
    public <T> T getBeanOfType(String name, Class<T> type) {
        ComponentMetadata cmd = this.getComponentMetadata(name);
        Class<?> cls = this.getClassForMetaData(cmd);
        if (cls != null && type.isAssignableFrom(cls)) {
            return type.cast(this.container.getComponentInstance(name));
        }
        return this.orig.getBeanOfType(name, type);
    }

    @Override
    public List<String> getBeanNamesOfType(Class<?> type) {
        LinkedHashSet<String> names = new LinkedHashSet<String>();
        for (String s2 : this.container.getComponentIds()) {
            ComponentMetadata cmd = this.container.getComponentMetadata(s2);
            Class<?> cls = this.getClassForMetaData(cmd);
            if (cls == null || !type.isAssignableFrom(cls)) continue;
            names.add(s2);
        }
        names.addAll(this.orig.getBeanNamesOfType(type));
        return new ArrayList<String>(names);
    }

    @Override
    public <T> Collection<? extends T> getBeansOfType(Class<T> type) {
        ArrayList<T> list = new ArrayList<T>();
        for (String s2 : this.container.getComponentIds()) {
            ComponentMetadata cmd = this.container.getComponentMetadata(s2);
            Class<?> cls = this.getClassForMetaData(cmd);
            if (cls == null || !type.isAssignableFrom(cls)) continue;
            list.add(type.cast(this.container.getComponentInstance(s2)));
        }
        if (list.isEmpty() && this.context != null) {
            try {
                ServiceReference[] refs = this.context.getServiceReferences(type.getName(), null);
                if (refs != null) {
                    for (ServiceReference r : refs) {
                        list.add(type.cast(this.context.getService(r)));
                    }
                }
            }
            catch (Exception ex) {
                LOG.info("Try to find the Bean with type:" + type + " from OSGi services and get error: " + ex);
            }
        }
        list.addAll(this.orig.getBeansOfType(type));
        return list;
    }

    @Override
    public <T> boolean loadBeansOfType(Class<T> type, ConfiguredBeanLocator.BeanLoaderListener<T> listener) {
        ComponentMetadata cmd;
        ArrayList<String> names = new ArrayList<String>();
        boolean loaded = false;
        for (String s2 : this.container.getComponentIds()) {
            cmd = this.container.getComponentMetadata(s2);
            Class<?> cls = this.getClassForMetaData(cmd);
            if (cls == null || !type.isAssignableFrom(cls)) continue;
            names.add(s2);
        }
        Collections.reverse(names);
        for (String s2 : names) {
            Class<?> beanType;
            Class<T> t2;
            if (!listener.loadBean(s2, t2 = (beanType = this.getClassForMetaData(cmd = this.container.getComponentMetadata(s2))).asSubclass(type))) continue;
            Object o = this.container.getComponentInstance(s2);
            if (listener.beanLoaded(s2, type.cast(o))) {
                return true;
            }
            loaded = true;
        }
        try {
            ServiceReference[] refs;
            if (this.context != null && (refs = this.context.getServiceReferences(type.getName(), null)) != null) {
                for (ServiceReference r : refs) {
                    Object o2 = this.context.getService(r);
                    Class<T> t3 = o2.getClass().asSubclass(type);
                    if (!listener.loadBean(t3.getName(), t3)) continue;
                    if (listener.beanLoaded(t3.getName(), type.cast(o2))) {
                        return true;
                    }
                    loaded = true;
                }
            }
        }
        catch (Exception ex) {
            LOG.info("Try to find the Bean with type:" + type + " from OSGi services and get error: " + ex);
        }
        return this.orig.loadBeansOfType(type, listener) || loaded;
    }

    @Override
    public boolean hasConfiguredPropertyValue(String beanName, String propertyName, String value) {
        ComponentMetadata cmd = this.getComponentMetadata(beanName);
        if (cmd instanceof BeanMetadata) {
            BeanMetadata br = (BeanMetadata)cmd;
            for (BeanProperty s2 : br.getProperties()) {
                if (!propertyName.equals(s2.getName())) continue;
                return true;
            }
            return false;
        }
        return this.orig.hasConfiguredPropertyValue(beanName, propertyName, value);
    }

    @Override
    public boolean hasBeanOfName(String name) {
        ComponentMetadata cmd = this.getComponentMetadata(name);
        if (cmd instanceof BeanMetadata) {
            return true;
        }
        return this.orig.hasBeanOfName(name);
    }
}

