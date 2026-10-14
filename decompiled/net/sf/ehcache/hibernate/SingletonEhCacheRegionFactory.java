/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.hibernate.AbstractEhcacheRegionFactory;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonEhCacheRegionFactory
extends AbstractEhcacheRegionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SingletonEhCacheRegionFactory.class);
    private static final AtomicInteger REFERENCE_COUNT = new AtomicInteger();

    public SingletonEhCacheRegionFactory(Properties prop) {
    }

    public void start(Settings settings, Properties properties) throws org.hibernate.cache.CacheException {
        try {
            String configurationResourceName = null;
            if (properties != null) {
                configurationResourceName = (String)properties.get("net.sf.ehcache.configurationResourceName");
            }
            if (configurationResourceName == null || configurationResourceName.length() == 0) {
                this.manager = CacheManager.create();
                REFERENCE_COUNT.incrementAndGet();
            } else {
                URL url2;
                try {
                    url2 = new URL(configurationResourceName);
                }
                catch (MalformedURLException e) {
                    if (!configurationResourceName.startsWith("/")) {
                        configurationResourceName = "/" + configurationResourceName;
                        LOG.debug("prepending / to {}. It should be placed in the root of the classpath rather than in a package.", (Object)configurationResourceName);
                    }
                    url2 = SingletonEhCacheRegionFactory.loadResource(configurationResourceName);
                }
                Configuration configuration = HibernateUtil.loadAndCorrectConfiguration(url2);
                this.manager = CacheManager.create(HibernateUtil.overwriteCacheManagerIfConfigured(configuration, properties));
                REFERENCE_COUNT.incrementAndGet();
            }
            this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void stop() {
        try {
            if (this.manager != null) {
                if (REFERENCE_COUNT.decrementAndGet() == 0) {
                    this.manager.shutdown();
                }
                this.manager = null;
            }
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }
}

