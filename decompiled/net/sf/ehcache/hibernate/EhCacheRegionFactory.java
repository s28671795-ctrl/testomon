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
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.hibernate.AbstractEhcacheRegionFactory;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheRegionFactory
extends AbstractEhcacheRegionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(EhCacheRegionFactory.class);

    public EhCacheRegionFactory(Properties prop) {
    }

    public void start(Settings settings, Properties properties) throws org.hibernate.cache.CacheException {
        this.settings = settings;
        if (this.manager != null) {
            LOG.warn("Attempt to restart an already started EhCacheRegionFactory. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Using previously created EhCacheRegionFactory. If this behaviour is required, consider using SingletonEhCacheRegionFactory.");
            return;
        }
        try {
            String configurationResourceName = null;
            if (properties != null) {
                configurationResourceName = (String)properties.get("net.sf.ehcache.configurationResourceName");
            }
            if (configurationResourceName == null || configurationResourceName.length() == 0) {
                Configuration configuration = ConfigurationFactory.parseConfiguration();
                this.manager = new CacheManager(configuration);
            } else {
                URL url2;
                try {
                    url2 = new URL(configurationResourceName);
                }
                catch (MalformedURLException e) {
                    url2 = EhCacheRegionFactory.loadResource(configurationResourceName);
                }
                Configuration configuration = HibernateUtil.loadAndCorrectConfiguration(url2);
                this.manager = new CacheManager(HibernateUtil.overwriteCacheManagerIfConfigured(configuration, properties));
            }
            this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
        }
        catch (CacheException e) {
            if (e.getMessage().startsWith("Cannot parseConfiguration CacheManager. Attempt to create a new instance of CacheManager using the diskStorePath")) {
                throw new org.hibernate.cache.CacheException("Attempt to restart an already started EhCacheRegionFactory. Use sessionFactory.close() between repeated calls to buildSessionFactory. Consider using SingletonEhCacheRegionFactory. Error from ehcache was: " + e.getMessage());
            }
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }

    public void stop() {
        try {
            if (this.manager != null) {
                this.mbeanRegistrationHelper.unregisterMBean();
                this.manager.shutdown();
                this.manager = null;
            }
        }
        catch (CacheException e) {
            throw new org.hibernate.cache.CacheException((Throwable)e);
        }
    }
}

