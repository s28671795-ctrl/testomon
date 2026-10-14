/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ELContextCleaner
implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(ELContextCleaner.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Class beanELResolver = Loader.loadClass("jakarta.el.BeanELResolver");
            Field field2 = this.getField(beanELResolver);
            field2.setAccessible(true);
            this.purgeEntries(field2);
            if (LOG.isDebugEnabled()) {
                LOG.debug("javax.el.BeanELResolver purged");
            }
        }
        catch (ClassNotFoundException beanELResolver) {
        }
        catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
            LOG.warn("Cannot purge classes from javax.el.BeanELResolver", e);
        }
        catch (NoSuchFieldException e) {
            LOG.debug("Not cleaning cached beans: no such field javax.el.BeanELResolver.properties");
        }
    }

    protected Field getField(Class<?> beanELResolver) throws SecurityException, NoSuchFieldException {
        if (beanELResolver == null) {
            return null;
        }
        return beanELResolver.getDeclaredField("properties");
    }

    protected void purgeEntries(Field properties) throws IllegalArgumentException, IllegalAccessException {
        if (properties == null) {
            return;
        }
        Map map = (Map)properties.get(null);
        if (map == null) {
            return;
        }
        Iterator itor = map.keySet().iterator();
        while (itor.hasNext()) {
            Class clazz = (Class)itor.next();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Clazz: {} loaded by {}", (Object)clazz, (Object)clazz.getClassLoader());
            }
            if (Thread.currentThread().getContextClassLoader().equals(clazz.getClassLoader())) {
                itor.remove();
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("removed");
                continue;
            }
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug("not removed: contextclassloader={} clazz's classloader={}", (Object)Thread.currentThread().getContextClassLoader(), (Object)clazz.getClassLoader());
        }
    }
}

