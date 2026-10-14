/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceReference
 */
package org.apache.cxf.bus.osgi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class OSGiBeanLocator
implements ConfiguredBeanLocator {
    private static final Logger LOG = LogUtils.getL7dLogger(OSGiBeanLocator.class);
    private static final String COMPATIBLE_LOCATOR_PROP = "org.apache.cxf.bus.osgi.locator";
    private static final String COMPATIBLE_LOCATOR_PROP_CHECK = "org.apache.cxf.bus.osgi.locator.check";
    final ConfiguredBeanLocator cbl;
    final BundleContext context;
    private boolean checkCompatibleLocators;

    public OSGiBeanLocator(ConfiguredBeanLocator c, BundleContext ctx) {
        this.cbl = c;
        this.context = ctx;
        String checkProp = this.context.getProperty(COMPATIBLE_LOCATOR_PROP_CHECK);
        this.checkCompatibleLocators = checkProp == null || PropertyUtils.isTrue(checkProp);
    }

    @Override
    public <T> T getBeanOfType(String name, Class<T> type) {
        return this.cbl.getBeanOfType(name, type);
    }

    @Override
    public <T> Collection<? extends T> getBeansOfType(Class<T> type) {
        Collection<T> ret = this.cbl.getBeansOfType(type);
        if (ret == null || ret.isEmpty()) {
            return this.getBeansFromOsgiService(type);
        }
        return ret;
    }

    private <T> List<T> getBeansFromOsgiService(Class<T> type) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            ServiceReference[] refs = this.context.getServiceReferences(type.getName(), null);
            if (refs != null) {
                for (ServiceReference r : refs) {
                    if (type == ClassLoader.class && this.checkCompatibleLocators && !PropertyUtils.isTrue(r.getProperty(COMPATIBLE_LOCATOR_PROP))) continue;
                    list.add(type.cast(this.context.getService(r)));
                }
            }
        }
        catch (Exception ex) {
            LOG.info("Tried to find the Bean with type:" + type + " from OSGi services and get error: " + ex);
        }
        return list;
    }

    @Override
    public <T> boolean loadBeansOfType(Class<T> type, ConfiguredBeanLocator.BeanLoaderListener<T> listener) {
        return this.cbl.loadBeansOfType(type, listener);
    }

    @Override
    public boolean hasConfiguredPropertyValue(String beanName, String propertyName, String value) {
        return this.cbl.hasConfiguredPropertyValue(beanName, propertyName, value);
    }

    @Override
    public List<String> getBeanNamesOfType(Class<?> type) {
        return this.cbl.getBeanNamesOfType(type);
    }

    @Override
    public boolean hasBeanOfName(String name) {
        return this.cbl.hasBeanOfName(name);
    }
}

