/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.Mergeable
 *  org.springframework.beans.PropertyValue
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apache.cxf.bus.spring;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.ExtensionManagerImpl;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.springframework.beans.Mergeable;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringBeanLocator
implements ConfiguredBeanLocator {
    private static final Logger LOG = LogUtils.getL7dLogger(SpringBeanLocator.class);
    ApplicationContext context;
    ConfiguredBeanLocator orig;
    Set<String> passThroughs = new HashSet<String>();
    Object bundleContext;
    boolean osgi = true;

    public SpringBeanLocator(ApplicationContext ctx) {
        this(ctx, null);
    }

    public SpringBeanLocator(ApplicationContext ctx, Bus bus) {
        this.context = ctx;
        if (bus != null) {
            this.orig = bus.getExtension(ConfiguredBeanLocator.class);
            if (this.orig instanceof ExtensionManagerImpl) {
                ArrayList<String> names = new ArrayList<String>();
                for (String s2 : ctx.getBeanDefinitionNames()) {
                    names.add(s2);
                    for (String s22 : ctx.getAliases(s2)) {
                        names.add(s22);
                    }
                }
                ((ExtensionManagerImpl)this.orig).removeBeansOfNames(names);
            }
        }
        this.loadOSGIContext(bus);
    }

    private void loadOSGIContext(Bus b) {
        this.bundleContext = this.findBundleContext(this.context, b);
        if (this.bundleContext == null) {
            this.osgi = false;
        }
    }

    private Object findBundleContext(ApplicationContext applicationContext, Bus b) {
        Object answer = null;
        for (ApplicationContext aContext = applicationContext; aContext != null && answer == null; aContext = aContext.getParent()) {
            answer = this.getBundleContext(aContext, b);
        }
        return answer;
    }

    private Object getBundleContext(ApplicationContext applicationContext, Bus b) {
        try {
            Method m4 = applicationContext.getClass().getMethod("getBundleContext", new Class[0]);
            Object o = m4.invoke((Object)applicationContext, new Object[0]);
            if (o != null && b != null) {
                Class<?> cls = m4.getReturnType();
                b.setExtension(o, cls);
            }
            return o;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    @Override
    public <T> T getBeanOfType(String name, Class<T> type) {
        T t2 = null;
        try {
            t2 = type.cast(this.context.getBean(name, type));
        }
        catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
            // empty catch block
        }
        if (t2 == null) {
            t2 = this.orig.getBeanOfType(name, type);
        }
        return t2;
    }

    @Override
    public List<String> getBeanNamesOfType(Class<?> type) {
        LinkedHashSet<String> s2 = new LinkedHashSet<String>(Arrays.asList(this.context.getBeanNamesForType(type, false, false)));
        s2.removeAll(this.passThroughs);
        s2.addAll(this.orig.getBeanNamesOfType(type));
        return new ArrayList<String>(s2);
    }

    @Override
    public <T> Collection<? extends T> getBeansOfType(Class<T> type) {
        LinkedHashSet<String> s2 = new LinkedHashSet<String>(Arrays.asList(this.context.getBeanNamesForType(type, false, false)));
        s2.removeAll(this.passThroughs);
        LinkedList<T> lst = new LinkedList<T>();
        for (String n : s2) {
            lst.add(type.cast(this.context.getBean(n, type)));
        }
        lst.addAll(this.orig.getBeansOfType(type));
        if (lst.isEmpty()) {
            this.tryOSGI(lst, type);
        }
        return lst;
    }

    private <T> void tryOSGI(Collection<T> lst, Class<T> type) {
        if (!this.osgi) {
            return;
        }
        try {
            Class<?> contextClass = this.findContextClass(this.bundleContext.getClass());
            Method m4 = contextClass.getMethod("getServiceReference", String.class);
            ReflectionUtil.setAccessible(m4);
            Object o = m4.invoke(this.bundleContext, type.getName());
            if (o != null) {
                m4 = contextClass.getMethod("getService", m4.getReturnType());
                ReflectionUtil.setAccessible(m4);
                o = m4.invoke(this.bundleContext, o);
                lst.add(type.cast(o));
            }
        }
        catch (NoSuchMethodException e) {
            this.osgi = false;
        }
        catch (Throwable e) {
            LOG.log(Level.WARNING, "Could not get service for " + type.getName(), e);
        }
    }

    private Class<?> findContextClass(Class<?> cls) {
        for (Class<?> c : cls.getInterfaces()) {
            if (!"org.osgi.framework.BundleContext".equals(c.getName())) continue;
            return c;
        }
        for (Class<?> c : cls.getInterfaces()) {
            Class<?> c2 = this.findContextClass(c);
            if (c2 == null) continue;
            return c2;
        }
        Class<?> c2 = this.findContextClass(cls.getSuperclass());
        if (c2 != null) {
            return c2;
        }
        return cls;
    }

    @Override
    public <T> boolean loadBeansOfType(Class<T> type, ConfiguredBeanLocator.BeanLoaderListener<T> listener) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(this.context.getBeanNamesForType(type, false, false)));
        list.removeAll(this.passThroughs);
        Collections.reverse(list);
        boolean loaded = false;
        for (String s2 : list) {
            Class beanType;
            Class<T> t2;
            if (!listener.loadBean(s2, t2 = (beanType = this.context.getType(s2)).asSubclass(type))) continue;
            Object o = this.context.getBean(s2);
            if (listener.beanLoaded(s2, type.cast(o))) {
                return true;
            }
            loaded = true;
        }
        return loaded || this.orig.loadBeansOfType(type, listener);
    }

    @Override
    public boolean hasConfiguredPropertyValue(String beanName, String propertyName, String searchValue) {
        if (this.context.containsBean(beanName) && !this.passThroughs.contains(beanName)) {
            ConfigurableApplicationContext ctxt = (ConfigurableApplicationContext)this.context;
            BeanDefinition def = ctxt.getBeanFactory().getBeanDefinition(beanName);
            if (!ctxt.getBeanFactory().isSingleton(beanName) || def.isAbstract()) {
                return false;
            }
            Collection ids = null;
            PropertyValue pv = def.getPropertyValues().getPropertyValue(propertyName);
            if (pv != null) {
                Object value = pv.getValue();
                if (!(value instanceof Collection)) {
                    throw new RuntimeException("The property " + propertyName + " must be a collection!");
                }
                if (value instanceof Mergeable) {
                    if (!((Mergeable)value).isMergeEnabled()) {
                        ids = (Collection)value;
                    }
                } else {
                    ids = (Collection)value;
                }
            }
            if (ids != null) {
                for (Object o : ids) {
                    if (!(o instanceof TypedStringValue ? searchValue.equals(((TypedStringValue)o).getValue()) : searchValue.equals(o))) continue;
                    return true;
                }
            }
        }
        return this.orig.hasConfiguredPropertyValue(beanName, propertyName, searchValue);
    }

    public <T> List<T> getOSGiServices(Class<T> type) {
        ArrayList<T> lst = new ArrayList<T>();
        if (!this.osgi) {
            return lst;
        }
        Class<?> contextClass = this.findContextClass(this.bundleContext.getClass());
        try {
            Method m4 = contextClass.getMethod("getServiceReference", String.class);
            Class<?> servRefClass = m4.getReturnType();
            m4 = contextClass.getMethod("getServiceReferences", String.class, String.class);
            Object o = ReflectionUtil.setAccessible(m4).invoke(this.bundleContext, type.getName(), null);
            if (o != null) {
                m4 = contextClass.getMethod("getService", servRefClass);
                ReflectionUtil.setAccessible(m4);
                for (int x = 0; x < Array.getLength(o); ++x) {
                    Object ref = Array.get(o, x);
                    Object o2 = m4.invoke(this.bundleContext, ref);
                    if (o2 == null) continue;
                    lst.add(type.cast(o2));
                }
            }
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (Throwable e) {
            e.printStackTrace();
            LOG.log(Level.FINE, "Could not get services for " + type.getName(), e);
        }
        return lst;
    }

    @Override
    public boolean hasBeanOfName(String name) {
        if (this.context.containsBean(name)) {
            return true;
        }
        return this.orig.hasBeanOfName(name);
    }
}

